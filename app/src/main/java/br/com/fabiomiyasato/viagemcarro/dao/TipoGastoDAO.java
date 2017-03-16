package br.com.fabiomiyasato.viagemcarro.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.fabiomiyasato.viagemcarro.model.TipoGasto;

/**
 * Created by FabioMiyasato on 15/03/17.
 */

public class TipoGastoDAO {

    private DBOpenHelper banco;

    public TipoGastoDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String TABELA_TIPO = "tipo";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";

    public List<TipoGasto> getAll() {
        List<TipoGasto> tipoList = new LinkedList<>();

        String query = "SELECT  * FROM " + TABELA_TIPO;

        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TipoGasto tipo = null;

        if (cursor.moveToFirst()) {
            do {
                tipo = new TipoGasto();
                tipo.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                tipo.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                tipoList.add(tipo);
            } while (cursor.moveToNext());
        }
        return tipoList;
    }

    public TipoGasto getBy(int id) {

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_TIPO, colunas, where, null, null, null, null, null);

        TipoGasto tipo = null;

        if(cursor != null)
        {
            cursor.moveToFirst();
            tipo = new TipoGasto();
            tipo.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            tipo.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return tipo;
    }

}
