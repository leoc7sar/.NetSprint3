package com.mottu.web;

import com.mottu.dominio.Entregador;
import com.mottu.repositorio.EntregadorRepositorio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/admin/entregadores")
public class EntregadorController {
  private final EntregadorRepositorio repo;
  public EntregadorController(EntregadorRepositorio repo){ this.repo = repo; }

  @GetMapping
  public String listar(Model m){ m.addAttribute("itens", repo.findAll()); return "entregador/lista"; }

  @GetMapping("/novo")
  public String formNovo(Model m){ m.addAttribute("entregador", new Entregador()); return "entregador/form"; }

  @PostMapping
  public String criar(@Valid @ModelAttribute("entregador") Entregador e, BindingResult br){
    if(br.hasErrors()) return "entregador/form";
    repo.save(e);
    return "redirect:/admin/entregadores";
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model m){ m.addAttribute("entregador", repo.findById(id).orElseThrow()); return "entregador/form"; }

  @PostMapping("/{id}")
  public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("entregador") Entregador e, BindingResult br){
    if(br.hasErrors()) return "entregador/form";
    e.setId(id); repo.save(e); return "redirect:/admin/entregadores";
  }

  @PostMapping("/{id}/excluir")
  public String excluir(@PathVariable Long id){ repo.deleteById(id); return "redirect:/admin/entregadores"; }
}
