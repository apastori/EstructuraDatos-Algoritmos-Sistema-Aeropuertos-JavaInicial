package dominio;

public class Vuelo implements Comparable<Vuelo>, IVuelo {

    private String codigoAeropuertoOrigen;

    private String codigoAeropuertoDestino;

    private String codigoVuelo;

    private double combustible;

    private double minutos;

    private double costoDolares;

    private String codigoAerolinea;

    public Vuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino,
                 String codigoVuelo, double combustible, double minutos, double costoDolares,
                 String codigoAerolinea) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.codigoVuelo = codigoVuelo;
        this.combustible = combustible;
        this.minutos = minutos;
        this.costoDolares = costoDolares;
        this.codigoAerolinea = codigoAerolinea;
    }

    public Vuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino,
                 String codigoVuelo, double combustible, double minutos, double costoDolares) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.codigoVuelo = codigoVuelo;
        this.combustible = combustible;
        this.minutos = minutos;
        this.costoDolares = costoDolares;
        this.codigoAerolinea = null;
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
    public String getCodigoAeropuertoDestino() {
        return this.codigoAeropuertoDestino;
    }

    @Override
    public String getCodigoVuelo() {
        return this.codigoVuelo;
    }

    @Override
    public double getCombustible() {
        return this.combustible;
    }

    @Override
    public double getMinutos() {
        return this.minutos;
    }

    @Override
    public double getCostoDolares() {
        return this.costoDolares;
    }

    @Override
    public String getCodigoAerolinea() {
        return this.codigoAerolinea;
    }

    @Override
    public int compareTo(Vuelo o) {
        int comparison = this.getCodigoVuelo().compareTo(o.getCodigoVuelo());
        return comparison;
    }
}
