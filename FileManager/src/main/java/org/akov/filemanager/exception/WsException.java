package org.akov.filemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class WsException extends ResponseStatusException {
    public WsException(HttpStatus code, String message) {
        super(code, message);
    }
}
