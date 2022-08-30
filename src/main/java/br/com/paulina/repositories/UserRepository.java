package br.com.paulina.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.paulina.domain.Usuario;
@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

}
