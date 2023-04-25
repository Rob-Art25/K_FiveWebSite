package com.K_Five.web.controller;

import java.net.MalformedURLException;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.K_Five.web.models.entity.Book;
import com.K_Five.web.models.entity.Song;
import com.K_Five.web.models.service.IBookService;
import com.K_Five.web.models.service.IUploadService;

@Controller
@RequestMapping("/book")
public class BookController 
{

	@Autowired
	IBookService bookService;
	
	@Autowired
	IUploadService uploadService;
	
	@GetMapping("/books")
	public String books(Model model)
	{
		model.addAttribute("listaBooks", bookService.obtenerTodos());
		return "books";
	}
	
	@GetMapping("/registrarBook")
	public String registrar(Map<String, Object> model)
	{
		Book book = new Book();
		model.put("titulo", "Registrar Nuevo Libro");
		model.put("libroNuevo", book);		
		return "/Books/BookRegister";
	}
	
	@PostMapping("/saveBook")
	public String coverArt(@Validated @ModelAttribute("song") Book book, BindingResult result, Model model,
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
				if(book.getID() != null && book.getCaratula() != null && book.getCaratula().length() > 0)
				{
					uploadService.delete(book.getCaratula());
				}
				String uniqueFilename = uploadService.copy(image);
				book.setCaratula(uniqueFilename);
			}
			bookService.guardar(book);
			System.out.println(book.toString());
			flash.addFlashAttribute("success", "Éxito al cargar la carátula!");			
		}		
		return "redirect:/book/books";
	}
	
	@GetMapping("editarBook/{ID}")
	public String editar(@PathVariable(value = "ID") Long id, Map<String, Object>model)
	{
		// Llamar al Dao para editar:
		Book book = null;
		
		model.put("titulo", "Editar Libro");
		
		if(id > 0)
		{
			book = bookService.buscar(id);
		}
		else
		{
			return "/books";
		}
		
		model.put("libroNuevo", book);
		
		return "Books/BookRegister";
	}
	
	@GetMapping("/eliminarBook/{ID}")
	public String eliminar(@PathVariable(value = "ID")Long id, Map<String, Object> model)
	{
		if(id > 0)
		{
			Book book = bookService.buscar(id);
			bookService.eliminar(book.getID());
		}
		
		return "redirect:/book/books";
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
	
	@GetMapping("/book/{ID}")
	public String songCard(@PathVariable(value = "ID") Long id, Map<String, Object> model)
	{
		
		Book book = bookService.buscar(id);
		
		model.put("libro", book.getNombre());
		model.put("descripcion", book.getDescripcion());
		model.put("precio", book.getPrecio());
		model.put("caratula", book.getCaratula());
		// Poner aquí el Álbum... model.put("album", song.getAlbum());
		return "/Books/card";
	}
	
}
