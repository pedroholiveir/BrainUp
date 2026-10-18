package com.example.brainupprototipo;

/**
 * Configuração da correção de redação.
 * Troque a URL pela do seu Worker depois do deploy.
 * NUNCA coloque a chave do Gemini aqui: ela fica só no Worker.
 */
public final class RedacaoConfig {
    private RedacaoConfig() {}

    public static final String URL = "https://brainup-redacao.brainuptcc.workers.dev";
    public static final int MIN_CHARS = 200;
    public static final int MAX_CHARS = 6000;
}
