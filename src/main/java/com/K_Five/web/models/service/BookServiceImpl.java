package com.K_Five.web.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.K_Five.web.models.dao.IBooksDAO;
import com.K_Five.web.models.entity.Book;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements IBookService
{
	//Usando el CRUD Repository
	@Autowired
	IBooksDAO booksDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Book> obtenerTodos() 
	{
		
		return (List<Book>)booksDAO.findAll();
	}

	@Override
	@Transactional
	public Book guardar(Book book) 
	{
		
		return booksDAO.save(book);
	}

	@Override
	@Transactional
	public Book buscar(Long id) 
	{
		
		return booksDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) 
	{
		booksDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Book> obtenerTodosPagina(Pageable pageable) 
	{	
		
		return null;
	}
	
	
}
