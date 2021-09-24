package com.example.pegawai;

public class KantorResponse {
    private String message;

    public KantorResponse() {
    }

    public KantorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}