package com.junittest.work.services.impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.junittest.work.entities.User;
import com.junittest.work.entities.dto.UserDTO;
import com.junittest.work.repositories.UserRepository;
import com.junittest.work.services.impl.UserServiceImpl;

@SpringBootTest
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl service;
	@Mock
	private UserRepository repository;
	@Mock
	private ModelMapper mapper;
	
	private User user;
	private UserDTO userDTO;
	private Optional<User> optUser;
	
	final long ID = 1;
	final String NAME = "Roberto Rongo";
	final String EMAIL = "rongo@email.com";
	final String PHONE = "971124455";
	final String PASSWORD = "asd111";
	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void whenFindByIdReturnUserInstance() {
		/**
		 * Busca um objeto do tipo 'User' do repositório, retorna a classe
		 * instanciada no teste 'startUser()' e verifica se o objeto do
		 * repositório não é 'null'. Em seguida compara ambos os objetos,
		 * garantindo que a classe do objeto instanciado no teste, pertence a 
		 * mesma classe do objeto buscado no repositório.
		 */
		when(repository.findById(anyLong())).thenReturn(optUser);
		User response = service.findById(ID);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
	}

	@Test
	void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PHONE, PASSWORD);
		optUser = Optional.of(new User(ID, NAME, EMAIL, PHONE, PASSWORD));
	}

}
