package com.junittest.work.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.junittest.work.entities.User;
import com.junittest.work.entities.dto.UserDTO;
import com.junittest.work.services.impl.UserServiceImpl;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	private static final String ID = "/{id}";

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserServiceImpl service;
	
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> listDTO = service.findAll().stream()
				.map(x -> mapper.map(x, UserDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = ID)
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok()
				.body(mapper.map(service.findById(id), 
						UserDTO.class));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO obj) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(ID)
				.buildAndExpand(service.insert(obj).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = ID)
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO obj) {
		obj.setId(id);
		User newObj = service.update(id, obj);
		return ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
	}
	
	@DeleteMapping(value = ID)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
