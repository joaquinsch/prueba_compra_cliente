package com.example.prueba2024.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.prueba2024.model.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

}
