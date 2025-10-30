package com.example.demo.Entity;
import java.time.LocalDate;


import com.example.demo.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public  class User {
	public User() {
		
	}
   
	public User(String firstName, String lastName, String email, String password, Role role, String gender,
			LocalDate dateOfBirth) {
		this.firstName = firstName;
		this.lastName=lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.gender=gender;
		this.dateOfBirth = dateOfBirth;
		
		
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank

    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @NotBlank
    private String gender;
    
    @Past
    private LocalDate dateOfBirth;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}




}
