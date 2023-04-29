package com.K_Five.web.models.entity;

import java.awt.Image;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Game")
public class Game implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String nombre;
	private String Descripcion;
	private Double precio;
	private String image;
	private String downloadLink;
	
	
	

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public String getImage() 
	{
		return image;
	}

	public void setImage(String image) 
	{
		this.image = image;
	}

	public Long getID() 
	{
		
		return ID;
	}

	public void setID(Long ID) 
	{		
		this.ID = ID;
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

	private static final long serialVersionUID = 7914876736365365909L;

	@Override
	public String toString() 
	{
		return "Game [ID=" + ID + ", nombre=" + nombre + 
				", Descripcion=" +	Descripcion + 
				", precio=" + precio  +
				", IMG= " + image + "]";
	}
	
	
	
}
