package com.transitops.backend.entity;

import com.transitops.backend.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 500)
    private String description;
}