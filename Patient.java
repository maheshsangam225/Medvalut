package com.example.demo.Entity;

import java.time.LocalDate;

import com.example.demo.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)

public  class Patient  extends User{

    public Patient() {
		super();
	}

	private String address;
    @NotBlank
    @Column(unique = true, nullable=false)
    private String phoneNumber;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String emergencyContact;

    public Patient(String firstName,String lastName, String email, String password,String gender,LocalDate dateOfBirth, String address, String phoneNumber, String emergencyContact) {

       super(firstName, lastName, email, password, Role.ROLE_PATIENT, gender, dateOfBirth);
    
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emergencyContact = emergencyContact;

    }
}