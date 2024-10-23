package BalancaExcessoes.services;

import BalancaExcessoes.Main;
import BalancaExcessoes.exceptions.PrecoInvalido;
import BalancaExcessoes.interfaces.IBalanca;
import BalancaExcessoes.models.Produto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilizolaSmart implements IBalanca<Produto> {
    @Override
    public void exportar(List<Produto> produtos, String pastaArquivoTxt) throws PrecoInvalido {
        File directory = new File(pastaArquivoTxt);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pastaArquivoTxt + "/CADTXT.TXT"))) {
            for (Produto produto : produtos) {
                String linha = formatarProduto(produto);
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        } catch (ArrayIndexOutOfBoundsException array) {
            System.out.println("Indices da lista invalidos" + array.getMessage());
        } catch (IllegalArgumentException erro) {
            System.out.println("Valor inserido invalido" + erro.getMessage());
        } catch (NoClassDefFoundError classe) {
            System.out.println("Classe referenciada invalida" + classe.getMessage());
        }
    }

    private String formatarProduto(Produto produto) throws PrecoInvalido {

        String codigo = String.format("%06d", produto.getCodigo());
        String tipo = "9".equals(produto.getTipo()) ? "P" : "U"; // 9 coloquei como se fosse para o peso
        String descricao = String.format("%-22s", produto.getDescricao());
        double valor = produto.getValor() * 100;
        if(valor >= 9000) {
            throw new PrecoInvalido("Preco nao pode ultrapassar 9000");
        }
        String preco = String.format("%07d", (int) valor);

        return codigo + tipo + descricao + preco + "000";
    }
}