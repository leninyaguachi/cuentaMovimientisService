package com.banco.cuentamovimientosservice.service;

import com.banco.cuentamovimientosservice.entity.Movimientos;
import com.banco.cuentamovimientosservice.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientosService {

    private final MovimientosRepository movimientosRepository;

    @Autowired
    public MovimientosService(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    public List<Movimientos> findAll() {
        return movimientosRepository.findAll();
    }

    public Optional<Movimientos> findById(Long id) {
        return movimientosRepository.findById(id);
    }

    public Movimientos save(Movimientos movimientos) {
        return movimientosRepository.save(movimientos);
    }

    public void deleteById(Long id) {
        if (movimientosRepository.existsById(id)) {
            movimientosRepository.deleteById(id);
        } else {
            throw new RuntimeException("Movimiento no encontrado con id: " + id);
        }
    }

    public Movimientos registrarMovimiento(Movimientos movimientos) {
        Optional<Movimientos> ultimoMovimiento = movimientosRepository.findTopByCuentaOrderByFechaDesc(movimientos.getCuenta());
        double saldoActual = ultimoMovimiento.map(Movimientos::getSaldo).orElse(0.0);

        if (movimientos.getTipoMovimiento().equalsIgnoreCase("retiro") && saldoActual < movimientos.getValor()) {
            throw new IllegalStateException("Saldo no disponible");
        }

        movimientos.setSaldo(saldoActual + (movimientos.getTipoMovimiento().equalsIgnoreCase("deposito") ? movimientos.getValor() : -movimientos.getValor()));
        return movimientosRepository.save(movimientos);
    }
}