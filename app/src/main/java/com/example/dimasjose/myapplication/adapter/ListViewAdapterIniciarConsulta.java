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
 * Created by dimas jose on 01/04/2018.
 */

public class ListViewAdapterIniciarConsulta extends ArrayAdapter<Usuario> {
    private ArrayList<Usuario> usuarios;
    public ListViewAdapterIniciarConsulta(@NonNull Context context, List<Usuario> usuarios) {

        super(context,0, usuarios);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Usuario usuario = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_iniciarconsulta, parent, false);
        TextView tvNome = convertView.findViewById(R.id.textViewNome2);

        tvNome.setText(usuario.nome);

        return convertView;
    }
}
