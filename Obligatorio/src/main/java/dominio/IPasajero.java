package dominio;

import interfaz.Categoria;

public interface IPasajero {
    public String toString();

    public String getNombre();

    public String getTelefono();

    public String getCedula();

    public Categoria getCategoria();

    public int getCedulaInt();

}

