package carloszucherato.com.gamepato;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Desenvolvimento-003 on 22/06/2017.
 */

public class Pato {

    private int x;
    private int y;
    private int altura;
    private int largura;
    private int cor;
    private int direcao;
    private int velocidade = 1;
    private Bitmap img_scr;

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public Pato(int x, int y, int altura, int largura, int cor, int direcao, Context context) {
        this.x = x;
        this.y = y;
        this.altura = altura;
        this.largura = largura;
        this.cor = cor;
        this.direcao = direcao;
        this.img_scr = BitmapFactory.decodeResource( context.getResources(), cor );
    }

    public void setImg_scr(Bitmap img_scr) {
        this.img_scr = img_scr;
    }

    public void desenhaNo(Canvas canvas){
        Paint myPaint = new Paint();
        //myPaint.setColor(cor);
        //myPaint.setStrokeWidth(10);
        canvas.drawBitmap(img_scr,x,y,myPaint);
        //canvas.drawRect(x, y, x+largura, y+altura, myPaint);
    }

    public void voa(Tela tela){
        switch(this.direcao){
            case 0:
                this.y-=velocidade;
                if ( y < 0 ){
                    this.direcao = 4;
                }
                break;
            case 1:
                this.y -= velocidade;
                this.x += velocidade;
                if ( y < 0 ){
                    this.direcao = 3;
                }
                if ( (x + this.largura) > tela.getLargura() ){
                    this.direcao = 7;
                }
                break;
            case 2:
                this.x += velocidade;
                if ( (x + this.largura) > tela.getLargura() ){
                    this.direcao = 6;
                }
                break;
            case 3:
                this.y += velocidade;
                this.x += velocidade;
                if ( (y + this.altura) > tela.getAltura() ){
                    this.direcao = 1;
                }
                if ( x + this.largura > tela.getLargura() ){
                    this.direcao = 5;
                }

                break;
            case 4:
                this.y += velocidade;
                if ( (y + altura) > tela.getAltura() ){
                    this.direcao = 0;
                }
                break;
            case 5:
                this.y+=velocidade;
                this.x-=velocidade;
                if ( (y + this.altura) > tela.getAltura() ){
                    this.direcao = 7;
                }
                if ( x < 0 ){
                    this.direcao = 3;
                }
                break;
            case 6:
                this.x -= velocidade;
                if ( x < 0 ){
                    this.direcao = 2;
                }
                break;
            case 7:
                this.y -= velocidade;
                this.x -= velocidade;
                if ( y < 0 ){
                    this.direcao = 5;
                }
                if ( x < 0 ){
                    this.direcao = 1;
                }
                break;
            case 8:
                this.y+=10;
                if ( (y - altura) > tela.getAltura() ){
                    //y= 0 - altura;
                }
                break;
            default:
        }
    }

    public boolean intercepta(float x, float y) {

        return x >= this.x && x <= (this.x + this.largura) && y >= this.y && y <= (this.y + this.altura);
    }

    public void cai(){
        this.altura+=5;
    }

    public void pula(){

        this.altura-=150;
    }
}
