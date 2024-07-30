package com.analisedecredito.aplicacao_analise_credito.enums;

public enum Identificador {

    RENDATOTAL(1, "RendaTotal"),
    PARCELA(2, "Parcela"),
    IDADE(3, "Idade"),
    BENEFICIOINSS(34, "BeneficioInss");

    private final int valor;
    private final String descricao;

    Identificador(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Identificador fromValor(int valor) {
        for (Identificador identificador : Identificador.values()) {
            if (identificador.getValor() == valor) {
                return identificador;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + valor);
    }
}
 