package com.mottu.web;

import com.mottu.dominio.*;
import com.mottu.repositorio.*;
import com.mottu.servico.ServicoLocacao;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller @RequestMapping("/locacoes")
public class LocacaoController {
  private final EntregadorRepositorio entregadores;
  private final MotoRepositorio motos;
  private final LocacaoRepositorio locacoes;
  private final ServicoLocacao servico;

  public LocacaoController(EntregadorRepositorio entregadores, MotoRepositorio motos, LocacaoRepositorio locacoes, ServicoLocacao servico){
    this.entregadores = entregadores; this.motos = motos; this.locacoes = locacoes; this.servico = servico;
  }

  @GetMapping
  public String listar(Model m){ m.addAttribute("itens", locacoes.findAll()); return "locacao/lista"; }

  @GetMapping("/abrir")
  public String formAbrir(Model m){
    m.addAttribute("entregadores", entregadores.findAll());
    m.addAttribute("motos", motos.findByDisponivelTrue());
    return "locacao/abrir";
  }

  @PostMapping("/abrir")
  public String abrir(@RequestParam Long entregadorId,
                      @RequestParam Long motoId,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                      @RequestParam @NotNull BigDecimal valorDiaria){
    Entregador e = entregadores.findById(entregadorId).orElseThrow();
    Moto mo = motos.findById(motoId).orElseThrow();
    servico.abrirLocacao(e, mo, dataInicio, valorDiaria);
    return "redirect:/locacoes";
  }

  @GetMapping("/{id}/fechar")
  public String formFechar(@PathVariable Long id, Model m){ m.addAttribute("locacao", locacoes.findById(id).orElseThrow()); return "locacao/fechar"; }

  @PostMapping("/{id}/fechar")
  public String fechar(@PathVariable Long id,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
                       @RequestParam Pagamento.Metodo metodo){
    servico.fecharLocacaoEPagar(id, dataFim, metodo);
    return "redirect:/pagamentos";
  }
}
