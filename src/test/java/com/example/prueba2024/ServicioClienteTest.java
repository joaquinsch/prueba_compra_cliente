package com.example.prueba2024;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.prueba2024.model.Cliente;
import com.example.prueba2024.service.IClienteService;

@SpringBootTest
public class ServicioClienteTest {
	
	private IClienteService iClienteService;
	
	 /* @BeforeEach
	    public void setUp() {
	        clienteService = Mockito.mock(IClienteService.class);
	     }
	 */
	@Test
	public void testListarClientesListaConDosClientesMock() {
		iClienteService = Mockito.mock(IClienteService.class);
		List<Cliente> clientesMock = new ArrayList<>();
		clientesMock.add(new Cliente(null,"carlos","perez", "asd@gmail.com", LocalDate.of(1990, 5, 15), null));
		clientesMock.add(new Cliente(null,"jose","gomez", "asd123@gmail.com", LocalDate.of(2001, 10, 06), null));

		when(iClienteService.listarClientes()).thenReturn(clientesMock);

		List<Cliente> clientes = iClienteService.listarClientes();
		Assert.assertEquals(2, clientes.size());
	}
	
	@Test
	public void testListarClientesDespuesDeEliminarUnoMock() {
		iClienteService = Mockito.mock(IClienteService.class);
		List<Cliente> clientesMock = new ArrayList<>();
		clientesMock.add(new Cliente(1L,"carlos","perez", "asd@gmail.com", LocalDate.of(1990, 5, 15), null));
		clientesMock.add(new Cliente(2L,"jose","gomez", "asd123@gmail.com", LocalDate.of(2001, 10, 06), null));
		when(iClienteService.listarClientes()).thenReturn(clientesMock);
		List<Cliente> clientes = iClienteService.listarClientes();
		
		Assert.assertEquals(2, clientes.size());
		doAnswer(invocation -> {
	        Long idEliminar = invocation.getArgument(0);
	        clientesMock.removeIf(cliente -> cliente.getId().equals(idEliminar));
	        return null; // Método void
	    }).when(iClienteService).eliminarCliente(Mockito.anyLong());
		
		iClienteService.eliminarCliente(1L);
		
		Assert.assertEquals(1, clientes.size());
		Assert.assertEquals(clientes.get(0).getNombre(), "jose");
		
		
	}

	@Test
	public void buscarClienteMock() {
		iClienteService = Mockito.mock(IClienteService.class);
		List<Cliente> clientesMock = new ArrayList<>();
		clientesMock.add(new Cliente(1L,"carlos","perez", "asd@gmail.com", LocalDate.of(1990, 5, 15), null));
		Cliente cliente2 = new Cliente(2L,"jose","gomez", "asd123@gmail.com", LocalDate.of(2001, 10, 06), null);
		clientesMock.add(cliente2);
		
		doAnswer(invocation -> {
			Long idBuscado = invocation.getArgument(0);
			for (Cliente c : clientesMock) {
				if (c.getId().equals(idBuscado)) {
					return c;
				}
			}
			return null;
		}).when(iClienteService).buscarCliente(Mockito.anyLong());
		
		Cliente buscado = iClienteService.buscarCliente(2L);
		Assert.assertEquals(cliente2, buscado);
		
		Cliente clienteNoExistente = iClienteService.buscarCliente(3L);
		Assert.assertNull(clienteNoExistente);
	}

	@Test
	public void eliminarClienteMock() {
		iClienteService = Mockito.mock(IClienteService.class);
		List<Cliente> clientesMock = new ArrayList<>();
		clientesMock.add(new Cliente(1L,"carlos","perez", "asd@gmail.com", LocalDate.of(1990, 5, 15), null));
		Cliente cliente2 = new Cliente(2L,"jose","gomez", "asd123@gmail.com", LocalDate.of(2001, 10, 06), null);
		clientesMock.add(cliente2);
		
		when(iClienteService.listarClientes()).thenReturn(clientesMock);
		List<Cliente> clientes = iClienteService.listarClientes();
		
		doAnswer(invocation ->{
			Long idEliminar = invocation.getArgument(0); // es el numero de parametro del metodo mockeado
			for (Cliente c : clientesMock) {
				if (c.getId().equals(idEliminar)) {
					clientesMock.remove(c);
					break;
				}
			}
			return null;
		}).when(iClienteService).eliminarCliente(Mockito.anyLong());
		
		iClienteService.eliminarCliente(1L);
		Assert.assertEquals(1, clientes.size());
	}
}
