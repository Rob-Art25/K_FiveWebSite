package com.K_Five.web.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadService  
{
	public Resource load(String fileName) throws MalformedURLException;
	public String copy(MultipartFile file) throws IOException;
	public boolean delete(String fileName);
}
