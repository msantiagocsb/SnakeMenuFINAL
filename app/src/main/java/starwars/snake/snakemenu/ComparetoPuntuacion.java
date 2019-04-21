package starwars.snake.snakemenu;

import java.util.Comparator;

public class ComparetoPuntuacion implements Comparator <Jugador>{
@Override
public int compare(Jugador o1, Jugador o2) {


                if(o1.getPuntuacion()>o2.getPuntuacion()){
                        return -1;
                }else if(o1.getPuntuacion()>o2.getPuntuacion()){
                        return 0;
                }else{
                        return 1;
                }
        }
        }


