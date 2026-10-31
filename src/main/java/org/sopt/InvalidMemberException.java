package org.sopt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMemberException extends RuntimeException {
    public InvalidMemberException(String message) { super(message); }
}
