package com.K_Five.web.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.K_Five.web.models.entity.Game;

@Repository
public interface IGamesDAO extends PagingAndSortingRepository<Game, Long> 
{

}
