package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Marca;

public class MarcaDao {
    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "rm99738";
    private final String PASS = "261204";

    public void inserir(Marca marca) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO marcas (nome) VALUES (?)");
        ps.setString(1, marca.getNome());

        ps.executeUpdate();
        con.close();
    }

    public List<Marca> buscarTodos() throws SQLException {
        var marcas = new ArrayList<Marca>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT * FROM marcas");

        while (rs.next()) {
            marcas.add(new Marca(
                    rs.getInt("id"),
                    rs.getString("nome")));
        }

        con.close();
        return marcas;
    }

    public void apagar(Marca marca) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM marcas WHERE id=?");
        ps.setInt(1, marca.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Marca marca) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE marcas SET nome=? WHERE id=?");
        ps.setString(1,marca.getNome());
        ps.setInt(2, marca.getId());
        ps.executeUpdate();
        con.close();
    }
}
