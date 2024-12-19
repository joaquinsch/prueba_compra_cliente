package com.example.prueba2024;


import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.repository.CompraRepositorio;
import com.example.prueba2024.service.CompraService;

@ExtendWith(MockitoExtension.class)
public class ServicioCompraTest {
	
	@Mock
	private CompraRepositorio compraRepo;
	
	@InjectMocks
	private CompraService compraServicio;

	
	@Test
	public void listarCompras() {
		/*List<Compra> comprasMock = new ArrayList<>();
		comprasMock.add(new Compra(null, Double.valueOf(50), LocalDate.of(2024,11, 01)));
		comprasMock.add(new Compra(null, Double.valueOf(100), LocalDate.of(2024,11, 02)));
		comprasMock.add(new Compra(null, Double.valueOf(50.5), LocalDate.of(2024,11, 22)));
		comprasMock.add(new Compra(null, Double.valueOf(23000), LocalDate.of(2024,11, 10)));

		when(compraServicio.listarCompras()).thenReturn(comprasMock);
		
		List<Compra> compras = compraServicio.listarCompras();
		assertEquals(4, compras.size());*/
		
		//given
		List<Compra> comprasMock = new ArrayList<>();
		comprasMock.add(new Compra(null, Double.valueOf(50), LocalDate.of(2024,11, 01), null, null));
		comprasMock.add(new Compra(null, Double.valueOf(100), LocalDate.of(2024,11, 02), null, null));
		comprasMock.add(new Compra(null, Double.valueOf(50.5), LocalDate.of(2024,11, 22), null, null));
		comprasMock.add(new Compra(null, Double.valueOf(23000), LocalDate.of(2024,11, 10), null, null));
		given(compraRepo.findAll()).willReturn(comprasMock);
		
		//when
		List<Compra> compras = compraServicio.listarCompras();
		
		//then
		//assertThat(compras).isNotNull();
		assertEquals(4, compras.size());
	}

	@Test
	public void guardarCompra() {
		//given
		Compra compra = new Compra(null, Double.valueOf(50), LocalDate.of(2024, 11, 01), null , null);
		given(compraRepo.save(compra)).willReturn(compra);
		
		//when
		Compra compraGuardada = compraServicio.crearCompra(compra);
		
		//then
		Assert.assertNotNull(compraGuardada);
	}

	@Test
	public void listaComprasVacia() {
		List<Compra> comprasVacia = new ArrayList<>();
		given(compraRepo.findAll()).willReturn(comprasVacia);
		
		List<Compra> compras = compraServicio.listarCompras();
		
		//assertThat(compras).isEmpty();
		assertEquals(0, compras.size());
	}

	@Test
	public void buscarCompra() {
		//given

		Compra compra1 = new Compra(1L, Double.valueOf(50), LocalDate.of(2024, 11, 01), null , null);
		given(compraRepo.save(compra1)).willReturn(compra1);
		given(compraRepo.findById(compra1.getId_compra())).willReturn(Optional.of(compra1));

		//when
		Compra guardada = compraServicio.crearCompra(compra1);
		Compra buscada = compraServicio.buscarCompra(guardada.getId_compra());

		//assertThat(buscada).isNotNull();
		//assertThat(buscada.getId_compra()).isEqualTo(1L);
	} 

	@Test
	public void actualizarCompra() {
		Compra unaCompra = new Compra(1L, Double.valueOf(50), LocalDate.of(2024, 11, 01), 
				new Cliente(1L, "Carlos", "Pérez", "carlitosperez@gmail.com", LocalDate.of(1964, 10, 5), new ArrayList<>()), null);
		Compra compraEditada = new Compra(1L, Double.valueOf(60), LocalDate.of(2024, 11, 01), 
		            new Cliente(1L, "Carlos", "Pérez", "carlitosperez@gmail.com", LocalDate.of(1964, 10, 5), new ArrayList<>()), null);

		Mockito.when(this.compraRepo.findById(compraEditada.getId_compra())).thenReturn(Optional.of(unaCompra));
		Mockito.when(this.compraRepo.save(compraEditada)).thenReturn(compraEditada);
		
		Compra actualizada = this.compraServicio.editarCompra(compraEditada);
		
		Assert.assertEquals(unaCompra.getPrecio(), Double.valueOf(50));
		Assert.assertEquals(actualizada.getPrecio(), Double.valueOf(60));
	}
	
	
}
