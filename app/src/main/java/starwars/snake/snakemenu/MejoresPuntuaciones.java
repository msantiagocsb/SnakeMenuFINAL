package starwars.snake.snakemenu;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MejoresPuntuaciones implements Serializable {
    private ArrayList<Jugador> mejorespuntuaciones;
    public MejoresPuntuaciones(){
        mejorespuntuaciones=new ArrayList<Jugador>();
        //Jugador jugador1= new Jugador("NULL");
        /*for(int i=0;i<5;i++){
          //  mejorespuntuaciones.set(i,new Jugador("Hola"));
            mejorespuntuaciones.add(new Jugador("NULL"));
        }*/
    }
    public void añadirPuntuacion(Jugador j1) {
        //boolean añadido = false;
        mejorespuntuaciones.add(j1);

               Collections.sort(mejorespuntuaciones,new ComparetoPuntuacion());
               if(mejorespuntuaciones.size()==6){
                   mejorespuntuaciones.remove(5);
               }


            }
    public int getTamaño(){
        int tamaño=mejorespuntuaciones.size();
        return tamaño;
    }
    public int getPuntuacion(int i){
        int puntuacion=mejorespuntuaciones.get(i).getPuntuacion();
        return puntuacion;
    }
    public String getNombre(int i){
        String nombre=mejorespuntuaciones.get(i).getNombre();
        return nombre;
    }
    public void guardarText(String nombreFichero){

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombreFichero);
            pw = new PrintWriter(fichero);

            for(int i=0;i<5;i++){
                pw.println(mejorespuntuaciones.get(i));
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
