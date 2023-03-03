package com.api.rest.categoria.controladores;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.api.rest.categoria.entidades.Categoria;
import com.api.rest.categoria.repositorios.CategoriaRepository;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<Page<Categoria>> listarCategorias(Pageable pageable){
		return ResponseEntity.ok(categoriaRepository.findAll(pageable));
	}
	
	@PostMapping
	public ResponseEntity<Categoria> guardarCategoria(@Valid @RequestBody Categoria categoria){
		Categoria categoriaGuardada = categoriaRepository.save(categoria);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoriaGuardada.getId()).toUri();
		return ResponseEntity.created(ubicacion).body(categoriaGuardada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id,@Valid @RequestBody Categoria categoria){
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		
		if(!categoriaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		categoria.setId(categoriaOptional.get().getId());
		categoriaRepository.save(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> eliminarCategoria(@PathVariable Integer id){
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		
		if(!categoriaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		categoriaRepository.delete(categoriaOptional.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Integer id){
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		
		if(!categoriaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok(categoriaOptional.get());
	}
}
