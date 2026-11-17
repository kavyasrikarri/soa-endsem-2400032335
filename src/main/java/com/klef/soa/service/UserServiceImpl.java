
package com.klef.soa.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.soa.entity.User;
import com.klef.soa.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user)
    {
        if(repository.findByEmail(user.getEmail()).isPresent())
        {
            throw new IllegalArgumentException("Email already exists");
        }

        if(repository.findByContact(user.getContact()).isPresent())
        {
            throw new IllegalArgumentException("Contact already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        return StreamSupport.stream(
                repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "User not found with id: " + id));
    }

    @Override
    public User updateUser(Long id, User request)
    {
        User user = repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "User not found with id: " + id));

        repository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                        "Email already exists");
                });

        repository.findByContact(request.getContact())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                        "Contact already exists");
                });

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setContact(request.getContact());
        user.setRole(request.getRole());

        if(request.getPassword() != null &&
                !request.getPassword().isBlank())
        {
            user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        }

        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id)
    {
        User user = repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "User not found with id: " + id));

        repository.deleteById(id);
    }
}
