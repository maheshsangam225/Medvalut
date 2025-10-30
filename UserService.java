package com.example.demo.Service;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@NoArgsConstructor
@RequiredArgsConstructor

public class UserService {
   
	private final UserRepository userRepository;
	 public UserService(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}


    public User Register(RegisterDTO registerDTO) {
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email Already Exists");

        }
        String FirstName = registerDTO.getFirstName();
return null;
    }
    
}