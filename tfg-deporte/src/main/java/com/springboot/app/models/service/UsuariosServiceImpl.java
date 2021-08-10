package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IPlanificacionesDao;
import com.springboot.app.models.dao.IProductosDao;
import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Usuario;

@Service
public class UsuariosServiceImpl implements IUsuariosService{
    
	@Autowired
	private IUsuarioDao usuarioDato;

	@Autowired
	private IProductosDao productoDao;
	
	@Autowired
	private IPlanificacionesDao planificacioneDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDato.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDato.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)	
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return usuarioDato.findById(id).orElse(null);
	}
 
	@Override
	@Transactional	
	public void delete(Long id) {
		usuarioDato.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)	
	public List<Producto> finByNombre(String term) {
		// TODO Auto-generated method stub
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void savePlanificacione(Planificacione planificacione) {
		// TODO Auto-generated method stub
		planificacioneDao.save(planificacione);
	}

	@Override
	@Transactional(readOnly = true)		
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}
	
	

}
