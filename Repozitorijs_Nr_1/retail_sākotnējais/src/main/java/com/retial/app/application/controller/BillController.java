package com.retial.app.application.controller;

import com.retial.app.domain.entity.Bill;
import com.retial.app.domain.entity.User;
import com.retial.app.domain.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bill/")
@AllArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping("calculate")
    public double calculateNetPrice(@RequestBody User user, @RequestBody Bill bill) {
        return billService.calculateNetPrice(user, bill);
    }
}
