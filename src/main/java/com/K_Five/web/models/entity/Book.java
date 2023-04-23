package com.K_Five.web.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
public class Book implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String nombre;
	private String Descripcion;
	private Double precio;
	private String caratula;
	
	
	
	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public Long getID() 
	{
		return ID;
	}

	public void setID(Long iD) 
	{
		this.ID = iD;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getDescripcion() 
	{
		return Descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.Descripcion = descripcion;
	}

	public Double getPrecio() 
	{
		return precio;
	}

	public void setPrecio(Double precio) 
	{
		this.precio = precio;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	private static final long serialVersionUID = -2557474984202728103L;



	@Override
	public String toString() 
	{
		return "Book [ID=" + ID + ", nombre=" + nombre + 
				", Descripcion=" + Descripcion + ", precio=" + precio + "]";
	}	
}
