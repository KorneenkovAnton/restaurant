package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.Role;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Антон on 09.01.2020.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User register(User user) {
        user.setRole(Role.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
