package com.example.prueba2024;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
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

	@Test
	public void crearProductoTest() throws Exception {
		Producto p1 = new Producto(1L, "coca", "gaseosa", new ArrayList<>());
		Mockito.when(this.iProductoService.crearProducto(p1)).thenReturn(p1);
		String p1JSON = objectMapper.writeValueAsString(p1);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/productos/crear")
				.contentType(MediaType.APPLICATION_JSON)
				.content(p1JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.codigo_producto").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("coca"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value("gaseosa"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.compras").isEmpty())
				.andExpect(MockMvcResultMatchers.content().string("Se ha creado el producto"));
	}
}
