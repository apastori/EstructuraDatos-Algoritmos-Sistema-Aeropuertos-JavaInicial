package EstructurasDatos;

public class NodoABB<T> {

    private T dato;

    private NodoABB izq;

    private NodoABB der;

    public NodoABB(T dato, NodoABB izq, NodoABB der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }

    public NodoABB(T dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
    }


    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoABB getIzq() {
        return izq;
    }

    public void setIzq(NodoABB izq) {
        this.izq = izq;
    }

    public NodoABB getDer() {
        return der;
    }

    public void setDer(NodoABB der) {
        this.der = der;
    }
}
