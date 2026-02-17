package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.ChangedPasswordRequest;
import com.asymmetric_auth.dto.request.ProfileUpdateRequest;
import com.asymmetric_auth.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void updateProfileInfo(ProfileUpdateRequest request, UUID userId);

    void changePassword(ChangedPasswordRequest request, UUID userId);

    void deactivateAccount(UUID userId);

    void reactivatedAccount(UUID userId);

    void deleteAccount(UUID userId);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID userId);
}
