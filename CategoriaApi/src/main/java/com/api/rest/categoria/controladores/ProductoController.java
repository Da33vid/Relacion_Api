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
import com.api.rest.categoria.entidades.Producto;
import com.api.rest.categoria.repositorios.CategoriaRepository;
import com.api.rest.categoria.repositorios.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<Page<Producto>> listarProductos(Pageable pageable){
		return ResponseEntity.ok(productoRepository.findAll(pageable));
	}
	
	@PostMapping
	public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody Producto producto){
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(producto.getCategoria().getId());
		
		if(!categoriaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		producto.setCategoria(categoriaOptional.get());
		Producto productoGuardado = productoRepository.save(producto);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productoGuardado.getId()).toUri();
		
		return ResponseEntity.created(ubicacion).body(productoGuardado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Producto> actualizarProducto(@Valid @RequestBody Producto producto,@PathVariable Integer id){
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(producto.getCategoria().getId());
		
		if(!categoriaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		Optional<Producto> productoOptional = productoRepository.findById(id);
		if(!productoOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		producto.setCategoria(categoriaOptional.get());
		producto.setId(productoOptional.get().getId());
		productoRepository.save(producto);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Producto> eliminarProducto(@PathVariable Integer id){
		Optional<Producto> productoOptional = productoRepository.findById(id);
		
		if(!productoOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		productoRepository.delete(productoOptional.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id){
		Optional<Producto> productoOptional = productoRepository.findById(id);
		
		if(!productoOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok(productoOptional.get());
	}
}
