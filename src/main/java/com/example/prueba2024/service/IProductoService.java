package com.example.prueba2024.service;

import java.util.List;

import com.example.prueba2024.model.Producto;

public interface IProductoService {
	public Producto crearProducto(Producto producto);
	public List<Producto> listarProductos();
}
