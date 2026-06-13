package com.mydietplan.service;

import com.mydietplan.model.User;

public interface AuthService {
    boolean register(String email, String password);
    User login(String email, String password);
    void logout();
}
