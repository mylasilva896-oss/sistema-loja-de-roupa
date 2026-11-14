package entidade;

import java.sql.Date;

public class Roupa {
    private int id;
    private String peca;
    private String marca;
    private String tamanho;
    private String cor;
    private double preco;
    private int quantidadeEstoque;
    private Date dataCadastro;

    public Roupa() {
    }

    public Roupa(String peca, String marca, String tamanho, String cor, double preco, int quantidadeEstoque, Date dataCadastro) {
        this.peca = peca;
        this.marca = marca;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataCadastro = dataCadastro;
    }

    public Roupa(int id, String peca, String marca, String tamanho, String cor, double preco, int quantidadeEstoque, Date dataCadastro) {
        this.id = id;
        this.peca = peca;
        this.marca = marca;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeca() {
        return peca;
    }

    public void setPeca(String peca) {
        this.peca = peca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
