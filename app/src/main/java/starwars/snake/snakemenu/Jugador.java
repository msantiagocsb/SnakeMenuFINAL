package starwars.snake.snakemenu;

import java.io.Serializable;

public class Jugador implements Serializable {
    private String  Nombre;
    private int Puntuacion;


    public Jugador(String Nombre){
        this.Nombre=Nombre;
        this.Puntuacion=0;
    }
    public String getNombre(){
        return Nombre;
    }
    public void setNombre(String Nombre){

        this.Nombre = Nombre;
    }
    public int getPuntuacion(){
        return Puntuacion;
    }
    public void setPuntuacion(int Puntuacion){

        this.Puntuacion = Puntuacion;
    }
}
