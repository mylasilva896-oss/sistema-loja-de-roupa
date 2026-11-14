package telas;

import dao.RoupaDAO;
import entidade.Roupa;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaCadastroRoupa extends JFrame {

    private JTextField txtId;
    private JTextField txtPeca;
    private JTextField txtMarca;
    private JTextField txtTamanho;
    private JTextField txtCor;
    private JTextField txtPreco;
    private JTextField txtEstoque;
    private JTextField txtDataCadastro;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private Roupa roupaEdicao;
    private Runnable aoSalvar;

    public TelaCadastroRoupa(Roupa roupaParaEditar, Runnable aoSalvar) {
        this.roupaEdicao = roupaParaEditar;
        this.aoSalvar = aoSalvar;

        configurarJanela();
        criarComponentes();

        if (roupaEdicao != null) {
            carregarDadosEdicao();
        }
    }

    private void configurarJanela() {
        setTitle(roupaEdicao == null ? "Cadastro de Roupa" : "Editar Roupa");
        setSize(450, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel lblId = new JLabel("Código (ID):");
        lblId.setBounds(25, 20, 150, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(185, 20, 220, 25);
        txtId.setEnabled(false); // Chave primária bloqueada na edição
        add(txtId);

        JLabel lblPeca = new JLabel("Peça / Modelo:");
        lblPeca.setBounds(25, 60, 150, 25);
        add(lblPeca);

        txtPeca = new JTextField();
        txtPeca.setBounds(185, 60, 220, 25);
        add(txtPeca);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(25, 100, 150, 25);
        add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setBounds(185, 100, 220, 25);
        add(txtMarca);

        JLabel lblTamanho = new JLabel("Tamanho (P/M/G/GG):");
        lblTamanho.setBounds(25, 140, 150, 25);
        add(lblTamanho);

        txtTamanho = new JTextField();
        txtTamanho.setBounds(185, 140, 220, 25);
        add(txtTamanho);

        JLabel lblCor = new JLabel("Cor:");
        lblCor.setBounds(25, 180, 150, 25);
        add(lblCor);

        txtCor = new JTextField();
        txtCor.setBounds(185, 180, 220, 25);
        add(txtCor);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(25, 220, 150, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(185, 220, 220, 25);
        add(txtPreco);

        JLabel lblEstoque = new JLabel("Qtd. em Estoque:");
        lblEstoque.setBounds(25, 260, 150, 25);
        add(lblEstoque);

        txtEstoque = new JTextField();
        txtEstoque.setBounds(185, 260, 220, 25);
        add(txtEstoque);

        JLabel lblDataCadastro = new JLabel("Entrada (dd/mm/aaaa):");
        lblDataCadastro.setBounds(25, 300, 150, 25);
        add(lblDataCadastro);

        txtDataCadastro = new JTextField();
        txtDataCadastro.setBounds(185, 300, 220, 25);
        add(txtDataCadastro);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(100, 350, 110, 30);
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(230, 350, 110, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void carregarDadosEdicao() {
        txtId.setText(String.valueOf(roupaEdicao.getId()));
        txtPeca.setText(roupaEdicao.getPeca());
        txtMarca.setText(roupaEdicao.getMarca());
        txtTamanho.setText(roupaEdicao.getTamanho());
        txtCor.setText(roupaEdicao.getCor());
        txtPreco.setText(String.valueOf(roupaEdicao.getPreco()));
        txtEstoque.setText(String.valueOf(roupaEdicao.getQuantidadeEstoque()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataCadastro.setText(sdf.format(roupaEdicao.getDataCadastro()));
    }

    private void salvar() {
        String peca = txtPeca.getText().trim();
        String marca = txtMarca.getText().trim();
        String tamanho = txtTamanho.getText().trim();
        String cor = txtCor.getText().trim();
        String precoTexto = txtPreco.getText().trim().replace(",", ".");
        String estoqueTexto = txtEstoque.getText().trim();
        String dataTexto = txtDataCadastro.getText().trim();

        if (peca.isBlank() || marca.isBlank() || tamanho.isBlank() || cor.isBlank() || precoTexto.isBlank() || estoqueTexto.isBlank() || dataTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            int estoque = Integer.parseInt(estoqueTexto);

            if (preco <= 0 || estoque < 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser maior que zero e o estoque não pode ser negativo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataConvertida = sdf.parse(dataTexto);
            Date dataCadastro = new Date(dataConvertida.getTime());

            RoupaDAO dao = new RoupaDAO();

            if (roupaEdicao == null) {
                Roupa nova = new Roupa(peca, marca, tamanho, cor, preco, estoque, dataCadastro);
                if (dao.inserir(nova)) {
                    JOptionPane.showMessageDialog(this, "Roupa cadastrada com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar roupa no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                roupaEdicao.setPeca(peca);
                roupaEdicao.setMarca(marca);
                roupaEdicao.setTamanho(tamanho);
                roupaEdicao.setCor(cor);
                roupaEdicao.setPreco(preco);
                roupaEdicao.setQuantidadeEstoque(estoque);
                roupaEdicao.setDataCadastro(dataCadastro);

                if (dao.atualizar(roupaEdicao)) {
                    JOptionPane.showMessageDialog(this, "Roupa atualizada com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar roupa no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço e Quantidade em Estoque devem ser números válidos.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de entrada inválida. Utilize o formato dd/mm/aaaa.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        }
    }
}