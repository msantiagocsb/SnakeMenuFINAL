package starwars.snake.snakemenu;

import java.io.Serializable;

public class Dificultad implements Serializable {
    int dificultad;
    Dificultad(int dificultad){
        this.dificultad=dificultad;
    }
    public void setDificultad(int dificultad){

        this.dificultad = dificultad;
    }
    public int getDificultad(){
        return dificultad;
    }
}
