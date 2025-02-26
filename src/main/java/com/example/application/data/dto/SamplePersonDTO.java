package com.example.application.data.dto;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.Email;

public class SamplePersonDTO {
	private Long id;
	private int consistencyVersion;
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String phone;
	private LocalDate dateOfBirth;
	private String occupation;
	private String role;
	private boolean important;
	private String img;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion(int consistencyVersion) {
		this.consistencyVersion = consistencyVersion;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SamplePersonDTO other = (SamplePersonDTO) obj;
		return Objects.equals(getId(), other.getId());
	}

}
