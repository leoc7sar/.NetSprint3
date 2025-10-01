package com.mottu.repositorio;
import com.mottu.dominio.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LocacaoRepositorio extends JpaRepository<Locacao, Long> {}
