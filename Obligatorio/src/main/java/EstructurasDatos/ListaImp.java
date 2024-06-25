package EstructurasDatos;

import java.util.Iterator;

public class ListaImp<T extends Comparable<T>> implements ILista<T> {

    protected NodoLista<T> inicio;

    protected int cant;

    public ListaImp() {
        this.inicio = null;
        this.cant = 0;
    }

    public NodoLista<T> getInicio() {
        return this.inicio;
    }

    @Override
    public void insertar(T dato) {
        NodoLista<T> nuevoNodo=new NodoLista<T>(dato,inicio);
        cant++;
        this.inicio=nuevoNodo;
    }

    @Override
    public int getCantidad() {
        return this.cant;
    }

    @Override
    public boolean existe(T dato) {
        if (this.esVacia()) {
            return false;
        }
        NodoLista<T> nodoInicial = this.getInicio();
        while (nodoInicial != null) {
            if (nodoInicial.getDato().compareTo(dato) == 0) {
                return true;
            }
            nodoInicial = nodoInicial.getSig();
        }
        return false;
    }

    @Override
    public T recuperar(T dato) {
        return null;
    }

    @Override
    public T recuperar(int indice) {
        return null;
    }

    @Override
    public void mostrarIter() {

    }

    @Override
    public void mostrarRec() {

    }

    @Override
    public boolean esVacia() {
        return this.cant == 0;
    }

    public String toString() {
        if (this.esVacia()) {
            return "";
        }
        String listaString = "";
        NodoLista<T> nodoInicial = this.getInicio();
        while (nodoInicial != null) {
            listaString += nodoInicial.getDato().toString();
            listaString += "|";
            nodoInicial = nodoInicial.getSig();
        }
        return listaString;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private NodoLista<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.getDato();
                aux = aux.getSig();
                return dato;
            }

            @Override
            public void remove(){
            }

        };
    }

}
