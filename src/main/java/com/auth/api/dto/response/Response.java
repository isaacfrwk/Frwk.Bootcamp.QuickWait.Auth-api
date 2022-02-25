package com.auth.api.dto.response;

import lombok.Data;

public class Response {
    private Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    @Data
    public static class JwtResponse extends Response {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String username;
        private String email;

        public JwtResponse(String accessToken, Long id, String username, String email, Integer status) {
            this.token = accessToken;
            this.id = id;
            this.username = username;
            this.email = email;
            super.setStatus(status);
        }
    }

    @Data
    public static class MessageResponse extends Response {
        private String message;

        public  MessageResponse(String message, Integer status) {
            this.message = message;
            super.setStatus(status);
        }
    }
}
