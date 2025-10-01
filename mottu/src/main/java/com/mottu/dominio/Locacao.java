package com.mottu.dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "locacoes")
public class Locacao {

    public enum Status { ABERTA, ENCERRADA }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Entregador entregador;

    @ManyToOne(optional = false)
    private Moto moto;

    @NotNull
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotNull @Positive
    private BigDecimal valorDiaria;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ABERTA;

    public Locacao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Entregador getEntregador() { return entregador; }
    public void setEntregador(Entregador entregador) { this.entregador = entregador; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public BigDecimal getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(BigDecimal valorDiaria) { this.valorDiaria = valorDiaria; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
