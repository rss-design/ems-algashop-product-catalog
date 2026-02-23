package com.algaworks.algashop.product.catalog.application.category.query;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailOutput {
  private UUID id;
  private String name;
  private Boolean enabled;
}
