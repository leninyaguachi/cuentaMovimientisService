package com.banco.cuentamovimientosservice.integration;

import com.banco.cuentamovimientosservice.entity.Movimientos;
import com.banco.cuentamovimientosservice.repository.MovimientosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class IntegrationTest {

    private final RabbitTemplate rabbitTemplate;
    private final MovimientosRepository movimientosRepository;

    @Autowired
    public IntegrationTest(RabbitTemplate rabbitTemplate, MovimientosRepository movimientosRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.movimientosRepository = movimientosRepository;
    }

    @Test
    public void testSendAndReceiveMessage() {
        // Crear y guardar un movimiento
        Movimientos movimiento = new Movimientos();
        movimiento.setFecha(LocalDate.now());
        movimiento.setTipoMovimiento("deposito");
        movimiento.setValor(100);
        movimiento.setSaldo(100);

        movimientosRepository.save(movimiento);

        // Enviar un mensaje a la cola de RabbitMQ
        rabbitTemplate.convertAndSend("movimientos", "Test message");

        // Verificar que los movimientos existan en la base de datos
        List<Movimientos> movimientos = movimientosRepository.findAll();
        assertFalse(movimientos.isEmpty());
    }
}
