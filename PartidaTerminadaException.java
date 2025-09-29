public class PartidaTerminadaException extends RuntimeException {
    public PartidaTerminadaException() {
        super("Partida terminada por el usuario");
    }
    
    public PartidaTerminadaException(String mensaje) {
        super(mensaje);
    }
}