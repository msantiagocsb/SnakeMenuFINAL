package starwars.snake.snakemenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.view.View;


public class PantallaJuego extends SurfaceView implements Runnable {


//SPRINT 2

    //thread para iniciar el juego
    private Thread thread = null;

    // para tener el contexto de la actividad (la referencia de ella)
   private Context context;


    // AÑADIR SONIDOS


    //movimiento de la serpiente
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    //empieza a la derecha
    private Heading heading = Heading.UP;

    // variables del tamño de la pantalla
    private int screenX;
    private int screenY;

    // el tamaño de la serpiente
    private int tamaño;

    //coordenadas de la comida
    private int comidaX;
    private int comidaY;

    private int comidaVenenosaX;
    private int comidaVenenosaY;

    // el tamaño en pixeles de cada bloque en pantalla
    private int blockSize;

    // bloques que hay en la pantalla de juego
    private int  bloquesX;
    private int bloquesY;


    private long nextFrameTime;
    //velocidad del snake
    private int FPS = 5;
    //
    private final long MILLIS_PER_SECOND = 1000;


    // variable con los puntos que llevas
    private int puntuacion;

    // coordenadas de cada parte del snake
    private int[] snakeXs;
    private int[] snakeYs;


    //comprobar si se está jugando
    private volatile boolean isPlaying;

    //fondo del juego
    private Canvas canvas;

    // nesario para usar canvas
    private SurfaceHolder surfaceHolder;

    private boolean perdido=false;//comprueba si has perdido
    // variables para pintar partes
    private Paint paint;
    private Paint paint_cabeza;//para pintar la cabeza
    //private Bitmap halcon;
    boolean moverComida =false; //para mover la comida 1 de cada 2 veces
    int ultimoMoviento;//variable que guarda la ultima direccion que llevaba la comida
    boolean repetir=false;//comprueba si repite el movimiento para alejarse del marco de la pantalla
    boolean perdida;
    int pausa;//variable para pausar el juego

    int finPartida;
    long t;//variable que comprueba el tiempo para la comida venenosa
    int cambiarPosicion;
    int cambiarPosicionSerpiente;
    int cambiar;

    boolean mejorado;
    boolean pausado;
    private MejoresPuntuaciones mejoresPuntuaciones1;
    private Jugador jugador1;
    Disparos disparos;
    Serpientes serpientesAux;
    boolean guardado;
    int frecuencia;
    int frecuenciaDisparo;
    int nombre;
    String titulo;
    int dificultad;


    int nombre2;
    String titulo2;
    public PantallaJuego(Context context, Point size, MejoresPuntuaciones mejoresPuntuaciones, Jugador jugador, int dificultad){
        super(context);

        context = context;

        screenX = size.x;
        screenY = size.y;
        this.dificultad=dificultad;
        if(this.dificultad==1){
            this.bloquesX=40;
            frecuenciaDisparo=12;
            cambiarPosicion=FPS*10;
            cambiarPosicionSerpiente=FPS*25;
        }else if(this.dificultad==2){
            this.bloquesX=60;
            frecuenciaDisparo=9;
            FPS=7;
            cambiarPosicion=FPS*10;
            cambiarPosicionSerpiente=FPS*18;
        }else if(this.dificultad==3){
            this.bloquesX=90;
            frecuenciaDisparo=6;
            FPS=10;
            cambiarPosicion=FPS*10;
            cambiarPosicionSerpiente=FPS*11;
        }else{
            this.bloquesX=10;
        }
        // comprueba cuantos pixeles son cada bloque
        blockSize = screenX / bloquesX;
        bloquesY = screenY / blockSize;


        // Falta la musica

        mejoresPuntuaciones1=mejoresPuntuaciones;
        jugador1=jugador;

        // inicializa los objetos para pintar
        surfaceHolder = getHolder();
        paint = new Paint();
        paint_cabeza= new Paint();//para pintar la cabeza diferente

        //maxima puntuacion
        snakeXs = new int[400];
        snakeYs = new int[400];



        // empieza el juego
        juegoNuevo(mejoresPuntuaciones1,jugador1);
    }


    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if(Actualizar()) {
                comprobar();
                interfaz();
            }

        }
    }
    public void pause() {
        isPlaying = false;
        try {
            thread.join();
            pausado=true;
        } catch (InterruptedException e) {
            // Error
        }
    }
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
        pausado=false;

    }
    public void terminar (){
        //isPlaying=false;
        try{

           // pausado=true;
        }catch(UnsupportedOperationException e){

        }
    }
    //FALTA EL MENU DE PAUSA
    //FALTA EL MENU REANUDAR




    public void juegoNuevo(MejoresPuntuaciones mejoresPuntuaciones1, Jugador jugador1) {
        // empieza con el tamaño de 1
        tamaño = 4;
        snakeXs[0] = bloquesX / 2;
        snakeYs[0] = bloquesY/ 2;
        mejorado=false;
        this.mejoresPuntuaciones1=mejoresPuntuaciones1;
        this.jugador1=new Jugador(jugador1.getNombre());
        // pone la primera comida en la partida
        colocarComida();
        colocarComidaVenenosa();
        t=(System.currentTimeMillis()/1000);
        cambiar=0;
        finPartida=1;
        frecuencia=1;
        nombre=1;
        nombre2=1;
        // pone la puntuacion a 0
        serpientesAux=new Serpientes();

        this.jugador1.setPuntuacion(0);
        disparos= new Disparos();

        Disparo nuevo=new Disparo(comidaX,comidaY);
        disparos.AñadirDisparos(nuevo);
       nombre++;

        pausa=0;
        perdida=false;//variable para comprobar si has perdidio la partida
        // actualiza el siguiente frame
        nextFrameTime = System.currentTimeMillis();
    }



    public void colocarComida() {
        Random random = new Random();
        comidaX = random.nextInt(bloquesX - 1) + 1;
        comidaY = random.nextInt(bloquesY - 2);
    }
    public void colocarComidaVenenosa() {
        Random random = new Random();
        comidaVenenosaX = random.nextInt(bloquesX - 1) + 1;
        comidaVenenosaY = random.nextInt(bloquesY - 2);
    }



    private void comer() {

        // aumenta en 1 el tamaño de la serpiente
        tamaño++;

        // Vuelve a poner comida en la partida
        colocarComida();

        jugador1.setPuntuacion(jugador1.getPuntuacion()+1);
        //Falta el sonido para comer
    }
    private void comerVeneno() {

        // aumenta en 1 el tamaño de la serpiente
        tamaño--;

        // Vuelve a poner comida en la partida
        colocarComidaVenenosa();
        jugador1.setPuntuacion(jugador1.getPuntuacion()-1);
        //Falta el sonido para comer
    }
    private void movimiento(){

        for (int i = tamaño; i > 0; i--) {
            //para que el cuerpo siga a la cabeza
            //no se incluye la cabeza
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];


        }

        //Mueve la cabeza
        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;
        }
    }
    //falta añadir cuando se choque con su cuerpo que muera
    private void movimientoComida(){ //para que la comida se mueva
        int movimiento;

        if(moverComida==true){//la comida se mueve 1 vez por cada 2 veces que se mueve la serpiente
            movimiento= movimientoAleatorio();
            //segundavez=true;}
            switch (movimiento) {
                case 1:
                    comidaY--;
                    break;

                case 2:
                    comidaX++;
                    break;

                case 3:
                    comidaY++;
                    break;

                case 4:
                    comidaX--;
                    break;
            }
            moverComida=false;}//para que en el siguiente refresco no se mueva
        else moverComida = true;//para que en el siguiente respfreesco se mueva



    }
    private void movimientoDisparo(){

        for (int i = 0; i < disparos.getTamaño(); i++) {
            switch (disparos.getDireccion(i)) {
                case 1:
                    disparos.setDisparoY(i,-1);
                    break;

                case 2:
                    disparos.setDisparoX(i,1);
                    break;

                case 3:
                    disparos.setDisparoY(i,1);
                    break;

                case 4:
                    disparos.setDisparoX(i,-1);
                    break;
            }
        }


        }
    private void movimientoSepientesAux(){

        for (int i = 0; i < serpientesAux.getTamaño(); i++) {
            serpientesAux.mover(i);
        }


    }



    public int movimientoAleatorio(){//moviento aleatorio para la comida
        //crea un movimiento
        int movimiento;


        if(repetir==false){
            if(comprobarComidaArriba()==true){
                ultimoMoviento=1;
                repetir=true;
            }
            else if(comprobarComidaIzquierda()==true){
                ultimoMoviento=4;
                repetir=true;
            } else if (comprobarComidaDerecha()==true) {
                ultimoMoviento=2;
                repetir=true;
            }else if (comprobarComidaAbajo()==true) {
                ultimoMoviento=3;
                repetir=true;
            }
            else {
                Random random = new Random();
                ultimoMoviento= random.nextInt(4 - 1) + 1;
            }


            return ultimoMoviento;}
        else{
            repetir=false;
            return ultimoMoviento;
        }

    }
    private boolean comprobarComidaArriba(){
        boolean cambiarDireccionArriba=false;

        if (comidaY == (bloquesY-4)) cambiarDireccionArriba=true;

        return cambiarDireccionArriba;
    }
    private boolean comprobarComidaAbajo(){
        boolean cambiarDireccionAbajo=false;

        if (comidaY == 0) cambiarDireccionAbajo=true;

        return cambiarDireccionAbajo;
    }
    private boolean comprobarComidaIzquierda(){
        boolean cambiarDireccionIzquierda=false;
        if(comidaX>= bloquesX) cambiarDireccionIzquierda=true;
        return cambiarDireccionIzquierda;
    }

    private boolean comprobarComidaDerecha(){
        boolean cambiarDireccionDerecha=false;
        if(comidaX==0) cambiarDireccionDerecha=true;
        return cambiarDireccionDerecha;
    }
    public void comprobar() {
        //comprueba si ha comido


        if (snakeXs[0] == comidaX && snakeYs[0] == comidaY) {
            comer();
        }
        if (snakeXs[0] == comidaVenenosaX && snakeYs[0] == comidaVenenosaY) {
            comerVeneno();
        }

        if(cambiar % cambiarPosicion==0) {
            colocarComidaVenenosa();

        }
        if((cambiar % cambiarPosicionSerpiente)==0){

            Random random=new Random();
            snakeXs[0]= random.nextInt(bloquesX - tamaño) + 1;
            snakeYs[0]=random.nextInt(bloquesY - tamaño);

        }
        if(frecuencia % frecuenciaDisparo==0) {
            titulo = Integer.toString(nombre);
            Disparo titulo=new Disparo(comidaX,comidaY);
            disparos.AñadirDisparos(titulo);
            nombre++;

        }
        cambiar++;
        frecuencia++;
        movimiento();
        movimientoComida();
        movimientoDisparo();
        movimientoSepientesAux();



        //comprobar si esta al final de la pantalla
        if (comprobarLimite()) {//espera 3 segundos para empezar un nuevo juego
                if(mejorado==false) {
                    mejoresPuntuaciones1.añadirPuntuacion(jugador1);
                    guardado = guardarEstado(mejoresPuntuaciones1);
                    mejorado = true;
                }

            //cuando te sales de los limites de la pantalla emnpieza un juego



        }
    }

    private static boolean guardarEstado(MejoresPuntuaciones mejoresPuntuaciones) {

        boolean guardado=false;
        String uno;
        String dos;

        try {


            FileOutputStream fos = new FileOutputStream("punt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mejoresPuntuaciones);

            oos.close();

        }
        catch (Exception e){
            guardado=false;

        }
        return guardado;
    }


    private boolean comprobarLimite(){//comprueba si la serpiente se ha salido del limite

        boolean muerto = false;

        // comprueba si está fuera de la pantalla
        if (snakeXs[0] == -1){
            muerto = true;
            perdida=true;
        }
        if (snakeXs[0] > bloquesX) {
            muerto = true;
            perdida=true;
        }
        if (snakeYs[0] == -1) {
            muerto = true;
            perdida=true;}
        if (snakeYs[0] >= (bloquesY-2)) {
            muerto = true;
            perdida=true;}
        for (int i = tamaño - 1; i > 0; i--) {//comprueba si se come asi mismo
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                muerto = true;
                perdida=true;
            }}
            for (int j = tamaño - 1; j > 0; j--) {//comprueba si se come asi mismo
                for(int k=0;k<disparos.getTamaño();k++){
                if ( (disparos.getDisparoX(k) == snakeXs[j]) && (disparos.getDisparoY(k) == snakeYs[j])) {
                    jugador1.setPuntuacion(jugador1.getPuntuacion()-1);
                }}

        }
        if(jugador1.getPuntuacion()==-1){
            muerto=true;
            jugador1.setPuntuacion(0);
            perdida=true;

        }




        return muerto;
    }

    public void interfaz() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);




            paint.setColor(Color.GREEN);
            paint_cabeza.setColor(Color.WHITE);

            if(perdida==false) {
                paint_cabeza.setTextSize(90);
                canvas.drawText("Marcador: " + jugador1.getPuntuacion(), 10, 70, paint_cabeza);


                for (int i = 0; i < tamaño; i++) {
                    if (i == 0) {//pinta la cabeza de un color y el cuerpo de otro
                        canvas.drawRect(snakeXs[i] * blockSize,
                                (snakeYs[i] * blockSize),
                                (snakeXs[i] * blockSize) + blockSize,
                                (snakeYs[i] * blockSize) + blockSize,
                                paint_cabeza);
                    }


                    else {
                        if (i % 2 == 0) {//para los trozos pares
                            canvas.drawRect(snakeXs[i] * blockSize,
                                    (snakeYs[i] * blockSize),
                                    (snakeXs[i] * blockSize) + blockSize,
                                    (snakeYs[i] * blockSize) + blockSize,
                                    paint);
                        } else {//para los trozos impares
                            canvas.drawRect(snakeXs[i] * blockSize,
                                    (snakeYs[i] * blockSize),
                                    (snakeXs[i] * blockSize) + blockSize,
                                    (snakeYs[i] * blockSize) + blockSize,
                                    paint_cabeza);
                        }
                    }

                }
               if(pausado==false){ paint_cabeza.setTextSize(60);
                canvas.drawText("||", screenX-50, screenY-90, paint_cabeza);}
                else if(pausado ==true){
                   paint_cabeza.setTextSize(60);
                   canvas.drawText(">", screenX-50, screenY-90, paint_cabeza);}


                for(int k=0;k<disparos.getTamaño();k++){
                    paint.setColor(Color.argb(255, colorAleatorio(), colorAleatorio(), colorAleatorio()));
                    canvas.drawRect(disparos.getDisparoX(k) * blockSize,
                            (disparos.getDisparoY(k) * blockSize),
                            (disparos.getDisparoX(k) * blockSize) + blockSize,
                            (disparos.getDisparoY(k) * blockSize) + blockSize,
                            paint);

                }
                /*for(int k=0;k<serpientesAux.getTamaño();k++){
                    for(int i=0; i<serpientesAux.getTamañoSerpiente(i);i++){
                    paint.setColor(Color.argb(255, colorAleatorio(), colorAleatorio(), colorAleatorio()));
                    canvas.drawRect(serpientesAux.getSnakeAuxX(k,i) * blockSize,
                            (serpientesAux.getSnakeAuxY(k,i) * blockSize),
                            (serpientesAux.getSnakeAuxX(k,i) * blockSize) + blockSize,
                            (serpientesAux.getSnakeAuxY(k,i) * blockSize) + blockSize,
                            paint);

                }}*/
                //color de la comida
                paint.setColor(Color.argb(255, 255, 0, 0));
                //dibuja la comida
                canvas.drawRect(comidaX * blockSize,
                        (comidaY * blockSize),
                        (comidaX * blockSize) + blockSize,
                        (comidaY * blockSize) + blockSize,
                        paint);

                paint.setColor(Color.argb(255, 255, 0, 255));
                //dibuja la comida
                canvas.drawRect(comidaVenenosaX * blockSize,
                        (comidaVenenosaY * blockSize),
                        (comidaVenenosaX * blockSize) + blockSize,
                        (comidaVenenosaY * blockSize) + blockSize,
                        paint);
            }
            else{ //cuando pierde por algun motivo en pantalla sale un mensaje de que has perdido y la puncion que alcanzaste en la partida.

                paint_cabeza.setTextSize(160);
                canvas.drawText("Perdiste "+jugador1.getNombre()+"!", screenX/3-200, screenY / 3, paint_cabeza);
                paint_cabeza.setTextSize(120);
                canvas.drawText("Marcador:"+jugador1.getPuntuacion(), screenX/3, screenY / 3 + 120, paint_cabeza);
                paint_cabeza.setTextSize(90);
                canvas.drawText("Mejores Puntuaciones", screenX/3, screenY / 3 + 220, paint_cabeza);

                int j=280;
                for(int k=0;k<mejoresPuntuaciones1.getTamaño();k++){
                    paint_cabeza.setTextSize(50);
                    canvas.drawText((k+1)+" "+mejoresPuntuaciones1.getNombre(k)+" "+mejoresPuntuaciones1.getPuntuacion(k), screenX/3, screenY / 3 + j, paint_cabeza);
                    j=j+60;

                }

                /*if(guardado==true){
                    j=j+60;
                    paint_cabeza.setTextSize(50);
                    canvas.drawText("Guardado!",screenX/3, screenY / 3 + j, paint_cabeza);
                }
                else if(guardado==false){
                    j=j+60;
                    paint_cabeza.setTextSize(50);
                    canvas.drawText("NO Guardado!",screenX/3, screenY / 3 + j, paint_cabeza);

                }*/


            }
            // desbloquea el canvas para que se muestren las nueva sposiciones (si las hay) en el frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public int colorAleatorio(){
        //crea un color aleatorio
        int color;
        Random random = new Random();
        color = random.nextInt(255 - 1) + 1;

        return color;

    }
    public boolean Actualizar() {

        // actualiza en cada frame
        if(nextFrameTime <= System.currentTimeMillis()){


            // define cuando será el siguiente frame
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // devuelve true para que se actualice y dibuja
            if(perdida==true){
                if(finPartida % 24==0){
                    juegoNuevo(mejoresPuntuaciones1,jugador1);
                }else{
                    finPartida++;
                }
            }


            return true;
        }

        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() > (screenX - 110) && motionEvent.getY() > (screenY - 140)) {
                    if (pausa == 0) {
                        pause();
                        pausa = 1;
                    } else {
                        resume();
                        pausa = 0;
                    }

                }
                //cuando se toca del medio de la pantalla hacia la derecha
                else if (motionEvent.getX() >= (screenX / 2) && motionEvent.getX() < (screenX - 110) && motionEvent.getY() < (screenY - 140)) {
                    switch (heading) {
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                    //cuando se toca del medio de la pantalla hacia la izquierda
                } else {
                    switch (heading) {
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
                if (perdida == true) {
                    if (motionEvent.getX() > (screenX - 110) && motionEvent.getY() > (screenY - 140)) {
                       terminar();}}



            /*case MotionEvent.ACTION_MOVE:
                if(pausa==0) {
                    pause();
                    pausa=1;
                }
                else{
                    resume();
                    pausa=0;
                }*/


        }

        return true;
    }


}
