package com.algaworks.algashop.product.catalog.presentation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailOutput {
  private UUID id;
  private OffsetDateTime addedAt;
  private String name;
  private String brand;
  private BigDecimal regularPrice;
  private BigDecimal salePrice;
  private Boolean inStock;
  private Boolean enabled;
  private UUID categoryId;
  private String description;
}
