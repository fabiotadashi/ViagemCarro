package br.com.fabiomiyasato.viagemcarro;

import android.content.Intent;
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


public class NovoGastoActivity extends AppCompatActivity {

    public final static int CODE_NOVO_GASTO = 1002;

    private TextInputLayout tilDescricao;
    private Spinner spTipoGasto;
    private EditText etValor;

    private List<TipoGasto> tipoGastoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);

        tilDescricao = (TextInputLayout) findViewById(R.id.tilDescricao);
        etValor = (EditText) findViewById(R.id.etValor);
        spTipoGasto = (Spinner) findViewById(R.id.spTipoGasto);

        TipoGastoDAO tipoGastoDAO = new TipoGastoDAO(this);
        tipoGastoList = tipoGastoDAO.getAll();

        ArrayAdapter<TipoGasto> adapter =
                new ArrayAdapter<TipoGasto>(getApplicationContext(), R.layout.tipo_spinner_item, tipoGastoList);

        adapter.setDropDownViewResource(R.layout.tipo_spinner_item);

        spTipoGasto.setAdapter(adapter);
    }

    public void cadastrar(View v) {
        GastoDAO gastoDAO = new GastoDAO(this);
        Gasto gasto = new Gasto();
        gasto.setDescricao(tilDescricao.getEditText().getText().toString());
        gasto.setValor(Double.valueOf(etValor.getText().toString()));
        gasto.setTipo((TipoGasto) spTipoGasto.getSelectedItem());

        gastoDAO.add(gasto);

        retornaParaTelaAnterior();
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_NOVO_GASTO, intentMessage);
        finish();
    }
}
