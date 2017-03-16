package br.com.fabiomiyasato.viagemcarro;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.com.fabiomiyasato.viagemcarro.dao.GastoDAO;
import br.com.fabiomiyasato.viagemcarro.dao.TipoGastoDAO;
import br.com.fabiomiyasato.viagemcarro.model.Gasto;
import br.com.fabiomiyasato.viagemcarro.model.TipoGasto;

public class DetalheActivity extends AppCompatActivity {

    public final static int CODE_ATUALIZAR_GASTO = 1003;
    public final static int CODE_DELETE_GASTO = 1004;

    private int gastoId = 0;

    private TextInputLayout tilDescricao;
    private Spinner spTipoGasto;
    private EditText etValor;

    private List<TipoGasto> tipoGastoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        tilDescricao = (TextInputLayout) findViewById(R.id.tilEditDescricao);
        etValor = (EditText) findViewById(R.id.etEditValor);
        spTipoGasto = (Spinner) findViewById(R.id.spEditTipoGasto);

        TipoGastoDAO tipoGastoDAO = new TipoGastoDAO(this);
        tipoGastoList = tipoGastoDAO.getAll();

        ArrayAdapter<TipoGasto> adapter =
                new ArrayAdapter<TipoGasto>(getApplicationContext(), R.layout.tipo_spinner_item, tipoGastoList);

        adapter.setDropDownViewResource(R.layout.tipo_spinner_item);

        spTipoGasto.setAdapter(adapter);

        GastoDAO gastoDAO = new GastoDAO(this);
        if(getIntent() != null) {
            gastoId = getIntent().getIntExtra("id",0);
            Gasto gasto = gastoDAO.getBy(gastoId);
            tilDescricao.getEditText().setText(gasto.getDescricao());
            etValor.setText(String.valueOf(gasto.getValor()));
            spTipoGasto.setSelection(adapter.getPosition(gasto.getTipo()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDelete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletar();
            }
        });

    }

    public void atualizar(View v) {
        GastoDAO gastoDAO = new GastoDAO(this);
        Gasto gasto = new Gasto();
        gasto.setId(gastoId);
        gasto.setDescricao(tilDescricao.getEditText().getText().toString());
        gasto.setValor(Double.valueOf(etValor.getText().toString()));
        gasto.setTipo((TipoGasto) spTipoGasto.getSelectedItem());

        gastoDAO.atualizar(gasto);

        retornaParaTelaAnterior(CODE_ATUALIZAR_GASTO);
    }


    public void deletar() {
        GastoDAO gastoDAO = new GastoDAO(this);
        gastoDAO.delete(gastoId);

        retornaParaTelaAnterior(CODE_DELETE_GASTO);
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior(int code) {
        Intent intentMessage=new Intent();
        setResult(code, intentMessage);
        finish();
    }
}
