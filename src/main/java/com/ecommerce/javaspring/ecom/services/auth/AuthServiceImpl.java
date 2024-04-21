package com.ecommerce.javaspring.ecom.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.javaspring.ecom.dto.SignupRequest;
import com.ecommerce.javaspring.ecom.dto.UserDto;
import com.ecommerce.javaspring.ecom.entity.User;
import com.ecommerce.javaspring.ecom.enums.UserRole;
import com.ecommerce.javaspring.ecom.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

	public UserDto createUser(SignupRequest signupRequest) {

		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createUser = userRepository.save(user);

		UserDto userDto = new UserDto();
		userDto.setId(createUser.getId());

		return userDto;
	}

	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}

}
