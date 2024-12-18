package com.example.prueba2024.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.repository.ClienteRepositorio;

@Service
public class ClienteService implements IClienteService{
	@Autowired
	private ClienteRepositorio clienteRepo;

	@Override
	public void crearCliente(Cliente cliente) {
		clienteRepo.save(cliente);
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepo.findAll();
	}

	@Override
	public void eliminarCliente(Long id) {
		clienteRepo.deleteById(id);
	}

	@Override
	public Cliente editarCliente(Cliente datosCliente) {
		Cliente cliente = clienteRepo.save(datosCliente);
		return cliente;
	}

	@Override
	public Cliente buscarCliente(Long id) {
		return clienteRepo.findById(id).orElse(null);
	}
}
