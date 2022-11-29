package com.junittest.work.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.junittest.work.entities.User;
import com.junittest.work.entities.dto.UserDTO;
import com.junittest.work.repositories.UserRepository;
import com.junittest.work.services.exceptions.DataIntegrityViolationException;
import com.junittest.work.services.exceptions.ResourceNotFoundException;

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
	void whenFindAllReturnUserList() {
		/**
		 * Busca uma lista do tipo 'User' do repositorio, verifica se o objeto
		 * da lista não é 'null', verifica se o tamanho da lista é o mesmo
		 * instanciado no caso de teste, e em seguida compara ambos os objetos,
		 * garantindo que a classe do objeto instanciado no teste, pertence a 
		 * mesma classe dos objetos da lista buscados no repositório.
		 */
		when(repository.findAll()).thenReturn(List.of(user));
		List<User> response = service.findAll();
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(0).getClass());
	}

	@Test
	void whenFindByIdReturnUserInstance() {
		/**
		 * Busca um objeto do tipo 'User' do repositório, verifica se o objeto 
		 * do repositório não é 'null', e em seguida compara ambos os objetos,
		 * garantindo que a classe do objeto instanciado no teste, pertence a 
		 * mesma classe do objeto buscado no repositório.
		 */
		when(repository.findById(anyLong())).thenReturn(optUser);
		User response = service.findById(ID);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
	}
	
	@Test
	void whenFindByIdReturnNotFound() {
		/**
		 * Caso o objeto do tipo 'User' não seja encontrado, o método lança uma 
		 * excessão, e verifica se a excessão está de acordo com o erro. 
		 */
		when(repository.findById(anyLong()))
			.thenThrow(new ResourceNotFoundException("Objeto não encontrado."));
		try {
			service.findById(ID);
		}
		catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}

	@Test
	void whenInsertReturnSuccess() {
		/**
		 * Insere um novo objeto no repositorio, verifica se o objeto inserido
		 * no repositorio não é 'null', e en seguida, compara o objeto inserido
		 * com o objeto final instanciado na classe de teste, e verifica se 
		 * ambos os objetos pertencem da mesma classe.
		 */
		when(repository.save(any())).thenReturn(user);
		User response = service.insert(userDTO);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
	}
	
	@Test
	void whenInsertFailsReturnDataIntegrityViolation() {
		/**
		 * Caso o objeto não possa ser inserido no repositorio, o método lança 
		 * uma excessão, e verifica se a excessão está de acordo com o erro. 
		 */
		when(repository.findByEmail(anyString())).thenReturn(optUser);
		try {
			optUser.get().setId((long) 2);
			service.insert(userDTO);
		}
		catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
		}
	}
	
	@Test
	void whenUpdateReturnSuccess() {
		/**
		 * Atualiza um objeto no repositorio, verifica se o objeto atualizado
		 * no repositorio não é 'null', e en seguida, compara o objeto 
		 * atualizado com o objeto final instanciado na classe de teste, e 
		 * verifica se ambos os objetos pertencem da mesma classe.
		 */
		when(repository.save(any())).thenReturn(user);
		User response = service.update((long) 1, userDTO);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
	}
	
	@Test
	void whenUpdateFailsReturnDataIntegrityViolation() {
		/**
		 * Caso o objeto do repositorio não possa ser atualizado, o método 
		 * lança uma excessão, e verifica se a excessão está de acordo com o 
		 * erro. 
		 */
		when(repository.findByEmail(anyString())).thenReturn(optUser);
		try {
			optUser.get().setId((long) 2);
			service.update((long) 1, userDTO);
		}
		catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
		}
	}

	@Test
	void whenDeleteReturnSuccess() {
		/**
		 * Exclui um objeto do repositorio e verifica se o objeto foi excluido
		 * corretamente fazendo uma passagem pelo repositorio.
		 */
		when(repository.findById(anyLong())).thenReturn(optUser);
		doNothing().when(repository).deleteById(anyLong());
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void whenDeleteFailsReturnNotFound() {
		/**
		 * Caso um objeto do repositorio não possa ser excluido, o método lança 
		 * uma excessão, e verifica se a excessão está de acordo com o erro. 
		 */
		when(repository.findById(anyLong()))
			.thenThrow(new ResourceNotFoundException("Objeto não encontrado."));
		try {
			service.delete(ID);
		}
		catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PHONE, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PHONE, PASSWORD);
		optUser = Optional.of(new User(ID, NAME, EMAIL, PHONE, PASSWORD));
	}

}
