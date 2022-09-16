package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.RefreshTokenRepository;
import io.creator.appscalr.dao.UserRepository;
import io.creator.appscalr.dto.*;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.RefreshToken;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.exceptions.AppscalrException;
import io.creator.appscalr.exceptions.UserAlreadyExistAuthenticationException;
import io.creator.appscalr.security.JwtProvider;
import io.creator.appscalr.service.Userservice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class Userserviceimpl implements Userservice {

    private final UserRepository userdao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final Changelogserviceimpl userlog;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public User signup(RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException {
        User user = new User();
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(registerRequest.getEmail()) && userdao.findByEmail(registerRequest.getEmail()).isEmpty()){
            user.setEmail(registerRequest.getEmail());
            user.setFullname(registerRequest.getFullname());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setSpecialite(registerRequest.getSpecialite());
            user.setCreated_at(Instant.now());
            user.setHasfeedback(false);
            user.setEnabled(true);
            userdao.save(user);
            userlog.newUserLog(user,"Account created, Welcome to APPSCALR!");
        }
        else{
            throw new UserAlreadyExistAuthenticationException(
                    "Email address invalid or already exists : "
                            +  registerRequest.getEmail());
        }
        return user;
    }

    @Override
    public User updateUser(User usertoupdate, RegisterRequest updateRequest) {
        Optional<User> userOptional = userdao.findById(usertoupdate.getUserid());
        EmailValidator validator = EmailValidator.getInstance();
        if (userOptional.isPresent() && validator.isValid(updateRequest.getEmail())){
            User existinguser = userOptional.get();
            existinguser.setEmail(updateRequest.getEmail());
            existinguser.setFullname(updateRequest.getFullname());
            existinguser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            existinguser.setSpecialite(updateRequest.getSpecialite());
            userdao.save(existinguser);
            userlog.newUserLog(existinguser,"You have just updated your profile.");
            return existinguser;
        }else return null;
    }

    @Override
    public void delete(User user) {
        userdao.delete(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                //.refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getEmail()).getToken())
                //.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getEmail())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshTokenBean = refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenBean.getEmail());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenBean.getEmail())
                .build();
    }


    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userdao.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public StatsResponse getUserStats(User user){
        StatsResponse stats = new StatsResponse();
        stats.setAuthor(user.getFullname());
        stats.setApps(user.getApps().size());
        long pages =0;
        for (App app: user.getApps()) {
            pages += app.getPages().size();
        }
        stats.setPages(pages);
        stats.setUserlogs(user.getLogs().size());
        return stats;
    }

}
