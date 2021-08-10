package com.springboot.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.app.models.service.IUsuariosService;

@Controller
public class PlaniController {
	
	@Autowired
	private IUsuariosService planiService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value="/listarpla", method=RequestMethod.GET)
	public String listarp(Model model) {
		model.addAttribute("titulo", "Listado de Planificaciones");
		model.addAttribute("planificacione", planiService.findAll2());
			
		return "listarpla";
	}	

}
