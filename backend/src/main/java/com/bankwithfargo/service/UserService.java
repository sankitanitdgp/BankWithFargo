package com.bankwithfargo.service;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.UserLoginRepository;
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
    public ArrayList<UserLoginRequestDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        ArrayList<UserLoginRequestDTO> returnValue = new ArrayList<>();
        BeanUtils.copyProperties(users, returnValue);
        return returnValue;
    }

    public String authorizeUser(UserLoginRequestDTO user){
        try{
            User foundUser=userRepository.findOneByEmail(user.getEmail());
            if(foundUser!=null) {
                if(foundUser.getPassword().equals(user.getPassword()))
                    return "User authorized successfully";
                else
                    return "Incorrect password";
            } else {
                return "User not found";
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Transactional
    public User createUser(UserSignupRequestDTO user){

            try{
                User newUser= new User();
                BeanUtils.copyProperties(user,newUser);
                return userRepository.save(newUser);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        return null;
    }

}
