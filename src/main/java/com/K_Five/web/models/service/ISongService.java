package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.K_Five.web.models.entity.Song;

public interface ISongService 
{
	
	public List<Song> obtenerTodos();
	public Song guardar(Song song);
	public Song buscar(Long id);
	public void eliminar(Long id);
	public Page<Song> obtenerTodosPagina(Pageable pageable);
	
}
