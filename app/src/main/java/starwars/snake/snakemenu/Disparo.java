package starwars.snake.snakemenu;

import java.util.Random;

public class Disparo {
    int disparoX;
    int disparoY;
    int direccion;
    Disparo(int comidaX, int comidaY){
        this.disparoX=comidaX;
        this.disparoY=comidaY;
        Random random = new Random();
        direccion= random.nextInt(4 - 1) + 1;

    }
    public void setDisparoX(int disparoX){

        this.disparoX = disparoX;
    }
    public void setDisparoY(int disparoY){

        this.disparoY = disparoY;
    }
    public void setDireccion(int direccion){

        this.direccion = direccion;
    }
    public int getDisparoX(){
        return disparoX;
    }
    public int getDisparoY(){
        return disparoY;
    }
    public int getDireccion(){
        return direccion;
    }

}
