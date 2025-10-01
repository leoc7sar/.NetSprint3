package com.mottu.seguranca;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_papeis")
public class UsuarioPapel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private Papel papel;

    public UsuarioPapel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Papel getPapel() { return papel; }
    public void setPapel(Papel papel) { this.papel = papel; }
}
