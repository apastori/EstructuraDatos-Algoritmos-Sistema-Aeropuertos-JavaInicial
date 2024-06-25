package sistema;

import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sistema.ImplementacionSistema;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListarPasajerosPorCategoriaTest {
    private Sistema sistema;
    private Retorno retorno;

    @BeforeEach
    void setUp() {
        sistema = new ImplementacionSistema();
        sistema.inicializarSistema(10,10);

        sistema.registrarPasajero(new String("4.685.375-3"), new String("Juliana"),  new String("1234"), Categoria.ESTANDAR);
        sistema.registrarPasajero(new String("5.135.139-2"), new String("Gaston"),  new String("3456"), Categoria.ESTANDAR);
        sistema.registrarPasajero(new String("5.888.365-4"), new String("Alejandra"),  new String("5634"), Categoria.PLATINO);
        sistema.registrarPasajero(new String("5.447.365-1"), new String("Gustavo"),  new String("23456"), Categoria.PLATINO);
    }

    @Test
    void noDeberiaListarPasajajerosPorTipoParamNull(){
        retorno = sistema.listarPasajerosPorCategoria(null);
        assertEquals(Retorno.Resultado.ERROR_1,retorno.getResultado());

    }

    @Test
    void deberiaListarPasajerosPorTipo(){
        retorno = sistema.listarPasajerosPorCategoria(Categoria.ESTANDAR);
        assertEquals(Retorno.Resultado.OK,retorno.getResultado());
        assertEquals("4.685.375-3;Juliana;1234;Estándar|5.135.139-2;Gaston;3456;Estándar",retorno.getValorString());

        retorno = sistema.listarPasajerosPorCategoria(Categoria.PLATINO);
        assertEquals(Retorno.Resultado.OK,retorno.getResultado());
        assertEquals("5.447.365-1;Gustavo;23456;Platino|5.888.365-4;Alejandra;5634;Platino",retorno.getValorString());
    }

    @Test
    void deberiaListarPasajerosPorTipoVacio(){
        retorno = sistema.listarPasajerosPorCategoria(Categoria.FRECUENTE);
        assertEquals(Retorno.Resultado.OK,retorno.getResultado());
        assertEquals("",retorno.getValorString());
    }
}
