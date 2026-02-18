//package com.tfg.service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.tfg.model.Incidencia;
//import com.tfg.repository.IncidenciaRepository;
//
//@Service
//public class IncidenciaServiceImpl implements IncidenciaService {
//
//	private final IncidenciaRepository incidenciaRepository;
//
//	public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository) {
//		this.incidenciaRepository = incidenciaRepository;
//	}
//
//	@Override
//	public Incidencia crearIncidencia(Incidencia incidencia) {
//		incidencia.setFecha(LocalDate.now());
//		return incidenciaRepository.save(incidencia);
//	}
//
//	@Override
//	public List<Incidencia> listarIncidencias() {
//		return incidenciaRepository.findAll();
//	}
//
//	@Override
//	public Optional<Incidencia> obtenerPorId(Long id) {
//		return incidenciaRepository.findById(id);
//	}
//
//	@Override
//	public void eliminarIncidencia(Long id) {
//		incidenciaRepository.deleteById(id);
//	}
//}