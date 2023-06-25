package com.backend.integrador.service.impl;

import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.repository.OdontologoRepository;
import com.backend.integrador.service.IOdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final ObjectMapper objectMapper;
    private final OdontologoRepository odontologoRepository;

    private final Gson gson;

    @Autowired
    public OdontologoService(ObjectMapper objectMapper, OdontologoRepository odontologoRepository) {
        this.objectMapper = objectMapper;
        this.odontologoRepository = odontologoRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public OdontologoDto buscarOdontologoPorId(Long id) {

        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoDto odontologoDto = null;
        if (odontologoBuscado != null) {
            odontologoDto = objectMapper.convertValue(odontologoBuscado, OdontologoDto.class);
            LOGGER.info("Odontologo encontrado: {}", gson.toJson(odontologoDto)); //TODO: Probar si hace falta usar la clase JsonPrinter para hacer el toString
        } else {
            LOGGER.info("El id no se encuentra registrado en la base de datos");


        }
        return odontologoDto;
    }


    @Override
    public List<OdontologoDto> listarOdontologos() {
        List<OdontologoDto> odontologos = odontologoRepository.findAll().stream()
                .map(o -> objectMapper.convertValue(o, OdontologoDto.class)).toList();
        ;
        LOGGER.info("Listado de todos los odontologos: {}", gson.toJson(odontologos));
        return odontologos;
    }

    @Override
    public OdontologoDto registrarOdontologo(Odontologo odontologo) {
        Odontologo odGuardado = odontologoRepository.save(odontologo);
        LOGGER.info("Odontologo guardado: {}", gson.toJson(odGuardado));
        return objectMapper.convertValue(odGuardado, OdontologoDto.class);
    }
    @Override
    public OdontologoDto actualizarOdontologo(Odontologo odontologo) {
        Odontologo odontologoAActualizar = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDto odontologoDtoActualizado = null;
        if (odontologoAActualizar != null) {
            odontologoAActualizar = odontologo;
            odontologoRepository.save(odontologoAActualizar);
            odontologoDtoActualizado = objectMapper.convertValue(odontologoAActualizar, OdontologoDto.class);
            LOGGER.warn("Odontologo actualizado: {}", gson.toJson(odontologoDtoActualizado));
        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el odontologo no se encuentra registrado");

        }
        return odontologoDtoActualizado;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if (buscarOdontologoPorId(id) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el odontologo con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el odontologo con id " + id);
        }
    }
}
