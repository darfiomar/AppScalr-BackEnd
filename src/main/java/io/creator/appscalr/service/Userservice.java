package io.creator.appscalr.service;

import io.creator.appscalr.dto.RegisterRequest;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.exceptions.UserAlreadyExistAuthenticationException;

public interface Userservice {
    public User signup(RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException;
    public User updateUser(User user, RegisterRequest updateRequest);
    public void delete(User user);
}
