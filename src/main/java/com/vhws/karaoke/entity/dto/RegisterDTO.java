package com.vhws.karaoke.entity.dto;

import com.vhws.karaoke.entity.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}