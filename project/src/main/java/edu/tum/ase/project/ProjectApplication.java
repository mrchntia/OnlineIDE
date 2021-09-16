package edu.tum.ase.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;



@SpringBootApplication
@EnableEurekaClient
public class ProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}


	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext context) {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		return new OAuth2RestTemplate(details, context);
	}


}