package com.junittest.work.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.junittest.work.entities.User;
import com.junittest.work.entities.dto.UserDTO;
import com.junittest.work.services.impl.UserServiceImpl;

@SpringBootTest
class UserResourceTest {

	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;

	private User user;
	private UserDTO userDTO;
	
	final long ID = 1;
	final String NAME = "Roberto Rongo";
	final String EMAIL = "rongo@email.com";
	final String PHONE = "971124455";
	final String PASSWORD = "asd111";
	
	private int INDEX = 0;
	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindAllReturnUserDTOList() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDTO);
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
	}

	@Test
	void whenFindByIdReturnSuccess() {
		when(service.findById(anyLong())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		ResponseEntity<UserDTO> response = resource.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
	}

	@Test
	void whenInsertReturnSuccess() {
		when(service.insert(any())).thenReturn(user);
		ResponseEntity<UserDTO> response = resource.insert(userDTO);
		assertNotNull(response.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void whenUpdateReturnSuccess() {
		when(service.update(ID, userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
	}

	@Test
	void whenDeleteReturnSuccess() {
		doNothing().when(service).delete(anyLong());
        ResponseEntity<Void> response = resource.delete(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PHONE, PASSWORD);
	}

}
