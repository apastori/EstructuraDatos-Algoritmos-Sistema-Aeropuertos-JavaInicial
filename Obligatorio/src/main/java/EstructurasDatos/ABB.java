package EstructurasDatos;

import dominio.DominioyCantNodos;

public class ABB<T extends Comparable<T>> implements IAbb<T> {
    private NodoABB<T> raiz;

    public ABB(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    public ABB() {
        this.raiz = null;
    }

    public void insertar(T dato) {
        if (this.raiz == null) {
            this.raiz = new NodoABB<T>(dato);
        } else {
            insertar(this.raiz, dato);
        }
    }

    //    a. 4, 2, 6, 1, 3, 5, 7
    private void insertar(NodoABB<T> nodo, T dato) {
        //comparo si es menor el dato
        if (dato.compareTo(nodo.getDato()) > 0) {
            if (nodo.getDer() == null) {
                nodo.setDer(new NodoABB<T>(dato));
            } else {
                insertar(nodo.getDer(), dato);
            }
        } else {
            if (nodo.getIzq() == null) {
                nodo.setIzq(new NodoABB<T>(dato));
            } else {
                insertar(nodo.getIzq(), dato);
            }
        }
    }

    public boolean pertenece(T dato) {
        return this.pertence(this.raiz, dato);
    }

    private boolean pertence(NodoABB<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getDato().compareTo(dato) == 0) {
            return true;
        }
        if (dato.compareTo(nodo.getDato()) > 0) {
            return pertence(nodo.getDer(), dato);
        }
        return pertence(nodo.getIzq(), dato);
    }

    //public T perteneceObj(T dato) {
    //    return this.perteneceObj(this.raiz, dato);
    //}
    public DominioyCantNodos perteneceObj(T dato) {
        DominioyCantNodos objetoCantidadNodos = new DominioyCantNodos();
        NodoABB<T> nodoInicial = this.raiz;
        this.pertence(nodoInicial, dato, objetoCantidadNodos);
        return objetoCantidadNodos;
    }

    private void pertence(NodoABB<T> nodo, T dato, DominioyCantNodos pasajeroyCantNodos) {
        if (nodo == null) {
            return;
        }
        if (nodo.getDato().compareTo(dato) == 0) {
            pasajeroyCantNodos.setDominio(nodo.getDato().toString());
            return;
        }
        pasajeroyCantNodos.setcantNodos(pasajeroyCantNodos.getcantNodos() + 1);
        if (dato.compareTo(nodo.getDato()) > 0) {
            pertence(nodo.getDer(), dato, pasajeroyCantNodos);
        }
        pertence(nodo.getIzq(), dato, pasajeroyCantNodos);
    }

    public String listarAscendente() {
        if (this.raiz != null) {
            String listaAscendenteString = listarAscendente(this.raiz);
            return listaAscendenteString.substring(0, listaAscendenteString.length() - 1);
        } else {
            System.out.println("ERROR la raíz está Ø");
            return "";
        }
    }

    private String listarAscendente(NodoABB<T> nodo) {
        if (nodo == null) {
            return "";
        }
        return listarAscendente(nodo.getIzq()) + nodo.getDato().toString() + "|" + listarAscendente(nodo.getDer());
    }

    public String listarDescendente() {
        if (this.raiz != null) {
            String listaDescendenteString = listarDescendentemente(this.raiz);
            return listaDescendenteString.substring(0, listaDescendenteString.length() - 1);
        } else {
            System.out.println("ERROR la raíz está Ø");
            return "";
        }
    }

    private String listarDescendentemente(NodoABB<T> nodo) {
        if (nodo == null) {
            return "";
        }
        return listarDescendentemente(nodo.getDer()) + nodo.getDato().toString() + "|" + listarDescendentemente(nodo.getIzq());
    }

    public T borrarMinimo() {
        if (this.raiz != null) {
            if (this.raiz.getIzq() == null) {
                T dato = this.raiz.getDato();
                this.raiz = this.raiz.getDer();
                return dato;
            }
            return borrarMinimo(this.raiz);
        }
        return null;
    }

    private T borrarMinimo(NodoABB<T> nodo) {
        if (nodo.getIzq().getIzq() == null) {
            T dato = (T) nodo.getIzq().getDato();
            nodo.setIzq(nodo.getIzq().getDer());
            return dato;
        }
        return (T) borrarMinimo(nodo.getIzq());
    }

    public ListaImp<T> obtenerListaAscendente() {
        ListaImp<T> lista_ascendente = new ListaImp<T>();
        obtenerListaAscendente(this.raiz, lista_ascendente);
        return lista_ascendente;
    }

    private void obtenerListaAscendente(NodoABB nodo, ListaImp<T> lista) {
        if (nodo != null) {
            obtenerListaAscendente(nodo.getIzq(), lista);
            lista.insertar((T) nodo.getDato());
            obtenerListaAscendente(nodo.getDer(), lista);
        }
    }

    public void imprimirElementosNivel(int nivel) {
        System.out.println(imprimirElementosNivel(this.raiz, nivel, 0));
    }

    private String imprimirElementosNivel(NodoABB nodo, int nivel, int nivelActual) {
        if (nodo != null) {
            if (nivel == nivelActual) {
                return nodo.getDato() + " ";
            }
            return imprimirElementosNivel(nodo.getIzq(), nivel, nivelActual + 1) + imprimirElementosNivel(nodo.getDer(), nivel, nivelActual + 1);
        }
        return "";
    }

    public int altura() {
        return altura(this.raiz);
    }

    private int altura(NodoABB nodo) {
        if (nodo == null) {
            return -1;
        } else {
            return 1 + Math.max(altura(nodo.getDer()), altura(nodo.getIzq()));
        }
    }

    public int cantidadElementosMayores(T valor) {
        return cantidadElementosMayores(this.raiz, valor);
    }

    private int cantidadElementosMayores(NodoABB nodo, T valor) {
        if (nodo != null) {
            if (valor.compareTo((T) nodo.getDato()) < 0) {
                //llamada recursiva sobre izq
                //insertar en lista
                //llamada recursiva en der
                return 1 + cantidadElementosMayores(nodo.getIzq(), valor) + cantidadElementosMayores(nodo.getDer(), valor);
            }
            return cantidadElementosMayores(nodo.getDer(), valor);
        }
        return 0;
    }

    public ListaImp<T> listaElementosMayores(T valor) {
        ListaImp<T> listaMayores = new ListaImp<>();
        listaElementosMayores(this.raiz, valor, listaMayores);
        return listaMayores;
    }

    private void listaElementosMayores(NodoABB nodo, T valor, ListaImp<T> lista) {
        if (nodo != null) {
            if (valor.compareTo((T) nodo.getDato()) < 0) {
                listaElementosMayores(nodo.getIzq(), valor, lista);
                lista.insertar((T) nodo.getDato());
                listaElementosMayores(nodo.getDer(), valor, lista);
            } else {
                listaElementosMayores(nodo.getDer(), valor, lista);
            }
        }
    }

    public int encontrarNivel(T valor) {
        return encontrarNivel(this.raiz, valor, 0);
    }

    private int encontrarNivel(NodoABB nodo, T valor, int nivelActual) {
        if (nodo != null) {
            if (valor.compareTo((T) nodo.getDato()) == 0) {
                return nivelActual;
            }
            if (valor.compareTo((T) nodo.getDato()) < 0) {
                return encontrarNivel(nodo.getIzq(), valor, nivelActual + 1);
            }
            return encontrarNivel(nodo.getDer(), valor, nivelActual + 1);
        }
        return -1;
    }

    public void imprimirNiveles() {
        int altura = altura(this.raiz);
        for (int i = 0; i <= altura; i++) {
            imprimirElementosNivel(i);
        }
    }

    public int cantNodosQueNoSonHojasMayoresA(T dato) {
        return cantNodosQueNoSonHojasMayoresA(this.raiz, dato);
    }

    private int cantNodosQueNoSonHojasMayoresA(NodoABB nodo, T dato) {
        if (nodo != null) {
            if (nodo.getIzq() != null || nodo.getDer() != null) {
                if (dato.compareTo((T) nodo.getDato()) < 0) {
                    return 1 + cantNodosQueNoSonHojasMayoresA(nodo.getIzq(), dato) + cantNodosQueNoSonHojasMayoresA(nodo.getDer(), dato);
                }
                return cantNodosQueNoSonHojasMayoresA(nodo.getDer(), dato);
            }
        }
        return 0;
    }

    //private void cargarListaDecRec(NodoABB nodo,ListaImp lista){
        //if (nodo!=null){
          //  if(nodo.getDer()==null && nodo.getIzq()==null){
            //    lista.insertar(nodo);
            //}else{
              //  cargarListaDecRec(nodo.getDer(),lista);
            //    cargarListaDecRec(nodo.getIzq(),lista);
            //}
        //}
    //}

    @Override
    public int cantNodos() {
        return cantNodos(this.raiz);
    }

    private int cantNodos(NodoABB nodo) {
        if (nodo != null) {
            return 1 + cantNodos(nodo.getIzq()) + cantNodos(nodo.getDer());
        }
        return 0;
    }

}
