package com.example;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.MarcaDao;
import com.example.data.ProdutoDao;
import com.example.model.Marca;
import com.example.model.Produto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    @FXML
    TextField txtNome;
    @FXML
    TextField txtValor;
    @FXML
    TableView<Produto> tabelaProduto;
    @FXML
    TableColumn<Produto, Integer> colId;
    @FXML
    TableColumn<Produto, Integer> colMarca;
    @FXML
    TableColumn<Produto, String> colNome;
    @FXML
    TableColumn<Produto, Integer> colValor;
    @FXML ComboBox<Marca> cboMarca;

    @FXML TextField txtNomeMarca;
    @FXML TableView<Marca> tabelaMarca;
    @FXML TableColumn<Marca, Integer> colIdMarca;
    @FXML TableColumn<Marca, String> colNomeMarca;



    ProdutoDao produtoDao = new ProdutoDao();
    MarcaDao marcaDao = new MarcaDao();


    public void cadastrarProduto() {
        var produto = new Produto(
                txtNome.getText(),
                Integer.parseInt(txtValor.getText()),
                cboMarca.getSelectionModel().getSelectedItem()
                );

        try {
            produtoDao.inserir(produto);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        atualizarProdutos();
    }

    public void atualizarProdutos() {
        tabelaProduto.getItems().clear();
        try {
            produtoDao.buscarTodos().forEach(produto -> tabelaProduto.getItems().add(produto));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar produto. " + e.getMessage());
        }
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o item selecionado? Esta ação não pode ser desfeita.");
        var resposta = alert.showAndWait();
        return resposta.get().getButtonData().equals(ButtonData.OK_DONE);
    }

    public void apagarProduto() {
        var produto = tabelaProduto.getSelectionModel().getSelectedItem();

        if (produto == null) {
            mostrarMensagem("Erro", "Selecione um produto na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                produtoDao.apagar(produto);
                tabelaProduto.getItems().remove(produto);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar produto do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void editarProduto(Produto produto) {
        try {
            produtoDao.atualizar(produto);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do produto");
            e.printStackTrace();
        }
    }

    public void cadastrarMarca() {
        var marca = new Marca(txtNomeMarca.getText());

        try {
            marcaDao.inserir(marca);
            cboMarca.getItems().clear();
            cboMarca.getItems().addAll(marcaDao.buscarTodos());
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        atualizarMarcas();
    }

    public void atualizarMarcas() {
        tabelaMarca.getItems().clear();
        try {
            marcaDao.buscarTodos().forEach(marca -> tabelaMarca.getItems().add(marca));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar marca. " + e.getMessage());
        }
    }
    
    public void apagarMarca() {
        var marca = tabelaMarca.getSelectionModel().getSelectedItem();

        if (marca == null) {
            mostrarMensagem("Erro", "Selecione um marca na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                marcaDao.apagar(marca);
                tabelaMarca.getItems().remove(marca);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar marca do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }
    
    private void editarMarca(Marca marca){
        try {
           marcaDao.atualizar(marca);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do produto");
            e.printStackTrace();
        }
    }
    
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(event -> editarProduto(event.getRowValue().nome(event.getNewValue())));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colValor.setOnEditCommit(event -> editarProduto(event.getRowValue().valor(event.getNewValue())));

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        tabelaProduto.setEditable(true);


        colIdMarca.setCellValueFactory(new PropertyValueFactory<>("id"));

        colNomeMarca.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNomeMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colNomeMarca.setOnEditCommit(event -> editarMarca(event.getRowValue().nome(event.getNewValue())));

        tabelaMarca.setEditable(true);


        try {
            cboMarca.getItems().clear();
            cboMarca.getItems().addAll(marcaDao.buscarTodos());
        } catch (SQLException e) {
            mostrarMensagem("ERRO","Erro ao carregar clientes");
        }

        atualizarProdutos();
        atualizarMarcas();
    }

}
