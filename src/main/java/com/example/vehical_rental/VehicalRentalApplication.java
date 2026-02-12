package com.example.vehical_rental;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VehicalRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicalRentalApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com") == null) {
                UserDtls admin = new UserDtls();
                admin.setFullName("System Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Default Admin Created: admin@gmail.com / admin123");
            }
        };
    }

}
