package com.tiendanube.service;

import com.tiendanube.dto.TransactionDTO;
import com.tiendanube.model.PaymentMethod;
import com.tiendanube.model.Receivable;
import com.tiendanube.model.Transaction;
import com.tiendanube.respository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private NumeratorService numeratorService;

    @Autowired
    private ReceivableService receivableService;

    @Autowired
    private TransactionRepository transactionRepository;


    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransaction(String id) {
        return transactionRepository.findById(id).map(this::toDto);
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Receivable receivable = receivableService.createReceivable(PaymentMethod.fromValue(dto.getMethod()), dto.getValue());

        String transactionId = numeratorService.generateUniqueId();

        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .value(dto.getValue())
                .cardCvv(dto.getCardCvv())
                .cardNumber(dto.getCardNumber())
                .cardExpirationDate(dto.getCardExpirationDate())
                .cardHolderName(dto.getCardHolderName())
                .method(PaymentMethod.fromValue(dto.getMethod()))
                .description(dto.getDescription())
                .receivable(receivable)
                .build();

        return toDto(transactionRepository.save(transaction));
    }

    public boolean deleteTransaction(String id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TransactionDTO toDto(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .cardExpirationDate(transaction.getCardExpirationDate())
                .cardNumber(transaction.getCardNumber())
                .cardCvv(transaction.getCardCvv())
                .cardHolderName(transaction.getCardHolderName())
                .description(transaction.getDescription())
                .method(transaction.getMethod().getName())
                .value(transaction.getValue())
                .build();
    }
}
