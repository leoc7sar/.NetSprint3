package com.mottu.repositorio;
import com.mottu.dominio.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MotoRepositorio extends JpaRepository<Moto, Long> {
  List<Moto> findByDisponivelTrue();
}
