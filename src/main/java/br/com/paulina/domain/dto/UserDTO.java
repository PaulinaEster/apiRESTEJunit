package br.com.paulina.domain.dto;
 
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Integer id;
	private String name;
	 
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY )
	private String password;
}
