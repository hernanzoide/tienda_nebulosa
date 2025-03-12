package com.tiendanube.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetNumeratorRequestDTO {

    private long value;
}
