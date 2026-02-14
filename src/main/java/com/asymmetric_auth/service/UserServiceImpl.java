package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.ChangedPasswordRequest;
import com.asymmetric_auth.dto.request.ProfileUpdateRequest;
import com.asymmetric_auth.entities.User;
import com.asymmetric_auth.entities.UserMapper;
import com.asymmetric_auth.exception.BusinessException;
import com.asymmetric_auth.exception.ErrorCode;
import com.asymmetric_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void updateProfileInfo(final ProfileUpdateRequest request, final UUID userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));
        this.userMapper.mergerUserInfo(user, request);
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(final ChangedPasswordRequest request, final UUID userId) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }

        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if (!this.passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        if (request.getNewPassword().equals(request.getCurrentPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        final String encoded = this.passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encoded);
        this.userRepository.save(user);
    }

    @Override
    public void deactivateAccount(final UUID userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if (!user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    public void reactivatedAccount(final UUID userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if (user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(final UUID userId) {
        //
    }
}
