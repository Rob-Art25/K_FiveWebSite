package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.K_Five.web.models.dao.IGamesDAO;
import com.K_Five.web.models.entity.Game;

@Service
public class GameServiceImpl implements IGameService 
{

	// Usando el CRUD Repository:
	@Autowired
	IGamesDAO gamesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Game> obtenerTodos() 
	{
	
		return (List<Game>)gamesDAO.findAll();
	}

	@Override
	@Transactional
	public Game guardar(Game game) 
	{
		
		return gamesDAO.save(game);
	}

	@Override
	@Transactional
	public Game buscar(Long id) 
	{	
		
		return gamesDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) 
	{		
		gamesDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Game> obtenerTodosPagina(Pageable pageable) 
	{	
		
		return gamesDAO.findAll(pageable);
	}
	
}
