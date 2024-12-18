package com.example.prueba2024;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.Invocation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.prueba2024.controller.ClienteController;
import com.example.prueba2024.controller.CompraController;
import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.model.Compra;
import com.example.prueba2024.service.IClienteService;
import com.example.prueba2024.service.ICompraService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class CompraControllerTest {
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@InjectMocks
	private CompraController compraController;

	@Mock
	private ICompraService iCompraServicio;
	
	@Mock
	private IClienteService iClienteService;
	
	@BeforeEach
	public void setUp() {
		/*
		 * MockitoAnnotations.openMocks(this) es una llamada a la 
		 * API de Mockito que inicializa los mocks 
		 * (objetos simulados o "mocked") en el contexto de la prueba.
		 * 
		 * Esto es útil cuando tienes mocks declarados con la anotación @Mock y 
		 * deseas que se inicien antes de cada prueba.
		 */
		MockitoAnnotations.openMocks(this);
		/*
		 * MockMvcBuilders.standaloneSetup(compraController): Crea una configuración de MockMvc 
		 * de manera "independiente" 
		 * usando el controlador compraController. Esto indica que solo este controlador será 
		 * utilizado en el test.
		 * .build(): Finalmente, construye el objeto MockMvc que usarás para 
		 * realizar las pruebas en tu controlador.
		 */
		this.mockMvc = MockMvcBuilders.standaloneSetup(compraController).build();
		this.objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());// necesario para las fechas
	}
	
	Compra COMPRA_1 = new Compra(1L, Double.valueOf(10000), LocalDate.of(1993, 8, 20), null);
	Compra COMPRA_2 = new Compra(2L, Double.valueOf(678), LocalDate.of(2005, 8, 3), null);
	Compra COMPRA_3 = new Compra(3L, Double.valueOf(500), LocalDate.of(1993, 2, 04), null);
	List<Compra> comprasCliente = new ArrayList<>();
	Cliente cliente = new Cliente(1L, "Carlos", "Pérez", "carlitosperez@gmail.com", LocalDate.of(1964, 10, 5), comprasCliente);
	Compra COMPRA_4 = new Compra(4L, Double.valueOf(45), LocalDate.of(2020, 8, 3), cliente);
	Compra COMPRA_4_EDITADA = new Compra(4L, Double.valueOf(50), LocalDate.of(2019, 8, 20), cliente);

	@Test
	public void obtenerTodasLasComprasTest() throws Exception {
		List<Compra> listaCompras = new ArrayList<>();
		listaCompras.add(COMPRA_1);
		listaCompras.add(COMPRA_2);
		listaCompras.add(COMPRA_3);
		Mockito.when(iCompraServicio.listarCompras()).thenReturn(listaCompras);

		mockMvc.perform(MockMvcRequestBuilders
			.get("/compras/listar")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id_compra").value(1))  // Verifica el primer campo "id" del primer objeto en la lista
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].precio").value(10000))  // Verifica el nombre del primer producto
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id_compra").value(2))  // Verifica el id del segundo producto
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].precio").value(678))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].id_compra").value(3))  // Verifica el id del segundo producto
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].precio").value(500));
	}

	@Test
	public void crearCompraTest() throws Exception {
		Mockito.when(iCompraServicio.buscarCompra(COMPRA_4.getId_compra())).thenReturn(COMPRA_4);
		Mockito.when(iCompraServicio.crearCompra(COMPRA_4)).thenReturn(COMPRA_4);
		Mockito.when(iClienteService.buscarCliente(COMPRA_4.getCliente().getId())).thenReturn(COMPRA_4.getCliente());
		// paso la compra a json
		String compraJson = objectMapper.writeValueAsString(COMPRA_4);
		//System.out.println(compraJson);
		Assert.assertNotNull(COMPRA_4.getCliente());
		mockMvc.perform(MockMvcRequestBuilders.post("/compras/crear")
				.contentType(MediaType.APPLICATION_JSON)
				.content(compraJson))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("Se ha creado la compra"));
	}

	//@Disabled
	@Test
	public void eliminarCompraTest() throws Exception{
		Mockito.when(iCompraServicio.buscarCompra(COMPRA_4.getId_compra())).thenReturn(COMPRA_4);
		Mockito.doNothing().when(iCompraServicio).eliminarCompra(COMPRA_4.getId_compra());
		mockMvc.perform(MockMvcRequestBuilders.delete("/compras/eliminar/4")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isAccepted())
			.andExpect(MockMvcResultMatchers.content().string("Se ha eliminado la compra"));
	}

	@Test 
	public void eliminarCompraQueNoExiste() throws Exception{
		Mockito.when(iCompraServicio.buscarCompra(COMPRA_4.getId_compra())).thenReturn(COMPRA_4);
		Mockito.doNothing().when(iCompraServicio).eliminarCompra(COMPRA_4.getId_compra());
		mockMvc.perform(MockMvcRequestBuilders.delete("/compras/eliminar/6")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("La compra no existe"));	
	}

	@Test
	public void editarCompra() throws Exception{
		Mockito.when(iCompraServicio.buscarCompra(COMPRA_4.getId_compra())).thenReturn(COMPRA_4);
		// al editarla, ya se le pasan los datos modificados
		Mockito.when(iCompraServicio.editarCompra(COMPRA_4_EDITADA)).thenReturn(COMPRA_4_EDITADA);
		String compraEditadaJson = objectMapper.writeValueAsString(COMPRA_4_EDITADA);

		mockMvc.perform(MockMvcRequestBuilders.put("/compras/editar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(compraEditadaJson))
				.andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().string("Se ha editado la compra"));
	}

	@Test
	public void obtenerCompraDeMayorPrecio() throws Exception{
		Mockito.when(iCompraServicio.obtenerCompraDeMayorPrecio()).thenReturn(COMPRA_1);
		String compraMayorJson = this.objectMapper.writeValueAsString(COMPRA_1);
		mockMvc.perform(MockMvcRequestBuilders.get("/compras/compramayor")
				.contentType(MediaType.APPLICATION_JSON)
				.content(compraMayorJson))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(10000));
	}
}
