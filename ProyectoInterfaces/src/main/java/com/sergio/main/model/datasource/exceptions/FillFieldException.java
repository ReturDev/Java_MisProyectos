package com.sergio.main.model.datasource.exceptions;

import java.io.IOException;

public class FillFieldException extends IOException {

    public FillFieldException() {
    }

    public FillFieldException(String message) {
        super(message);
    }
}
