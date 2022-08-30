package br.com.paulina.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.paulina.domain.Usuario;
import br.com.paulina.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		Usuario u1 = new Usuario(null, "Paulina", "paulina@paulina.com", "123");
		Usuario u2 = new Usuario(null, "Paula", "paula@paula.com", "123");
		
		repository.saveAll(List.of(u1, u2));
	}
	
	
}
