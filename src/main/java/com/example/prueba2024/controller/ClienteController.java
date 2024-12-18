package com.example.prueba2024.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.service.IClienteService;
import com.example.prueba2024.service.ICompraService;

@RestController
public class ClienteController {
	@Autowired
	private IClienteService iClienteService;
	
	@PostMapping("/clientes/crear")
	public String crearCliente(@RequestBody Cliente cliente) {
		iClienteService.crearCliente(cliente);
		return "Se ha creado el cliente";
	}
	@GetMapping("/clientes/listar")
	public List<Cliente> listarClientes() {
		return iClienteService.listarClientes();
	}
	@DeleteMapping("clientes/eliminar/{id}")
	public String eliminarCliente(@PathVariable Long id) {
		iClienteService.eliminarCliente(id);
		return "Se ha eliminado el cliente";
	}
	@PutMapping("clientes/editar/{id}")
	public String editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente cliente_buscado = iClienteService.buscarCliente(id);
		cliente_buscado.setApellido(cliente.getApellido());
		cliente_buscado.setNombre(cliente.getNombre());
		cliente_buscado.setMail(cliente.getMail());
		cliente_buscado.setFecha_nacimiento(cliente.getFecha_nacimiento());
		iClienteService.editarCliente(cliente_buscado);
		return "Se ha editado el cliente";
	}

}
