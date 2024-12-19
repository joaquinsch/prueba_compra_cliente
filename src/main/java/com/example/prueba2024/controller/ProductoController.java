package com.example.prueba2024.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba2024.model.Producto;
import com.example.prueba2024.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private IProductoService iProductoService;
	
	@PostMapping("/productos/crear")
	public ResponseEntity<String> crearProducto(@RequestBody Producto producto){
		try {
			iProductoService.crearProducto(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body("Se ha creado el producto");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
		}
	}
	
}
