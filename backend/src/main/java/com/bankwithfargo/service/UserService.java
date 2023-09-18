package com.bankwithfargo.service;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.UserLoginRepository;
import com.bankwithfargo.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserLoginRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
//        ArrayList<UserLoginRequestDTO> returnValue = new ArrayList<>();
//        BeanUtils.copyProperties(users, returnValue);
//        return returnValue;
    }

    @Transactional
    public User createUser(UserSignupRequestDTO user){

        User u= userRepository.findOneByEmail(user.getEmail());
        System.out.println("********* "+ u +"******************");
        if(u==null) {
            try {
                User newUser = new User();
                BeanUtils.copyProperties(user, newUser);
                return userRepository.save(newUser);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

}
