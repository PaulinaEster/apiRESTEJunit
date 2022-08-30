package br.com.paulina.domain;
 

import javax.persistence.*;

import lombok.*;

@Data      /* Essa anotação do lombok faz os metodos getters e setters hascode e equals */
@NoArgsConstructor
@AllArgsConstructor 
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(unique = true)
	private String email;
	private String password;
	
}
