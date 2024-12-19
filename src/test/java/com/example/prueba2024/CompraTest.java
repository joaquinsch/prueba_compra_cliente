package com.example.prueba2024;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.prueba2024.model.Compra;

public class CompraTest {

	@Test
	public void compraTienePrecioInicialDeCero() {
		Compra compra = new Compra(null, 0.0, null, null, null);
		assertEquals(Double.valueOf(0) ,compra.getPrecio());
	}

	@Test
	public void compraTieneUnaFechaEnNull() {
		Compra compra = new Compra(null, 0.0, null, null, null);
		assertEquals(null, compra.getFecha());
	}

	
}
