package com.itm.apicalendario.repository;

import com.itm.apicalendario.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
    Optional<Tipo> findByTipo(String tipo);
}
