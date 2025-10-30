-- Inserir clientes
INSERT INTO clientes (nome, email, telefone, endereco, data_cadastro, ativo) VALUES
('João Silva', 'joao@email.com', '(11) 99999-1111', 'Rua A, 123 - São Paulo/SP', CURRENT_TIMESTAMP, true),
('Maria Santos', 'maria@email.com', '(11) 99999-2222', 'Rua B, 456 - São Paulo/SP', CURRENT_TIMESTAMP, true);

-- Inserir restaurantes
INSERT INTO restaurantes (nome, categoria, endereco, telefone, taxa_entrega, avaliacao, ativo) VALUES
('Pizzaria Bella', 'Italiana', 'Av. Paulista, 1000 - São Paulo/SP', '(11) 3333-1111', 5.00, 4.5, true),
('Burger House', 'Hamburgueria', 'Rua Augusta, 500 - São Paulo/SP', '(11) 3333-2222', 3.50, 4.2, true);

-- Inserir produtos
INSERT INTO produtos (nome, descricao, preco, categoria, disponivel, restaurante_id) VALUES
('Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 35.90, 'Pizza', true, 1),
('X-Burger', 'Hambúrguer, queijo, alface e tomate', 18.90, 'Hambúrguer', true, 2);

-- Inserir pedidos de exemplo
INSERT INTO pedidos (numero_pedido, data_pedido, status, valor_total, observacoes, cliente_id, restaurante_id, itens) VALUES
('PED123', CURRENT_TIMESTAMP, 'PENDENTE', 35.90, 'Sem cebola', 1, 1, 'Pizza Margherita'),
('PED124', CURRENT_TIMESTAMP, 'CONFIRMADO', 18.90, '', 2, 2, 'X-Burger');