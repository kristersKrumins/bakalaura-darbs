package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddProductController {

    @Autowired
    private PartService partService;

    @Autowired
    private ProductService productService;

    @GetMapping("/products/add")
    public String showFormAddProduct(Model theModel) {
        Product product = new Product();  // Create a new product per request

        List<Part> allParts = partService.findAll();
        List<Part> availParts = new ArrayList<>();
        for (Part p : allParts) {
            if (!product.getParts().contains(p)) {
                availParts.add(p);
            }
        }

        theModel.addAttribute("product", product);
        theModel.addAttribute("parts", allParts);
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product.getParts());

        return "add-product-form";
    }
}
