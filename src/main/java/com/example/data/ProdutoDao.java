package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Produto;
import com.example.model.Marca;

public class ProdutoDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "rm99738";
    private final String PASS = "261204";
    
    public void inserir(Produto produto) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO produtos (nome, valor, marca) VALUES (?, ?, ?)");
        ps.setString(1, produto.getNome());
        ps.setInt(2, produto.getValor());
        ps.setInt(3, produto.getMarca().getId());

        ps.executeUpdate();
        con.close();
    }

    public List<Produto> buscarTodos() throws SQLException{
        var produtos = new ArrayList<Produto>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT produtos.*, marcas.nome as marca_nome from produtos inner join marcas on produtos.marca = marcas.id");

        while(rs.next()){
            produtos.add(new Produto(
                rs.getInt("id"),
                rs.getString("nome"),  
                rs.getInt("valor"), 
               new Marca (
                   rs.getInt("marca"),
                   rs.getString("marca_nome")
                )
            ));
        }

        con.close();
        return produtos;
    }

    public void apagar(Produto produto) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM produtos WHERE id=?"); 
        ps.setInt(1, produto.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Produto produto) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE produtos SET nome=?, valor=? WHERE id=?");
        ps.setString(1, produto.getNome());
        ps.setInt(2, produto.getValor());
        ps.setInt(3, produto.getId());
        ps.executeUpdate();
        con.close();

    }

}
