package com.backend.integrador.controller;


import com.backend.integrador.dto.TurnoDto;
import com.backend.integrador.entity.Turno;
import com.backend.integrador.exceptions.BadRequestException;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final ITurnoService turnoService;

    @Autowired
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarTurnoPorId(@PathVariable Long id) {
        TurnoDto turnoDto = turnoService.buscarTurnoPorId(id);
        if (turnoDto != null) {
            return ResponseEntity.ok(turnoDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public List<TurnoDto> listarTurnos() {
        return turnoService.listarTodos();
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws BadRequestException {
        TurnoDto turnoDto = turnoService.guardarTurno(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoDto);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarPaciente(@RequestBody Turno turno) {
        TurnoDto turnoDto = turnoService.actualizarTurno(turno);
        if (turnoDto != null) {
            return ResponseEntity.ok(turnoDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}