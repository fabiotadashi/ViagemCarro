package br.com.fabiomiyasato.viagemcarro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.fabiomiyasato.viagemcarro.model.Gasto;
import br.com.fabiomiyasato.viagemcarro.model.TipoGasto;

/**
 * Created by FabioMiyasato on 16/03/17.
 */

public class GastoDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;
    private Context context;

    public GastoDAO(Context context) {
        this.context = context;
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_GASTO = "gasto";

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_VALOR = "valor";
    private static final String COLUNA_TIPO_ID = "tipo_id";

    //private static final String[] COLUMNS = {COLUNA_ID, COLUNA_NOME, COLUNA_CLUBE_ID};

    public String add(Gasto gasto){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO, gasto.getDescricao());
        values.put(COLUNA_TIPO_ID, gasto.getTipo().getId());
        values.put(COLUNA_VALOR, gasto.getValor());

        resultado = db.insert(TABELA_GASTO,
                null,
                values);

        db.close();

        if(resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }


    public String atualizar(Gasto gasto){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO, gasto.getDescricao());
        values.put(COLUNA_TIPO_ID, gasto.getTipo().getId());
        values.put(COLUNA_VALOR, gasto.getValor());

        resultado = db.update(TABELA_GASTO,
                values,
                "id = "+gasto.getId(),
                null);

        db.close();

        if(resultado == -1) {
            return "Erro ao atualizar registro";
        } else {
            return "Registro atualizado com sucesso";
        }
    }


    public String delete(int id){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();


        resultado = db.delete(TABELA_GASTO,
                "id = "+id,
                null);

        db.close();

        if(resultado == -1) {
            return "Erro ao atualizar registro";
        } else {
            return "Registro atualizado com sucesso";
        }
    }


    public List<Gasto> getAll() {
        List<Gasto> gastoList = new LinkedList<>();

        String rawQuery = "SELECT t.*, c.nome FROM " + this.TABELA_GASTO + " t INNER JOIN " + TipoGastoDAO.TABELA_TIPO
                + " c ON t." + this.COLUNA_TIPO_ID + " = c." + TipoGastoDAO.COLUNA_ID +
                " ORDER BY " + this.COLUNA_ID  + " ASC";

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);

        Gasto gasto = null;
        if (cursor.moveToFirst()) {
            do {
                gasto = new Gasto();

                gasto.setId(cursor.getInt(0));
                gasto.setDescricao(cursor.getString(2));
                gasto.setValor(cursor.getDouble(3));
                gasto.setTipo(new TipoGasto(cursor.getInt(1),
                        cursor.getString(4)));

                gastoList.add(gasto);
            } while (cursor.moveToNext());
        }
        return gastoList;
    }

    public Gasto getBy(int id) {

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_TIPO_ID, COLUNA_DESCRICAO, COLUNA_VALOR};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_GASTO, colunas, where, null, null, null, null, null);

        TipoGastoDAO tipoGastoDAO = new TipoGastoDAO(context);

        Gasto gasto = null;

        if(cursor != null)
        {
            cursor.moveToFirst();
            gasto = new Gasto();
            gasto.setDescricao(cursor.getString(cursor.getColumnIndex(COLUNA_DESCRICAO)));
            gasto.setValor(Double.valueOf(cursor.getString(cursor.getColumnIndex(COLUNA_VALOR))));
            gasto.setTipo(tipoGastoDAO.getBy(Integer.valueOf(cursor.getString(cursor.getColumnIndex(COLUNA_TIPO_ID)))));
            gasto.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return gasto;
    }

}
