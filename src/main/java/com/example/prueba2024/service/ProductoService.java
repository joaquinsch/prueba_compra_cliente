package com.example.prueba2024.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba2024.model.Producto;
import com.example.prueba2024.repository.ProductoRepositorio;

@Service
public class ProductoService implements IProductoService {
	@Autowired
	private ProductoRepositorio productoRepo;
	
	@Override
	public void crearProducto(Producto producto) {
		productoRepo.save(producto);
	}
	
}
