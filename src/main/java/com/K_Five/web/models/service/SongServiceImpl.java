package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.K_Five.web.models.dao.ISongDAO;
import com.K_Five.web.models.entity.Song;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongServiceImpl implements ISongService 
{
	
	// Usando el CRUD Repository
	@Autowired
	ISongDAO songDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Song> obtenerTodos() 
	{
		
		return (List<Song>)songDAO.findAll();
	}

	@Override
	@Transactional
	public Song guardar(Song song) 
	{
		
		return songDAO.save(song);
	}

	@Override
	@Transactional
	public Song buscar(Long id) 
	{	
		
		return songDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) 
	{
		
		songDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Song> obtenerTodosPagina(Pageable pageable) 
	{
		
		return songDAO.findAll(pageable);
	}
	
	
}
