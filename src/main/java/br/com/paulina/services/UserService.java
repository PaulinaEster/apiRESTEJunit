package br.com.paulina.services;

import java.util.List;

import br.com.paulina.domain.Usuario;
import br.com.paulina.domain.dto.UserDTO;

public interface UserService {

	Usuario findById(Integer id);

	List<Usuario> findAll();
	
	Usuario create(UserDTO user);
	
	Usuario update(UserDTO user);
	
	void delete(Integer id);
	
}
