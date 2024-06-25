package com.banco.cuentamovimientosservice.repository;

import com.banco.cuentamovimientosservice.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {}