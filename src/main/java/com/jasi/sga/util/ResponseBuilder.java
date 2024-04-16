package com.jasi.sga.util;

import com.jasi.sga.dto.Meta;
import com.jasi.sga.dto.SgaResponse;
import com.jasi.sga.err.SgaError;

public abstract interface ResponseBuilder {
    public static SgaResponse buildSuccessResponse(String message, Object data) {
        Meta meta = new Meta(TimeUtils.getTimestamp(), message);
        return new SgaResponse(meta, data);
    }

    public static SgaResponse buildErrorResponse(String message, int errorCode, String errorMessage) {
        Meta meta = new Meta(TimeUtils.getTimestamp(), message);
        return new SgaResponse(meta, new SgaError(errorCode, errorMessage));
    }
}
