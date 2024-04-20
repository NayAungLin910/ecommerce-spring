package com.ecommerce.javaspring.ecom.dto;

import com.ecommerce.javaspring.ecom.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
	private long id;
	private String email;
	private String name;
	private UserRole role;
	
}
