package com.mottu.repositorio;
import com.mottu.dominio.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {}
