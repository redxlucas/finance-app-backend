package com.ifrs.financeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.TransactionRequestDTO;
import com.ifrs.financeapp.dto.TransactionResponseDTO;
import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.RecurrenceType;
import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.repository.CategoryRepository;
import com.ifrs.financeapp.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public TransactionResponseDTO save(TransactionRequestDTO transactionRequestDTO, User user) {
        Category category = categoryRepository.findById(transactionRequestDTO.categoryId())
                .orElseThrow(
                        () -> new RuntimeException("Categoria não encontrada: " + transactionRequestDTO.categoryId()));

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.amount());
        transaction.setTransactionDate(transactionRequestDTO.transactionDate());
        transaction.setTransactionType(transactionRequestDTO.transactionType());
        transaction.setRecurrenceType(transactionRequestDTO.recurrenceType());
        transaction.setCurrency(transactionRequestDTO.currency());
        transaction.setDescription(transactionRequestDTO.description());
        transaction.setCategory(category);
        transaction.setUser(user);

        if (transaction.getRecurrenceType() == RecurrenceType.FIXED) {
            transaction.setFixedRecurrencePeriodType(transactionRequestDTO.fixedRecurrencePeriodType());
            transaction.setRecurrenceDayOfMonth(transactionRequestDTO.recurrenceDayOfMonth());
            transaction.setRecurrenceEndDate(transactionRequestDTO.recurrenceEndDate());
        }

        transactionRepository.save(transaction);

        return new TransactionResponseDTO(transaction);
    }

    public Page<TransactionResponseDTO> getAll(Pageable pageable, User user) {
        return transactionRepository.findAllByUser(user, pageable).map(TransactionResponseDTO::new);
    }

    @Transactional
    public boolean deleteTransactionByIdAndUser(Long transactionId, User user) {
        return transactionRepository.findById(transactionId)
                .filter(transaction -> transaction.getUser().getId().equals(user.getId()))
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return true;
                })
                .orElse(false);
    }
}
