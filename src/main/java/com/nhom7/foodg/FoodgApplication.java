package com.nhom7.foodg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FoodgApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodgApplication.class, args);
	}

	@GetMapping("/")
	public String getCategoryList(){
		return "success";
	}
}
