package com.retial.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;
    private String name;
    private double price;
    private boolean isGrocery;

}
