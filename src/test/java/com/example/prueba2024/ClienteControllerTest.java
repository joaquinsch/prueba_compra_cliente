package com.example.prueba2024;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.prueba2024.controller.ClienteController;
import com.example.prueba2024.service.IClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private ClienteController clienteController;
	
	@Mock
	private IClienteService iClienteService;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
	}

	@Test
	public void clienteHaceUnaCompraYSeAgregaASuListaDeCompras() {
		//Mockito.when(this.iClienteService)
	}
	
	
}
