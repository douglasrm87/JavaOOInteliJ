create table cliente (id integer primary key auto_increment,nome varchar (100));
create table produto (id integer primary key auto_increment,descricao varchar (100) , preco_unitario numeric (20,2));
create table pedido (id integer primary key auto_increment, cliente_id integer references cliente (id), data_pedido timestamp, total numeric (20,2));
create table item_pedido (id integer primary key auto_increment, pedido_id integer references pedido (id),produto_id integer references produto (id), quantidade integer);