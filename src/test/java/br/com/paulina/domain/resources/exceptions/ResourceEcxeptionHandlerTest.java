package br.com.paulina.domain.resources.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.paulina.services.exceptions.DataIntegratyViolationException;
import br.com.paulina.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class ResourceEcxeptionHandlerTest {
	
	@InjectMocks
	private ResourseEcxeptionHandler handler;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void ObjectNotFoundExceptionTest(){
		ResponseEntity<StandardError> responseEntity = handler
				.objectNotFound(new ObjectNotFoundException("Objeto não encontrado"), 
				new MockHttpServletRequest());
		
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		Assertions.assertEquals(responseEntity.getClass(), ResponseEntity.class); 
	}
	
	@Test
	void DataIntegratyViolationExceptionTest(){
		ResponseEntity<StandardError> responseEntity = handler
				.objectNotFound(new DataIntegratyViolationException("Objeto não encontrado"), 
				new MockHttpServletRequest());
		
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertEquals(responseEntity.getClass(), ResponseEntity.class);
	}
	
}
