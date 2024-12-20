package com.example.prueba2024;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.prueba2024.controller.ProductoController;
import com.example.prueba2024.model.Producto;
import com.example.prueba2024.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

	//usar autowired aca para notener q instanciarlo
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@InjectMocks
	private ProductoController productoController;

	@Mock
	private IProductoService iProductoService;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
		this.objectMapper = new ObjectMapper();
	}

	//@Disabled
	@Test
	public void crearProductoTest() throws Exception {
		Producto p1 = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
		Mockito.when(this.iProductoService.crearProducto(Mockito.any(Producto.class))).thenReturn(p1);
		String p1JSON = objectMapper.writeValueAsString(p1);
		System.out.println(p1JSON);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/productos/crear")
				.contentType(MediaType.APPLICATION_JSON)
				.content(p1JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("Se ha creado el producto"));
	}

	@Test
	public void deberiaListarTodosLosProductos() throws Exception {
	    Producto prod1 = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
	    Producto prod2 = new Producto(2L, "fanta", "gaseosa", new ArrayList<>());
	    Producto prod3 = new Producto(3L, "sprite", "gaseosa", new ArrayList<>());
	    List<Producto> productos = new ArrayList<>();
	    productos.add(prod1);
	    productos.add(prod2);
	    productos.add(prod3);

	    Mockito.when(this.iProductoService.listarProductos()).thenReturn(productos);

	    // Convert the list to JSON
	    String listaProductosJson = objectMapper.writeValueAsString(productos);

	    Assert.assertEquals(3, iProductoService.listarProductos().size());
	    mockMvc.perform(MockMvcRequestBuilders.get("/productos/listar")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json(listaProductosJson));
	}
}
