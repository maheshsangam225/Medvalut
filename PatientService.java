package com.example.demo.Service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.Patient;
import com.example.demo.Repository.PatientRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public  class PatientService {
    



	private final PatientRepository patientRepository;
	private final PasswordEncoder passwordEncoder;
	
	public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
		super();
		this.patientRepository = patientRepository;
		this.passwordEncoder = passwordEncoder;
		
	}
    

	public Patient register(RegisterDTO registerDTO) {
		
		// TODO Auto-generated method stub
		  if (patientRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
	            throw new RuntimeException("Patient already exists");
	        }
	        String FirstName = registerDTO.getFirstName();
	        String LastName = registerDTO.getLastName();
	        String Email = registerDTO.getEmail();
	        String Password = registerDTO.getPassword();
	        //Role  = registerDTO.getFirstName();
	        String gender = registerDTO.getGender();
	        LocalDate dateOfBirth = registerDTO.getDateOfBirth();
	        String address = registerDTO.getAddress();
	        String phoneNumber = registerDTO.getPhoneNumber();
            String EmergencyContact = registerDTO.getEmergencyContact();
	        //String Specilisation = registerDTO.getSpecilisation();
	      //  String LicenseNo = registerDTO.getLicenseNo();
	        //String HospitalName = registerDTO.getHospitalName();
	       String hpassword = passwordEncoder.encode(Password);
	        
	        //Patient doctor = new Doctor(FirstName, LastName, Email, hpassword, gender, dateOfBirth, Specilisation, LicenseNo, HospitalName);
	        	Patient patient = new Patient(FirstName, LastName, Email, hpassword, gender, dateOfBirth, address, phoneNumber, EmergencyContact);	
	return patientRepository.save(patient);

	}

}