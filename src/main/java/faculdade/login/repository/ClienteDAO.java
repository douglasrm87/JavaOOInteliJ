package faculdade.login.repository;
import model.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteDAO {
    // coluna ID é auto incremento.
    private static String INSERT_CLI = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static String SELECT_BY_NOME = "select * from cliente where nome like ? ";
    private static String UPDATE = "update cliente set nome = ? where id = ?";
    private static String DELETE = "delete from cliente where id = ?";
    @Autowired
    private JdbcTemplate jdbcConexao;
    public Cliente atualizarCliente(Cliente cliente){
        jdbcConexao.update(UPDATE, new Object []{
                cliente.getNome(),cliente.getId()});
        return cliente;
    }
    public Cliente deletarCliente(Cliente cliente){
        jdbcConexao.update(DELETE, new Object []{cliente.getId()});
        return cliente;
    }
    public List <Cliente> obterClienteByNome (String nome){
        return jdbcConexao.query(SELECT_BY_NOME , new Object[]{"%"+nome+"%"} ,new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer idC = rs.getInt("id");
                String nomeC = rs.getString("nome");
                return new Cliente (idC,nomeC);
            }
        });
    }
    public List<Cliente> obterTodos (){
        return jdbcConexao.query(SELECT_ALL, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer idC = rs.getInt("id");
                String nomeC = rs.getString("nome");
                return new Cliente (idC,nomeC);
            }
        });
    }
    public Cliente salvarCliente(Cliente cliente){
        // inserção, atualização e deleção. Aqui vamos inserir um cliente no banco de Dados.
        jdbcConexao.update(INSERT_CLI, new Object[]{cliente.getNome()});
        return cliente;
    }

}
