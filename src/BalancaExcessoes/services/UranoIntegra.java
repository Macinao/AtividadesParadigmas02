package BalancaExcessoes.services;

import BalancaExcessoes.exceptions.CodigoInvalido;
import BalancaExcessoes.exceptions.DescricaoVazia;
import BalancaExcessoes.exceptions.FormatacaoVazia;
import BalancaExcessoes.interfaces.IBalanca;
import BalancaExcessoes.models.Produto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class UranoIntegra implements IBalanca<Produto> {

    @Override
    public void exportar(List<Produto> produtos, String pastaArquivoTxt) throws CodigoInvalido, DescricaoVazia, FormatacaoVazia {
        File directory = new File(pastaArquivoTxt);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pastaArquivoTxt + "/PRODUTOS.TXT"))) {
            for (Produto produto : produtos) {
                String linha = formatarProduto(produto);
                if(linha.isEmpty()){
                    throw new FormatacaoVazia("Formatacao nao pode estar vazia!");
                }
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

    private String formatarProduto(Produto produto) throws CodigoInvalido, DescricaoVazia {
        if(produto.getCodigo() < 0) {
            throw new CodigoInvalido("Codigo: " + produto.getCodigo() + "invalido!");
        }
        String codigo = String.format("%06d", produto.getCodigo());
        String flag = "*";
        String tipo = "9".equals(produto.getTipo()) ? "0" : "6"; // 9 coloquei como se fosse para o peso
        String descricao = String.format("%-20s", produto.getDescricao());
        if(descricao.isEmpty()) {
            throw new DescricaoVazia("A descrisao nao pode estar vazia");
        }
        String preco = String.format("%09.2f", produto.getValor()).replace(".", ",");

        return codigo + flag + tipo + descricao + preco + "00000D";
    }
}

