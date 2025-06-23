package com.ifrs.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.dto.dashboard.DashboardBalanceDTO;
import com.ifrs.financeapp.dto.dashboard.DashboardCategoriesDTO;
import com.ifrs.financeapp.dto.dashboard.DashboardChartDTO;
import com.ifrs.financeapp.dto.dashboard.DashboardDataDTO;
import com.ifrs.financeapp.dto.dashboard.DashboardExpenseDTO;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDataDTO> getAllData(@AuthenticationPrincipal User user) {
        Long userId = user.getId();

        DashboardBalanceDTO balance = dashboardService.getCurrentBalance(userId);
        DashboardExpenseDTO expense = dashboardService.getTotalExpense(userId);
        DashboardCategoriesDTO categories = dashboardService.findTopExpenseCategoryByMonth(userId);
        List<DashboardChartDTO> charts = dashboardService.getDataChartMonth(userId);

        return ResponseEntity.ok().body(new DashboardDataDTO(balance, expense, categories, charts));
    }

    @GetMapping("/balance")
    public ResponseEntity<DashboardBalanceDTO> getBalance(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        return ResponseEntity.ok().body(dashboardService.getCurrentBalance(userId));
    }

    @GetMapping("/total-expenses")
    public ResponseEntity<DashboardExpenseDTO> getExpenses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(dashboardService.getTotalExpense(user.getId()));
    }

}
