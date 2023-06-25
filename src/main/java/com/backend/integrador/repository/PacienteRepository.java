package com.backend.integrador.repository;

import com.backend.integrador.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository <Paciente,Long> {
}
