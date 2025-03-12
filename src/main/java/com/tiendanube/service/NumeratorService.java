package com.tiendanube.service;

import com.tiendanube.client.NumeratorClient;
import com.tiendanube.client.LockNumeratorRequestDTO;
import com.tiendanube.client.SetNumeratorRequestDTO;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NumeratorService {

    private final NumeratorClient numeratorClient;

    @Value("${numerator.api.max-retries}")
    private int maxRetries;

    public NumeratorService(NumeratorClient numeratorClient) {
        this.numeratorClient = numeratorClient;
    }

    public String generateUniqueId() {
        try {
            int currentRetries = maxRetries;

            while (currentRetries > 0) {
                try {
                    numeratorClient.lockNumerator(new LockNumeratorRequestDTO(5000));
                    break;
                } catch (FeignException fe) {
                    if (currentRetries == 1) {
                        throw new Exception("Failed to generate unique id", fe);
                    }
                    log.error("currentRetries: {} error: {} ", currentRetries, fe.getMessage(), fe);
                    currentRetries--;
                }
            }

            //we could also do this with max retries
            long currentNumerator = numeratorClient.getNumerator().getNumerator()+1;

            do {
                try {
                    numeratorClient.setNumerator(SetNumeratorRequestDTO.builder().value(currentNumerator).build());
                    log.info("CurrentNumerator: {}", currentNumerator);
                } catch (FeignException fe) {
                    if (currentRetries == 1) {
                        throw new Exception("Failed to generate unique id", fe);
                    }
                    log.error("currentRetries: {} error: {} ", currentRetries, fe.getMessage(), fe);
                    currentRetries--;
                }
                finally {
                    numeratorClient.unlockNumerator();
                    //could add more error handling in case can't unlock.
                }
            } while (currentNumerator == -1 && currentRetries > 0);

            if (currentNumerator == -1 || currentRetries == 0) {
                throw new Exception("Max retries exhausted.");
            }

            log.info("Generated new unique id: {}", currentNumerator);
            return String.valueOf(currentNumerator);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to generate unique id", e);
        }
    }
}