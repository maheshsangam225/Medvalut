package com.example.demo.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.Patient;
import com.example.demo.Entity.User;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.DoctorService;
import com.example.demo.Service.JwtService;
import com.example.demo.Service.PatientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@AllArgsConstructor
public  class AuthController {
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
			 DoctorRepository doctorRepository, PatientRepository patientRepository,
			PatientService patientService, DoctorService doctorService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		//this.userRepository = userRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.patientService = patientService;
		this.doctorService = doctorService;
	}


	private  AuthenticationManager authenticationManager;
    private JwtService jwtService;
  //  private  UserRepository userRepository;
    private  DoctorRepository doctorRepository;
    private  PatientRepository patientRepository;
    private  PatientService patientService;
    private  DoctorService doctorService;
    
   
		
	

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginDTO loginDTO) {
    	Authentication authToken =  new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
  
        try {
        	Authentication authentication = authenticationManager.authenticate(authToken);
        	
            User user = doctorRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new RuntimeException("user not found"));

            String token = jwtService.GenerateToken(authentication);
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (java.lang.Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/doctor/register")
    
    public ResponseEntity<?> DoctorRegister( @Valid @RequestBody RegisterDTO regsiterDTO) {
        try {
            Doctor doctor = doctorService.register(regsiterDTO);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/patient/register")
    public ResponseEntity<?> PatientRegister(@RequestBody RegisterDTO regsiterDTO) {
        try {
            Patient patient = patientService.register(regsiterDTO);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}