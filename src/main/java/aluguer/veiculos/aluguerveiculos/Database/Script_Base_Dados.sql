CREATE TABLE [Tipo_Cliente]--Valores ja predefinidos
(
    [tipo_cliente] varchar(255) NOT NULL,
    CONSTRAINT PK_Tipo_Cliente PRIMARY KEY ([tipo_cliente])
);
GO

CREATE TABLE [Tipo_Conta]--Valores ja predefinidos
(
    [tipo_conta] varchar(255) NOT NULL,
    CONSTRAINT PK_Tipo_Conta PRIMARY KEY ([tipo_conta])
);
GO

CREATE TABLE [Marca]
(
    [id_marca] int IDENTITY (1,1),
    [marca]    varchar(255) NOT NULL,
    CONSTRAINT PK_Marca PRIMARY KEY ([id_marca])
);
GO

CREATE TABLE [Modelo]
(
    [id_modelo] int IDENTITY (1,1),
    [id_marca]  int          NOT NULL,
    [modelo]    varchar(255) NOT NULL,
    CONSTRAINT PK_Modelo PRIMARY KEY ([id_modelo]),
    CONSTRAINT FK_Modelo_marca FOREIGN KEY ([id_marca]) REFERENCES Marca ([id_marca])
);
GO

CREATE TABLE [Veiculo]
(
    [id_veiculo] int IDENTITY (1,1),
    [matricula]  varchar(8)    NOT NULL,
    [id_modelo]  int           NOT NULL,
    [preco]      decimal(9, 2) NOT NULL,
    CONSTRAINT UK_Veiculo_matricula UNIQUE ([matricula]),
    CONSTRAINT CK_Veiculo_matricula
        CHECK (matricula LIKE '[0-9][0-9]-[0-9][0-9]-[A-Z][A-Z]' OR
               matricula LIKE '[0-9][0-9]-[A-Z][A-Z]-[0-9][0-9]' OR
               matricula LIKE '[A-Z][A-Z]-[0-9][0-9]-[0-9][0-9]'),
    CONSTRAINT PK_Veiculo PRIMARY KEY ([id_veiculo]),
    CONSTRAINT FK_Veiculo_modelo FOREIGN KEY (id_modelo) REFERENCES Modelo ([id_modelo])
);
GO

CREATE TABLE [Servico]
(
    [id_servico] int IDENTITY (1,1),
    [servico]    varchar(255)  NOT NULL,
    [preco]      decimal(9, 2) NOT NULL,
    [descricao]  varchar(255)  NOT NULL,
    CONSTRAINT PK_Servico PRIMARY KEY ([id_servico])
);
GO

CREATE TABLE [Funcionario]
(
    [id_funcionario] integer IDENTITY (1,1) NOT NULL,
    [nome]           varchar(255)           NOT NULL,
    [email]          varchar(255)           NOT NULL,
    [password]       varchar(255)           NOT NULL,
    CONSTRAINT PK_Funcionario PRIMARY KEY ([id_funcionario]),
    CONSTRAINT CK_Funcionario_email CHECK (email LIKE '%_@__%.%'),
    CONSTRAINT UK_Funcionario_email UNIQUE ([email])
);
GO

CREATE TABLE [Cliente]
(
    [id_cliente]   integer IDENTITY (1,1) NOT NULL,
    [tipo_cliente] varchar(255)           NOT NULL,
    [tipo_conta]   varchar(255)           NOT NULL,
    [nome]         varchar(255)           NOT NULL,
    CONSTRAINT PK_Cliente PRIMARY KEY ([id_cliente]),
    CONSTRAINT FK_Cliente_tipo_conta FOREIGN KEY ([tipo_conta]) REFERENCES Tipo_Conta ([tipo_conta]),
    CONSTRAINT FK_Cliente_tipo_cliente FOREIGN KEY ([tipo_cliente]) REFERENCES Tipo_Cliente ([tipo_cliente])
);
GO

CREATE TABLE [Recibo]
(
    [id_recibo] int IDENTITY (1,1),
    [data]      date,
    [preco]     decimal(9, 2) NOT NULL,
    CONSTRAINT PK_Recibo PRIMARY KEY ([id_recibo])
);
GO

CREATE TABLE [Fatura]--Tabela Fatura, se id_recibo for null quer dizer que ainda nao foi paga, caso contrario ja foi paga
(
    [id_fatura]      int IDENTITY (1,1),
    [id_funcionario] int NOT NULL,
    [id_recibo]      int,
    [data]           date,
    CONSTRAINT PK_Fatura PRIMARY KEY ([id_fatura]),
    CONSTRAINT PK_Fatura_id_recibo FOREIGN KEY ([id_recibo]) REFERENCES Recibo ([id_recibo]),
    CONSTRAINT PK_Fatura_id_funcionario FOREIGN KEY ([id_funcionario]) REFERENCES Funcionario ([id_funcionario])
);
GO

CREATE TABLE [Recibo_Fatura]--Tabela Recibo_Fatura, regista todas as faturas que um recibo foi usado
(
    [id_fatura] int NOT NULL,
    [id_recibo] int NOT NULL,
    CONSTRAINT PK_Recibo_Fatura_id_fatura FOREIGN KEY ([id_fatura]) REFERENCES Fatura ([id_fatura]),
    CONSTRAINT PK_Recibo_Fatura_id_recibo FOREIGN KEY ([id_recibo]) REFERENCES Recibo ([id_recibo])
);
GO

CREATE TABLE [Condutor]
(
    [id_condutor] integer IDENTITY (1,1) NOT NULL,
    [nome]        varchar(255)           NOT NULL,
    [morada]      varchar(255),
    [cc]          varchar(8)             NOT NULL,
    [nr_carta]    varchar(10)            NOT NULL,
    CONSTRAINT PK_Condutor PRIMARY KEY ([id_condutor]),
    CONSTRAINT UK_Condutor_nr_cc UNIQUE ([cc]),
    CONSTRAINT CK_Condutor_nr_carta CHECK ([nr_carta] LIKE '[A-Z][A-Z]-[0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT UK_Condutor_nr_carta UNIQUE ([nr_carta])
);
GO

CREATE TABLE [Pedido]
(
    [id_pedido]  int IDENTITY (1,1),
    [id_fatura]  int,
    [id_cliente] int NOT NULL,
    [data]       date,
    [preco]      decimal(9, 2),
    CONSTRAINT PK_Pedido PRIMARY KEY ([id_pedido]),
    CONSTRAINT PK_Pedido_id_cliente FOREIGN KEY ([id_cliente]) REFERENCES Cliente ([id_cliente]),
    CONSTRAINT PK_Pedido_id_fatura FOREIGN KEY ([id_fatura]) REFERENCES Fatura ([id_fatura])
);
GO

CREATE TABLE [Aluguer]--Tabela aluguer, se data_entrega for null quer dizer que nao foi entregue, caso tenha algum valor a entrega ja foi devolvida
(
    [id_aluguer]   integer IDENTITY (1,1) NOT NULL,
    [id_veiculo]   int                    NOT NULL,
    [id_condutor]  integer                NOT NULL,
    [id_pedido]    integer                NOT NULL,
    [data_inicio]  DATE                   NOT NULL,
    [data_fim]     DATE                   NOT NULL,
    [data_entrega] DATE,
    [desconto]     int DEFAULT 0,
    CONSTRAINT PK_Aluguer PRIMARY KEY ([id_aluguer]),
    CONSTRAINT FK_Aluguer_matricula FOREIGN KEY ([id_veiculo]) REFERENCES Veiculo ([id_veiculo]),
    CONSTRAINT FK_Aluguer_Condutor FOREIGN KEY ([id_condutor]) REFERENCES Condutor ([id_condutor]),
    CONSTRAINT FK_Aluguer_id_pedido FOREIGN KEY ([id_pedido]) REFERENCES Pedido ([id_pedido]),
    CONSTRAINT CK_Aluguer_desconto CHECK ([desconto] <= 100)
);
GO

CREATE TABLE [Servico_Aluguer]--Tabela para registar os serviços que cada reserva tem
(
    [id_servico] int NOT NULL,
    [id_aluguer] int NOT NULL,
    [desconto]   int default 0,
    CONSTRAINT PK_Servico_Aluguer_servico FOREIGN KEY ([id_servico]) REFERENCES Servico ([id_servico]),
    CONSTRAINT PK_Servico_Aluguer_id_aluguer FOREIGN KEY ([id_aluguer]) REFERENCES Aluguer ([id_aluguer]),
    CONSTRAINT CK_Servico_Aluguer_desconto CHECK ([desconto] <= 100)
);
GO

CREATE TABLE [CustosReparacaoVeiculo]--Tabela para os custos de reparaçao/manutença do carros
(
    [id_reparacao] integer IDENTITY (1,1) NOT NULL,
    [id_veiculo]   int                    NOT NULL,
    [preco]        decimal(9, 2)          NOT NULL,
    [descricao]    varchar(255)           NOT NULL,
    [data]         date                   NOT NULL,
    CONSTRAINT PK_CustosReparacaoVeiculo PRIMARY KEY ([id_reparacao]),
    CONSTRAINT FK_CustosReparacaoVeiculo_matricula FOREIGN KEY ([id_veiculo]) REFERENCES Veiculo ([id_veiculo])
);
GO

CREATE TABLE [Permissoes]--Tabela permissoes tem varios campos de gestao com valores bit, 0 = sem permissao, 1 = com permissao, para cada funcionario.
(
    [id_funcionario]     int NOT NULL,
    [gestaoFuncionarios] bit default 0,
    [gestaoPedidos]      bit default 0,
    [gestaoVeiculos]     bit default 0,
    [gestaoClientes]     bit default 0,
    [gestaoMarcas]       bit default 0,
    [gestaoModelos]      bit default 0,
    [gestaoServicos]     bit default 0,
    [gestaoAluguers]     bit default 0,
    [gestaoReparacoes]   bit default 0,
    [gestaoRecibos]      bit default 0,
    [gestaoFaturas]      bit default 0,
    [gestaoCondutores]   bit default 0,
    CONSTRAINT UK_Permissoes_id_funcionario UNIQUE ([id_funcionario]),
    CONSTRAINT PK_Permissoes_id_funcionario FOREIGN KEY ([id_funcionario]) REFERENCES Funcionario ([id_funcionario])
);
GO

---------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE AlugarVeiculo @idVeiculo INT,
                               @idCondutor INT,
                               @idPedido INT,
                               @data_inicio DATE,
                               @data_fim DATE,
                               @desconto DECIMAL(9, 2)
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Aluguer WHERE id_veiculo = @idVeiculo AND data_entrega IS NULL)
        BEGIN
            RAISERROR ('Veículo não disponível para aluguel.', 16, 1);
            RETURN;
        END

    INSERT INTO Aluguer (id_veiculo, id_condutor, id_pedido, data_inicio, data_fim, data_entrega, desconto)
    VALUES (@idVeiculo, @idCondutor, @idPedido, @data_inicio, @data_fim, NULL, @desconto);
END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE VerificarModeloDeMarca @idMarca INT,
                                        @idModelo INT
AS
BEGIN
    IF NOT EXISTS (SELECT Modelo.id_marca, id_modelo
                   FROM Modelo
                            INNER JOIN Marca ON Marca.id_marca = Modelo.id_marca
                   WHERE Modelo.id_marca = @idMarca
                     AND Modelo.id_modelo = @idModelo)
        BEGIN
            RAISERROR ('Esse modelo nao pertence a essa Marca.', 16, 1);
            RETURN;
        END

    RETURN 1;
END;
GO
---------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE EliminarVeiculo @idVeiculo INT
AS
BEGIN
    IF EXISTS (SELECT id_veiculo FROM Aluguer WHERE id_veiculo = @idVeiculo AND data_entrega IS NULL)
        BEGIN
            RAISERROR ('Esse veiculo está em uso em uma reserva.', 16, 1);
            RETURN;
        END

    DELETE
    FROM Veiculo
    WHERE id_veiculo = @idVeiculo
END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE AdicionarVeiculo @idModelo INT,
                                  @matricula VARCHAR(8),
                                  @preco DECIMAL(9, 2)
AS
BEGIN
    IF (@matricula NOT LIKE '[0-9][0-9]-[0-9][0-9]-[A-Z][A-Z]' AND
        @matricula NOT LIKE '[0-9][0-9]-[A-Z][A-Z]-[0-9][0-9]' AND
        @matricula NOT LIKE '[A-Z][A-Z]-[0-9][0-9]-[0-9][0-9]')
        BEGIN
            RAISERROR ('Matricula com formato errado.', 16, 1);
            RETURN;
        END

    INSERT INTO Veiculo
        (id_modelo, matricula, preco)
    VALUES (@idModelo, @matricula, @preco)
END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE FUNCTION TotalPrecoPagamentoAluguer(
    @idAluguer INT
)
    RETURNS DECIMAL(9, 2)
AS
BEGIN
    DECLARE @precoAluguer DECIMAL(9, 2);
    DECLARE @totalServicos DECIMAL(9, 2);
    DECLARE @descontoAluguer DECIMAL(5, 2);
    DECLARE @descontoServicos DECIMAL(5, 2);

    -- Obtém o preço do aluguer
    SELECT @precoAluguer = v.preco * DATEDIFF(day, a.data_inicio, a.data_fim)
    FROM Aluguer a
             JOIN Veiculo v ON a.id_veiculo = v.id_veiculo
    WHERE a.id_aluguer = @idAluguer;

    -- Obtém o desconto do aluguer
    SELECT @descontoAluguer = desconto
    FROM Aluguer
    WHERE id_aluguer = @idAluguer;

    -- Verifica se há serviços para o aluguer
    IF EXISTS (SELECT 1 FROM Servico_Aluguer WHERE id_aluguer = @idAluguer)
        BEGIN
            -- Obtém o total dos serviços do aluguer com desconto
            SELECT @totalServicos = SUM(s.preco * (1 - sa.desconto / 100))
            FROM Servico_Aluguer sa
                     INNER JOIN Servico s ON sa.id_servico = s.id_servico
            WHERE sa.id_aluguer = @idAluguer;

            -- Obtém o desconto total dos serviços
            SELECT @descontoServicos = SUM(sa.desconto)
            FROM Servico_Aluguer sa
            WHERE sa.id_aluguer = @idAluguer;
        END
    ELSE
        BEGIN
            -- Define os valores como zero se não houver serviços
            SET @totalServicos = 0;
            SET @descontoServicos = 0;
        END;

    -- Calcula o preço a pagar considerando os descontos
    DECLARE @precoAPagar DECIMAL(9, 2);
    SET @precoAPagar =
                (@precoAluguer * (1 - @descontoAluguer / 100)) + (@totalServicos * (1 - @descontoServicos / 100));

    RETURN @precoAPagar;
END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE FUNCTION TotalPrecoPagamentoPedido(
    @idPedido INT
)
    RETURNS DECIMAL(12, 2)
AS
BEGIN
    DECLARE @totalPrecoPagamento DECIMAL(12, 2);

    -- Obtém a soma dos preços de pagamento de todos os alugueres associados ao pedido
    SELECT @totalPrecoPagamento = SUM(dbo.TotalPrecoPagamentoAluguer(a.id_aluguer))
    FROM Aluguer a
    WHERE a.id_pedido = @idPedido;

    -- Se o valor for NULL, substitui por zero
    IF @totalPrecoPagamento IS NULL
        SET @totalPrecoPagamento = 0;

    RETURN @totalPrecoPagamento;
END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE TRIGGER TriggerAtualizaoPrecoPedido_Aluguer
    ON Aluguer
    AFTER INSERT, UPDATE
    AS
BEGIN
    UPDATE Pedido
    SET preco = dbo.TotalPrecoPagamentoPedido(id_pedido)
    WHERE id_pedido IN (SELECT id_pedido FROM inserted);
END;

    CREATE TRIGGER TriggerAtualizaoPrecoPedidoServico_Aluguer
        ON Servico_Aluguer
        AFTER INSERT, UPDATE
        AS
    BEGIN
        UPDATE Pedido
        SET preco = dbo.TotalPrecoPagamentoPedido(id_pedido)
        WHERE id_pedido IN (SELECT id_pedido FROM inserted);
    END;
GO

---------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE PagarFatura(
    @idRecibo INT,
    @idFatura INT,
    @idFuncionario INT,
    @data DATE
)
AS
BEGIN
    DECLARE @valorRecibo DECIMAL(12, 2);
    DECLARE @totalFatura DECIMAL(12, 2);
    DECLARE @totalFaturasDoRecibo DECIMAL(12, 2);

    -- Obtém o valor do recibo
    SELECT @valorRecibo = preco FROM Recibo WHERE id_recibo = @idRecibo;

    -- Obtém o total da fatura (valor total dos pedidos)
    SELECT @totalFatura = ISNULL(SUM(Pedido.preco), 0)
    FROM Pedido
             INNER JOIN Fatura ON Pedido.id_fatura = Fatura.id_fatura
    WHERE Fatura.id_fatura = @idFatura;

    -- Obtém o total das faturas em que já foi usado o recibo
    SELECT @totalFaturasDoRecibo = ISNULL(SUM(Pedido.preco), 0)
    FROM Pedido
             INNER JOIN Fatura ON Pedido.id_fatura = Fatura.id_fatura
    WHERE Fatura.id_recibo = @idRecibo;

    -- Verifica se o valor do recibo é suficiente para pagar as faturas
    IF (@valorRecibo >= @totalFatura + @totalFaturasDoRecibo)
        BEGIN
            -- Atualiza o campo de recibo da fatura
            UPDATE Fatura
            SET id_recibo      = @idRecibo,
                id_funcionario = @idFuncionario,
                data           = @data
            WHERE id_fatura = @idFatura;
        END
    ELSE
        BEGIN
            -- Lida com o caso em que o valor do recibo é insuficiente para pagar a fatura
            DECLARE @errorMessage NVARCHAR(200);
            SET @errorMessage = 'Esse recibo não é suficiente para pagar a(s) fatura(s).';
            RAISERROR (@errorMessage, 16, 1);
        END
END;
GO

---------------------------------------------------------------------------------------------------------------------

