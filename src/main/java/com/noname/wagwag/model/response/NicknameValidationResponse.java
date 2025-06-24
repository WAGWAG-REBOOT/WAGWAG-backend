package com.noname.wagwag.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 닉네임 중복 검사 API 응답 DTO
 * {
 * "isExist": true | false
 * }
 */
public record NicknameValidationResponse(boolean isExist) {}
