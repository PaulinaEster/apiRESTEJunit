package br.com.paulina.domain.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.paulina.domain.Usuario;
import br.com.paulina.domain.dto.UserDTO;
import br.com.paulina.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResources {
	
	@Autowired
	private ModelMapper mapper;
	
	
	
	@Autowired
	private UserService service;	
	
	@GetMapping(value = "/{id}") /* ciando endpoint para pegar usuario por id, rececbe um ID  e retorna um objeto com o ID correspondente*/
	public ResponseEntity<UserDTO> findById( @PathVariable Integer id ){
		return ResponseEntity.ok().body( mapper.map(service.findById(id), UserDTO.class) );
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<Usuario> list = service.findAll();
		List<UserDTO> listDtos = list.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDtos);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser( @RequestBody UserDTO newUser){
		Usuario newObjDto = service.create(newUser);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObjDto.getId()).toUri();
		return ResponseEntity.created(uri).build();				
	}
	
	@PutMapping(value = "/{id}") 
	public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO obj){
		obj.setId(id);
		Usuario upUser = service.update(obj);
		return ResponseEntity.ok().body(mapper.map(upUser, UserDTO.class));		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
