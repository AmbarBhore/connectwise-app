package com.example.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class RmmAgentApplication {

	    public static void main(String[] args) {
		            SpringApplication.run(RmmAgentApplication.class, args);
			        }

	        @GetMapping("/health")
		    public String health() {
			            return "RMM Agent is healthy";
				        }

		    @GetMapping("/info")
		        public String info() {
				        return "Agent ID: 1234 | Version: 3.1 | Host: " + System.getenv("HOSTNAME");
					    }
}
