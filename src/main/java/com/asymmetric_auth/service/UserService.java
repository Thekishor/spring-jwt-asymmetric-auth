package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.ChangedPasswordRequest;
import com.asymmetric_auth.dto.request.ProfileUpdateRequest;

import java.util.UUID;

public interface UserService {

    void updateProfileInfo(ProfileUpdateRequest request, UUID userId);

    void changePassword(ChangedPasswordRequest request, UUID userId);

    void deactivateAccount(UUID userId);

    void reactivatedAccount(UUID userId);

    void deleteAccount(UUID userId);
}
