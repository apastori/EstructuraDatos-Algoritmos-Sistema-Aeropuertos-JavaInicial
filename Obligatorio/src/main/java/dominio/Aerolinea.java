package dominio;

public class Aerolinea implements Comparable<Aerolinea>, IAerolinea {
    private String codigo;

    private String nombre;

    public Aerolinea(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Aerolinea(String codigo) {
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
    public int compareTo(Aerolinea o) {
        int comparison = this.getCodigo().compareTo(o.getCodigo());
        return comparison;
    }
}
