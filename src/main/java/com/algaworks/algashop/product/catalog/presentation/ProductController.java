package com.algaworks.algashop.product.catalog.presentation;

import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutput;
import com.algaworks.algashop.product.catalog.application.product.query.ProductQueryService;
import com.algaworks.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductQueryService productQueryService;
  private final ProductManagementApplicationService productManagementApplicationService;;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDetailOutput create(@RequestBody @Valid ProductInput input) {
    UUID productId;
    try {
      productId = productManagementApplicationService.create(input);
    } catch (CategoryNotFoundException e) {
      throw new UnprocessableContentException(e.getMessage(), e);
    }
    return productQueryService.findById(productId);
  }

  @GetMapping("/{productId}")
  public ProductDetailOutput findById(@PathVariable UUID productId) {
    return productQueryService.findById(productId);
 }

  @GetMapping
  public PageModel<ProductDetailOutput> filter(
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "number", required = false) Integer number
  ) {
    return productQueryService.filter(size, number);
  }

  @PutMapping("/{productId}")
  public ProductDetailOutput update(@PathVariable UUID productId,
                                    @RequestBody @Valid ProductInput input) {
      productManagementApplicationService.update(productId,input);
      return productQueryService.findById(productId);
  }

  @DeleteMapping("/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID productId) {
        productManagementApplicationService.disable(productId);
    }

}