package com.example.brainupprototipo;

import java.security.PublicKey;

public class Perguntas {

    public String assunto;
    public String pergunta;
    public String imagem;
    public String tipo;
    public String[] alternativas;
    public int resposta;


    public Perguntas(String assunto, String pergunta, String imagem, String tipo, String[] alternativas, int resposta) {

        this.assunto = assunto;
        this.pergunta = pergunta;
        this.imagem = imagem;
        this.tipo = tipo;
        this.alternativas = alternativas;
        this.resposta = resposta;

    }
}
