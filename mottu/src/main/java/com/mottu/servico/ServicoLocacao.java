package com.mottu.servico;

import com.mottu.dominio.*;
import com.mottu.repositorio.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ServicoLocacao {
  private final LocacaoRepositorio locacoes;
  private final MotoRepositorio motos;
  private final PagamentoRepositorio pagamentos;

  public ServicoLocacao(LocacaoRepositorio locacoes, MotoRepositorio motos, PagamentoRepositorio pagamentos){
    this.locacoes = locacoes;
    this.motos = motos;
    this.pagamentos = pagamentos;
  }

  @Transactional
  public Locacao abrirLocacao(@Valid Entregador entregador, @Valid Moto moto, LocalDate dataInicio, BigDecimal valorDiaria){
    if(!moto.isDisponivel()) throw new IllegalStateException("Moto indisponível");
    moto.setDisponivel(false);
    motos.save(moto);
    Locacao l = new Locacao();
    l.setEntregador(entregador);
    l.setMoto(moto);
    l.setDataInicio(dataInicio);
    l.setValorDiaria(valorDiaria);
    l.setStatus(Locacao.Status.ABERTA);
    return locacoes.save(l);
  }

  @Transactional
  public Pagamento fecharLocacaoEPagar(Long locacaoId, LocalDate dataFim, Pagamento.Metodo metodo){
    Locacao l = locacoes.findById(locacaoId).orElseThrow();
    if(l.getStatus() == Locacao.Status.ENCERRADA) throw new IllegalStateException("Locação já encerrada");
    long dias = Math.max(1, ChronoUnit.DAYS.between(l.getDataInicio(), dataFim));
    BigDecimal total = l.getValorDiaria().multiply(BigDecimal.valueOf(dias));
    l.setDataFim(dataFim);
    l.setTotal(total);
    l.setStatus(Locacao.Status.ENCERRADA);
    locacoes.save(l);

    Moto m = l.getMoto();
    m.setDisponivel(true);
    motos.save(m);

    Pagamento p = new Pagamento();
    p.setLocacao(l);
    p.setValor(total);
    p.setDataPagamento(java.time.LocalDateTime.now());
    p.setMetodo(metodo);
    return pagamentos.save(p);
  }
}
