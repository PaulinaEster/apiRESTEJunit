package br.com.paulina.service.impl;

 

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest; 

import br.com.paulina.domain.Usuario;
import br.com.paulina.domain.dto.UserDTO;
import br.com.paulina.repositories.UserRepository;
import br.com.paulina.services.exceptions.DataIntegratyViolationException;
import br.com.paulina.services.exceptions.ObjectNotFoundException;
import br.com.paulina.services.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {
	
	private static final Integer ID = 1;
	private static final String NAME = "Paulina";
	private static final String EMAIL = "paulina@lina.com";
	private static final String SENHA = "123";
	
	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private Usuario user;
	private UserDTO userDTO;
	private Optional<Usuario> optional;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	
	
	@Test 
	void findByIdWhenUserReturn() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optional);
		
		Usuario response = service.findById(ID);
		 
		Assertions.assertNotNull(response);
		Assertions.assertEquals(Usuario.class, response.getClass());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(EMAIL, response.getEmail());
		Assertions.assertEquals(NAME, response.getName());
	}
	
	@Test
	void findByIdWhenUserNotFound() {
		Mockito.when(repository.findById(Mockito.anyInt()))
			.thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		 
		try {
			service.findById(ID);
		} catch (Exception e) {
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
			Assertions.assertEquals(e.getMessage() , "Objeto não encontrado");
		}
	}
	
	@Test
	void findAllWhenReturnListOfUsers() {
		Mockito.when(repository.findAll()).thenReturn(List.of(user));
		
		List<Usuario> response = service.findAll();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(Usuario.class, response.get(0).getClass());
		Assertions.assertEquals(response.get(0).getId(), user.getId());
		Assertions.assertEquals(response.get(0).getEmail(), user.getEmail());
		Assertions.assertEquals(response.get(0).getName(), user.getName());
	}
	
	
	@Test
	void createWhenReturnSucces() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(user);
		
		Usuario response = service.create(userDTO);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(Usuario.class, response.getClass());
		Assertions.assertEquals(user.getId(), response.getId());
		Assertions.assertEquals(user.getEmail(), response.getEmail());
		Assertions.assertEquals(user.getName(), response.getName());
	}
	
	@Test
	void createWhenReturnDataIntegratyViolationException() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optional);
	
		try {
			optional.get().setId(2);
			Usuario response = service.create(userDTO);
		} catch (Exception e) {
			Assertions.assertEquals(e.getClass() , DataIntegratyViolationException.class);
			Assertions.assertEquals(e.getMessage(), "E-mail já cadastrado no sistema");
		} 
	}
	
	
	@Test
	void updateWhenReturnSucces() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(user);
		
		Usuario response = service.update(userDTO);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(Usuario.class, response.getClass());
		Assertions.assertEquals(user.getId(), response.getId());
		Assertions.assertEquals(user.getEmail(), response.getEmail());
		Assertions.assertEquals(user.getName(), response.getName());
	}
	
	@Test
	void updateWhenReturnDataIntegratyViolationException() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optional);
		try {
			optional.get().setId(2);
			Usuario response = service.update(userDTO);
		} catch (Exception e) {
			Assertions.assertEquals(e.getClass() , DataIntegratyViolationException.class);
			Assertions.assertEquals(e.getMessage(), "E-mail já cadastrado no sistema");
		} 
	}
	
	@Test
	void deleteWithSucces() {
		Mockito.when(repository.findById(ID)).thenReturn(optional);
		doNothing().when(repository).deleteById(Mockito.anyInt());
		service.delete(ID);
		verify(repository, times(1)).deleteById(Mockito.anyInt());
	}
	
	@Test
	void deleteObjectNotFoundException() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		try {
			service.delete(ID);
		} catch (Exception e) {
			Assertions.assertEquals(e.getClass(), ObjectNotFoundException.class);
			Assertions.assertEquals(e.getMessage(), "Objeto não encontrado");
		}
	}
	
	private void startUser() {
		user = new Usuario(ID, NAME,  EMAIL, SENHA);
		userDTO = new UserDTO(ID, NAME,  EMAIL, SENHA);
		optional = Optional.of(new Usuario(ID, NAME,  EMAIL, SENHA));
	}
	
}
