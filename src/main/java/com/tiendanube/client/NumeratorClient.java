package com.tiendanube.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "numerator-client", url = "${numerator.api.url}")
public interface NumeratorClient {

    @GetMapping("/numerator")
    GetNumeratorResponseDTO getNumerator();

    @PutMapping("/numerator")
    void setNumerator(@RequestBody SetNumeratorRequestDTO setNumeratorRequestDTO);

    @PutMapping("/numerator/test-and-set")
    TestAndSetNumeratorResponse testAndSetNumerator(@RequestBody TestAndSetNumeratorRequestDTO testAndSetNumeratorRequestDTO);

    @PostMapping("/numerator/lock")
    void lockNumerator(@RequestBody LockNumeratorRequestDTO lockNumeratorRequestDTO);

    @DeleteMapping("/numerator/lock")
    void unlockNumerator();
}

