package com.retial.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    private Long id;
    private List<Item> items;

    public double getTotalAmount() {
    if (items == null) return 0.0;
    return items.stream().mapToDouble(Item::getPrice).sum();
}

public double getTotalAmountExcludingGroceries() {
    if (items == null) return 0.0;
    return items.stream()
        .filter(item -> !item.isGrocery())
        .mapToDouble(Item::getPrice)
        .sum();
}


}
