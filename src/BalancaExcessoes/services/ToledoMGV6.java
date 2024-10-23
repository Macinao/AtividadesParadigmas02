package BalancaExcessoes.services;

import BalancaExcessoes.exceptions.DescricaoBranca;
import BalancaExcessoes.interfaces.IBalanca;
import BalancaExcessoes.models.Produto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ToledoMGV6 implements IBalanca<Produto> {

    @Override
    public void exportar(List<Produto> produtos, String pastaArquivoTxt) throws DescricaoBranca {
        File directory = new File(pastaArquivoTxt);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pastaArquivoTxt + "/ITENSMGV.TXT"))) {
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

    private String formatarProduto(Produto produto) throws DescricaoBranca {

        String dept = "01";
        String tipo = "0";
        String codigo = String.format("%06d", produto.getCodigo());
        String preco = String.format("%06d", (int) (produto.getValor() * 100));
        String descricao = String.format("%-50s", produto.getDescricao());

        if(descricao.isBlank()){
            throw new DescricaoBranca("A descricao deve ser preenchida");
        }

        String linha = dept + tipo + codigo + preco + "000" + descricao +
                "0000000000|01|                                                                      0000000000000000000000000|0000|0||";

        return linha;
    }
}