package com.ifrs.financeapp.service;

import com.ifrs.financeapp.dto.TransactionRequestDTO;
import com.ifrs.financeapp.dto.TransactionResponseDTO;
import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.*;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.repository.CategoryRepository;
import com.ifrs.financeapp.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TransactionRequestDTO createRequestDTO(boolean fixed) {
        return new TransactionRequestDTO(
                BigDecimal.valueOf(100.50),
                LocalDate.of(2025, 6, 20),
                "Compra no mercado",
                TransactionType.EXPENSE,
                CurrencyType.BRL,
                fixed ? RecurrenceType.FIXED : RecurrenceType.VARIABLE,
                1L,
                fixed ? FixedRecurrencePeriodType.MONTHLY : null,
                fixed ? 15 : null,
                fixed ? LocalDate.of(2025, 12, 31) : null);
    }

    @Test
    void testSave_withFixedRecurrence() {
        User user = new User();
        user.setId(1L);

        TransactionRequestDTO dto = createRequestDTO(true);

        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        TransactionResponseDTO response = transactionService.save(dto, user);

        assertEquals("Compra no mercado", response.description());
        assertEquals(BigDecimal.valueOf(100.50), response.amount());
        assertEquals(RecurrenceType.FIXED, response.recurrenceType());

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testSave_categoryNotFound() {
        User user = new User();
        user.setId(1L);

        TransactionRequestDTO dto = createRequestDTO(false);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            transactionService.save(dto, user);
        });

        assertTrue(ex.getMessage().contains("Categoria não encontrada"));
    }

    @Test
    void testGetAll() {
        User user = new User();
        user.setId(1L);
        user.setLogin("lucas@email.com");

        Category category = new Category();
        category.setId(1L);
        category.setName("Alimentação");
        category.setColor("#FF5733");
        category.setType(TransactionType.EXPENSE);

        Pageable pageable = PageRequest.of(0, 10);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setDescription("Transação Teste");
        transaction.setCategory(category); // 🔧 ESSENCIAL para evitar o NPE

        when(transactionRepository.findAllByUser(eq(user), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(transaction)));

        Page<TransactionResponseDTO> page = transactionService.getAll(pageable, user);

        assertEquals(1, page.getTotalElements());
        assertEquals("Transação Teste", page.getContent().get(0).description());
    }

    @Test
    void testDeleteTransactionByIdAndUser_success() {
        User user = new User();
        user.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(100L);
        transaction.setUser(user);

        when(transactionRepository.findById(100L)).thenReturn(Optional.of(transaction));

        boolean deleted = transactionService.deleteTransactionByIdAndUser(100L, user);

        assertTrue(deleted);
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void testDeleteTransactionByIdAndUser_fail_differentUser() {
        User user = new User();
        user.setId(1L);

        User anotherUser = new User();
        anotherUser.setId(2L);

        Transaction transaction = new Transaction();
        transaction.setId(100L);
        transaction.setUser(anotherUser);

        when(transactionRepository.findById(100L)).thenReturn(Optional.of(transaction));

        boolean deleted = transactionService.deleteTransactionByIdAndUser(100L, user);

        assertFalse(deleted);
        verify(transactionRepository, never()).delete(any());
    }

    @Test
    void testDeleteTransactionByIdAndUser_notFound() {
        User user = new User();
        user.setId(1L);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean deleted = transactionService.deleteTransactionByIdAndUser(999L, user);

        assertFalse(deleted);
    }
}
