package com.infoshare.webapp.service;

import com.infoshare.webapp.model.User;
import com.infoshare.webapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAll(Long id){
        return userRepository.findAll();
    }
}
