package EstructurasDatos;

import dominio.Aeropuerto;
import dominio.Conexion;
import dominio.CostoyCamino;
import dominio.Vuelo;

import java.lang.reflect.Array;

public class Grafo {

    private int cantidadMaxVertices;
    private int cantidadActualVertices;
    private boolean esDirigido;
    private Aeropuerto[] vertices;
    private Arista[][] matAdy;

    public Grafo(int unTope) {
        this.cantidadMaxVertices = unTope;
        this.cantidadActualVertices = 0;
        this.esDirigido=false;
        this.vertices = new Aeropuerto[this.cantidadMaxVertices];
        this.matAdy = new Arista[this.cantidadMaxVertices][this.cantidadMaxVertices];
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            for (int j = 0; j < this.cantidadMaxVertices; j++) {
                this.matAdy[i][j] = new Arista();
            }
        }
    }

    public Grafo(int unTope, boolean esDirigido) {
        this.cantidadMaxVertices = unTope;
        this.cantidadActualVertices = 0;
        this.esDirigido=esDirigido;
        this.vertices = new Aeropuerto[this.cantidadMaxVertices];
        this.matAdy = new Arista[this.cantidadMaxVertices][this.cantidadMaxVertices];
        if (esDirigido) {
            for (int i = 0; i < this.cantidadMaxVertices; i++) {
                for (int j = 0; j < this.cantidadMaxVertices; j++) {
                    this.matAdy[i][j] = new Arista();
                }
            }
        } else {
            for (int i = 0; i < this.cantidadMaxVertices; i++) {
                for (int j = i; j < this.cantidadMaxVertices; j++) {
                    this.matAdy[i][j] = new Arista();
                    this.matAdy[j][i] = this.matAdy[i][j];
                }
            }
        }
    }

    public boolean esLleno() {
        return this.cantidadActualVertices == this.cantidadMaxVertices;
    }

    public boolean esVacio() {
        return this.cantidadActualVertices == 0;
    }

    public int getCantidadActualVertices() {
        return this.cantidadActualVertices;
    }

    // PRE: !esLleno()
    private int obtenerPosLibre() {
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            if (this.vertices[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Aeropuerto vert) {
        for (int i = 0; i < this.cantidadActualVertices; i++) {
            if (this.vertices[i].compareTo(vert) == 0) {
                return i;
            }
        }
        return -1;
    }

    // PRE: !esLleno && !existeVertice
    public void agregarVertice(Aeropuerto vert) {
        int posLibre = obtenerPosLibre();
        if (posLibre == -1) {
            throw new Error("Grafo alcanzo limite");
        }
        this.vertices[posLibre] = vert;
        this.cantidadActualVertices++;
    }

    // PRE: existeVertice
    public void borrarVertice(Aeropuerto vert) {
        int posVert = obtenerPos(vert);
        if (posVert == -1) {
            throw new Error("Vertice no existe");
        }
        this.vertices[posVert] = null;
        for (int i = 0; i < this.cantidadActualVertices; i++) {
            this.matAdy[posVert][i] = new Arista();
            this.matAdy[i][posVert] = new Arista();
        }
        this.cantidadActualVertices--;
    }

    public boolean existeVertice(Aeropuerto vert) {
        return this.obtenerPos(vert) != -1;
    }

    // existeVertice(origen) && existeVertice(destino) && !existeArista
    public void agregarArista(Aeropuerto origen, Aeropuerto destino, Conexion peso) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest].setExiste(true);
        this.matAdy[posOrig][posDest].setPeso(peso);
        if(!this.esDirigido){
            this.matAdy[posDest][posOrig].setExiste(true);
            this.matAdy[posDest][posOrig].setPeso(peso);
        }
    }

    // existeVertice(origen) && existeVertice(destino)
    public boolean existerArista(Aeropuerto origen, Aeropuerto destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].isExiste();
    }

    public Conexion obtenerArista(Aeropuerto origen, Aeropuerto destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        return this.matAdy[posOrig][posDest].getPeso();
    }

    // existeVertice(origen) && existeVertice(destino) && existeArista
    public void borrarArista(Aeropuerto origen, Aeropuerto destino) {
        int posOrig = obtenerPos(origen);
        int posDest = obtenerPos(destino);
        this.matAdy[posOrig][posDest] = new Arista();
    }

    public ILista<Aeropuerto> verticesAdyacentes(Aeropuerto vert) {
        ILista<Aeropuerto> retorno = new ListaImp<Aeropuerto>();
        int posVert = obtenerPos(vert);
        for (int i = 0; i < this.cantidadActualVertices; i++) {
            if (this.matAdy[posVert][i].isExiste()) {
                retorno.insertar(this.vertices[i]);
            }
        }
        return retorno;
    }

    // Pre: existeVertice(vert)
    public ILista<Aeropuerto> verticesIncidentes(Aeropuerto vert) {
        ILista<Aeropuerto> retorno = new ListaImp<Aeropuerto>();
        int posVert = obtenerPos(vert);
        for (int i = 0; i < this.cantidadActualVertices; i++) {
            if (this.matAdy[i][posVert].isExiste()) {
                retorno.insertar(this.vertices[i]);
            }
        }
        return retorno;
    }

    public int getCantidadMaxVertices() {
        return this.cantidadMaxVertices;
    }

    public Object[] getVertices() {
        return this.vertices;
    }

    public Arista[][] getMatAdy() {
        return this.matAdy;
    }

    public void dfs(Aeropuerto verticeInicial) {
        boolean[] visitados = new boolean[this.cantidadActualVertices];
        System.out.print("* -> " );
        dfs(verticeInicial,visitados);
        System.out.println(" *");
    }
    public void dfsOpt(Aeropuerto verticeInicial) {
        boolean[] visitados = new boolean[this.cantidadActualVertices];
        System.out.print("DFS Opt: * -> " );
        dfs(verticeInicial,visitados);
        System.out.println(" *");
    }

    public void bfs(Aeropuerto verticeInicial){
        boolean[] visitados=new boolean[this.cantidadActualVertices];
        Cola<Integer> cola=new ColaDinamica<>();
        int posVert = this.obtenerPos(verticeInicial);
        visitados[posVert] = true; //marcamos el vertice como visitado
        cola.encolar(posVert);
        System.out.print("BFS: * -> ");
        while (!cola.esVacia()){
            posVert=cola.desencolar();
            System.out.print(this.vertices[posVert]+" -> ");
            for (int i = 0; i < this.cantidadActualVertices; i++) {
                if(this.matAdy[posVert][i].isExiste() && !visitados[i]){
                   visitados[i]=true;
                   cola.encolar(i);
                }
            }
        }
        System.out.println(" *");
    }

    private void dfs(Aeropuerto ver, boolean[] visitados) {
        int posVertice = this.obtenerPos(ver);
        System.out.print(ver + " -> "); //hacer algo con V
        visitados[posVertice] = true;     //Marcarlo como visitado
        ILista<Aeropuerto> adyacentes = this.verticesAdyacentes(ver); // tomamos los adyacentes
        for (Aeropuerto vertAdy : adyacentes) {
            int posAdy = this.obtenerPos(vertAdy);
            if (!visitados[posAdy]) {
                dfs(vertAdy, visitados); // sino esta visitado repetimos a partir de este vertice
            }
        }
    }

    private void dfsOpt(Aeropuerto ver, boolean[] visitados) {
        int posVertice = this.obtenerPos(ver);
        System.out.print(ver + " -> "); //hacer algo con V
        visitados[posVertice] = true;
        for (int i = 0; i < this.cantidadActualVertices; i++) {
            if(this.matAdy[posVertice][i].isExiste() && !visitados[i]){
                dfs(this.vertices[i],visitados);
            }
        }
    }

    public void bfsConNivel(Aeropuerto verticeInicial){
        boolean[] visitados=new boolean[this.cantidadActualVertices];
        Cola<NodoNivel> cola=new ColaDinamica<>();
        int posVert = this.obtenerPos(verticeInicial);
        visitados[posVert] = true; //marcamos el vertice como visitado
        NodoNivel nodoInicial=new NodoNivel(posVert,verticeInicial,0);
        cola.encolar(nodoInicial);
        System.out.print("BFS: * -> ");
        while (!cola.esVacia()){
            NodoNivel nodoNivel=cola.desencolar();
            System.out.print("("+this.vertices[nodoNivel.getPosVertice()]+","+nodoNivel.getNivel()+" : " +nodoNivel.getVerticeAnterior()+") -> ");
            for (int i = 0; i < this.cantidadActualVertices; i++) {
                if(this.matAdy[nodoNivel.getPosVertice()][i].isExiste() && !visitados[i]){
                    NodoNivel nodoSig=new NodoNivel(i,this.vertices[nodoNivel.getPosVertice()],nodoNivel.getNivel()+1);
                    visitados[i]=true;
                    cola.encolar(nodoSig);
                }
            }
        }
        System.out.println(" *");
    }

    //PRE: Se considera VerticeInicial en el return del siguiente metodo en caso que tenga Vuelos con el Codigo Aerolinea
    public ABB<Aeropuerto> bfsConNivel(Aeropuerto verticeInicial, int nivel, String codigoAerolinea){
        boolean[] visitados=new boolean[this.cantidadActualVertices];
        Cola<NodoNivel> cola=new ColaDinamica<NodoNivel>();
        int posVert = this.obtenerPos(verticeInicial);
        visitados[posVert] = true; //marcamos el vertice como visitado
        NodoNivel nodoInicial = new NodoNivel(posVert,null,0);
        cola.encolar(nodoInicial);
        //String listadoAeropuertos = this.vertices[posVert].toString();
        //listadoAeropuertos += "|";
        ABB<Aeropuerto> arbolAeropuertos = new ABB<Aeropuerto>();
        while (!cola.esVacia()) {
            NodoNivel nodoNivel=cola.desencolar();
            if (nodoNivel.getNivel() >= nivel) {
                break;
            }
            for (int i = 0; i < this.cantidadActualVertices; i++) {
                if(this.matAdy[nodoNivel.getPosVertice()][i].isExiste() && !visitados[i]) {
                    Conexion conexion = this.matAdy[nodoNivel.getPosVertice()][i].getPeso();
                    if (conexion.existeVueloCodAerolinea(codigoAerolinea)) {
                        //Aeropuerto aeropuertoInsertar = this.vertices[nodoNivel.getPosVertice()];
                        //Aeropuerto aeropuertoInsertar = this.vertices[i];
                        //aeropuertos.insertar(aeropuertoInsertar);
                        arbolAeropuertos.insertar(this.vertices[i]);
                        visitados[i]=true;
                        NodoNivel nodoSig = new NodoNivel(i, this.vertices[nodoNivel.getPosVertice()], nodoNivel.getNivel() + 1);
                        cola.encolar(nodoSig);
                    }
                }
            }
        }
        arbolAeropuertos.insertar(this.vertices[posVert]);
        return arbolAeropuertos;
    }

    public int dijkstra(Aeropuerto verticeInicial, Aeropuerto verticeFinal) {
        int posInicial = this.obtenerPos(verticeInicial);
        int posFinal = this.obtenerPos(verticeFinal);

        boolean[] visitados=new boolean[this.cantidadMaxVertices];
        Aeropuerto[] anteriores=new Aeropuerto[this.cantidadMaxVertices];
        int[] costos=new int[this.cantidadMaxVertices];

        //inicializo mis arrays
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            costos[i]=Integer.MAX_VALUE;
            anteriores[i]= null;
        }
        //marcar el costo del vertice inicial
        costos[posInicial]=0;

        //recoreer todos los vertices no visitados y tomar el menor
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            //obtener vertice no visitado de menor costo
            int posVerticeMenor=this.obtenerPosVerticeMenorNoVisitado(costos,visitados);
            if(posVerticeMenor>-1){
                visitados[posVerticeMenor]=true;
                //Evaluo adyacentes y actualizo costos
                for (int j = 0; j < this.cantidadMaxVertices; j++) {
                   if(this.matAdy[posVerticeMenor][j].isExiste() && !visitados[j]){
                        double costoNuevo=0; //costos[posVerticeMenor]+this.matAdy[posVerticeMenor][j].getPeso();
                        if (costoNuevo<costos[j]){
                            //costos[j]=costoNuevo;
                            anteriores[j]=vertices[posVerticeMenor];
                        }
                   }
                }
            }
        }
        return costos[posFinal];
    }

    public CostoyCamino dijkstraMinutos(Aeropuerto verticeInicial, Aeropuerto verticeFinal) {
        int posInicial = this.obtenerPos(verticeInicial);
        int posFinal = this.obtenerPos(verticeFinal);

        boolean[] visitados=new boolean[this.cantidadMaxVertices];
        Aeropuerto[] anteriores=new Aeropuerto[this.cantidadMaxVertices];
        double[] costos=new double[this.cantidadMaxVertices];

        //inicializo mis arrays
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            costos[i]=Double.MAX_VALUE;
            anteriores[i]= null;
        }

        //Marcar el costo del vertice inicial
        costos[posInicial]=0;
        //recorrer todos los vertices no visitados y tomar el menor
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            //obtener vertice no visitado de menor costo
            int posVerticeMenor=this.obtenerPosVerticeMenorNoVisitado(costos,visitados);
            if(posVerticeMenor>-1){
                visitados[posVerticeMenor]=true;
                //Evaluo adyacentes y actualizo costos
                for (int j = 0; j < this.cantidadMaxVertices; j++) {
                    if(this.matAdy[posVerticeMenor][j].isExiste() && !visitados[j]) {
                        Conexion conexionActual = this.matAdy[posVerticeMenor][j].getPeso();
                        double cantidadMinutos = Double.sum(costos[posVerticeMenor], conexionActual.getMenorMinutos());
                        if(cantidadMinutos<costos[j]){
                            costos[j]=cantidadMinutos;
                            anteriores[j]=vertices[posVerticeMenor];
                        }
                    }
                }
            }
        }
        String camino = "";
        Aeropuerto aeropuertoActual = vertices[posFinal];
        while (aeropuertoActual != null) {
            if (!camino.isEmpty()) {
                camino = "|" + camino;
            }
            camino = aeropuertoActual.toString() + camino;
            aeropuertoActual = anteriores[this.obtenerPos(aeropuertoActual)];
        }
        return new CostoyCamino(costos[posFinal], camino);
    }

    public CostoyCamino dijkstraKilometros(Aeropuerto verticeInicial, Aeropuerto verticeFinal) {
        int posInicial = this.obtenerPos(verticeInicial);
        int posFinal = this.obtenerPos(verticeFinal);

        boolean[] visitados=new boolean[this.cantidadMaxVertices];
        Aeropuerto[] anteriores=new Aeropuerto[this.cantidadMaxVertices];
        double[] costos=new double[this.cantidadMaxVertices];

        //inicializo mis arrays
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            costos[i]=Double.MAX_VALUE;
            anteriores[i]= null;
        }

        //marcar el costo del vertice inicial
        costos[posInicial]=0;

        //recorrer todos los vertices no visitados y tomar el menor
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            //obtener vertice no visitado de menor costo
            int posVerticeMenor=this.obtenerPosVerticeMenorNoVisitado(costos,visitados);
            if(posVerticeMenor>-1){
                visitados[posVerticeMenor]=true;
                //Evaluo adyacentes y actualizo costos
                for (int j = 0; j < this.cantidadMaxVertices; j++) {
                    if(this.matAdy[posVerticeMenor][j].isExiste() && !visitados[j]) {
                        Conexion conexionActual = this.matAdy[posVerticeMenor][j].getPeso();
                        double cantidadKilometros = Double.sum(costos[posVerticeMenor], conexionActual.getKilometros());
                        if(cantidadKilometros<costos[j]){
                            costos[j]=cantidadKilometros;
                            anteriores[j]=vertices[posVerticeMenor];
                        }
                    }
                }
            }
        }
        String camino = "";
        Aeropuerto aeropuertoActual = vertices[posFinal];
        while (aeropuertoActual != null) {
            if (!camino.isEmpty()) {
                camino = "|" + camino;
            }
            camino = aeropuertoActual.toString() + camino;
            aeropuertoActual = anteriores[this.obtenerPos(aeropuertoActual)];
        }
        return new CostoyCamino(costos[posFinal], camino);
    }

    private int obtenerPosVerticeMenorNoVisitado(int[] costos,boolean[] visitados) {
        int pos=-1;
        int min=Integer.MAX_VALUE;
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            if(!visitados[i] && costos[i]<min){
                min=costos[i];
                pos=i;
            }
        }
        return pos;
    }

    private int obtenerPosVerticeMenorNoVisitado(double[] costos,boolean[] visitados) {
        int pos=-1;
        double min=Double.MAX_VALUE;
        for (int i = 0; i < this.cantidadMaxVertices; i++) {
            if(!visitados[i] && costos[i]<min){
                min=costos[i];
                pos=i;
            }
        }
        return pos;
    }

}
