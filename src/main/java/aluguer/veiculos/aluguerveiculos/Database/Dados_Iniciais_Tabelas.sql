INSERT INTO Tipo_Cliente (tipo_Cliente)
VALUES ('Individual')--id 1
INSERT INTO Tipo_Cliente (tipo_Cliente)
VALUES ('Empresa')--id 2

INSERT INTO Tipo_Conta (tipo_conta)
VALUES ('Conta corrente')--id 1
INSERT INTO Tipo_Conta (tipo_conta)
VALUES ('Esporadico')--id 2

INSERT INTO Funcionario (nome, email, password)
VALUES ('Admin', 'admin@admin.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918')--id 1

INSERT INTO Permissoes (id_funcionario, gestaoFuncionarios, gestaoPedidos, gestaoVeiculos, gestaoClientes, gestaoMarcas,
                        gestaoModelos, gestaoServicos, gestaoAluguers, gestaoReparacoes, gestaoRecibos, gestaoFaturas,
                        gestaoCondutores)
VALUES (1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)