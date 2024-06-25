package dominio;

public class DominioyCantNodos {
    private int cantNodos;

    private String dominio;

    public DominioyCantNodos(int cantNodos, String dominio) {
        this.cantNodos = cantNodos;
        this.dominio = dominio;
    }

    public DominioyCantNodos() {
        this.cantNodos = 0;
        this.dominio = "";
    }

    public int getcantNodos() {
        return this.cantNodos;
    }
    public String getDominio() {
        return this.dominio;
    }

    public void setcantNodos(int cantNodos) {
        this.cantNodos = cantNodos;
    }
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }
}
