package com.ren.blog.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
