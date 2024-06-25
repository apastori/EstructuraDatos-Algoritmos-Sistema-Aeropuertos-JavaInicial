package dominio;

import interfaz.Categoria;

public class Pasajero implements Comparable<Pasajero>, IPasajero {
    private String nombre;

    private String cedula;

    private String telefono;

    private Categoria categoria;

    public Pasajero(String nombre, String cedula, String telefono, Categoria categoria) {
        if (nombre == null || nombre.isEmpty()) {
            throw new Error("Nombre esta vacio o null");
        }
        this.nombre = nombre;
        if (cedula == null || cedula.isEmpty()) {
            throw new Error("Cedula esta vacia");
        }
        if (Integer.parseInt(cedula.charAt(0)+"") < 1) {
            throw new Error("Primer Digito Debe ser positivo");
        }
        if (true) {
            // Chequear Regular Expressions
        }
        this.cedula = cedula;
        if (telefono == null || telefono.isEmpty()) {
            throw new Error("Telefono esta vacia");
        }
        this.telefono = telefono;
        if (categoria == null) {
            throw new Error("Categoria is null");
        }
        this.categoria = categoria;
    }

    public Pasajero(String Cedulap) {
        this.nombre = null;
        this.cedula = Cedulap;
        this.telefono = null;
        this.categoria = null;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public String getTelefono() {
        return this.telefono;
    }

    @Override
    public String getCedula() {
        return this.cedula;
    }

    @Override
    public Categoria getCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
        return this.getCedula()+";"+this.getNombre()+";"+this.getTelefono()+";"+this.getCategoria().getTexto();
    }

    @Override
    public int getCedulaInt() {
        return Integer.parseInt(this.getCedula().substring(0, this.getCedula().indexOf("-") - 1).replace(".", ""));
    }

    @Override
    public int compareTo(Pasajero o) {
        return this.getCedulaInt() - o.getCedulaInt();
    }
}
