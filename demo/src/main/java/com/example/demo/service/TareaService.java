
package com.example.demo.service;

import com.example.demo.model.Tarea;
import com.example.demo.repository.TareaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    // Consultar todas las tareas
    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }

    // Consultar una tarea por ID
    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tarea no encontrada: " + id));
    }

    // Crear una tarea
    public Tarea crear(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    // Actualizar una tarea
    public Tarea actualizar(Long id, Tarea datos) {

        Tarea tareaExistente = obtenerPorId(id);

        tareaExistente.setTitulo(datos.getTitulo());
        tareaExistente.setDescripcion(datos.getDescripcion());
        tareaExistente.setEstado(datos.getEstado());

        return tareaRepository.save(tareaExistente);
    }

    // Eliminar una tarea
    public void eliminar(Long id) {

        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada: " + id);
        }

        tareaRepository.deleteById(id);
    }
}
