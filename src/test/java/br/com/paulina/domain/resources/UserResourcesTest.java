package br.com.paulina.domain.resources;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doNothing;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.paulina.domain.Usuario;
import br.com.paulina.domain.dto.UserDTO;
import br.com.paulina.repositories.UserRepository;
import br.com.paulina.services.impl.UserServiceImpl;

@SpringBootTest
public class UserResourcesTest {

	private static final Integer ID = 1;
	private static final String NAME = "Paulina";
	private static final String EMAIL = "paulina@lina.com";
	private static final String SENHA = "123";
	
	@InjectMocks
	private UserResources userResources;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;

	@Mock
	private ModelMapper mapper;
	 
	
	
	private Usuario user;
	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.startUsers();
	}
	
	@Test
	void findByIdWhenReturnSucces() {
		Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
		Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = userResources.findById(ID);
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getBody().getClass(), UserDTO.class);
		
		Assertions.assertEquals(userDTO.getId(), response.getBody().getId());
		Assertions.assertEquals(userDTO.getName(), response.getBody().getName());
		Assertions.assertEquals(userDTO.getEmail(), response.getBody().getEmail());
	}
	
	@Test
	void getAllUsersWhenReturnList() {
		Mockito.when(service.findAll()).thenReturn(List.of(user));
		Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> list = userResources.getAllUsers(); 
		
		Assertions.assertNotNull(list.getBody().get(0));
		Assertions.assertNotNull(list.getBody());
		Assertions.assertNotNull(list);
		Assertions.assertEquals(list.getBody().get(0).getClass(), UserDTO.class);
		Assertions.assertEquals(userDTO.getId(), list.getBody().get(0).getId());
		Assertions.assertEquals(userDTO.getName(), list.getBody().get(0).getName());
		Assertions.assertEquals(userDTO.getEmail(), list.getBody().get(0).getEmail());
	}
	
	@Test
	void createUserWhenReturnSucces() {
		Mockito.when(service.create(Mockito.any())).thenReturn(user); 
		ResponseEntity<UserDTO> response = userResources.createUser(userDTO);
		 
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertNotNull(response.getHeaders().get("Location"));
	}
	
	@Test
	void updateUserWhenReturnSucces() {
		Mockito.when(service.update(Mockito.any())).thenReturn(user);
		Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		ResponseEntity<UserDTO> response = userResources.updateUser(ID, userDTO);
		
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getBody().getClass(), UserDTO.class);
		Assertions.assertEquals(userDTO.getId(), response.getBody().getId());
		Assertions.assertEquals(userDTO.getName(), response.getBody().getName());
		Assertions.assertEquals(userDTO.getEmail(), response.getBody().getEmail());	
	}
	
	@Test
	void deleteWithSucces() {
		Mockito.doNothing().when(service).delete(Mockito.anyInt());
		ResponseEntity<UserDTO> response  = userResources.deleteUser(ID);
		Assertions.assertEquals(response.getClass(), ResponseEntity.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		
		
	}
	
	private void startUsers() {
		user = new Usuario(ID, NAME,  EMAIL, SENHA);
		userDTO = new UserDTO(ID, NAME,  EMAIL, SENHA); 
	}
	
	
}
