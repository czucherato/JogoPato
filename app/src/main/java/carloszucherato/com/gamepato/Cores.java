package carloszucherato.com.gamepato;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Desenvolvimento-003 on 22/06/2017.
 */

public class Cores {

    public static Paint getCorDoPassaro() {

        Paint vermelho = new Paint();
        vermelho.setColor(0xFFFF0000);
        return vermelho;

    }

    private static int[] cores = {
            Color.GRAY,
            Color.WHITE,
            Color.BLUE,
            Color.YELLOW,
            Color.RED
    };

    private static int[] imgPatos = {
            R.drawable.pato1,
            R.drawable.pato2,
            R.drawable.pato3,
            R.drawable.pato4
    };

    public static int sorteiaCor() {
        return cores[ (int) ( Math.random() * cores.length ) ];
    }

    public static int sorteiaImg() {
        return imgPatos[ (int) ( Math.random() * imgPatos.length ) ];
    }
}
