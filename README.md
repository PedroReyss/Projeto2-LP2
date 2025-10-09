# 📋 Sistema OrganizaTec - Sistema de Gestão Organizacional

## 🎯 **Intuito do Sistema**

O **OrganizaTec** é um sistema completo de gestão organizacional desenvolvido em Java com Hibernate e SQL Server. Ele permite o controle integrado de funcionários, terceirizados e visitantes, oferecendo funcionalidades de registro de ponto, atividades, contratos e relatórios.

**Objetivos principais:**
- Centralizar o controle de acesso de diferentes tipos de pessoas na organização
- Automatizar o registro de ponto para todos os colaboradores
- Gerar relatórios gerenciais em tempo real
- Exportar dados para PDF para análise externa

---

## 🏗️ **Arquitetura e Herança**

### **Diagrama de Herança (SINGLE_TABLE)**
```
Pessoa (Abstract)
├── Funcionario
├── Terceirizado
└── Visitante
```

### **Classe Pessoa (Abstrata)**
**Campos comuns a todos:**
- `id` (IDENTITY)
- `nome` (obrigatório)
- `cpf` (único, obrigatório)
- `matricula` (gerada automaticamente para funcionários)
- `numeroCracha` (gerado automaticamente para terceirizados/visitantes)
- `departamento` (relacionamento)

### **Classe Funcionario**
**Campos específicos:**
- `dataNascimento`
- `cargo` (relacionamento com Cargo)
- **Matrícula automática**: Gerada no formato `00001`, `00002`, etc.

### **Classe Terceirizado**
**Campos específicos:**
- `funcao`
- `empresaPrestadora`
- `dataInicioContrato`
- `dataFimContrato`
- `responsavel` (Funcionario responsável)
- **Crachá automático**: Gerado no formato `CR0001`, `CR0002`, etc.

### **Classe Visitante**
**Campos específicos:**
- `motivoVisita`
- `dataHoraEntrada` (registrada no cadastro)
- `dataHoraSaida` (registrada via ponto)
- `funcionarioVisitado`
- `departamentoVisitado` (definido automaticamente)
- **Crachá automático**: Gerado no formato `CR0001`, `CR0002`, etc.

---

## ⏰ **Sistema de Ponto**

### **Funcionamento para Todos os Tipos**
O sistema implementa **controle inteligente de ponto** que alterna automaticamente entre entrada e saída:

```
1ª batida: ENTRADA
2ª batida: SAÍDA  
3ª batida: ENTRADA
4ª batida: SAÍDA
(e assim por diante...)
```

### **Visitantes Também Batem Ponto**
**Visitantes utilizam o mesmo sistema de ponto** para controle de entrada.

- **Chegada**: Cadastro automático como "entrada" + geração de crachá
- **Durante a visita**: Podem bater ponto múltiplas vezes (entrada/saída)
- **Saída final**: Último ponto deve ser "saída"

**Exemplo de fluxo de visitante:**
```
08:00 - Cadastrado no sistema (entrada automática)
10:30 - Bate ponto (saída) → Sai para almoço
12:00 - Bate ponto (entrada) → Retorna do almoço
15:00 - Bate ponto (saída) → Saída final
```

---

## 🗄️ **Configuração do Banco de Dados**

### **1. Criar Banco de Dados**
Execute o script SQL completo no **SQL Server Management Studio**:

```sql
-- Criar database
CREATE DATABASE P2_LP2;
USE P2_LP2;

-- Executar todo o script de criação de tabelas fornecido
-- (Departamento, Cargo, Pessoa, Ponto, Atividade, etc.)
```

### **2. Configurar persistence.xml**
Edite o arquivo `src/main/resources/META-INF/persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url" 
          value="jdbc:sqlserver://localhost:1433;databaseName=P2_LP2;encrypt=true;trustServerCertificate=true"/>
<property name="jakarta.persistence.jdbc.user" value="SEU_USUARIO"/>
<property name="jakarta.persistence.jdbc.password" value="SUA_SENHA"/>
```

**Configurações:**
- **URL**: `jdbc:sqlserver://localhost:1433;databaseName=P2_LP2`
- **Usuário**: `sa` (ou seu usuário SQL Server)
- **Senha**: `123` (ou sua senha)
- **Dialect**: SQL Server (automático)

---

## 📋 **Funcionalidades do Sistema**

### **1. 🧍 Cadastrar Pessoa**
**Tipos disponíveis:**
- **Funcionário**: Dados completos + vínculo com cargo/departamento
- **Terceirizado**: Dados contratuais + responsável interno
- **Visitante**: Motivo + funcionário visitado

**Validações:**
- CPF único e com 11 dígitos
- Datas no formato dd/MM/yyyy
- Campos obrigatórios verificados

### **2. ⏰ Bater Ponto**
**Funciona para todos os tipos**: Funcionários, Terceirizados e Visitantes

**Lógica inteligente:**
- Primeira batida: `ENTRADA`
- Batidas subsequentes alternam automaticamente
- Registra data/hora exata do ponto

### **3. 📝 Registrar Atividade**
**Apenas para Funcionários**
- Descrição da atividade
- Horas trabalhadas (com validação 0.1-24h)
- Registro automático de data/hora

### **4. 📄 Renovar Contrato**
**Apenas para Terceirizados**
- Lista apenas terceirizados cadastrados
- Informa data atual do contrato
- Adiciona meses à data final
- Validação: 1-120 meses

### **5. 📊 Gerar Relatórios**
**Relatórios em tempo real:**
- **Circulação Diária**: Contagem por tipo + pessoas atualmente na empresa
- **Funcionários por Departamento**: Distribuição por departamento
- **Visitantes Ativos**: Status atual (dentro/fora) + tempo de permanência

### **6. 📄 Exportar PDF**
**Relatórios profissionais em PDF:**
- Nome automático com timestamp: `relatorios_organizatec_08102025_180245.pdf`
- Tabelas formatadas com cabeçalhos
- Dados consolidados do banco
- Salvo na pasta do projeto

---

## 🚀 **Fluxo de Uso Típico**

### **Para Funcionários/Terceirizados:**
1. Cadastrar pessoa
2. Bater ponto diariamente
3. Registrar atividades (funcionários)
4. Renovar contratos (terceirizados)

### **Para Visitantes:**
1. Cadastrar visitante (entrada automática)
2. Bater ponto para saídas/retornos
3. Bater ponto para saída final

### **Para Gestores:**
1. Gerar relatórios de circulação
2. Exportar PDF para reuniões
3. Monitorar pessoas na empresa em tempo real

---

## ⚙️ **Tecnologias Utilizadas**

- **Java 17**
- **Hibernate ORM 6.4.4**
- **SQL Server**
- **iText 7** (PDF)
- **JPA** (Jakarta Persistence)
- **Maven** (Gerenciamento de dependências)

---

## 📁 **Estrutura do Projeto**

```
src/
├── main/
│   ├── java/com/p2lp2/
│   │   ├── model/          # Entidades JPA
│   │   ├── service/        # Lógica de negócio
│   │   └── Main.java       # Classe principal
│   └── resources/
│       └── META-INF/
│           └── persistence.xml
```

---

## 🎯 **Benefícios do Sistema**

- ✅ **Controle unificado** de diferentes tipos de pessoas
- ✅ **Ponto inteligente** com alternância automática
- ✅ **Validações robustas** em todas as entradas
- ✅ **Relatórios em PDF** profissionais
- ✅ **Interface intuitiva** via console
- ✅ **Persistência robusta** com Hibernate

**Sistema pronto para uso em ambientes organizacionais!** 🏢
