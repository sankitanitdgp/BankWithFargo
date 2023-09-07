package com.bankwithfargo.service;

import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String authorizeUser(User user){
        User foundUser=userRepository.findOneByEmail(user.getEmail());
        if(foundUser!=null) {
            if(foundUser.getPassword().equals(user.getPassword()))
                return "User authorized successfully";
            else
                return "Incorrect password";
        } else {
            return "User not found";
        }
    }

    @Transactional
    public User createUser(User user){
        try{
            return userRepository.save(user);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
