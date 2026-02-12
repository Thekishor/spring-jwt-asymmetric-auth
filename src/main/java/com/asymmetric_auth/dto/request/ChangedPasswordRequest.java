package com.asymmetric_auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangedPasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
