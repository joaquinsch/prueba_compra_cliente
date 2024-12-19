package com.example.prueba2024.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.service.IClienteService;
import com.example.prueba2024.service.ICompraService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class CompraController {

	@Autowired
	private ICompraService iCompraService;
	
	@Autowired
	private IClienteService iClienteService;

	@PostMapping("/compras/crear")
	public ResponseEntity<String> crearCompra(@RequestBody Compra compra) {
		try {
			// ver que si el id del cliente no existe dar error
			if (compra.getCliente() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La compra debe estar asociada a un cliente");
			}
			Cliente cliente = iClienteService.buscarCliente(compra.getCliente().getId_cliente());
			if (cliente == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
			}	
			iCompraService.crearCompra(compra);
			return ResponseEntity.status(HttpStatus.CREATED).body("Se ha creado la compra");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error interno");
		}
	}
	
	@GetMapping("/compras/listar")
	public ResponseEntity<List<Compra>> verCompras(){
		return ResponseEntity.ok(iCompraService.listarCompras());
	}

	@DeleteMapping("/compras/eliminar/{id}")
	public ResponseEntity<String> eliminarCompra(@PathVariable Long id){
		try {
			Compra compra = iCompraService.buscarCompra(id);
			if (compra == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La compra no existe");
			}
			iCompraService.eliminarCompra(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Se ha eliminado la compra");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error interno");
		}
	}
	@PutMapping("/compras/editar")
	public ResponseEntity<String> editarCompra(@RequestBody Compra compra){
		try {
			Compra compraBuscada = iCompraService.buscarCompra(compra.getId_compra());
			if (compraBuscada == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La compra no existe");
			}
			iCompraService.editarCompra(compra);

			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Se ha editado la compra");
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error interno");
		}
	}

	@GetMapping("/compras/compramayor")
	public ResponseEntity<Compra> obtenerCompraMayor() {
		return ResponseEntity.ok(iCompraService.obtenerCompraDeMayorPrecio());
	}
}
