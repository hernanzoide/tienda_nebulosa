package com.tiendanube.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestAndSetNumeratorRequestDTO {

    private long oldValue;
    private long newValue;
}
