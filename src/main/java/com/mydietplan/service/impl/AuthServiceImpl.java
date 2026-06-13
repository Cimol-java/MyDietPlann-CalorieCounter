package com.mydietplan.service.impl;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.User;
import com.mydietplan.repository.UserRepository;
import com.mydietplan.service.AuthService;
import com.mydietplan.util.IdGenerator;

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(String email, String password) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new AppException("Format email tidak valid. Email harus mengandung '@'.");
        }
        if (password == null || password.length() < 6) {
            throw new AppException("Password minimal 6 karakter.");
        }
        if (userRepository.existsByEmail(email.trim())) {
            System.out.println("[ERROR] Email sudah terdaftar.");
            return false;
        }
        String id = IdGenerator.generate();
        User user = new User(id, email.trim(), password);
        userRepository.save(user);
        return true;
    }

    @Override
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new AppException("Email tidak boleh kosong.");
        }
        if (password == null || password.isEmpty()) {
            throw new AppException("Password tidak boleh kosong.");
        }
        User user = userRepository.findByEmail(email.trim());
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    @Override
    public void logout() {
        System.out.println("[OK] Berhasil logout.");
    }
}
