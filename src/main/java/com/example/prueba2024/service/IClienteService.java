package com.example.prueba2024.service;

import java.util.List;

import com.example.prueba2024.model.Cliente;

public interface IClienteService {
	public void crearCliente(Cliente cliente);
	public Cliente buscarCliente(Long id);
	public List<Cliente> listarClientes();
	public void eliminarCliente(Long id);
	public Cliente editarCliente(Cliente datosCliente);
}
