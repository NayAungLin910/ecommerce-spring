package com.ecommerce.javaspring.ecom.dto;

import lombok.Data;

@Data
public class SignupRequest {
	private String email;
	private String password;
	private String name;
}
