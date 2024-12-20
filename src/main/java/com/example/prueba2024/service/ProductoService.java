package com.example.prueba2024.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba2024.model.Producto;
import com.example.prueba2024.repository.ProductoRepositorio;

@Service
public class ProductoService implements IProductoService {
	@Autowired
	private ProductoRepositorio productoRepo;
	
	@Override
	public Producto crearProducto(Producto producto) {
		return productoRepo.save(producto);
	}

	@Override
	public List<Producto> listarProductos() {
		return productoRepo.findAll();
	}

	
}
