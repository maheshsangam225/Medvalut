package com.example.demo.Entity;

import java.time.LocalDate;

import com.example.demo.Enum.Role;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)



public  class Doctor extends User {
	public Doctor() {
		
	}

    @NotBlank
    private String specialisation;
    @NotBlank
    private String licenseNo;
    @NotBlank
    private String hospitalName;

    public Doctor(String firstName, String lastName, String email, String password, String gender, LocalDate dateOfBirth, String specialisation, String licenseNo, String hospitalName)
            {

        super( firstName, lastName, email, password, Role.ROLE_DOCTOR, gender, dateOfBirth);
        this.specialisation = specialisation;
        this.licenseNo = licenseNo;
        this.hospitalName = hospitalName;
            }
}