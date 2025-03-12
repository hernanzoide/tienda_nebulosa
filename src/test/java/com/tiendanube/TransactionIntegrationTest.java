package com.tiendanube;

import com.tiendanube.dto.TransactionDTO;
import com.tiendanube.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private TransactionDTO transaction;

    @BeforeEach
    public void setup() {

        transaction = TransactionDTO.builder()
                .id("transaction-id")
                .value(new BigDecimal("90"))
                .description("Test transaction")
                .method(PaymentMethod.DEBIT_CARD.getName())
                .cardNumber("1234567890123456")
                .cardHolderName("John Doe")
                .cardExpirationDate("12/25")
                .cardCvv("123")
                .build();
    }

    @Test
    public void testCreateTransaction() {
        ResponseEntity<TransactionDTO> response = restTemplate.postForEntity("/transactions", transaction, TransactionDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        TransactionDTO createdTransaction = response.getBody();
        assert createdTransaction != null;

        assertEquals(transaction.getValue(), createdTransaction.getValue());
        assertEquals(transaction.getDescription(), createdTransaction.getDescription());
        assertEquals(transaction.getMethod(), createdTransaction.getMethod());
    }

    @Test
    public void testCreateTransactionConcurrent() throws InterruptedException, ExecutionException {
        int numberOfThreads = 10;  //thread count
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        List<Future<ResponseEntity<TransactionDTO>>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(() -> {
                //thread code
                try {
                    TransactionDTO transaction = TransactionDTO.builder()
                            .description("Test Description")
                            .value(BigDecimal.valueOf(100))
                            .method("credit_card")
                            .build();

                    return restTemplate.postForEntity("/transactions", transaction, TransactionDTO.class);
                } finally {
                    // decrease count
                    latch.countDown();
                }
            }));
        }

        latch.await();

        for (Future<ResponseEntity<TransactionDTO>> future : futures) {
            ResponseEntity<TransactionDTO> response = future.get();

            assertEquals(HttpStatus.CREATED, response.getStatusCode());

            TransactionDTO createdTransaction = response.getBody();
            assert createdTransaction != null;

            // asserts
            assertEquals(BigDecimal.valueOf(100), createdTransaction.getValue());
            assertEquals("Test Description", createdTransaction.getDescription());
            assertEquals("credit_card", createdTransaction.getMethod());
        }

        executor.shutdown();
    }
}

