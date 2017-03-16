package br.com.fabiomiyasato.viagemcarro;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import br.com.fabiomiyasato.viagemcarro.adapter.GastoAdapter;
import br.com.fabiomiyasato.viagemcarro.dao.GastoDAO;
import br.com.fabiomiyasato.viagemcarro.listener.OnItemClickListener;
import br.com.fabiomiyasato.viagemcarro.model.Gasto;


public class MainActivity extends AppCompatActivity{

    GastoAdapter mAdapter;

    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotal = (TextView)findViewById(R.id.tvTotal);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chama a tela de cadastro e aguarda um retorno que irá chamar o método onActivityResult
                startActivityForResult(new Intent(MainActivity.this, NovoGastoActivity.class),
                        NovoGastoActivity.CODE_NOVO_GASTO);
            }
        });
        carregaGastos();
    }

    //Chamado qndo retornar para essa tela da tela de cadastro de um novo torcedor
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                break;
            case NovoGastoActivity.CODE_NOVO_GASTO:
                Toast.makeText(MainActivity.this, "Gasto Cadastrado", Toast.LENGTH_LONG).show();
                carregaGastos();
                break;
            case DetalheActivity.CODE_ATUALIZAR_GASTO:
                Toast.makeText(MainActivity.this, "Gasto Atualizado", Toast.LENGTH_LONG).show();
                carregaGastos();
                break;

            case DetalheActivity.CODE_DELETE_GASTO:
                Toast.makeText(MainActivity.this, "Gasto Deletado", Toast.LENGTH_LONG).show();
                carregaGastos();
                break;
        }

    }

    private void carregaGastos() {

        GastoDAO gastoDAO = new GastoDAO(this);
        StringBuilder sb= new StringBuilder();
        List<Gasto> gastoList = gastoDAO.getAll();

        double total = 0;
        for (Gasto g: gastoList) {
            total+= g.getValor();
        }

        tvTotal.setText("Gastos totais: "+total);

        RecyclerView rcLista = (RecyclerView) findViewById(R.id.rcLista);
        mAdapter = new GastoAdapter(MainActivity.this, gastoList);
        rcLista.setAdapter(mAdapter);
        rcLista.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Gasto gasto = mAdapter.getItem(position);
                Intent i = new Intent(MainActivity.this, DetalheActivity.class);
                i.putExtra("id", gasto.getId());

                startActivityForResult(i,
                        DetalheActivity.CODE_ATUALIZAR_GASTO);

            }
        });

    }

}
