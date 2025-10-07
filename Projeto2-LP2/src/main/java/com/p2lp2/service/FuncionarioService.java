package com.p2lp2.service;

import com.p2lp2.model.Cargo;
import com.p2lp2.model.Funcionario;
import com.p2lp2.model.Ponto;
import com.p2lp2.model.Departamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FuncionarioService {
    private EntityManager em;

    public FuncionarioService(EntityManager em) {
        this.em = em;
    }

    // a desenvolver
    public void baterPonto(Funcionario func, String tipo) {
        Ponto ponto = new Ponto(func, LocalDateTime.now(), tipo);
        em.persist(ponto);
    }
}