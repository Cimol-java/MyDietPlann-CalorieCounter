package com.mydietplan.service.impl;

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
        if (userRepository.existsByEmail(email)) {
            System.out.println("[ERROR] Email sudah terdaftar.");
            return false;
        }
        String id = IdGenerator.generate();
        User user = new User(id, email, password);
        userRepository.save(user);
        return true;
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
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
