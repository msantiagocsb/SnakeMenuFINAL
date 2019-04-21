package starwars.snake.snakemenu;

import java.util.ArrayList;

public class Disparos {
    ArrayList <Disparo> disparos;
    Disparos(){
        disparos= new ArrayList<>();
    }
    public void AñadirDisparos(Disparo disparo){
        disparos.add(disparo);
    }
    public int getDisparoY(int i){
        int disparoY=disparos.get(i).getDisparoY();
        return disparoY;
    }
    public int getDisparoX(int i){
        int disparoX=disparos.get(i).getDisparoX();
        return disparoX;
    }
    public int getDireccion(int i){
        int direccion=disparos.get(i).getDireccion();
        return direccion;
    }
    public void setDisparoY(int i,int disparoY){
        disparos.get(i).setDisparoY(disparos.get(i).getDisparoY()+disparoY);

    }
    public void setDisparoX(int i,int disparoX){
        disparos.get(i).setDisparoX(disparos.get(i).getDisparoX()+disparoX);

    }
    public int getTamaño(){
        int i=disparos.size();
        return i;
    }

}
