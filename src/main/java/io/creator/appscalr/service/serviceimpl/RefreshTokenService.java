package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.RefreshTokenRepository;
import io.creator.appscalr.entities.RefreshToken;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.exceptions.AppscalrException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        refreshToken.setEmail(email);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppscalrException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }
}
