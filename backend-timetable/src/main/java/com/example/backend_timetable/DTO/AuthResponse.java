package com.example.backend_timetable.DTO;


public class AuthResponse {
    
    private boolean isError;   
    private String message;    
    private String uid;        

    public AuthResponse(boolean isError, String message, String uid) {
        this.isError = isError;
        this.message = message;
        this.uid = uid;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "isError=" + isError +
                ", message='" + message + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
