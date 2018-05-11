package com.example.dimasjose.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimas jose on 21/03/2018.
 */
// CLASSE QUE CRIA COMO QUE A LISTA VAI SER APRESENTADA
    // CONFIGURA O ADAPTER LEYOUT XML
public class ListViewAdapter extends ArrayAdapter<Usuario>{

private ArrayList<Usuario> usuarios;
    public ListViewAdapter(@NonNull Context context, List<Usuario> usuarios) {

        super(context,0, usuarios);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Usuario usuario = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout_relative, parent, false);
        TextView tvNome = (TextView) convertView.findViewById(R.id.textViewNome);
        TextView tvDatadenascimento = (TextView) convertView.findViewById(R.id.textViewData);
        TextView tvSexo = (TextView) convertView.findViewById(R.id.textViewSexo);
        TextView tvOcupacao = convertView.findViewById(R.id.textViewOcupacao);
        TextView tvObservacao = convertView.findViewById(R.id.textViewObservacao);
        TextView tvResultado = convertView.findViewById(R.id.textViewResultado);

        tvNome.setText(usuario.nome);
        tvDatadenascimento.setText(usuario.datadenascimento);
        tvSexo.setText(usuario.sexo);
        tvOcupacao.setText(usuario.ocupacao);
        tvObservacao.setText(usuario.observacao);
        tvResultado.setText(usuario.resultado);
        return convertView;
    }
}

