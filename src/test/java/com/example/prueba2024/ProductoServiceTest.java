package com.example.prueba2024;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.model.Producto;
import com.example.prueba2024.repository.ProductoRepositorio;
import com.example.prueba2024.service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
	
	@Mock
	private ProductoRepositorio productoRepo;
	
	@InjectMocks
	private ProductoService productoService;
	
	@Test
	public void crearUnProducto() {
		Producto prod = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
		Mockito.when(productoRepo.save(prod)).thenReturn(prod);
		Producto resultado = productoService.crearProducto(prod);

		// verifica que con productoRepo se haya llamado a .save()
		Mockito.verify(productoRepo).save(prod);
		Assert.assertEquals(resultado.getCodigo_producto(), Long.valueOf(1L));
		Assert.assertEquals(resultado.getNombre(), "coca");
		Assert.assertEquals(resultado.getDescripcion(), "gaseosa");
		Assert.assertEquals(resultado.getCompras().size(), 0);
	}

	@Test
	public void crearUnProductoAsociadoAUnaCompra() {
		Cliente cliente = new Cliente(1L, "jose", "perez", "asd@gmail.com", LocalDate.of(1999, 9, 29), new ArrayList<>());
		Producto prod = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
		Compra compra = new Compra(1L, Double.valueOf(5000), LocalDate.of(2024, 12, 19), cliente, new ArrayList<>());
		compra.getProductos().add(prod);
		cliente.getCompras().add(compra);
		prod.getCompras().add(compra);

		Mockito.when(this.productoRepo.save(prod)).thenReturn(prod);
		Producto resultado = this.productoService.crearProducto(prod);

		Mockito.verify(productoRepo).save(prod);
		Assert.assertEquals(resultado.getCodigo_producto(), Long.valueOf(1L));
		Assert.assertEquals(resultado.getCodigo_producto(), Long.valueOf(1L));
		Assert.assertEquals(resultado.getNombre(), "coca");
		Assert.assertEquals(resultado.getDescripcion(), "gaseosa");
		Assert.assertEquals(resultado.getCompras().size(), 1);
		Assert.assertEquals(resultado.getCompras().get(0).getCliente().getNombre(), "jose");
	}

	@Test
	public void listarLosProductos() {
		Producto prod1 = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
		Producto prod2 = new Producto(2L, "fanta", "gaseosa", new ArrayList<>());
		Producto prod3 = new Producto(3L, "sprite", "gaseosa", new ArrayList<>());
		List<Producto> productos = new ArrayList<>();
		productos.add(prod1);
		productos.add(prod2);
		productos.add(prod3);
		Mockito.when(this.productoRepo.findAll()).thenReturn(productos);

		List<Producto> resultado = this.productoService.listarProductos();

		Mockito.verify(productoRepo).findAll();
		Assert.assertEquals(resultado.size(), 3);
		Assert.assertEquals(resultado.get(0), productos.get(0));
		Assert.assertEquals(resultado.get(1), productos.get(1));
		Assert.assertEquals(resultado.get(2), productos.get(2));
		
	}
}
