package com.infoshare.webapp.service;

import com.infoshare.webapp.Dto.UserDto;
import com.infoshare.webapp.exception.UserAlreadyExistException;
import com.infoshare.webapp.model.Role;
import com.infoshare.webapp.model.User;
import com.infoshare.webapp.repository.RoleRepository;
import com.infoshare.webapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        Role role = roleRepository.findByName("USER");
        user.setRoles(Set.of(role));
        role.getUsers().add(user);
        return userRepository.save(user);
    }
    private boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
