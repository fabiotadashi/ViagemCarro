CREATE TABLE tipo (
   id integer NOT NULL PRIMARY KEY,
   nome varchar(255) NOT NULL
);

CREATE TABLE gasto (
   id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
   tipo_id integer NOT NULL,
   descricao varchar(255) NOT NULL,
   valor double NOT NULL,
   FOREIGN KEY (tipo_id) REFERENCES tipo (id)
);