package br.com.paulina.domain.resources.exceptions;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
	
	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String path;
}
