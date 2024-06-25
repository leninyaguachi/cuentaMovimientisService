package com.banco.cuentamovimientosservice.controller;

import com.banco.cuentamovimientosservice.entity.Cuenta;
import com.banco.cuentamovimientosservice.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta createCuenta(@Valid @RequestBody Cuenta cuenta) {
        return cuentaService.save(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @Valid @RequestBody Cuenta cuentaDetails) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        if (cuenta.isPresent()) {
            Cuenta updatedCuenta = cuenta.get();
            updatedCuenta.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
            updatedCuenta.setTipoCuenta(cuentaDetails.getTipoCuenta());
            updatedCuenta.setSaldoInicial(cuentaDetails.getSaldoInicial());
            updatedCuenta.setEstado(cuentaDetails.isEstado());
            return ResponseEntity.ok(cuentaService.save(updatedCuenta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        if (cuenta.isPresent()) {
            cuentaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}