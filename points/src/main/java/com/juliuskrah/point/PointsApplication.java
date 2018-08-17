package com.juliuskrah.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
@SpringBootApplication
public class PointsApplication {
	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(PointsApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "redirect:points/start/@50.8307467,-1.2099742/dest/@5.5790564,-0.7073872";
	}

	@GetMapping(path = "/points/start/@{start}/dest/@{dest}", produces = "application/json")
	public HttpEntity<Double> points(@PathVariable String start, @PathVariable String dest) {
		return ResponseEntity.ok(restTemplate.getForObject(
				"http://distance/distance/start/@{start}/dest/@{dest}",
				double.class, start, dest));
	}

	@Bean
	@LoadBalanced
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
