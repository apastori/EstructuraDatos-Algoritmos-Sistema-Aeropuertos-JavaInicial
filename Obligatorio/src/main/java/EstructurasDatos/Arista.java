package EstructurasDatos;

import dominio.Conexion;

public class Arista {

    private boolean existe;
    private Conexion peso;

    public Arista() {
        this.existe = false;
        this.peso = null;
    }

    public Arista(Conexion unPeso) {
        this.existe = true;
        this.peso = unPeso;
    }

    public boolean isExiste() {
        return this.existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Conexion getPeso() {
        return this.peso;
    }

    public void setPeso(Conexion peso) {
        this.peso = peso;
    }


}
