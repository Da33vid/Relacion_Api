package com.api.rest.categoria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.categoria.entidades.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
