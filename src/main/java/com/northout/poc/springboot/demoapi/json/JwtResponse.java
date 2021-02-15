package com.northout.poc.springboot.demoapi.json;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String token;

}