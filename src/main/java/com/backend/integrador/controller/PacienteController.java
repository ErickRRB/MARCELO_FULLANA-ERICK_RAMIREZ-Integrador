package com.backend.integrador.controller;

import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<PacienteDto> registrarPaciente(@RequestBody Paciente paciente) {
        PacienteDto pacienteDto = pacienteService.guardarPaciente(paciente);
        return Optional.ofNullable(pacienteDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@RequestBody Paciente paciente) {
        PacienteDto pacienteDto = pacienteService.actualizarPaciente(paciente);
        return Optional.ofNullable(pacienteDto)
                .map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    //GET
    @GetMapping
    public List<PacienteDto> listarTodos() {
        return pacienteService.listarPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPacientePorId(@PathVariable Long id) {
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.buscarPacientePorId(id);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public void eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
    }

}
