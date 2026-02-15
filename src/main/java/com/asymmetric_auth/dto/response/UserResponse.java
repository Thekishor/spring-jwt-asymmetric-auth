package com.asymmetric_auth.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean enabled;
}
