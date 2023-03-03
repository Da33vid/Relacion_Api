package com.api.rest.categoria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.categoria.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
