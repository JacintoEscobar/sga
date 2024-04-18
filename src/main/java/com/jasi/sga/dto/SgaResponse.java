package com.jasi.sga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SgaResponse {
    private Meta meta;
    private Object data;
}
