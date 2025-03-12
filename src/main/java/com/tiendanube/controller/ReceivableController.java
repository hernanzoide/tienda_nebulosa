package com.tiendanube.controller;

import com.tiendanube.dto.ReceivableDTO;
import com.tiendanube.service.ReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receivables")
public class ReceivableController {

    @Autowired
    ReceivableService receivableService;

    @GetMapping
    public List<ReceivableDTO> getAllReceivables() {
        return receivableService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceivableDTO> getReceivableById(@PathVariable String id) {
        Optional<ReceivableDTO> receivable = receivableService.findById(id);
        return receivable.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReceivableDTO> createReceivable(@RequestBody ReceivableDTO receivable) {
        return new ResponseEntity<>(receivableService.createReceivable(receivable), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceivable(@PathVariable String id) {
        if (receivableService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
