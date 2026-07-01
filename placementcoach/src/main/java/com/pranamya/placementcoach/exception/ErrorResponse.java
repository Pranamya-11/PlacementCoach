package com.pranamya.placementcoach.exception;

import java.time.LocalDateTime;

public class ErrorResponse  {
    private boolean success;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponse(boolean success,String message,int status,LocalDateTime timestamp){
        this.success=success;
        this.message=message;
        this.status=status;
        this.timestamp=timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
