package starwars.snake.snakemenu;

import java.util.Random;

public class SerpienteAux {
    int snakeAuxX[];
    int snakeAuxY[];
    int tamaño;
    int direccion;

    SerpienteAux(int tamañoX, int tamañoY) {
        Random random = new Random();
        tamaño = 4;
        snakeAuxX[0] = random.nextInt(tamañoX - tamaño) + 1;
        snakeAuxY[0] = random.nextInt(tamañoY - tamaño);
        direccion = random.nextInt(4 - 1) + 1;
        for (int i = tamaño; i > 0; i--) {
            //para que el cuerpo siga a la cabeza
            //no se incluye la cabeza
            snakeAuxX[i] = snakeAuxX[i - 1];
            snakeAuxY[i] = snakeAuxY[i - 1];


        }

    }

    public int getSnakeAuxX(int i) {
        return snakeAuxX[i];
    }

    public int getSnakeAuxY(int i) {
        return snakeAuxY[i];
    }

    public int getTamaño() {
        return tamaño;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setTamaño(int tamaño) {

        this.tamaño = tamaño;
    }

    public void setSnakeAuxX(int auX) {

        this.snakeAuxX[0] = auX;
    }

    public void setSnakeAuxY(int auY) {

        this.snakeAuxY[0] = auY;
    }

    public void moverSerpiente() {
        switch (getDireccion()) {
            case 1:
                setSnakeAuxY(getSnakeAuxY(0) - 1);
                for (int i = tamaño; i > 0; i--) {
                    snakeAuxY[i] = snakeAuxY[i - 1];
                    snakeAuxX[i] = snakeAuxX[i - 1];
                }
                break;

            case 2:
                setSnakeAuxX(getSnakeAuxX(0) + 1);
                for (int i = tamaño; i > 0; i--) {
                    snakeAuxY[i] = snakeAuxY[i - 1];
                    snakeAuxX[i] = snakeAuxX[i - 1];
                }
                break;

            case 3:
                setSnakeAuxY(getSnakeAuxY(0) + 1);
                for (int i = tamaño; i > 0; i--) {
                    snakeAuxY[i] = snakeAuxY[i - 1];
                    snakeAuxX[i] = snakeAuxX[i - 1];
                }
                break;

            case 4:
                setSnakeAuxX(getSnakeAuxX(0) - 1);
                for (int i = tamaño; i > 0; i--) {
                    snakeAuxY[i] = snakeAuxY[i - 1];
                    snakeAuxX[i] = snakeAuxX[i - 1];
                }
                break;

        }
    }
}

