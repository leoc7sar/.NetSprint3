package com.mottu.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.mottu.seguranca.Usuario;
import com.mottu.seguranca.UsuarioPapel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

interface UsuarioRepositorio extends JpaRepository<Usuario, Long> { Usuario findByUsername(String u); }
interface UsuarioPapelRepositorio extends JpaRepository<UsuarioPapel, Long> { java.util.List<UsuarioPapel> findByUsuario(Usuario u); }

@Configuration
public class SegurancaConfig {
  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/css/**","/","/login").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/locacoes/**","/pagamentos/**").hasAnyRole("ADMIN","USUARIO")
        .anyRequest().authenticated())
      .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
      .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
      .csrf(Customizer.withDefaults());
    return http.build();
  }

  @Autowired UsuarioRepositorio usuarios;
  @Autowired UsuarioPapelRepositorio usuariosPapeis;

  @Bean @Transactional UserDetailsService userDetailsService(){
    return username -> {
      var u = usuarios.findByUsername(username);
      if (u == null) throw new UsernameNotFoundException("Usuário não encontrado");
      var auths = usuariosPapeis.findByUsuario(u).stream()
          .map(up -> new org.springframework.security.core.authority.SimpleGrantedAuthority(up.getPapel().getName()))
          .toList();
      return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), u.isEnabled(), true, true, true, auths);
    };
  }
}
