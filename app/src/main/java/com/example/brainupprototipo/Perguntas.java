package com.example.brainupprototipo;

public class Perguntas {

    public String assunto;
    public String pergunta;
    public String imagem;
    public String tipo;
    public String dificuldade;
    public String[] alternativas;
    public int resposta;

    public Perguntas(String assunto, String pergunta, String imagem, String tipo,
                     String dificuldade, String[] alternativas, int resposta) {
        this.assunto = assunto;
        this.pergunta = pergunta;
        this.imagem = imagem;
        this.tipo = tipo;
        this.dificuldade = dificuldade;
        this.alternativas = alternativas;
        this.resposta = resposta;
    }
}
