package com.example.prueba2024.service;

import java.util.List;

import com.example.prueba2024.model.Compra;

public interface ICompraService {
	public List<Compra> listarCompras();
	public Compra crearCompra(Compra compra);
	public Compra buscarCompra(Long id);
	public Compra editarCompra(Compra compra);
	public void eliminarCompra(Long id);
	

}
