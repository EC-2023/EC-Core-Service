package src.config.filter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionResponse {
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    int statusCode;
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd"));
    String message;
    String path;
    String error;
}
