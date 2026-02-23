package com.algaworks.algashop.product.catalog.presentation;

import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryManagementService;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryQueryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryQueryService categoryQueryService;
  private final CategoryManagementService categoryManagementService;

  @GetMapping
  public PageModel<CategoryDetailOutput> filter(@RequestParam(defaultValue = "0") Integer size,
                                                @RequestParam(defaultValue = "10") Integer page) {
    return categoryQueryService.filter(size, page);
  }

  @GetMapping("/{categoryId}")
  public CategoryDetailOutput findById(@PathVariable UUID categoryId) {
    return categoryQueryService.findById(categoryId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDetailOutput create(@RequestBody @Valid CategoryInput input) {
    UUID categoryId = categoryManagementService.create(input);
    return categoryQueryService.findById(categoryId);
  }

  @PutMapping("/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryDetailOutput update(@PathVariable UUID categoryId,
                                     @RequestBody @Valid CategoryInput input) {
    categoryManagementService.update(categoryId, input);
    return categoryQueryService.findById(categoryId);
  }

  @DeleteMapping("/{categoryId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void disable(@PathVariable UUID categoryId) {
    categoryManagementService.disable(categoryId);
  }




}
