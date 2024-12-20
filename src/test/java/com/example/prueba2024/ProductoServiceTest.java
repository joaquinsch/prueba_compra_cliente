package com.example.prueba2024;

import java.time.LocalDate;
import java.util.ArrayList;

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
}
