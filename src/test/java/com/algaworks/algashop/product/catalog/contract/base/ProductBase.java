package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.presentation.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(ProductController.class)
public class ProductBase {

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
      .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
      .build());

    RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
  }

}
