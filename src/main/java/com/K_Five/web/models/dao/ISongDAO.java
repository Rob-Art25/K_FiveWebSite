package com.K_Five.web.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.K_Five.web.models.entity.Song;

public interface ISongDAO extends PagingAndSortingRepository<Song, Long>
{

}
