package com.example.prueba2024;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.repository.ClienteRepositorio;
import com.example.prueba2024.repository.CompraRepositorio;

@DataJpaTest
public class RepositorioCompraTest {

	@Autowired
	private CompraRepositorio compraRepo;
	
	@Autowired
	private ClienteRepositorio clienteRepo;
	
	@BeforeEach
	public void setUp() {
        compraRepo.deleteAll();
    }

	@Test
	public void listarTodasLasCompras() {

		List<Compra> listaCompras = new ArrayList<>();
		 
		assertEquals(listaCompras, compraRepo.findAll());
	}

	@Test
	public void buscarCompraPorIdYCompararSuPrecio() {
		Cliente cli1 = new Cliente();
		clienteRepo.save(cli1);
		Compra compra1 = new Compra(null, Double.valueOf(50), null, cli1, null);
		//Compra compra2 = new Compra(null, Double.valueOf(0), null);
		Compra compraGuardada1 = compraRepo.save(compra1);
		//Compra compraGuardada2 = compraRepo.save(compra2);
		Compra compraBuscada = compraRepo.findById(compraGuardada1.getId_compra()).orElse(null);
		assertEquals(Double.valueOf(50), compraBuscada.getPrecio());
	}

	@Test
	public void eliminarUnaCompra() {
		Cliente cli1 = new Cliente();
		clienteRepo.save(cli1);
		Compra compra1 = new Compra(null, Double.valueOf(50), null, cli1, null);
		Compra compraGuardada1 = compraRepo.save(compra1);
		assertEquals(1, compraRepo.findAll().size());
		compraRepo.deleteById(compraGuardada1.getId_compra());
		assertEquals(0, compraRepo.findAll().size());
	}

	
}
