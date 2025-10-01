package com.mottu.web;

import com.mottu.dominio.Moto;
import com.mottu.repositorio.MotoRepositorio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/admin/motos")
public class MotoController {
  private final MotoRepositorio repo;
  public MotoController(MotoRepositorio repo){ this.repo = repo; }

  @GetMapping
  public String listar(Model m){ m.addAttribute("itens", repo.findAll()); return "moto/lista"; }

  @GetMapping("/nova")
  public String form(Model m){ m.addAttribute("moto", new Moto()); return "moto/form"; }

  @PostMapping
  public String criar(@Valid @ModelAttribute Moto mc, BindingResult br){
    if(br.hasErrors()) return "moto/form";
    repo.save(mc); return "redirect:/admin/motos";
  }

  @GetMapping("/{id}/editar")
  public String editar(@PathVariable Long id, Model m){ m.addAttribute("moto", repo.findById(id).orElseThrow()); return "moto/form"; }

  @PostMapping("/{id}")
  public String atualizar(@PathVariable Long id, @Valid @ModelAttribute Moto mc, BindingResult br){
    if(br.hasErrors()) return "moto/form";
    mc.setId(id); repo.save(mc); return "redirect:/admin/motos";
  }

  @PostMapping("/{id}/excluir")
  public String excluir(@PathVariable Long id){ repo.deleteById(id); return "redirect:/admin/motos"; }
}
