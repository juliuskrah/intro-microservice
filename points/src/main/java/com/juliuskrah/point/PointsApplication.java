package com.juliuskrah.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class PointsApplication {
	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(PointsApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return restTemplate.getForObject("http://distance/", String.class);
	}
	
	@GetMapping(path = "/points/start/@{start}/dest/@{dest}", produces = "application/json")
	public String points(@PathVariable String start, @PathVariable String dest) {
		return "{\"start\": \"" + start + "\", \"dest\":\"" + dest + "\"}";
	}

	@Bean
	@LoadBalanced
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
