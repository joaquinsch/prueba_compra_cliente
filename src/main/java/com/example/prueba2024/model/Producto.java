package com.example.prueba2024.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productos")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long codigo_producto;
	private String nombre;
	private String descripcion;

	@ManyToMany
	@JoinTable(name = "compra_producto", joinColumns = 
		@JoinColumn(name = "id_producto", referencedColumnName = "id_producto"), 
		inverseJoinColumns = 
		@JoinColumn(name = "id_compra", referencedColumnName = "id_compra"))
	private List<Compra> compras;
}
