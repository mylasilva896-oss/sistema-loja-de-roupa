package telas;

import dao.RoupaDAO;
import entidade.Roupa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaConsultaRoupa extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    private RoupaDAO dao = new RoupaDAO();
    private List<Roupa> listaRoupas;

    public TelaConsultaRoupa() {
        setTitle("Loja de Roupas - Consulta de Estoque");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Peça", "Marca", "Tamanho", "Cor", "Preço (R$)", "Estoque", "Data Entrada"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnNovo = new JButton("Nova Roupa");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> {
            TelaCadastroRoupa tela = new TelaCadastroRoupa(null, this::carregarTabela);
            tela.setVisible(true);
        });

        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());

        carregarTabela();
    }

    public void carregarTabela() {
        modeloTabela.setRowCount(0);
        listaRoupas = dao.listar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Roupa r : listaRoupas) {
            modeloTabela.addRow(new Object[]{
                    r.getId(),
                    r.getPeca(),
                    r.getMarca(),
                    r.getTamanho(),
                    r.getCor(),
                    String.format("%.2f", r.getPreco()),
                    r.getQuantidadeEstoque(),
                    sdf.format(r.getDataCadastro())
            });
        }
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma roupa na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Roupa selecionada = listaRoupas.get(linha);
        TelaCadastroRoupa tela = new TelaCadastroRoupa(selecionada, this::carregarTabela);
        tela.setVisible(true);
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma roupa na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Roupa selecionada = listaRoupas.get(linha);
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a peça \"" + selecionada.getPeca() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(selecionada.getId())) {
                JOptionPane.showMessageDialog(this, "Roupa excluída com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir roupa.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}