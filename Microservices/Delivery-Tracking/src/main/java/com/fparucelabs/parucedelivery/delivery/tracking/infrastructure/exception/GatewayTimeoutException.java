package com.fparucelabs.parucedelivery.delivery.tracking.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutException extends RuntimeException {

    public GatewayTimeoutException() {
    }

    public GatewayTimeoutException(Throwable cause) {
        super(cause);
    }
}
