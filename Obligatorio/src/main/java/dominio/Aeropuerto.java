package dominio;

public class Aeropuerto implements IAeropuerto, Comparable<Aeropuerto> {
    private String codigo;

    private String nombre;

    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Aeropuerto(String codigo) {
        this.codigo = codigo;
        this.nombre = null;
    }

    @Override
    public String getCodigo() {
        return this.codigo;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public String toString() {
        return this.getCodigo()+";"+this.getNombre();
    }

    @Override
    public int compareTo(Aeropuerto o) {
        return this.getCodigo().compareTo(o.getCodigo());
    }
}
