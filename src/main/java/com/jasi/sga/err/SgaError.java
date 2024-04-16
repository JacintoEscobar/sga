package com.jasi.sga.err;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SgaError {
    private int code;
    private String message;
}
