package dominio;

public class CostoyCamino {
    private Double costo;

    private String camino;

    public CostoyCamino(double costo, String camino) {
        this.costo = costo;
        this.camino = camino;
    }

    public Double getCosto() {
        return this.costo;
    }
    public String getCamino() {
        return this.camino;
    }

}
