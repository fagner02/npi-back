package com.example.demo.service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.demo.specifications.UserSpecification.hasNameLike;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário"));

        return org.springframework.security.core.userdetails.User.builder().username(user.email).password(user.password).build();
    }

    public Page<User> findAll(Pageable pageable, String name) {
        Specification<User> specification = Specification.allOf(hasNameLike(name));
        return userRepo.findAll(specification, pageable);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário."));
    }

    public User post(CreateUserDTO user) {
        if (userRepo.existsByEmail(user.email)) throw new IllegalArgumentException("O email já está cadastrado");
        return getUser(user);
    }

    private User getUser(CreateUserDTO user) {
        User newUser = new User();
        newUser.email = user.email;
        newUser.password = passwordEncoder.encode(user.password);
        newUser.username = user.username;
        return userRepo.save(newUser);
    }

    public void update(Long id, CreateUserDTO user) {
        User oldUser = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário"));
        if (!Objects.equals(oldUser.email, user.email)) {
            throw new IllegalArgumentException("O email não pode ser modificado");
        }

        User newUser = new User();
        newUser.id = id;
        newUser.password = passwordEncoder.encode(user.password);
        userRepo.save(newUser);
    }

    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("Usuário");
        }
        userRepo.deleteById(id);
    }
}
