package com.asymmetric_auth.service;

import com.asymmetric_auth.dto.request.AuthenticationRequest;
import com.asymmetric_auth.dto.request.RefreshRequest;
import com.asymmetric_auth.dto.request.RegistrationRequest;
import com.asymmetric_auth.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
