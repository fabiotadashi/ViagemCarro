package br.com.fabiomiyasato.viagemcarro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import br.com.fabiomiyasato.viagemcarro.R;
import br.com.fabiomiyasato.viagemcarro.listener.OnItemClickListener;
import br.com.fabiomiyasato.viagemcarro.model.Gasto;

public class GastoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Gasto> data = Collections.emptyList();

    private OnItemClickListener clickListener;

    public GastoAdapter(Context context, List<Gasto> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gasto, parent, false);
        GastoItemHolder holder = new GastoItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GastoItemHolder myHolder = (GastoItemHolder) holder;
        Gasto current = data.get(position);
        myHolder.tvDescricao.setText(current.getDescricao());
        myHolder.tvValor.setText("R$"  + current.getValor());
        myHolder.tvTipo.setText("Categoria: "+ current.getTipo().getNome());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public Gasto getItem(int position) {
        return data.get(position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private class GastoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDescricao;
        TextView tvValor;
        TextView tvTipo;


        private GastoItemHolder(View itemView) {
            super(itemView);
            tvDescricao = (TextView) itemView.findViewById(R.id.tvDescricao);
            tvValor = (TextView) itemView.findViewById(R.id.tvValor);
            tvTipo = (TextView) itemView.findViewById(R.id.tvTipo);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }

    }
}