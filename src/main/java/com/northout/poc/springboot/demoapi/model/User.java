package com.northout.poc.springboot.demoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {	
   
	public User(UserType type, int years) {
		super();
		this.type = type;
		this.years = years;
	}

	private UserType type;

    private int years;

    // Standard getters and setters

    public enum UserType {
        MANAGEMENT,
        DEVELOPER;
    }
}