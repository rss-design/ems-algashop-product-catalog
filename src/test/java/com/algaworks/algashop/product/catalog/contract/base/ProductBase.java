package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.product.query.PageModel;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutput;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutputTestDataBuilder;
import com.algaworks.algashop.product.catalog.application.product.query.ProductQueryService;
import com.algaworks.algashop.product.catalog.presentation.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ProductController.class)
public class ProductBase {

  @Autowired
  private WebApplicationContext context;

  @MockitoBean
  private ProductQueryService productQueryService;

  @MockitoBean
  private ProductManagementApplicationService productManagementApplicationService;

  public static final UUID validProductId = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");
  public static final UUID invalidProductId = UUID.fromString("21651a12-b126-4213-ac21-19f66ff4642e");
  public static final UUID createdProductId = UUID.fromString("f7c6843f-465c-476d-9a9b-4783bde4dc5e");
  public static final UUID updatedProductId = UUID.fromString("019c714f-68a7-7bdc-9608-892941fc2f1e");
  public static final UUID updatedNotFoundProductId = UUID.fromString("019c714f-68a7-7bdc-9608-892941fc2f1e");
  public static final UUID deletedNotFoundProductId = UUID.fromString("19c70e7-d858-7462-96f6-d8741729ec67");

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
      .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build());

    RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

    mockValidProductFindById();
    mockFilterProducts();
    mockCreateProduct();
    mockInvalidProductFindById();
    mockUpdateProduct();
    mockUpdateNotFoundProduct();
    mockDeleteProduct();
    mockDeleteNotFoundProduct();
  }

  private void mockInvalidProductFindById() {
    Mockito.when(productQueryService.findById(invalidProductId))
      .thenThrow(new ResourceNotFoundException());
  }

  private void mockCreateProduct() {
    Mockito.when(productManagementApplicationService.create(Mockito.any(ProductInput.class)))
      .thenReturn(createdProductId);

    Mockito.when(productQueryService.findById(createdProductId))
      .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().inStock(false).build());
  }

  private void mockFilterProducts() {
    Mockito.when(productQueryService.filter(
        Mockito.anyInt(), Mockito.anyInt()))
      .then((answer)-> {
        Integer size = answer.getArgument(0);

        return PageModel.<ProductDetailOutput>builder()
          .number(0)
          .size(size)
          .totalPages(1)
          .totalElements(2)
          .content(
            List.of(
              ProductDetailOutputTestDataBuilder.aProduct().build(),
              ProductDetailOutputTestDataBuilder.aProductAlt1().build()
            )
          ).build();
      });
  }

  private void mockValidProductFindById() {
    Mockito.when(productQueryService.findById(validProductId))
      .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().id(validProductId).build());
  }

  private void mockUpdateProduct() {
    Mockito.doNothing().when(productManagementApplicationService)
      .update(any(UUID.class), any(ProductInput.class));

    Mockito.when(productQueryService.findById(updatedProductId))
      .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().id(updatedProductId).build());
  }

  private void mockUpdateNotFoundProduct() {
    Mockito.doThrow(new ResourceNotFoundException())
      .when(productManagementApplicationService)
      .update(eq(updatedNotFoundProductId), any(ProductInput.class));
  }

  private void mockDeleteProduct() {
    Mockito.doNothing().when(productManagementApplicationService).disable(any(UUID.class));
  }

  private void mockDeleteNotFoundProduct() {
    Mockito.doThrow(new ResourceNotFoundException())
      .when(productManagementApplicationService)
      .disable(eq(deletedNotFoundProductId));
  }

}
