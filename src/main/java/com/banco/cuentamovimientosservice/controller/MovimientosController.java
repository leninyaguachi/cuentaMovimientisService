package com.banco.cuentamovimientosservice.controller;

import com.banco.cuentamovimientosservice.entity.Movimientos;
import com.banco.cuentamovimientosservice.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    private final MovimientosService movimientosService;

    @Autowired
    public MovimientosController(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    @GetMapping
    public List<Movimientos> getAllMovimientos() {
        return movimientosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientosById(@PathVariable Long id) {
        Optional<Movimientos> movimientos = movimientosService.findById(id);
        return movimientos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimientos createMovimientos(@Valid @RequestBody Movimientos movimientos) {
        return movimientosService.save(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimientos(@PathVariable Long id, @Valid @RequestBody Movimientos movimientosDetails) {
        Optional<Movimientos> movimientos = movimientosService.findById(id);
        if (movimientos.isPresent()) {
            Movimientos updatedMovimientos = movimientos.get();
            updatedMovimientos.setFecha(movimientosDetails.getFecha());
            updatedMovimientos.setTipoMovimiento(movimientosDetails.getTipoMovimiento());
            updatedMovimientos.setValor(movimientosDetails.getValor());
            updatedMovimientos.setSaldo(movimientosDetails.getSaldo());
            return ResponseEntity.ok(movimientosService.save(updatedMovimientos));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimientos(@PathVariable Long id) {
        Optional<Movimientos> movimientos = movimientosService.findById(id);
        if (movimientos.isPresent()) {
            movimientosService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}