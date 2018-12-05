package com.example.dimasjose.myapplication.model;

import java.io.Serializable;

/**
 * Created by dimas jose on 20/03/2018.
 */


//DETERMINAR OS PARAMETROS QUE VAI TER UM USUARIO, NOME,SEXO E ETC.
public class Usuario implements Serializable {
    private static final long searailVerionUID = 1L;
    public long id;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", datadenascimento='" + datadenascimento + '\'' +
                ", ocupacao='" + ocupacao + '\'' +
                ", observacao='" + observacao + '\'' +
                ", sexo='" + sexo + '\'' +
                ", resultado='" + resultado + '\'' +
                ", pitchbreaks='" + pitchbreaks + '\'' +
                ", f0='" + f0 + '\'' +
                ", sexo='" + jitter + '\'' +
                ", snr='" + snr + '\'' +
                ", shimmerRMS='" + shimmerRMS + '\'' +
                '}';
    }

    public String nome;
    public String datadenascimento;
    public String ocupacao;
    public String observacao;
    public String sexo;
    public String resultado;
    public String pitchbreaks;
    public String f0;
    public String jitter;
    public String snr;
    public String shimmerRMS;

}
