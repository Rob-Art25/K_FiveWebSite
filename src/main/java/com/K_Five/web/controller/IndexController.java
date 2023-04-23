package com.K_Five.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController 
{
	
	@GetMapping("/index")
	public String index()
	{
		
		// El return busca el archivo con extensión html...
		return "index";
	}
	
}
