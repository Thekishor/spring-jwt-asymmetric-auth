package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.ChangedPasswordRequest;
import com.asymmetric_auth.dto.request.ProfileUpdateRequest;

public interface UserService {

    void updateProfileInfo(ProfileUpdateRequest request, String userId);

    void changePassword(ChangedPasswordRequest request, String userId);

    void deactivateAccount(String userId);

    void reactivatedAccount(String userId);

    void deleteAccount(String userId);
}
