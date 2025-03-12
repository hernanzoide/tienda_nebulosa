package com.tiendanube.service;

import com.tiendanube.dto.ReceivableDTO;
import com.tiendanube.model.PaymentMethod;
import com.tiendanube.model.Receivable;
import com.tiendanube.model.ReceivableStatus;
import com.tiendanube.respository.ReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReceivableService {

    @Autowired
    ReceivableRepository receivableRepository;

    @Autowired
    NumeratorService numeratorService;

    public Receivable createReceivable(PaymentMethod method, BigDecimal value) {
        String receivableId = numeratorService.generateUniqueId();

        BigDecimal discount = getDiscount(method, value);

        Receivable receivable = Receivable.builder()
                .id(receivableId)
                .total(value.subtract(discount))
                .createDate(LocalDateTime.now())
                .discount(discount)
                .subtotal(value)
                .status(getStatus(method))
                .build();

        return receivableRepository.save(receivable);
    }

    private BigDecimal getDiscount(PaymentMethod method, BigDecimal value) {
        return PaymentMethod.CREDIT_CARD.equals(method) ?
                value.multiply(new BigDecimal("4")).divide(new BigDecimal("100")) :
                value.multiply(new BigDecimal("2")).divide(new BigDecimal("100"));
    }

    private ReceivableStatus getStatus(PaymentMethod method) {
        return PaymentMethod.CREDIT_CARD.equals(method) ?  ReceivableStatus.PAID : ReceivableStatus.WAITING_FUNDS;
    }

    public ReceivableDTO createReceivable(ReceivableDTO receivableDTO) {
        String receivableId = numeratorService.generateUniqueId();

        Receivable receivable = Receivable.builder()
                .id(receivableId)
                .total(receivableDTO.getTotal())
                .createDate(LocalDateTime.now())
                .discount(receivableDTO.getDiscount())
                .subtotal(receivableDTO.getSubtotal())
                .status(ReceivableStatus.fromValue(receivableDTO.getStatus()))
                .build();

        return toDto(receivableRepository.save(receivable));
    }

    public Optional<ReceivableDTO> findById(String id) {
        return receivableRepository.findById(id).map(this::toDto);
    }

    private ReceivableDTO toDto(Receivable receivable) {
        return ReceivableDTO.builder()
                .id(receivable.getId())
                .total(receivable.getTotal())
                .status(receivable.getStatus().getName())
                .subtotal(receivable.getSubtotal())
                .createDate(receivable.getCreateDate())
                .discount(receivable.getDiscount())
                .build();
    }

    public List<ReceivableDTO> findAll() {
        return receivableRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public boolean deleteById(String id) {
        if (receivableRepository.existsById(id)) {
            receivableRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
