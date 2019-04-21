package starwars.snake.snakemenu;

import java.util.ArrayList;

public class Serpientes {
    ArrayList<SerpienteAux> serpientes;
    Serpientes(){
        serpientes=new ArrayList<SerpienteAux>();
    }
    public int getSnakeAuxX(int i,int k){
        int SnakeAuxX=serpientes.get(i).getSnakeAuxX(k);
        return SnakeAuxX;
    }
    public int getSnakeAuxY(int i, int k){
        int SnakeAuxY=serpientes.get(i).getSnakeAuxY(k);
        return SnakeAuxY;
    }
    public void setTamaño(int i,int tamaño){
       serpientes.get(i).setTamaño(serpientes.get(i).getTamaño()+tamaño);

    }
    public void setSnakeAuxX(int i,int auxX){
        serpientes.get(i).setSnakeAuxX(serpientes.get(i).getSnakeAuxX(i)+auxX);

    }
    public void setSnakeAuxY(int i,int auxY){
        serpientes.get(i).setSnakeAuxY(serpientes.get(i).getSnakeAuxX(i)+auxY);

    }
    public int getDireccion(int i){
        int direccion=serpientes.get(i).getDireccion();
        return direccion;
    }
    public int getTamaño(){
        int i=serpientes.size();
        return i;
    }
    public int getTamañoSerpiente(int i){
        int tamañoS=serpientes.get(i).getTamaño();
        return tamañoS;
    }
    public void mover(int i){
        serpientes.get(i).moverSerpiente();
    }
    public void AñadirSerpiente(SerpienteAux serpiente){
        serpientes.add(serpiente);
    }
}
