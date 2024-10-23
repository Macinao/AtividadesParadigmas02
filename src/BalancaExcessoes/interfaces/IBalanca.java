package BalancaExcessoes.interfaces;

import BalancaExcessoes.exceptions.*;

import java.util.List;

public interface IBalanca<T> {
    void exportar(List<T> produtos, String pastaArquivoTxt) throws CodigoInvalido, DescricaoBranca, PrecoInvalido, DescricaoVazia, FormatacaoVazia;
}
