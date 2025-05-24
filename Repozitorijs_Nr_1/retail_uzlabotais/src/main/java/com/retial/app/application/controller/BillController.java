package com.retial.app.application.controller;

import com.retial.app.domain.entity.Bill;
import com.retial.app.domain.entity.User;
import com.retial.app.domain.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/calculate")
    public double calculate(@RequestBody BillRequest request) {
        return billService.calculateNetPrice(request.user(), request.bill());
    }

    public record BillRequest(User user, Bill bill) { }
}
