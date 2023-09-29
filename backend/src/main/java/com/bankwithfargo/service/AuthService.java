package com.bankwithfargo.service;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.UserLoginRepository;
import com.bankwithfargo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    @Autowired
    UserLoginRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserLoginRepository userLoginRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userLoginRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public String authorizeUser(UserLoginRequestDTO user){
        try{
            User foundUser=userRepository.findOneByEmail(user.getEmail());
            if(foundUser!=null) {
                if(passwordEncoder.matches(user.getPassword(), foundUser.getPassword()))
                    return jwtTokenProvider.generateToken(user.getEmail());
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
}
