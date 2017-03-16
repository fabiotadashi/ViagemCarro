package br.com.fabiomiyasato.viagemcarro.model;

/**
 * Created by FabioMiyasato on 15/03/17.
 */

public class TipoGasto {

    private int id;
    private String nome;

    public TipoGasto() {

    }

    public TipoGasto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoGasto tipoGasto = (TipoGasto) o;

        return id == tipoGasto.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return nome;
    }

}
