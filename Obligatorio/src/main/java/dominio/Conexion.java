package dominio;

import EstructurasDatos.ListaImp;
import EstructurasDatos.Nodo;
import EstructurasDatos.NodoLista;

public class Conexion implements IConexion {
    private String codigoAeropuertoOrigen;
    private String codigoAeropuertoDestino;
    private double kilometros;
    private double menorMinutos;
    private ListaImp<Vuelo> listaVuelos;

    public Conexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double
                    kilometros) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.kilometros = kilometros;
        this.menorMinutos = Double.MAX_VALUE;
        this.listaVuelos = new ListaImp<Vuelo>();
    }

    public ListaImp<Vuelo> getVuelos() {
        return this.listaVuelos;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getCodigoAeropuertoOrigen() {
        return this.codigoAeropuertoOrigen;
    }

    @Override
    public String getCodigoAeropuertDestino() {
        return this.codigoAeropuertoDestino;
    }

    @Override
    public double getKilometros() {
        return this.kilometros;
    }

    public double getMenorMinutos() {
        return this.menorMinutos;
    }

    public void agregarVuelo(Vuelo vueloNuevo) {
        if (vueloNuevo.getMinutos() < this.menorMinutos) {
            this.menorMinutos = vueloNuevo.getMinutos();
        }
        this.listaVuelos.insertar(vueloNuevo);
    }

    public boolean existeVuelo(Vuelo vuelo) {
        return this.listaVuelos.existe(vuelo);
    }

    public boolean existeVueloCodAerolinea(String codigoAerolinea) {
        NodoLista<Vuelo> nodoVueloActual = this.getVuelos().getInicio();
        while (nodoVueloActual != null) {
            if (nodoVueloActual.getDato().getCodigoAerolinea().equals(codigoAerolinea)) {
                return true;
            }
            nodoVueloActual = nodoVueloActual.getSig();
        }
        return false;
    }

}
