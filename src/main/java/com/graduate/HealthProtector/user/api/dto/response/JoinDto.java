package com.graduate.HealthProtector.user.api.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String email;
}
