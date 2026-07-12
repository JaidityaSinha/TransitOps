package com.transitops.backend.repository;

import com.transitops.backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    Double getTotalExpenses();

}