package BalancaExcessoes.factory;


import BalancaExcessoes.enums.TipoBalanca;
import BalancaExcessoes.exceptions.TipoInvalidoEnum;
import BalancaExcessoes.interfaces.IBalanca;
import BalancaExcessoes.models.Produto;
import BalancaExcessoes.services.FilizolaSmart;
import BalancaExcessoes.services.ToledoMGV6;
import BalancaExcessoes.services.UranoIntegra;

public class BalancaFactory {
    public static IBalanca<Produto> getBalanca(TipoBalanca tipo) throws TipoInvalidoEnum {

        switch (tipo) {
            case FINIZOLA_SMART:
                return new FilizolaSmart();
            case TOLEDO_MGV6:
                return new ToledoMGV6();
            case URANO_INTEGRA:
                return new UranoIntegra();
            default:
                throw new TipoInvalidoEnum("enum Invalido!");
        }

    }
}