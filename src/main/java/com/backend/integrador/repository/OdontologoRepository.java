package com.backend.integrador.repository;

import com.backend.integrador.entity.Odontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

}
