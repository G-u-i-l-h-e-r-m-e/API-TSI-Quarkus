-- Este arquivo permite escrever comandos SQL que serão executados em teste e desenvolvimento.
-- Os comandos estão preparados para compatibilidade com o banco de dados.

INSERT INTO treino (id, nome, notas, duracao, data, objetivo, exercicios) VALUES
                                                                              (1, 'Treino de Peito A', 'Boa performance', 60, '2025-04-01 07:30:00', 'Hipertrofia', '["Supino Reto", "Crucifixo", "Flexão de Braço"]'),
                                                                              (2, 'Cardio Intenso', 'Manter frequência alta', 45, '2025-04-02 08:00:00', 'Perda de Peso', '["Corrida", "Jump", "Burpee"]'),
                                                                              (3, 'Funcional Leve', 'Alongamento pré-treino', 30, '2025-04-03 09:00:00', 'Condicionamento', '["Prancha", "Polichinelo", "Agachamento Isométrico"]');
INSERT INTO exercicio (id, nome, descricao, musculos_principais, musculos_secundarios, tipo, instrucoes) VALUES
                                                                                                             (1, 'Supino Reto', 'Exercício de empurrar com barra', 'Peitoral', 'Tríceps, Ombros', 'Força', 'Deite-se, segure a barra e empurre para cima'),
                                                                                                             (2, 'Agachamento Livre', 'Exercício de membros inferiores com barra', 'Quadríceps', 'Glúteos, Posterior de Coxa', 'Força', 'Desça com a barra controladamente e suba'),
                                                                                                             (3, 'Corrida', 'Atividade aeróbica contínua', 'Cardiorrespiratório', 'Pernas', 'Cardio', 'Correr por tempo ou distância determinada');
INSERT INTO musculo (id, nome, descricao, grupo_muscular) VALUES
                                                              (1, 'Peitoral', 'Músculo responsável pela adução dos braços', 'Peito'),
                                                              (2, 'Quadríceps', 'Músculo frontal da coxa, essencial para agachar', 'Pernas'),
                                                              (3, 'Tríceps', 'Responsável pela extensão do braço', 'Braços');
INSERT INTO api_key (id, valor, active, role, criadaEm) VALUES
    (1, 'MINHA_CHAVE_SECRETA', true, 'ADMIN', CURRENT_TIMESTAMP);
