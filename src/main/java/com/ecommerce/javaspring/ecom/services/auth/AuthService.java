package com.ecommerce.javaspring.ecom.services.auth;

import com.ecommerce.javaspring.ecom.dto.SignupRequest;
import com.ecommerce.javaspring.ecom.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest signupRequest);
	
	Boolean hasUserWithEmail(String email);
}
