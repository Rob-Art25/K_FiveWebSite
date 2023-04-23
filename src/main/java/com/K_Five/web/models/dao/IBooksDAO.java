package com.K_Five.web.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.K_Five.web.models.entity.Book;

public interface IBooksDAO extends PagingAndSortingRepository<Book, Long>
{

}
