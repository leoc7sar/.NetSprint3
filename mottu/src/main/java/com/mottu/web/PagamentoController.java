package com.mottu.web;

import com.mottu.repositorio.PagamentoRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/pagamentos")
public class PagamentoController {
  private final PagamentoRepositorio pagamentos;
  public PagamentoController(PagamentoRepositorio pagamentos){ this.pagamentos = pagamentos; }
  @GetMapping public String listar(Model m){ m.addAttribute("itens", pagamentos.findAll()); return "pagamento/lista"; }
}
