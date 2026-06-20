package com.mydietplan.auth.bizz;

import com.mydietplan.auth.User;
import com.mydietplan.auth.UserRepository;
import com.mydietplan.exception.AppException;
import com.mydietplan.util.IdGenerator;

public class AuthBizz implements IAuthBizz {
    private final UserRepository userRepository;

    public AuthBizz(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // register
    @Override
    public boolean register(String email, String password) {
        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new AppException("Format email tidak valid. Email harus mengandung '@' dan titik ('.').");
        }

        String processedEmail = email.trim().toLowerCase();

        if (password == null || password.length() < 6) {
            throw new AppException("Password minimal 6 karakter.");
        }
        if (userRepository.existsByEmail(processedEmail)) {
            throw new AppException("Email sudah terdaftar. Silakan gunakan email lain.");
        }
        String id = IdGenerator.generate();
        User user = new User(id, processedEmail, password);
        userRepository.save(user);
        return true;
    }

    // login
    @Override
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new AppException("Email tidak boleh kosong.");
        }

        String processedEmail = email.trim().toLowerCase();

        if (password == null || password.isEmpty()) {
            throw new AppException("Password tidak boleh kosong.");
        }
        User user = userRepository.findByEmail(processedEmail);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    // logout
    @Override
    public void logout() {
        System.out.println("[OK] Berhasil logout.");
    }
}
