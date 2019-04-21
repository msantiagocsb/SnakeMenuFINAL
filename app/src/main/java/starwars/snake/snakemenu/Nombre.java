package starwars.snake.snakemenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Nombre extends AppCompatActivity {
    EditText text;
    Boolean acabarjuego;
    Bundle Puntuaciones;
    Bundle Jugador;
    Jugador jugador;
    MejoresPuntuaciones mejoresPuntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);
        Puntuaciones =getIntent().getExtras();
        mejoresPuntuaciones= (MejoresPuntuaciones) Puntuaciones.getSerializable("Puntuaciones");
        //Jugador=getIntent().getExtras();
        //jugador= (Jugador) Jugador.getSerializable("Nombre");
        //jugador= (Jugador) Jugador.getSerializable("Nombre");
        //jugador=new Jugador(nombre);
        Button btn1 = (Button) findViewById(R.id.boton_Jugar2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text=(EditText)findViewById(R.id.nombre);
                //String nombre=text.getText().toString();
                jugador=new Jugador(text.getText().toString());
                Intent intent = new Intent(v.getContext(), DificultadActivity.class);
                intent.putExtra("Puntuaciones1", mejoresPuntuaciones);
                intent.putExtra("Nombre", jugador);

                startActivityForResult(intent, 0);
            }
        });
        Button btn2 = (Button) findViewById(R.id.boton_salir2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
