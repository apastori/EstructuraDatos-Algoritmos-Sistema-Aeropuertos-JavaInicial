package EstructurasDatos;

public interface ILista<T> extends Iterable<T> {

    //Agrega un elemento al inicio
    public void insertar(T dato);

    //retorna la cantidad de elementos
    public int getCantidad();

    //retorna si existe un elemento utilizando internamente el equals de la clase
    public boolean existe(T dato);

    //retorna el elemento utilizando internamente el equals de la clase o null si no lo encuentra
    public T recuperar(T dato);

    //retorna el elemento ubicado en el indice pasado por parametro
    public T recuperar(int indice);

    //muestra los elementos por consola de inicio a fin
    //implementarlo de forma iterativa
    public void mostrarIter();

    //muestra los elementos por consola de inicio a fin
    //implementarlo de forma recursiva
    public void mostrarRec();

    //Retorna true si la lista esta vacia
    public boolean esVacia();

}
