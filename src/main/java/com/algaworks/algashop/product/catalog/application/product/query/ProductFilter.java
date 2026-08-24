package com.algaworks.algashop.product.catalog.application.product.query;

import com.algaworks.algashop.product.catalog.application.utility.SortablePageFilter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductFilter extends SortablePageFilter<ProductFilter.SortType> {

    private String term;

    private Boolean hasDiscount;

    private Boolean enabled;

    private Boolean inStock;

    private BigDecimal priceFrom;
    private BigDecimal priceTo;

    private UUID[] categoriesId;

    private OffsetDateTime addedAtFrom;
    private OffsetDateTime addedAtTo;

    @Override
    public SortType getSortByPropertyOrDefault() {
      return getSortByProperty() == null ? SortType.ADDED_AT : getSortByProperty();
    }

    @Override
    public Sort.Direction getSortDirectionOrDefault() {
      return getSortDirection() == null ? Sort.Direction.ASC : getSortDirection();
    }

    @Getter
    @RequiredArgsConstructor
    public enum SortType {
        ADDED_AT("addedAt"),
        SALE_PRICE("salePrice");

        private final String propertyName;
    }
}