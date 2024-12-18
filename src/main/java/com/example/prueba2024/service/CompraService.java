package com.example.prueba2024.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.repository.ClienteRepositorio;
import com.example.prueba2024.repository.CompraRepositorio;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CompraService implements ICompraService{
	
	@Autowired
	private CompraRepositorio compraRepositorio;
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Override
	public List<Compra> listarCompras() {
		return compraRepositorio.findAll();
	}

	@Override
	public Compra buscarCompra(Long id) {
		return compraRepositorio.findById(id).orElse(null);
	}

	@Override
	public Compra crearCompra(Compra compra) {
		return compraRepositorio.save(compra);
		
	}

	public Compra editarCompra(Compra compra) {
		Compra compraBuscada = buscarCompra(compra.getId_compra());
		if (compra.getCliente().getId() != compraBuscada.getCliente().getId()) {
			throw new EntityNotFoundException("El cliente que querés modificar no existe");
		}
		return compraRepositorio.save(compra);
	}

	@Override
	public void eliminarCompra(Long id) {
		compraRepositorio.deleteById(id);
		
	}

}
