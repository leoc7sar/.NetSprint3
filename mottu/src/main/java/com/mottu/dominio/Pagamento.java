package com.mottu.dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    public enum Metodo { PIX, CARTAO, BOLETO }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Locacao locacao;

    @NotNull @Positive
    private BigDecimal valor;

    @NotNull
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    private Metodo metodo;

    public Pagamento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Locacao getLocacao() { return locacao; }
    public void setLocacao(Locacao locacao) { this.locacao = locacao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public Metodo getMetodo() { return metodo; }
    public void setMetodo(Metodo metodo) { this.metodo = metodo; }
}
