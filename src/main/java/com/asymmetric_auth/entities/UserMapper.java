package com.asymmetric_auth.entities;

import com.asymmetric_auth.dto.request.ProfileUpdateRequest;
import com.asymmetric_auth.dto.request.RegistrationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public void mergerUserInfo(final User user, final ProfileUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getFirstName()) &&
                !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNoneBlank(request.getLastName()) &&
                !user.getLastName().equals(request.getLastName())) {
            user.setFirstName(request.getLastName());
        }

        if (request.getDateOfBirth() != null &&
                !request.getDateOfBirth().equals(user.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
    }

    public User toUser(RegistrationRequest request) {
        return User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(true)
                .phoneVerified(true)
                .build();
    }
}
