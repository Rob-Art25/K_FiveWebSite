package com.K_Five.web.controller;

import java.net.MalformedURLException;
import java.util.Map;

import javax.annotation.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.K_Five.web.models.entity.Song;
import com.K_Five.web.models.service.ISongService;
import com.K_Five.web.models.service.IUploadService;

@Controller
@RequestMapping("/song")
public class MusicController 
{

	@Autowired
	ISongService musicService;
	
	@Autowired
	IUploadService uploadService;
	
	
	@GetMapping("/music")
	public String music(Model model)
	{
		model.addAttribute("listaCanciones", musicService.obtenerTodos());
		return "music";
	}
	
	@GetMapping("/registrarSong")
	public String registrar(Map<String, Object> model)
	{
		Song song = new Song();
		model.put("titulo", "Registrar Canción");
		model.put("cancionNueva", song);		
		return "/Music/SongRegister";
	}

	
	@GetMapping("editarSong/{ID}")
	public String editar(@PathVariable(value = "ID") Long id, Map<String, Object>model)
	{
		// Llamar al Dao para editar:
		Song song = null;
		
		model.put("titulo", "Editar Canción");
		
		if(id > 0)
		{
			song = musicService.buscar(id);
		}
		else
		{
			return "/music";
		}
		
		model.put("cancionNueva", song);
		
		return "Music/SongRegister";
	}
	
	@GetMapping("/eliminarSong/{ID}")
	public String eliminar(@PathVariable(value = "ID")Long id, Map<String, Object> model)
	{
		if(id > 0)
		{
			Song song = musicService.buscar(id);
			musicService.eliminar(song.getID());
		}
		
		return "redirect:/song/music";
	}
	
	@PostMapping("/saveSong")
	public String coverArt(@Validated @ModelAttribute("song") Song song, BindingResult result, Model model,
			@RequestParam(name = "file", required = false) MultipartFile image, RedirectAttributes flash) throws Exception
	{
		if(result.hasErrors())
		{
			System.out.println(result.getFieldError());
			return "song/music";
		}
		else
		{
			if(!image.isEmpty())
			{
				if(song.getID() != null && song.getCaratula() != null && song.getCaratula().length() > 0)
				{
					uploadService.delete(song.getCaratula());
				}
				String uniqueFilename = uploadService.copy(image);
				song.setCaratula(uniqueFilename);
			}
			musicService.guardar(song);
			System.out.println(song.toString());
			flash.addFlashAttribute("success", "Éxito al cargar la carátula!");			
		}		
		return "redirect:/song/music";
	}
	
	@GetMapping(value = "/uploads/{filename}")
	public ResponseEntity<Resource> goToImage(@PathVariable String filename)
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
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
		resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/song/{ID}")
	public String songCard(@PathVariable(value = "ID") Long id, Map<String, Object> model)
	{
		
		Song song = musicService.buscar(id);
		
		model.put("song", song.getNombre());
		model.put("descripcion", song.getDescripcion());
		model.put("precio", song.getPrecio());
		model.put("caratula", song.getCaratula());
		// Poner aquí el Álbum... model.put("album", song.getAlbum());
		return "/Music/card";
	}
}
