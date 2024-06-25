package com.banco.cuentamovimientosservice.controller;

import com.banco.cuentamovimientosservice.entity.Movimientos;
import com.banco.cuentamovimientosservice.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final MovimientosRepository movimientosRepository;

    @Autowired
    public ReporteController(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movimientos>> getReportes(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<Movimientos> movimientos = movimientosRepository.findAllByFechaBetween(fechaInicio, fechaFin);
            return ResponseEntity.ok(movimientos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}