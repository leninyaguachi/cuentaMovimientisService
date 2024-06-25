package com.banco.cuentamovimientosservice.repository;

import com.banco.cuentamovimientosservice.entity.Cuenta;
import com.banco.cuentamovimientosservice.entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {
    List<Movimientos> findAllByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    Optional<Movimientos> findTopByCuentaOrderByFechaDesc(Cuenta cuenta);
}
