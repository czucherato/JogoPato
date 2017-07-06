package carloszucherato.com.gamepato;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Desenvolvimento-003 on 22/06/2017.
 */

public class Game extends SurfaceView implements Runnable, View.OnTouchListener {

    private final SurfaceHolder holder = getHolder();
    private Pato pato;
    int morto = 0;
    private Cores cores;
    private List<Pato> patos = new ArrayList<Pato>();
    private Tela tela;
    private Bitmap background;
    private boolean estaRodando = true;
    private int erro = 0;
    private int pontos = 0;
    private boolean gameOver = false;
    private boolean win = false;
    private int timeMiliseconds = 5000;

    private MediaPlayer mp2;
    private MediaPlayer mp4;
    private MediaPlayer mp5;

    public Game(Context context) {
        super(context);
        tela=new Tela(context);

        mp2 = MediaPlayer.create(context, R.raw.explosao);
        mp4 = MediaPlayer.create(context, R.raw.win);
        mp5 = MediaPlayer.create(context, R.raw.loose);

        inicializaElementos(context);
        setOnTouchListener(this);
    }

    public void inicializaElementos(Context context){

        patos = new ArrayList<Pato>();

        Random gerador = new Random();

        for (int x = 0; x < 5; x++) {
            int p_x = gerador.nextInt(tela.getLargura() - 50);
            int p_y = gerador.nextInt(tela.getAltura()  - 50);
            int p_direcao = gerador.nextInt(8);

            this.pato = new Pato(p_x, p_y, 120, 120, cores.sorteiaImg(), p_direcao, context);
            patos.add(this.pato);

            Bitmap back = BitmapFactory.decodeResource(getResources(),R.drawable.background);
            this.background= Bitmap.createScaledBitmap(back,tela.getLargura(),tela.getAltura(),false);

        }
    }

    @Override
    public void run() {
        while (estaRodando){
            if(!this.holder.getSurface().isValid()) continue;

            Canvas canvas = this.holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(this.background,0,0,null);

            timeMiliseconds -= 15;
            if(timeMiliseconds <= 0){
                gameOver = true;
                Paint myPaint = new Paint();

                myPaint.setColor(Color.BLACK);
                canvas.drawRect(0,0, tela.getLargura(), tela.getAltura(), myPaint);

                myPaint.setColor(Color.RED);
                myPaint.setTextSize(50);
                canvas.drawText("O TEMPO", (tela.getLargura() / 2)-110, 320, myPaint);
                canvas.drawText("ACABOU", (tela.getLargura() / 2)-100, 410, myPaint);

            }else{

                Paint paintTempo = new Paint();
                paintTempo.setColor(Color.RED);
                paintTempo.setTextSize(25);
                canvas.drawText("Tempo Restante: " + timeMiliseconds /1000, tela.getLargura() - 250, tela.getAltura() - 25, paintTempo);
            }

            Paint myPaint = new Paint();
            myPaint.setColor(Color.BLACK);
            myPaint.setTextSize(35);
            canvas.drawText("Tentativas: "+Integer.toString(erro)+"/5", 30, 50, myPaint);
            canvas.drawText("Pontos: "+Integer.toString(pontos), 30, 100, myPaint);

            if ((gameOver==false) && (win == false)){
                for (Pato p : patos) {
                    p.voa(this.tela);
                    p.desenhaNo(canvas);
                }
            }

            if (erro >= 5) {

                gameOver = true;
                myPaint.setColor(Color.BLACK);
                canvas.drawRect(0,0, tela.getLargura(), tela.getAltura(), myPaint);

                myPaint.setColor(Color.RED);
                myPaint.setTextSize(50);
                canvas.drawText("PERDEU", (tela.getLargura() / 2)-110, 320, myPaint);
                canvas.drawText("PLAYBOY", (tela.getLargura() / 2)-100, 410, myPaint);

                myPaint.setColor(Color.WHITE);
                myPaint.setTextSize(30);
                canvas.drawText("Restante: "+Integer.toString(5 - pontos), (tela.getLargura() / 2)-70, 550, myPaint);

                myPaint.setColor(Color.YELLOW);
                myPaint.setTextSize(20);
                canvas.drawText("Toque na tela para jogar novamente...", (tela.getLargura() / 2)-170, 700, myPaint);
                mp5.start();
                timeMiliseconds = 100000;
            }

            if (pontos == 5){

                win = true;
                myPaint.setColor(Color.BLUE);
                canvas.drawRect(0,0, tela.getLargura(), tela.getAltura(), myPaint);

                myPaint.setColor(Color.WHITE);
                myPaint.setTextSize(50);
                canvas.drawText("PARABÉNS", (tela.getLargura() / 2)-120, 320, myPaint);
                canvas.drawText("VOCÊ É FODA!", (tela.getLargura() / 2)-150, 410, myPaint);
                timeMiliseconds = 100000;
            }

            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void inicia(){
        this.estaRodando=true;
    }

    public void cancela(){
        this.estaRodando=false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch ( event.getAction() ) {

            case MotionEvent.ACTION_DOWN:

                if ((gameOver == true) || (win == true)) {
                    erro = 0;
                    pontos = 0;
                    inicializaElementos(getContext());
                    gameOver = false;
                    win = false;
                    timeMiliseconds = 5000;
                }else {
                    boolean acertou = false;

                    for (Pato p : patos) {
                        if (p.intercepta(event.getX(), event.getY()) == true) {
                            mp2.start();
                            acertou = true;
                            p.setImg_scr(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pato5));
                            p.setDirecao(8);
                            pontos += 1;}
                        if(pontos == 5){
                                mp4.start();
                        }
                    }

                    if (acertou == false) {

                        erro += 1;
                        if (erro == 5 ) {
                        }else{
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:

                break;
        }
        return false;
    }
}
