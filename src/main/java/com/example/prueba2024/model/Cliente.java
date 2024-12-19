package com.example.prueba2024.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id_cliente;
	private String nombre;
	private String apellido;
	private String mail;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fecha_nacimiento;
	@OneToMany(mappedBy = "cliente")
	//@JsonManagedReference ESTO TRAE PROBLEMAS CON EL TEST DE CREAROFERTA DEL COMPRA CONTROLLER
	@JsonIgnore // ESTO HACE QUE NO OCURRA LA RECURSIVIDAD INFINITA AL LISTAR TODOS,
	// PERO NO MUESTRA LA LISTA DE COMPRAS DE CADA CLIENTE.
	private List<Compra> compras;

}
