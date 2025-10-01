CREATE TABLE usuarios (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(200) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE papeis (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE usuarios_papeis (
  id BIGSERIAL PRIMARY KEY,
  usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
  papel_id BIGINT NOT NULL REFERENCES papeis(id)
);

INSERT INTO papeis(name) VALUES ('ROLE_ADMIN'), ('ROLE_USUARIO');

-- senha: 123 (BCrypt)
INSERT INTO usuarios(username, password) VALUES
  ('admin', '$2a$10$8rV3o5QH9xg6M2YpG7v3TOh0n3mJ6n5mJxGm0xLx3Jg2h1m4k0m7e'),
  ('usuario',  '$2a$10$8rV3o5QH9xg6M2YpG7v3TOh0n3mJ6n5mJxGm0xLx3Jg2h1m4k0m7e');

INSERT INTO usuarios_papeis(usuario_id, papel_id)
  SELECT u.id, p.id FROM usuarios u, papeis p WHERE u.username='admin' AND p.name='ROLE_ADMIN';
INSERT INTO usuarios_papeis(usuario_id, papel_id)
  SELECT u.id, p.id FROM usuarios u, papeis p WHERE u.username='usuario' AND p.name='ROLE_USUARIO';
