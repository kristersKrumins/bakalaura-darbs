package com.example.demo.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Entity
@DiscriminatorValue("2")
public class OutsourcedPart extends Part {
    private String companyName;

    @Min(value = 1, message = "Inventory must be greater than or equal to the minimum")
    private Integer testMin;

    @Max(value = 1000, message = "Inventory must be less than or equal to the maximum")
    private Integer testMax;

    public OutsourcedPart() {}

    public OutsourcedPart(String name, double price, int inv, int minInventory, int maxInventory, String companyName) {
        super(name, price, inv, minInventory, maxInventory);
        this.companyName = companyName;
        this.testMin = minInventory;
        this.testMax = maxInventory;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getTestMin() {
        return testMin != null ? testMin : 0;
    }

    public void setTestMin(Integer value) {
        this.testMin = value;
    }

    public Integer getTestMax() {
        return testMax != null ? testMax : 1000;
    }

    public void setTestMax(Integer value) {
        this.testMax = value;
    }
}
