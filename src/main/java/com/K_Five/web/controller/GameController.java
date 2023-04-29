package com.K_Five.web.controller;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.K_Five.web.models.entity.Game;
import com.K_Five.web.models.service.IGameService;
import com.K_Five.web.models.service.IUploadService;
import com.K_Five.web.util.PaginacionCalculos;

@Controller
@RequestMapping("/game")
public class GameController 
{
	@Autowired
	IGameService gamesService;
	
	@Autowired
	IUploadService uploadService;
	
	
	@GetMapping("/games")
	public String games(Model model)
	{			
		model.addAttribute("listaGames", gamesService.obtenerTodos());
		return "games";
	}
	
	
	@GetMapping("/listar/pag")
	public String listarPaginacion(@RequestParam(name = "page", defaultValue = "0")int page, Model model)
	{
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Game> games = gamesService.obtenerTodosPagina(pageRequest);
		
		PaginacionCalculos<Game> paginacionCalculos = new PaginacionCalculos<>("/Games/listar/pag", games);
		
		model.addAttribute("Titulo", "Juegos");
		model.addAttribute("listaGames", games);
		model.addAttribute("pagina", paginacionCalculos);
		
		model.addAttribute("paginacion", true);
		
		return "games";
	}

	
	@GetMapping("/registrar")
	public String registrar(Map<String, Object> model)
	{
		Game game = new Game();
		
		model.put("titulo", "Registrar Nuevo Juego 🎮");
		model.put("juegoNuevo", game);
		
		return "/Games/GameRegister";
	}
	
	@PostMapping("/registrar")
	public String guardar(Game game)
	{
		if(game.getID() != null)
		{
			System.out.println("Ya existe el objeto, Actualizando...");
			gamesService.guardar(game);
		}
		else
		{
			System.out.println("No existe el objeto, Registrando...");
			gamesService.guardar(game);
		}
		
		return "redirect:/games";
	}
	
	@GetMapping("editar/{ID}")
	public String editar(@PathVariable(value = "ID") Long id, Map<String, Object> model)
	{
		// Llamar al DAO para editar:
		Game game = null;
		
		model.put("titulo", "Editar Juego");
		if(id > 0)
		{
			game = gamesService.buscar(id);			
		}
		else
		{
			return "/games";
		}
		
		model.put("juegoNuevo", game);
		
		return "/Games/Gameregister";
	}
	
	@GetMapping("/eliminar/{ID}")
	public String eliminar(@PathVariable(value = "ID") Long id, Map<String, Object> model)
	{
		// Llamar al DAO para eliminar
		if(id != null && id > 0)
		{
			Game game = gamesService.buscar(id);
			gamesService.eliminar(game.getID());
		}
		return "redirect:/game/games";
	}
	
	// Cargar Páginas Específicas de cada Juego ---
	
	@GetMapping("/game/{ID}")
	public String gameCard(@PathVariable(value = "ID") Long id, Map<String, Object> model)
	{		
		Game game = gamesService.buscar(id);
		model.put("juego", game.getNombre());
		model.put("descripcion", game.getDescripcion());
		model.put("precio", game.getPrecio());
		model.put("image", game.getImage());
		model.put("link", game.getDownloadLink());
		return "/Games/card";
	}
	
	@GetMapping("/SuperPurpleBall")
	public String SPB()
	{
		
		return "Games/SuperPurpleBall";
	}
	
	@GetMapping("/AlexAndThePrincess")
	public String AAP()
	{
		
		return "Games/AlexAndThePrincess";
	}
	
	@GetMapping("/dogtrix")
	public String DGTX()
	{
		
		return "Games/dogtrix";
	}

	@GetMapping(value = "/uploads/{filename}")
	public ResponseEntity<Resource>goImage(@PathVariable String filename)
	{
		Resource resource = null;
		try 
		{
			resource = uploadService.load(filename);		
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}				
		return ResponseEntity.ok().header(
				HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + 
				resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping("/saveGameImage")
	public String saveScreenshot(@Validated @ModelAttribute("game") Game game, BindingResult result, Model model,
			@RequestParam(name = "file", required = false) MultipartFile image, RedirectAttributes flash) throws Exception
	{		
		if(result.hasErrors())
		{
			System.out.println(result.getFieldError());
			return "game/games";
		}
		else 
		{			
			if (!image.isEmpty()) 
			{
				if (game.getID() != null && game.getImage() != null && game.getImage().length() > 0) 
				{
					uploadService.delete(game.getImage());
				}
				String uniqueFilename = uploadService.copy(image);
				game.setImage(uniqueFilename);
				System.out.println("Logré registrar el juego con éxito...");
			}
			gamesService.guardar(game);
			
			System.out.println(game.toString());
			flash.addFlashAttribute("success", "Exito al subir la foto");				
		}
		
		
		return "redirect:/game/games";
	}
	
}
