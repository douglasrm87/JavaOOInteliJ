package faculdade.login.repository;

import model.entidades.ContaCorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO (Data Access Object) para gerenciar operações de Conta Corrente no banco de dados.
 * 
 * Este padrão separa a lógica de acesso a dados da lógica de negócio,
 * tornando o código mais organizado e fácil de manter.
 * 
 * Exemplos de uso:
 * - contaCorrente.obterContaByCpf("123.456.789-00")
 * - contaCorrente.salvarConta(novaConta)
 * - contaCorrente.atualizarSaldo(idConta, novoSaldo)
 */
@Repository
public class ContaCorrenteDAO {
    
    // Queries SQL - padrão educacional: deixar visível e bem comentado
    private static final String INSERT = "INSERT INTO conta_corrente (numero_conta, agencia, cpf, saldo, ativa, data_criacao) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM conta_corrente";
    private static final String SELECT_BY_ID = "SELECT * FROM conta_corrente WHERE id = ?";
    private static final String SELECT_BY_CPF = "SELECT * FROM conta_corrente WHERE cpf = ?";
    private static final String SELECT_BY_NUMERO_CONTA = "SELECT * FROM conta_corrente WHERE numero_conta = ?";
    private static final String UPDATE = "UPDATE conta_corrente SET numero_conta = ?, agencia = ?, cpf = ?, saldo = ?, ativa = ? WHERE id = ?";
    private static final String UPDATE_SALDO = "UPDATE conta_corrente SET saldo = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM conta_corrente WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Salva uma nova conta corrente no banco de dados
     */
    public ContaCorrente salvarConta(ContaCorrente conta) {
        jdbcTemplate.update(INSERT, 
            conta.getNumeroConta(),
            conta.getAgencia(),
            conta.getCpf(),
            conta.getSaldo(),
            conta.getAtiva(),
            Timestamp.valueOf(conta.getDataCriacao())
        );
        return conta;
    }

    /**
     * Obtém todas as contas correntes
     */
    public List<ContaCorrente> obterTodas() {
        return jdbcTemplate.query(SELECT_ALL, new ContaCorrenteRowMapper());
    }

    /**
     * Obtém uma conta corrente pelo ID
     */
    public ContaCorrente obterPorId(Integer id) {
        try {
            List<ContaCorrente> contas = jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, new ContaCorrenteRowMapper());
            return contas.isEmpty() ? null : contas.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtém uma conta corrente pelo CPF
     */
    public ContaCorrente obterPorCpf(String cpf) {
        try {
            List<ContaCorrente> contas = jdbcTemplate.query(SELECT_BY_CPF, new Object[]{cpf}, new ContaCorrenteRowMapper());
            return contas.isEmpty() ? null : contas.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtém uma conta corrente pelo número da conta
     */
    public ContaCorrente obterPorNumeroConta(String numeroConta) {
        try {
            List<ContaCorrente> contas = jdbcTemplate.query(SELECT_BY_NUMERO_CONTA, new Object[]{numeroConta}, new ContaCorrenteRowMapper());
            return contas.isEmpty() ? null : contas.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Atualiza os dados de uma conta corrente
     */
    public ContaCorrente atualizarConta(ContaCorrente conta) {
        jdbcTemplate.update(UPDATE,
            conta.getNumeroConta(),
            conta.getAgencia(),
            conta.getCpf(),
            conta.getSaldo(),
            conta.getAtiva(),
            conta.getId()
        );
        return conta;
    }

    /**
     * Atualiza apenas o saldo de uma conta
     */
    public void atualizarSaldo(Integer idConta, BigDecimal novoSaldo) {
        jdbcTemplate.update(UPDATE_SALDO, novoSaldo, idConta);
    }

    /**
     * Deleta uma conta corrente
     */
    public void deletarConta(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    /**
     * RowMapper interno - padrão Java para mapear ResultSet para objetos
     * Facilita a reutilização do código de mapeamento
     */
    private static class ContaCorrenteRowMapper implements RowMapper<ContaCorrente> {
        @Override
        public ContaCorrente mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContaCorrente conta = new ContaCorrente();
            conta.setId(rs.getInt("id"));
            conta.setNumeroConta(rs.getString("numero_conta"));
            conta.setAgencia(rs.getString("agencia"));
            conta.setCpf(rs.getString("cpf"));
            conta.setSaldo(rs.getBigDecimal("saldo"));
            conta.setAtiva(rs.getBoolean("ativa"));
            
            Timestamp ts = rs.getTimestamp("data_criacao");
            if (ts != null) {
                conta.setDataCriacao(ts.toLocalDateTime());
            }
            
            return conta;
        }
    }
}
