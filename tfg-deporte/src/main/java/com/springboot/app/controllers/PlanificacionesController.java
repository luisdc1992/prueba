package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.ItemPlanificacione;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IUsuariosService;

@Controller
@RequestMapping("/planificacione")
@SessionAttributes("planificacione")
public class PlanificacionesController {
	
	@Autowired
	private IUsuariosService usuarioService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping("formf/{usuarioId}")
	public String crear(@PathVariable(value="usuarioId") Long usuarioId, Map<String, Object> model, RedirectAttributes flash) {
		logger.info("Luis 1 ");
		Usuario usuario = usuarioService.findOne(usuarioId);
		
		if(usuario == null) {
			flash.addFlashAttribute("error", "El Usuario no existe en la BBDD");
			return "redirect:/listaru";
		}
		
		Planificacione planificacione = new Planificacione();
		planificacione.setUsuario(usuario);
		
		model.put("planificacione", planificacione);
		model.put("titulo", "Crear Planificaciones");
		
		return "planificacione/formf";
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		logger.info("Luis 2 ");
		return usuarioService.finByNombre(term);
    }
	
	@PostMapping("/formf")
	public String guardar(Planificacione planificacione, BindingResult result, Model model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status) {
		
		logger.info("Luis 3");
	
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Plani");
			return "planificacione/formf";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Plani2");
			model.addAttribute("error", "Error: La Planificacion NO puede no tener líneas!");
			return "planificacione/formf";
		}
		
		for(int i = 0; i < itemId.length; i++) {
			//log.info("ID Luis: "+ itemId[i].toString() + ", cantidad: " +cantidad[i].toString());
			Producto producto =  usuarioService.findProductoById(itemId[i]);
			
			ItemPlanificacione linea = new ItemPlanificacione();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			planificacione.addItemPlanificacione(linea);
			
			//log.info("ID: "+ itemId[i].toString() + ", cantidad: " +cantidad[i].toString());
			
		}
			
		usuarioService.savePlanificacione(planificacione);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Planificacion creada con exito");
		return "redirect:/veru" + planificacione.getUsuario().getId();
	
	}

}