package EstructurasDatos;

public interface IAbb<T extends Comparable<T>> {

    void insertar(T x);

    boolean pertenece(T x);

    String listarAscendente();

    String listarDescendente();

    T borrarMinimo();

    ListaImp<T> obtenerListaAscendente();

    void imprimirElementosNivel(int nivel);

    int altura();

    int cantidadElementosMayores(T valor);

    ListaImp<T> listaElementosMayores(T valor);

    public int cantNodos();

}
