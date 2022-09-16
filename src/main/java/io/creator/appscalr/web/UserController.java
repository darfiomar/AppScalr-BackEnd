package io.creator.appscalr.web;


import io.creator.appscalr.dto.LogoutResponse;
import io.creator.appscalr.dto.RegisterRequest;
import io.creator.appscalr.dto.StatsResponse;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.RefreshTokenService;
import io.creator.appscalr.service.serviceimpl.Userserviceimpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final Userserviceimpl authService;
    private final RefreshTokenService refreshTokenService;
    private final Userserviceimpl userserviceimpl;

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody RegisterRequest updateRequest) {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.updateUser(currentUser,updateRequest));
    }

    @GetMapping
    public ResponseEntity<User> getUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.getCurrentUser());
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.getUserStats(currentUser));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        User currentUser = userserviceimpl.getCurrentUser();
        LogoutResponse response = new LogoutResponse();
        response.setEmail(currentUser.getEmail());
        //refreshTokenService.deleteRefreshToken(currentUser.getEmail());
        response.setMessage("Logged out successfully! x)");
        System.out.println("Logged out! x)");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
