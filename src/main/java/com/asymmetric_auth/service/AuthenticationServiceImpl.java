package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.AuthenticationRequest;
import com.asymmetric_auth.dto.request.RefreshRequest;
import com.asymmetric_auth.dto.request.RegistrationRequest;
import com.asymmetric_auth.dto.response.AuthenticationResponse;
import com.asymmetric_auth.entities.Role;
import com.asymmetric_auth.entities.User;
import com.asymmetric_auth.entities.UserMapper;
import com.asymmetric_auth.exception.BusinessException;
import com.asymmetric_auth.exception.ErrorCode;
import com.asymmetric_auth.repository.RoleRepository;
import com.asymmetric_auth.repository.UserRepository;
import com.asymmetric_auth.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authenticate.getPrincipal();

        if (customUserDetails == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        final String token =
                this.jwtService.generateAccessToken(customUserDetails.getUsername());
        final String refreshToken =
                this.jwtService.generateRefreshToken(customUserDetails.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        this.roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken =
                this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkUserEmail(String email) {
        final boolean existsByEmail = this.userRepository.existsByEmailIgnoreCase(email);
        if (existsByEmail) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean existsByPhone = this.userRepository.existsByPhoneNumber(phoneNumber);
        if (existsByPhone) {
            throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.MISMATCH_PASSWORD);
        }
    }
}
