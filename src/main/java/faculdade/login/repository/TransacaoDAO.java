package faculdade.login.repository;

import model.entidades.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO (Data Access Object) para gerenciar operações de Transação no banco de dados.
 * 
 * Responsabilidades:
 * - Registrar todas as transações (saque, depósito, transferência)
 * - Recuperar histórico de transações
 * - Manter auditoria completa das operações
 */
@Repository
public class TransacaoDAO {
    
    // Queries SQL
    private static final String INSERT = "INSERT INTO transacao (id_conta_corrente, tipo, valor, data_hora, descricao) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM transacao ORDER BY data_hora DESC";
    private static final String SELECT_BY_CONTA = "SELECT * FROM transacao WHERE id_conta_corrente = ? ORDER BY data_hora DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM transacao WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Registra uma nova transação no banco
     */
    public Transacao salvarTransacao(Transacao transacao) {
        jdbcTemplate.update(INSERT,
            transacao.getIdContaCorrente(),
            transacao.getTipo(),
            transacao.getValor(),
            Timestamp.valueOf(transacao.getDataHora()),
            transacao.getDescricao()
        );
        return transacao;
    }

    /**
     * Obtém todas as transações do sistema
     */
    public List<Transacao> obterTodas() {
        return jdbcTemplate.query(SELECT_ALL, new TransacaoRowMapper());
    }

    /**
     * Obtém todas as transações de uma conta específica
     */
    public List<Transacao> obterPorContaCorrente(Integer idContaCorrente) {
        return jdbcTemplate.query(SELECT_BY_CONTA, new Object[]{idContaCorrente}, new TransacaoRowMapper());
    }

    /**
     * Obtém uma transação específica pelo ID
     */
    public Transacao obterPorId(Integer id) {
        try {
            List<Transacao> transacoes = jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, new TransacaoRowMapper());
            return transacoes.isEmpty() ? null : transacoes.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * RowMapper interno - padrão Java para mapear ResultSet para objetos
     */
    private static class TransacaoRowMapper implements RowMapper<Transacao> {
        @Override
        public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transacao transacao = new Transacao();
            transacao.setId(rs.getInt("id"));
            transacao.setIdContaCorrente(rs.getInt("id_conta_corrente"));
            transacao.setTipo(rs.getString("tipo"));
            transacao.setValor(rs.getBigDecimal("valor"));
            
            Timestamp ts = rs.getTimestamp("data_hora");
            if (ts != null) {
                transacao.setDataHora(ts.toLocalDateTime());
            }
            
            transacao.setDescricao(rs.getString("descricao"));
            return transacao;
        }
    }
}
