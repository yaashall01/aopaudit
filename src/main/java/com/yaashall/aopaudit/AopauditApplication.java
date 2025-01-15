package com.yaashall.aopaudit;

import com.yaashall.aopaudit.entity.User;
import com.yaashall.aopaudit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopauditApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopauditApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository){
		return args -> {
			userRepository.save(User.builder().username("Ahmed").email("ahmed@example.com").password("password1").build());
			userRepository.save(User.builder().username("Fatima").email("fatima@example.com").password("password2").build());
			userRepository.save(User.builder().username("Mohamed").email("mohamed@example.com").password("password3").build());
			userRepository.save(User.builder().username("Sara").email("sara@example.com").password("password4").build());
			userRepository.save(User.builder().username("Youssef").email("youssef@example.com").password("password5").build());
		};
	}
}
