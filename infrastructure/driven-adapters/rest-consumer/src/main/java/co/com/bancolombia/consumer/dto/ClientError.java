package co.com.bancolombia.consumer.dto;

public record ClientError(String error, String status, String detail) {
    public ClientError(String error, String status, String detail) {
        this.error = error;
        this.status = status;
        this.detail = detail;
    }
}
