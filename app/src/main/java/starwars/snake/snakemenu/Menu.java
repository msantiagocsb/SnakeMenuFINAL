package starwars.snake.snakemenu;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Menu extends AppCompatActivity {
    private static MejoresPuntuaciones mejoresPuntuaciones;
    Jugador jugador;
    SoundPool soundPool;
    int carga;
    private MediaPlayer mediaplayer;
    private int cancion1 = -1;
    private int cancion2= -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
       // int soundId = soundPool.load(this, R.raw.star_wars_cantina, 1);
// soundId for reuse later on


        //soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 0);
       // carga=soundPool.load(this,R.raw.mozart,1);
        //soundPool.play(carga,1,1,0,0,1);
        soundPool.play(cancion1, 1, 1, 0, 0, 1);
        //mediaplayer = MediaPlayer.create(this,R.raw.star_wars_cantina);
        //SharedPreferences sharprefs= getSharedPreferences("Punt", getApplicationContext().MODE_PRIVATE);
        boolean cargado = cargarEstado();
        if (cargado == false) {
            Toast.makeText(this, "Fallo al cargar lista", Toast.LENGTH_SHORT).show();
            mejoresPuntuaciones = new MejoresPuntuaciones();

        }
      //  soundPool.play(soundId, 1, 1, 0, 0, 1);
        //jugador = new Jugador("Miguel");
         //soundPool.play(carga,1,1,0,0,1);
        //mediaplayer.start();
        setContentView(R.layout.activity_menu);
        //soundPool.play(carga, 1, 1, 0, 0, 1);
        Button btn1 = (Button) findViewById(R.id.boton_Jugar);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Nombre.class);
                intent.putExtra("Puntuaciones", mejoresPuntuaciones);
                //intent.putExtra("Nombre", jugador);

                startActivityForResult(intent, 0);
            }
        });
        Button btn2 = (Button) findViewById(R.id.boton_salir);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private boolean cargarEstado() {
        ObjectInputStream entrada = null;
        boolean bienCargado = false;
        try {
            FileInputStream fis = new FileInputStream("Punt");
            ObjectInputStream ois = new ObjectInputStream(fis);


            mejoresPuntuaciones = (MejoresPuntuaciones) ois.readObject();

            ois.close();
            //Read from the stored file
            /*FileInputStream fileInputStream = new FileInputStream(new File(
                    "Punt"));
            ObjectInputStream input = new ObjectInputStream(fileInputStream);
            MejoresPuntuaciones mejoresPuntuaciones = (MejoresPuntuaciones) input.readObject();
            input.close();*/
            bienCargado = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bienCargado;
    }


       /* try {


            entrada = new ObjectInputStream(new FileInputStream("Punt"));
            mejoresPuntuaciones = (MejoresPuntuaciones)entrada.readObject();
            entrada.close();
            bienCargado = true;
        } catch (Exception ex) {

        }
        return bienCargado;
    }
*/

}

