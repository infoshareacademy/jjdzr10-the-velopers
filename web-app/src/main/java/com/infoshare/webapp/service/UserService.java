package com.infoshare.webapp.service;

import com.infoshare.webapp.Dto.UserDto;
import com.infoshare.webapp.exception.UserAlreadyExistException;
import com.infoshare.webapp.exception.UserNotCompareToEmailException;
import com.infoshare.webapp.exception.UserNotExistException;
import com.infoshare.webapp.model.Role;
import com.infoshare.webapp.model.User;
import com.infoshare.webapp.repository.RoleRepository;
import com.infoshare.webapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
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

    public User resetPassword(UserDto userDto){
        if (!emailExists(userDto.getEmail())) {
            throw new UserNotExistException("There is no account with that email address: "
                    + userDto.getEmail());
        }
        if (!compareNameWithEmail(userDto)){
            throw new UserNotCompareToEmailException("This email address: "+ userDto.getEmail() + " does not match to user name!");
        }
        LOGGER.info("Sending reset password e-mail to {}", userDto.getEmail());
        return userRepository.findUserByEmail(userDto.getEmail()).orElseThrow();
    }

    private boolean compareNameWithEmail(UserDto userDto) {
        return userRepository.findUserByEmail(userDto.getEmail())
                .filter(user -> user.getUsername().equals(userDto.getUsername())).isPresent();
    }

    private boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
