package BalancaExcessoes;

import BalancaExcessoes.enums.TipoBalanca;
import BalancaExcessoes.exceptions.*;
import BalancaExcessoes.factory.BalancaFactory;
import BalancaExcessoes.interfaces.IBalanca;
import BalancaExcessoes.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws TipoInvalidoEnum, PrecoInvalido, DescricaoBranca, DescricaoVazia, CodigoInvalido, FormatacaoVazia {

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Exceção não capturada pela thread " + thread.getName() + ": " + throwable.getMessage());
        });

        Produto produto1 = new Produto();
        produto1.setCodigo(276);
        produto1.setDescricao("QUEIJO GRUYERE KG");
        produto1.setTipo("9");
        produto1.setPrecoVenda(21.99);

        Produto produto2 = new Produto();
        produto2.setCodigo(288);
        produto2.setDescricao("QUEIJO PROVOLETE KG");
        produto2.setTipo("9");
        produto2.setPrecoVenda(12.29);

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        IBalanca balancaFilizola = BalancaFactory.getBalanca(TipoBalanca.FINIZOLA_SMART);
        balancaFilizola.exportar(produtos, "C:/Users/macia/teste");

        IBalanca balancaToledo = BalancaFactory.getBalanca(TipoBalanca.TOLEDO_MGV6);
        balancaToledo.exportar(produtos, "C:/Users/macia/teste");

        IBalanca balancaUrano = BalancaFactory.getBalanca(TipoBalanca.URANO_INTEGRA);
        balancaUrano.exportar(produtos, "C:/Users/macia/teste");

        System.out.println("Arquivos gerados com sucesso!");

    }
}
