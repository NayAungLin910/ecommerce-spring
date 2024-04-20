package com.ecommerce.javaspring.ecom.dto;

import lombok.Data;
@Data
public class AuthenticationRequest {
	private String username;
	private String password;
}
