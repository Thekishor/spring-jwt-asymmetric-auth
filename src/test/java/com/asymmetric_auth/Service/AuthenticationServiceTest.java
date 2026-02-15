package com.asymmetric_auth.Service;

import com.asymmetric_auth.dto.request.AuthenticationRequest;
import com.asymmetric_auth.dto.request.RegistrationRequest;
import com.asymmetric_auth.dto.response.UserResponse;
import com.asymmetric_auth.entities.Role;
import com.asymmetric_auth.entities.User;
import com.asymmetric_auth.entities.UserMapper;
import com.asymmetric_auth.repository.RoleRepository;
import com.asymmetric_auth.repository.UserRepository;
import com.asymmetric_auth.service.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create test case for user service")
class AuthenticationServiceImplTest {

    @Mock
    UserMapper userMapper;
    @Mock
    RoleRepository roleRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    private RegistrationRequest registrationRequest;
    private AuthenticationRequest authenticationRequest;
    private Role role;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        this.registrationRequest = RegistrationRequest.builder()
                .firstName("Kishor")
                .lastName("Pandey")
                .email("kishorpandey9811@gmail.com")
                .phoneNumber("9876436213")
                .password("Kishor93737@@##")
                .confirmPassword("Kishor93737@@##")
                .build();

        this.authenticationRequest = AuthenticationRequest.builder()
                .email("kishorpandey9811@gmail.com")
                .password("Kishor93737@@##")
                .build();

        this.role = Role.builder()
                .name("ROLE_USER")
                .id(UUID.randomUUID())
                .createdBy("Kishor")
                .build();

        this.user = User.builder()
                .firstName("Kishor")
                .lastName("Pandey")
                .email("kishorpandey9811@gmail.com")
                .phoneNumber("9876436213")
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(true)
                .phoneVerified(true)
                .build();

        this.userResponse = UserResponse.builder()
                .firstName("Kishor")
                .lastName("Pandey")
                .email("kishorpandey9811@gmail.com")
                .phoneNumber("9876436213")
                .enabled(true)
                .build();
    }

    @Nested
    @DisplayName("Create user auth test")
    class CreateUserServiceAuthenticationTest {

        @Test
        @DisplayName("Should register successfully")
        void userRegisterShouldRegisterSuccessfully() {
            // When
            when(userRepository.existsByEmailIgnoreCase(registrationRequest.getEmail()))
                    .thenReturn(false);
            when(userRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber()))
                    .thenReturn(false);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.ofNullable(role));
            when(userMapper.toUser(registrationRequest)).thenReturn(user);

            final List<Role> roles = new ArrayList<>();
            roles.add(role);

            user.setPassword(passwordEncoder.encode("Kishor93737@@##"));
            user.setRoles(roles);

            when(userRepository.save(user)).thenReturn(user);

            final List<User> users = new ArrayList<>();
            users.add(user);
            role.setUsers(users);

            when(roleRepository.save(role)).thenReturn(role);
            when(userMapper.userResponse(user)).thenReturn(userResponse);

            final UserResponse savedResponse = authenticationService.register(registrationRequest);

            // Then
            assertEquals(registrationRequest.getEmail(), user.getEmail());
            assertNotNull(savedResponse);
        }
    }
}
