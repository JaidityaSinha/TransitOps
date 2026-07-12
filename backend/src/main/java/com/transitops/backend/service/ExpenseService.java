package com.transitops.backend.service;

import com.transitops.backend.entity.Expense;
import com.transitops.backend.exception.ResourceNotFoundException;
import com.transitops.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));
    }

    public Expense saveExpense(Expense expense) {
        return repository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        expense.setTrip(updatedExpense.getTrip());

        // NEW DESIGN
        expense.setExpenseType(updatedExpense.getExpenseType());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());

        return repository.save(expense);
    }

    public void deleteExpense(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        repository.delete(expense);
    }
}