package sistema;

// import Statement interfaz
import interfaz.*;

// import Statement EstructurasDatos
import EstructurasDatos.*;

// import Statement Dominio
import dominio.*;

public class ImplementacionSistema implements Sistema {

    private ABB<Aeropuerto> aeropuertosABB;

    private ABB<Aerolinea> aerolineasABB;

    private ABB<Pasajero> pasajerosABB;

    private ABB<Pasajero> pasajerosEstandarABB;

    private ABB<Pasajero> pasajerosFrecuenteABB;

    private ABB<Pasajero> pasajerosPlatinosABB;

    private Grafo aeropuertosGrafo;

    private int maxCantidadAeropuertos;

    private int maxCantidadAerolineas;

    public ImplementacionSistema() {

    }

    @Override
    public Retorno inicializarSistema(int maxCantidadAeropuertos, int maxCantidadAerolineas) {
        if (Sistema.MIN_AEROPUERTOS >= maxCantidadAeropuertos) {
            return Retorno.error1("Cantidad de Aeropuertos es menor o igual a " + Sistema.MIN_AEROPUERTOS);
        }
        if (Sistema.MIN_AEROLINEAS >= maxCantidadAerolineas) {
            return Retorno.error2("Cantidad de Aerolineas es menor o igual a " + Sistema.MIN_AEROLINEAS);
        }
        // Maxima Cantidad de Aeropuertos
        this.maxCantidadAeropuertos = maxCantidadAeropuertos;
        // Maxima Cantidad de Aerolineas
        this.maxCantidadAerolineas = maxCantidadAerolineas;
        // Estructura Aerolineas, Aeropuertos, Pasajeros
        this.aeropuertosABB = new ABB<Aeropuerto>();
        this.aerolineasABB = new ABB<Aerolinea>();
        this.pasajerosABB = new ABB<Pasajero>();
        this.pasajerosEstandarABB = new ABB<Pasajero>();
        this.pasajerosFrecuenteABB = new ABB<Pasajero>();
        this.pasajerosPlatinosABB = new ABB<Pasajero>();
        this.aeropuertosGrafo = new Grafo(this.maxCantidadAeropuertos, true);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, String telefono, Categoria categoria) {
        if (nombre == null || nombre.isEmpty()) {
            return Retorno.error1("Nombre esta vacio o null");
        }
        if (cedula == null || cedula.isEmpty()) {
            return Retorno.error1("Cedula esta vacio o null");
        }
        if (telefono == null || telefono.isEmpty()) {
            return Retorno.error1("Telefono esta vacio o null");
        }
        if (categoria == null) {
            return Retorno.error1("Categoria esta vacio o null");
        }
        // Chequear formato cedula N.NNN.NNN-N o NNN.NNN-N
        String Cedula8DigitosRegex = "^[1-9]{1}[.][0-9]{3}.[0-9]{3}[-][0-9]{1}$";
        String Cedula7DigitosRegex = "^[1-9]{1}[0-9]{2}[.][0-9]{3}[-][0-9]{1}$";
        //String regex = "^(?!0)([1-9]\\d{2}\\.\\d{3}-\\d|[1-9]\\.\\d{3}\\.\\d{3}-\\d)$";
        if (!cedula.matches(Cedula7DigitosRegex) && !cedula.matches(Cedula8DigitosRegex)) {
            return Retorno.error2("Cedula no cumple con formato N.NNN.NNN-N o NNN.NNN-N");
        }
        Pasajero pasajeroNuevo = new Pasajero(nombre, cedula, telefono, categoria);
        if (this.pasajerosABB.pertenece(pasajeroNuevo)) {
            return Retorno.error3("Ya existe un pasajero registrado con esa cédula.");
        }
        this.pasajerosABB.insertar(pasajeroNuevo);
        switch (pasajeroNuevo.getCategoria().getTexto()) {
            case "Platino":
                this.pasajerosPlatinosABB.insertar(pasajeroNuevo);
                break;
            case "Frecuente":
                this.pasajerosFrecuenteABB.insertar(pasajeroNuevo);
                break;
            case "Estándar":
                this.pasajerosEstandarABB.insertar(pasajeroNuevo);
                break;
        }
        return Retorno.ok();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return Retorno.error1("Cedula esta vacio o null");
        }
        // Chequear formato cedula N.NNN.NNN-N o NNN.NNN-N
        String Cedula8DigitosRegex = "^[1-9]{1}[.][0-9]{3}.[0-9]{3}[-][0-9]{1}$";
        String Cedula7DigitosRegex = "^[1-9]{1}[0-9]{2}[.][0-9]{3}[-][0-9]{1}$";
        if (!cedula.matches(Cedula8DigitosRegex) && !cedula.matches(Cedula7DigitosRegex)) {
            return Retorno.error2("Cedula no cumple con formato N.NNN.NNN-N o NNN.NNN-N");
        }
        if (!this.buscarPasajeroCedula(cedula)) {
            return Retorno.error3("No Existe un pasajero registrado con esa cédula.");
        }
        DominioyCantNodos pasajeroCantNodos = this.buscarPasajeroObj(cedula);
        return Retorno.ok(pasajeroCantNodos.getcantNodos(), pasajeroCantNodos.getDominio());
    }
    private boolean buscarPasajeroCedula(String cedula) {
        Pasajero unPasajero = new Pasajero(cedula);
        return this.pasajerosABB.pertenece(unPasajero);
    }

    private DominioyCantNodos buscarPasajeroObj(String cedula) {
        Pasajero unPasajero = new Pasajero(cedula);
        return this.pasajerosABB.perteneceObj(unPasajero);
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.ok(this.pasajerosABB.listarAscendente());
    }

    @Override
    public Retorno listarPasajerosPorCategoria(Categoria categoria) {
        String listaAscendenteCategoria = "";
        switch (categoria.getTexto()) {
            case "Platino":
                listaAscendenteCategoria = this.pasajerosPlatinosABB.listarAscendente();
                break;
            case "Frecuente":
                listaAscendenteCategoria = this.pasajerosFrecuenteABB.listarAscendente();
                break;
            case "Estándar":
                listaAscendenteCategoria = this.pasajerosEstandarABB.listarAscendente();
                break;
        }
        return Retorno.ok(listaAscendenteCategoria);
    }

    @Override
    public Retorno registrarAerolinea(String codigo, String nombre) {
        // Validar Cantidad Nodos ABB Aerolineas es menor a maxAerolineas
        if (this.aerolineasABB.cantNodos() == this.maxCantidadAerolineas) {
            return Retorno.error1("La cantidad de Aerolineas es igual al MaxAerolineas");
        }
        if (codigo == null || codigo.isEmpty()) {
            return Retorno.error2("Codigo esta vacio o es null");
        }
        if (nombre == null || nombre.isEmpty()) {
            return Retorno.error2("Nombre esta vacio o es null");
        }
        Aerolinea aerolineaNueva = new Aerolinea(codigo, nombre);
        if (this.aerolineasABB.pertenece(aerolineaNueva)) {
            return Retorno.error3("Ya existe una Aerolinea registrada con esa codigo");
        }
        this.aerolineasABB.insertar(aerolineaNueva);
        return Retorno.ok();
    }

    @Override
    public Retorno listarAerolineasDescendente() {
        return Retorno.ok(this.aerolineasABB.listarDescendente());
    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {
        // Validar Cantidad Nodos ABB Aeropuertos es menor a maxAeropuertos
        if (this.aeropuertosGrafo.getCantidadActualVertices() == this.maxCantidadAeropuertos) {
            return Retorno.error1("La cantidad de Aeropuertos es igual al MaxAeropuertos");
        }
        if (codigo == null || codigo.isEmpty()) {
            return Retorno.error2("Codigo esta vacio o es null");
        }
        if (nombre == null || nombre.isEmpty()) {
            return Retorno.error2("Nombre esta vacio o es null");
        }
        Aeropuerto aeropuertoNuevo = new Aeropuerto(codigo, nombre);
        if (this.aeropuertosGrafo.existeVertice(aeropuertoNuevo)) {
            return Retorno.error3("Ya existe un Aeropuerto registrada con esa codigo");
        }
        this.aeropuertosGrafo.agregarVertice(aeropuertoNuevo);
        //this.aeropuertosABB.insertar(aeropuertoNuevo);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        if (kilometros <= 0) {
            return Retorno.error1("Kilometros es igual o menor a 0");
        }
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error2("Codigo Aeropuerto Origen esta vacio o es null");
        }
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty()) {
            return Retorno.error2("Codigo Aeropuerto Destino esta vacio o es null");
        }
        // Ahora se crean los Objetos Aeropuertos para pasarlos por parametro
        Aeropuerto aeropuertoOrigen = new Aeropuerto(codigoAeropuertoOrigen);
        // Chequear que CodigoAeropuertoOrigen Corresponda a Aeropuerto Creado
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoOrigen)) {
            return Retorno.error3("Codigo Aeropuerto Origen no corresponde con ningun Aeropuerto Creado");
        }
        Aeropuerto aeropuertoDestino = new Aeropuerto(codigoAeropuertoDestino);
        // Chequear que CodigoAeropuertoDestino Corresponda a Aeropuerto Creado
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoDestino)) {
            return Retorno.error4("Codigo Aeropuerto Destino no corresponde con ningun Aeropuerto Creado");
        }
        // Chequear que no haya conexion ya creada con mismo codigoAeropuertoOrigen y codigoAeropuertoDestino
        if (this.aeropuertosGrafo.existerArista(aeropuertoOrigen, aeropuertoDestino)) {
            return Retorno.error5("Ya existe conexión entre Aeropuerto Origen y Aeropuerto Destino");
        }
        Conexion nuevaConexion = new Conexion(codigoAeropuertoOrigen, codigoAeropuertoDestino, kilometros);
        this.aeropuertosGrafo.agregarArista(aeropuertoOrigen, aeropuertoDestino, nuevaConexion);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarVuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, String codigoDeVuelo,
                                  double combustible, double minutos, double costoEnDolares,
                                  String codigoAerolinea) {
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error2("Codigo Aeropuerto Origen esta vacio o es null");
        }
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty()) {
            return Retorno.error2("Codigo Aeropuerto Destino esta vacio o es null");
        }
        if (codigoDeVuelo == null || codigoDeVuelo.isEmpty()) {
            return Retorno.error2("Codigo de Vuelo esta vacio o es null");
        }
        if (combustible <= 0) {
            return Retorno.error1("Combustible es igual o menor a 0");
        }
        if (minutos <= 0) {
            return Retorno.error1("Minutos es igual o menor a 0");
        }
        if (costoEnDolares <= 0) {
            return Retorno.error1("Costo es igual o menor a 0");
        }
        if (codigoAerolinea == null || codigoAerolinea.isEmpty()) {
            return Retorno.error2("Codigo de Aerolinea esta vacio o es null");
        }
        // Chequear si no existe Aeropuerto Origen
        Aeropuerto aeropuertoOrigen = new Aeropuerto(codigoAeropuertoOrigen);
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoOrigen)) {
            return Retorno.error3("Codigo Aeropuerto Origen no esta registrado en Sistema");
        }
        Aeropuerto aeropuertoDestino = new Aeropuerto(codigoAeropuertoDestino);
        // Chequear si no existe Aertopuerto Destino
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoDestino)) {
            return Retorno.error4("Codigo Aeropuerto Destino no esta registrado en Sistema");
        }
        // Chequear si no existe Aerolinea
        Aerolinea aerolinea = new Aerolinea(codigoAerolinea);
        if (!this.aerolineasABB.pertenece(aerolinea)) {
            return Retorno.error5("Codigo Aerolinea no esta registrado en Sistema");
        }
        // Chequear si no existe Conexion entre Origen y Destino
        if (!this.aeropuertosGrafo.existerArista(aeropuertoOrigen, aeropuertoDestino)) {
            return Retorno.error6("No existe Conexión entre Origen y Destino");
        }
        // Chequear si existe Vuelo con ese codigo y esa conexion
        Vuelo nuevoVuelo = new Vuelo(codigoAeropuertoOrigen, codigoAeropuertoDestino, codigoDeVuelo, combustible, minutos, costoEnDolares, codigoAerolinea);
        Conexion conexionOrigenDestino = this.aeropuertosGrafo.obtenerArista(aeropuertoOrigen, aeropuertoDestino);
        if (conexionOrigenDestino.existeVuelo(nuevoVuelo)) {
            return Retorno.error7("Ya existe Vuelo con ese Codigo y esa conexion");
        }
        conexionOrigenDestino.agregarVuelo(nuevoVuelo);
        //this.aeropuertosGrafo.agregarArista(aeropuertoOrigen, aeropuertoDestino, conexionOrigenDestino);
        return Retorno.ok();
    }

    @Override
    public Retorno listadoAeropuertosCantDeEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        if (cantidad < 0) {
            return Retorno.error1("Cantidad es menor a 0");
        }
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error1("Codigo Aeropuerto Origen esta vacio o es null");
        }
        if (codigoAerolinea == null || codigoAerolinea.isEmpty()) {
            return Retorno.error1("Codigo Aerolinea esta vacio o es null");
        }
        // Chequear si Aeropuerto esta en el Sistema
        // Genero Objetos Aeropuertos para validar existencia
        Aeropuerto aeropuertoOrigen = new Aeropuerto(codigoAeropuertoOrigen);
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoOrigen)) {
            return Retorno.error2("Aeropuerto Origen no se encuentra registrado en Sistema");
        }
        // Chequear si Aerolinea esta en el sistema
        // Genero Objetos Aeropuertos para validar existencia
        Aerolinea aerolinea = new Aerolinea(codigoAerolinea);
        if (!this.aerolineasABB.pertenece(aerolinea)) {
            return Retorno.error3("Aerolinea no esta registrado en Sistema");
        }
        ABB<Aeropuerto> arbolAeropuertos = this.aeropuertosGrafo.bfsConNivel(aeropuertoOrigen, cantidad, codigoAerolinea);
        return Retorno.ok(arbolAeropuertos.listarAscendente());
        //return Retorno.ok(listadoAeropuertos.substring(0, listadoAeropuertos.length() - 1));
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error1("Codigo Aeropuerto Origen esta vacio o es null");
        }
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty()) {
            return Retorno.error1("Codigo Aeropuerto Destino esta vacio o es null");
        }
        // Chequear si Aeropuerto esta en el Sistema
        // Genero Objetos Aeropuertos para validar existencia
        Aeropuerto aeropuertoOrigen = new Aeropuerto(codigoAeropuertoOrigen);
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoOrigen)) {
            return Retorno.error3("Aeropuerto Origen no se encuentra registrado en Sistema");
        }
        Aeropuerto aeropuertoDestino = new Aeropuerto(codigoAeropuertoDestino);
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoDestino)) {
            return Retorno.error4("Aeropuerto Destino no se encuentra registrado en Sistema");
        }
        CostoyCamino cantidadMinimaKilometros = this.aeropuertosGrafo.dijkstraKilometros(aeropuertoOrigen, aeropuertoDestino);
        if (cantidadMinimaKilometros.getCosto() == Double.MAX_VALUE ) {
            return Retorno.error2("No Existe camino entre Aeropuerto Origen y Aeropuerto Destino");
        }
        return Retorno.ok(cantidadMinimaKilometros.getCosto().intValue(), cantidadMinimaKilometros.getCamino());
    }

    @Override
    public Retorno viajeCostoMinimoEnMinutos(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error1("Codigo Aeropuerto Origen esta vacio o es null");
        }
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty()) {
            return Retorno.error1("Codigo Aeropuerto Destino esta vacio o es null");
        }
        // Chequear si no existe Aeropuerto Origen
        Aeropuerto aeropuertoOrigen = new Aeropuerto(codigoAeropuertoOrigen);
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoOrigen)) {
            return Retorno.error3("Codigo Aeropuerto Origen no esta registrado en Sistema");
        }
        Aeropuerto aeropuertoDestino = new Aeropuerto(codigoAeropuertoDestino);
        // Chequear si no existe Aertopuerto Destino
        if (!this.aeropuertosGrafo.existeVertice(aeropuertoDestino)) {
            return Retorno.error4("Codigo Aeropuerto Destino no esta registrado en Sistema");
        }
        CostoyCamino cantidadMinimaMinutos = this.aeropuertosGrafo.dijkstraMinutos(aeropuertoOrigen, aeropuertoDestino);
        if (cantidadMinimaMinutos.getCosto() == Double.MAX_VALUE ) {
            return Retorno.error2("No Existe camino entre Aeropuerto Origen y Aeropuerto Destino");
        }
        return Retorno.ok(cantidadMinimaMinutos.getCosto().intValue(), cantidadMinimaMinutos.getCamino());
    }


}
