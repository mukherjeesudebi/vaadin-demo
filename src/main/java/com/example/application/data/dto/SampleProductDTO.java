package com.example.application.data.dto;

import java.util.Objects;

public class SampleProductDTO {
	private Long id;
	private int consistencyVersion;
	private String name;
	private String category;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
		SampleProductDTO other = (SampleProductDTO) obj;
		return Objects.equals(getId(), other.getId());
	}

}
