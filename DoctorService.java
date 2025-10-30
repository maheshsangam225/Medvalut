package com.example.demo.Service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.Doctor;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service

public  class DoctorService {
   

	private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    //private final UserRepository userRepository;
    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
		super();
		this.doctorRepository = doctorRepository;
		this.passwordEncoder = passwordEncoder;
		//this.userRepository = userRepository;
	}
    
@Transactional

    public Doctor register(RegisterDTO registerDTO) {


        if (doctorRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Doctor already exists");
        }
        String firstName = registerDTO.getFirstName();
        String lastName = registerDTO.getLastName();
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        //Role  = registerDTO.getFirstName();
        String gender = registerDTO.getGender();
        LocalDate dateOfBirth = registerDTO.getDateOfBirth();
       // String address = registerDTO.getAddress();
       // String phoneNumber = registerDTO.getPhoneNumber();
//        String EmergencyContact = registerDTO.getEmergencyContact();
        String specialisation = registerDTO.getSpecialisation();
        String licenseNo = registerDTO.getLicenseNo();
        String hospitalName = registerDTO.getHospitalName();
       String hpassword = passwordEncoder.encode(password);
        
        Doctor doctor = new Doctor(firstName, lastName, email, hpassword, gender, dateOfBirth, specialisation, licenseNo, hospitalName);
        		
return doctorRepository.save(doctor);
    }

}