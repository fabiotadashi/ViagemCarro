package br.com.fabiomiyasato.viagemcarro.model;

/**
 * Created by FabioMiyasato on 15/03/17.
 */

public class Gasto {

    private int id;
    private String descricao;
    private double valor;
    private TipoGasto tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoGasto getTipo() {
        return tipo;
    }

    public void setTipo(TipoGasto tipo) {
        this.tipo = tipo;
    }
}
