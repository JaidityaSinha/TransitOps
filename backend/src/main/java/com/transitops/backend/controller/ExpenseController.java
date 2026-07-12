package com.transitops.backend.controller;

import com.transitops.backend.entity.Expense;
import com.transitops.backend.service.ExpenseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCIAL_ANALYST','FLEET_MANAGER')")
    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCIAL_ANALYST','FLEET_MANAGER')")
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return service.getExpenseById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCIAL_ANALYST')")
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return service.saveExpense(expense);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCIAL_ANALYST')")
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @RequestBody Expense expense) {
        return service.updateExpense(id, expense);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
    }
}