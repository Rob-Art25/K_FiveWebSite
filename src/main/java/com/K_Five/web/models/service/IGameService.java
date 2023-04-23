package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.K_Five.web.models.entity.Game;

public interface IGameService 
{
	public List<Game> obtenerTodos();
	public Game guardar(Game game);
	public Game buscar(Long id);
	public void eliminar(Long id);
	public Page<Game> obtenerTodosPagina(Pageable pageable);
}
