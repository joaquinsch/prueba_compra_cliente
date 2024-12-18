package com.example.prueba2024.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.prueba2024.model.Compra;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra, Long>{

}
