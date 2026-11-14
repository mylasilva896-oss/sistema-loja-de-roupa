package dao;

import conexao.Conexao;
import entidade.Roupa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoupaDAO {

    public RoupaDAO() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS roupa (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "peca TEXT NOT NULL, " +
                     "marca TEXT NOT NULL, " +
                     "tamanho TEXT NOT NULL, " +
                     "cor TEXT NOT NULL, " +
                     "preco REAL NOT NULL, " +
                     "quantidade_estoque INTEGER NOT NULL, " +
                     "data_cadastro DATE NOT NULL" +
                     ");";
        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela roupa: " + e.getMessage());
        }
    }

    public boolean inserir(Roupa roupa) {
        String sql = "INSERT INTO roupa (peca, marca, tamanho, cor, preco, quantidade_estoque, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, roupa.getPeca());
            stmt.setString(2, roupa.getMarca());
            stmt.setString(3, roupa.getTamanho());
            stmt.setString(4, roupa.getCor());
            stmt.setDouble(5, roupa.getPreco());
            stmt.setInt(6, roupa.getQuantidadeEstoque());
            stmt.setDate(7, roupa.getDataCadastro());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar roupa: " + e.getMessage());
            return false;
        }
    }

    public List<Roupa> listar() {
        List<Roupa> lista = new ArrayList<>();
        String sql = "SELECT * FROM roupa";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Roupa r = new Roupa();
                r.setId(rs.getInt("id"));
                r.setPeca(rs.getString("peca"));
                r.setMarca(rs.getString("marca"));
                r.setTamanho(rs.getString("tamanho"));
                r.setCor(rs.getString("cor"));
                r.setPreco(rs.getDouble("preco"));
                r.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                r.setDataCadastro(rs.getDate("data_cadastro"));

                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar roupas: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Roupa roupa) {
        String sql = "UPDATE roupa SET peca = ?, marca = ?, tamanho = ?, cor = ?, preco = ?, quantidade_estoque = ?, data_cadastro = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, roupa.getPeca());
            stmt.setString(2, roupa.getMarca());
            stmt.setString(3, roupa.getTamanho());
            stmt.setString(4, roupa.getCor());
            stmt.setDouble(5, roupa.getPreco());
            stmt.setInt(6, roupa.getQuantidadeEstoque());
            stmt.setDate(7, roupa.getDataCadastro());
            stmt.setInt(8, roupa.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar roupa: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM roupa WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir roupa: " + e.getMessage());
            return false;
        }
    }
}