package EstructurasDatos;

import dominio.Aeropuerto;

public class NodoNivel {
    private int posVertice;

    private Aeropuerto verticeAnterior;

    private int nivel;

    public NodoNivel(int posVertice, Aeropuerto verticeAnterior, int nivel) {
        this.posVertice = posVertice;
        this.verticeAnterior = verticeAnterior;
        this.nivel = nivel;
    }

    public int getPosVertice() {
        return this.posVertice;
    }

    public void setPosVertice(int posVertice) {
        this.posVertice = posVertice;
    }

    public Aeropuerto getVerticeAnterior() {
        return this.verticeAnterior;
    }

    public void setVerticeAnterior(Aeropuerto verticeAnterior) {
        this.verticeAnterior = verticeAnterior;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
