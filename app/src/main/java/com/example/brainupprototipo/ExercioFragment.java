package com.example.brainupprototipo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.content.res.ColorStateList;

import java.util.List;

public class ExercioFragment extends Fragment {

    public ExercioFragment() {}

    LinearLayout multipla, vf;

    TextView textpergunta,textperguntavf,textexercio;

    ImageView imagempergunta,imagemperguntavf;

    Button btop1,btop2,btop3,btop4,verdadeiro,falso;

    ImageButton avancar;

    List<Perguntas> perguntas;

    int indice = 0;
    int acertos = 0;
    int respostaSelecionada = -1;
    boolean respondeu = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercio, container, false);

        // layouts
        multipla = view.findViewById(R.id.multipla);
        vf = view.findViewById(R.id.vf);

        // pergunta
        textpergunta = view.findViewById(R.id.textpergunta);
        imagempergunta = view.findViewById(R.id.imagempergunta);
        textperguntavf = view.findViewById(R.id.textperguntavf);
        imagemperguntavf = view.findViewById(R.id.imagemperguntavf);
        textexercio = view.findViewById(R.id.textexercio);

        // botoes
        btop1 = view.findViewById(R.id.btop1);
        btop2 = view.findViewById(R.id.bt0p2);
        btop3 = view.findViewById(R.id.btop3);
        btop4 = view.findViewById(R.id.btop4);

        imagempergunta = view.findViewById(R.id.imagempergunta);

        verdadeiro = view.findViewById(R.id.verdadeiro);
        falso = view.findViewById(R.id.falso);

        avancar = view.findViewById(R.id.btavancar);
        avancar.setVisibility(View.INVISIBLE);

        avancar.setOnClickListener(v ->{

            indice++;

            if(indice < perguntas.size()){
                mostrarPergunta();
            }
            else{
                abrirResultado();
            }
                });

        btop1.setOnClickListener(v -> selecionarResposta(0));
        btop2.setOnClickListener(v -> selecionarResposta(1));
        btop3.setOnClickListener(v -> selecionarResposta(2));
        btop4.setOnClickListener(v -> selecionarResposta(3));

        verdadeiro.setOnClickListener(v -> selecionarResposta(0));
        falso.setOnClickListener(v -> selecionarResposta(1));

        // carregar JSON
        perguntas = jsonutils.carregarPerguntas(requireContext(),"matematica.json");

        // mostrar primeira pergunta
        mostrarPergunta();

        return view;
    }

    public void mostrarPergunta(){

        respondeu = false;
        respostaSelecionada = -1;

        textexercio.setText("Exercício " + (indice + 1));

        avancar.setVisibility(View.INVISIBLE);

        limparSelecao();

        Perguntas p = perguntas.get(indice);

        textpergunta.setText(p.pergunta);
        textperguntavf.setText(p.pergunta);

        if(p.tipo.equals("multipla")){

            multipla.setVisibility(View.VISIBLE);
            vf.setVisibility(View.GONE);

            btop1.setText(p.alternativas[0]);
            btop2.setText(p.alternativas[1]);
            btop3.setText(p.alternativas[2]);
            btop4.setText(p.alternativas[3]);
        }

        if(p.tipo.equals("vf")){

            vf.setVisibility(View.VISIBLE);
            multipla.setVisibility(View.GONE);

            verdadeiro.setText(p.alternativas[0]);
            falso.setText(p.alternativas[1]);
        }

    }
    public void mostrarAvancar(){
        avancar.setVisibility(View.VISIBLE);
    }
    public void abrirResultado(){
        ResultadoFragment fragment = new ResultadoFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("acertos", acertos);
        bundle.putInt("total", perguntas.size());

        fragment.setArguments(bundle);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    public void selecionarResposta(int indiceSelecionado){

        Perguntas p = perguntas.get(indice);

        // 🔁 desselecionar
        if (respostaSelecionada == indiceSelecionado) {

            // se estava certo, remove ponto
            if (respostaSelecionada == p.resposta) {
                acertos--;
            }

            respostaSelecionada = -1;
            respondeu = false;

            limparSelecao();
            avancar.setVisibility(View.INVISIBLE);

            return;
        }

        // 👉 NOVA SELEÇÃO

        // se já tinha respondido antes, ajusta pontuação
        if (respostaSelecionada == p.resposta) {
            acertos--; // remove acerto antigo
        }

        respostaSelecionada = indiceSelecionado;
        respondeu = true;

        // se acertou agora
        if (indiceSelecionado == p.resposta) {
            acertos++;
        }

        limparSelecao();
        destacarSelecionado(indiceSelecionado);

        mostrarAvancar();
    }

    private void limparSelecao() {
        btop1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
        btop2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
        btop3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
        btop4.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
        verdadeiro.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
        falso.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.azul_app)));
    }

    private void destacarSelecionado(int index) {

        if(multipla.getVisibility() == View.VISIBLE){
            Button[] botoes = {btop1, btop2, btop3, btop4};
            botoes[index].setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btselecionada)));
        }

        if(vf.getVisibility() == View.VISIBLE){
            if(index == 0){
                verdadeiro.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btselecionada)));
            } else {
                falso.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.btselecionada)));
            }
        }
    }

}
