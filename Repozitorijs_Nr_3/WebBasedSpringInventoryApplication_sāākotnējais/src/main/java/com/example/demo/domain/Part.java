package com.example.demo.domain;

import com.example.demo.validators.ValidDeletePart;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.AssertTrue;

@Entity
@ValidDeletePart
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="part_type", discriminatorType = DiscriminatorType.INTEGER)
@Table(name="Parts")
public abstract class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    @Min(value = 0, message = "Price value must be positive")
    double price;

    @Min(value = 0, message = "Inventory value must be positive")
    int inv;

    @Min(value = 0, message = "Minimum inventory value must be positive")
    @Column(name = "min_inventory")
    int minInventory;

    @Min(value = 0, message = "Maximum inventory value must be positive")
    @Column(name = "max_inventory")
    int maxInventory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_parts",
        joinColumns = @JoinColumn(name = "part_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public Part() {}

    public Part(String name, double price, int inv, int min, int max) {
        this.name = name;
        this.price = price;
        this.minInventory = min;
        this.maxInventory = max;
        setInv(inv); // apply validation
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
    this.inv = inv;
    }	

    public int getMinInventory() {
        return minInventory;
    }

    public void setMinInventory(int minInventory) {
        this.minInventory = minInventory;
    }

    public int getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(int maxInventory) {
        this.maxInventory = maxInventory;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setMin(int min) {
        this.minInventory = min;
    }

    public void setMax(int max) {
        this.maxInventory = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;
        Part part = (Part) o;
        return Double.compare(part.price, price) == 0 &&
               inv == part.inv &&
               minInventory == part.minInventory &&
               maxInventory == part.maxInventory &&
               Objects.equals(name, part.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, inv, minInventory, maxInventory);
    }

    @Override
    public String toString() {
        return name;
    }
}
