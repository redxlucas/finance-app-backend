package com.ifrs.financeapp.model.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.transaction.TransactionType;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String color;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactionList = new ArrayList<>();
}
