package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.AuthenticationRequest;
import com.asymmetric_auth.dto.request.RefreshRequest;
import com.asymmetric_auth.dto.request.RegistrationRequest;
import com.asymmetric_auth.dto.response.AuthenticationResponse;
import com.asymmetric_auth.dto.response.UserResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    UserResponse register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
