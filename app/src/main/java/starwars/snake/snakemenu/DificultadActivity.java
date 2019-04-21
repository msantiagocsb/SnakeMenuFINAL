package starwars.snake.snakemenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DificultadActivity extends AppCompatActivity {
    Bundle Puntuaciones;
    Bundle Jugador;
    Jugador jugador;
    //Dificultad dificultad;
    int dificultad;
    MejoresPuntuaciones mejoresPuntuaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);
        Puntuaciones =getIntent().getExtras();
        mejoresPuntuaciones= (MejoresPuntuaciones) Puntuaciones.getSerializable("Puntuaciones1");
        Jugador =getIntent().getExtras();
        jugador= (Jugador) Jugador.getSerializable("Nombre");
        Button btn1 = (Button) findViewById(R.id.boton_JugarFacil);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dificultad=1;

                Intent intent = new Intent(v.getContext(), Snake.class);
                intent.putExtra("Puntuaciones1", mejoresPuntuaciones);
                intent.putExtra("Nombre", jugador);
                intent.putExtra("Dificultad", dificultad);

                startActivityForResult(intent, 0);
            }
        });
        Button btn2 = (Button) findViewById(R.id.boton_JugarNormal);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                dificultad=2;

                Intent intent = new Intent(v3.getContext(), Snake.class);
                intent.putExtra("Puntuaciones1", mejoresPuntuaciones);
                intent.putExtra("Nombre", jugador);
                intent.putExtra("Dificultad", dificultad);

                startActivityForResult(intent, 0);
            }
        });
        Button btn3 = (Button) findViewById(R.id.boton_JugarDificil);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                dificultad=3;


                Intent intent = new Intent(v1.getContext(), Snake.class);
                intent.putExtra("Puntuaciones1", mejoresPuntuaciones);
                intent.putExtra("Nombre", jugador);
                intent.putExtra("Dificultad", dificultad);

                startActivityForResult(intent, 0);
            }
        });
        Button btn4 = (Button) findViewById(R.id.boton_atras);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                finish();
            }
        });
    }
}
