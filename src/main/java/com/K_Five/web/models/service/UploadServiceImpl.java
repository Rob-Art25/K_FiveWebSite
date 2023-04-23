package com.K_Five.web.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements IUploadService 
{
	
	private final static String UPLOADS_FOLDER = "uploads";
	
	@Override
	public Resource load(String fileName) throws MalformedURLException 
	{
		Path path = getPath(fileName);
		Resource resource = new UrlResource(path.toUri());
		
		if(!resource.exists() || !resource.isReadable())
		{
			throw new RuntimeException("Error in path: " + path.toString());
		}		
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException
	{
		String UniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(UniqueFileName);
		Files.copy(file.getInputStream(), rootPath);		
		return UniqueFileName;
	}
	
	@Override
	public boolean delete(String fileName) 
	{
		Path rootPath = getPath(fileName);
		File file = rootPath.toFile();
		if(file.exists() && file.canRead())
		{
			if(file.delete())
			{
				return true;
			}
		}
		return false;
	}
	
	public Path getPath(String filename)
	{
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}
}
