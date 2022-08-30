package br.com.paulina.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.paulina.domain.Usuario;
import br.com.paulina.domain.dto.UserDTO;
import br.com.paulina.repositories.UserRepository;
import br.com.paulina.services.UserService;
import br.com.paulina.services.exceptions.DataIntegratyViolationException;
import br.com.paulina.services.exceptions.ObjectNotFoundException;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Usuario findById(Integer id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<Usuario> findAll() { 
		return	repository.findAll();
	}

	@Override
	public Usuario create(UserDTO user) {
		this.findByEmail(user);
		return repository.save(mapper.map(user, Usuario.class));
	}

	private void findByEmail(UserDTO user) {
		Optional<Usuario> userUsuario = repository.findByEmail(user.getEmail());	
		if(userUsuario.isPresent()  && !userUsuario.get().getId().equals(user.getId())){
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
		}
	}

	@Override
	public Usuario update(UserDTO user) { 
		this.findByEmail(user);
		return repository.save(mapper.map(user, Usuario.class));
	}

	@Override
	public void delete(Integer id) {
		this.findById(id);
		repository.deleteById(id);
		
	}
	
}
