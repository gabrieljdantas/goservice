-- INSERT ADMIN
insert into usuarios (dtype, nome, email, perfil, senha, habilitado)
value ("Administrador", "Admin", "admin@admin.com", "ADMIN", "$2a$10$r3r9V682sIhE/61jZjqkauT.08pxrBx.GE1T.yEogN5r8Ly2S8eTK", 1); # 123456

-- POPULATE
insert into usuarios (dtype, nome, email, perfil, senha, habilitado)
value ( 
	"Prestador",
	"Gustavo",
    "gustavo@gmail.com",
    "PRESTADOR",
    "$2a$10$r3r9V682sIhE/61jZjqkauT.08pxrBx.GE1T.yEogN5r8Ly2S8eTK", # 123456
    1
);
insert into usuarios (dtype, nome, email, perfil, senha, habilitado)
value ( 
	"Prestador",
	"Lucas",
    "lucas@gmail.com",
    "PRESTADOR",
    "$2a$10$r3r9V682sIhE/61jZjqkauT.08pxrBx.GE1T.yEogN5r8Ly2S8eTK", # 123456
    1
);
insert into usuarios (dtype, nome, email, perfil, senha, habilitado)
value ( 
	"Prestador",
	"Paulo",
    "paulo@gmail.com",
    "PRESTADOR",
    "$2a$10$r3r9V682sIhE/61jZjqkauT.08pxrBx.GE1T.yEogN5r8Ly2S8eTK", # 123456
    1
);
insert into usuarios (dtype, nome, email, perfil, senha, habilitado)
value ( 
	"Prestador",
	"Pedro",
    "pedro@gmail.com",
    "PRESTADOR",
    "$2a$10$r3r9V682sIhE/61jZjqkauT.08pxrBx.GE1T.yEogN5r8Ly2S8eTK", # 123456
    1
);

INSERT INTO servicos (categoria, descricao, nome) 
VALUES 
    ('Limpeza', 'Serviço de limpeza residencial', 'Limpeza Residencial'),
    ('Treinamento', 'Treinamento corporativo em liderança', 'Treinamento em Liderança'),
    ('Saúde', 'Consulta médica de clínico geral', 'Consulta Clínico Geral'),
    ('Desenvolvimento', 'Desenvolvimento de aplicativo móvel', 'Desenvolvimento de App'),
    ('Design', 'Design de logotipo para empresas', 'Design de Logotipo'),
    ('Limpeza', 'Limpeza de escritórios comerciais', 'Limpeza Comercial'),
    ('Treinamento', 'Treinamento em habilidades de comunicação', 'Treinamento em Comunicação'),
    ('Saúde', 'Sessão de fisioterapia para reabilitação', 'Fisioterapia de Reabilitação'),
    ('Desenvolvimento', 'Desenvolvimento de site institucional', 'Desenvolvimento de Site Institucional'),
    ('Design', 'Design de interface de usuário para aplicativos', 'Design de Interface de Aplicativo');

INSERT INTO prestadores_servicos (servico_id, prestador_id)
VALUES
    (1, 5),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 2),
    (7, 3),
    (8, 4),
    (9, 5),
    (10, 2),
    (1, 3),
    (2, 4),
    (3, 5),
    (4, 2),
    (5, 3),
    (6, 4),
    (7, 5),
    (8, 2),
    (9, 3),
    (10, 4);

-- CONSULTAS
select * from usuarios;
select * from servicos;
select * from prestadores_servicos;
select * from agendamentos;