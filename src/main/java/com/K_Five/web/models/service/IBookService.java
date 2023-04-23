package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.K_Five.web.models.entity.Book;

public interface IBookService 
{
	public List<Book> obtenerTodos();
	public Book guardar(Book book);
	public Book buscar(Long id);
	public void eliminar(Long id);
	public Page<Book> obtenerTodosPagina(Pageable pageable);
}
