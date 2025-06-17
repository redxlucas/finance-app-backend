package com.ifrs.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.dto.dashboard.DashboardBalanceDTO;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/balance")
    public ResponseEntity<DashboardBalanceDTO> getBalance(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        return ResponseEntity.ok().body(dashboardService.getCurrentBalance(userId));
    }

    // public ResponseEntity<DashboardExpensesDTO> getExpenses() {
    // return dashboardService.getTotalExpenses();
    // }

}
