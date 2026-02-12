package com.asymmetric_auth.controller;

import com.asymmetric_auth.dto.request.ChangedPasswordRequest;
import com.asymmetric_auth.dto.request.ProfileUpdateRequest;
import com.asymmetric_auth.entities.User;
import com.asymmetric_auth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfileInfo(
            @Valid
            @RequestBody final ProfileUpdateRequest request,
            final Authentication authentication
    ) {
        this.userService.updateProfileInfo(request, getUserId(authentication));
    }

    @PostMapping("/changePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @Valid
            @RequestBody final ChangedPasswordRequest request,
            final Authentication authentication
    ) {
        this.userService.changePassword(request, getUserId(authentication));
    }

    @PatchMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateAccount(
            final Authentication authentication
    ) {
        this.userService.deactivateAccount(getUserId(authentication));
    }

    @PatchMapping("/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateAccount(
            final Authentication authentication
    ) {
        this.userService.reactivatedAccount(getUserId(authentication));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            final Authentication authentication
    ) {
        this.userService.deleteAccount(getUserId(authentication));
    }

    private String getUserId(Authentication authentication) {
        return ((User) Objects.requireNonNull(authentication.getPrincipal())).getId();
    }
}
