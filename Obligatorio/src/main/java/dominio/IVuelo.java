package dominio;

public interface IVuelo {

    String toString();
    String getCodigoAeropuertoOrigen();

    String getCodigoAeropuertoDestino();

    String getCodigoVuelo();

    double getCombustible();

    double getMinutos();

    double getCostoDolares();

    String getCodigoAerolinea();
}
