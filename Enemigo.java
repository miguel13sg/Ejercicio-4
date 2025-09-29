import java.util.Random;

public class Enemigo extends Combatiente {
    private boolean esJefe;
    private TipoEnemigo tipo;

    public Enemigo(String nombre, int vida, int ataque, int defensa, TipoEnemigo tipo, boolean esJefe) {
        super(nombre, vida, ataque, defensa);
        this.tipo = tipo;
        this.esJefe = esJefe;

        if(esJefe) {
            this.agregarAtaque(ataque);
            this.agregarVida(vida);
            this.agregarDefensa(defensa);
        }
    }
    public static Enemigo crearEnemigoAleatorio(int numero, int cantidadJugadores, Random random) {
        TipoEnemigo tipoAleatorio = obtenerTipoAleatorio(random);
        boolean esJefe = random.nextDouble() < 0.2;
        
        // Balance dinámico basado en cantidad de jugadores
        int vidaBase = calcularVidaBase(cantidadJugadores, esJefe);
        int ataqueBase = calcularAtaqueBase(cantidadJugadores, esJefe);
        int defensaBase = calcularDefensaBase(cantidadJugadores, esJefe);
        
        String nombre = generarNombreEnemigo(tipoAleatorio, esJefe, numero);
        
        return new Enemigo(nombre, vidaBase, ataqueBase, defensaBase, tipoAleatorio, esJefe);
    }
    private static int calcularVidaBase(int cantidadJugadores, boolean esJefe) {
        int base = 40 + (cantidadJugadores * 10);
        return esJefe ? base * 2 : base;
    }

    private static int calcularAtaqueBase(int cantidadJugadores, boolean esJefe) {
        int base = 8 + (cantidadJugadores * 2);
        return esJefe ? (int)(base * 1.5) : base;
    }

    private static int calcularDefensaBase(int cantidadJugadores, boolean esJefe) {
        int base = 3 + cantidadJugadores;
        return esJefe ? (int)(base * 1.5) : base;
    }
    public static String generarNombreEnemigo(TipoEnemigo tipo, boolean esJefe, int numero) {
        String base = tipo.toString().toLowerCase();
        return esJefe ? "Jefe " + base + " " + numero : base + " " + numero;
    }

    private static TipoEnemigo obtenerTipoAleatorio(Random random) {
        TipoEnemigo[] tipos = TipoEnemigo.values();
        return tipos[random.nextInt(tipos.length)];
    }
    public boolean isEsJefe() {
        return esJefe;
    }
    
    public TipoEnemigo getTipoEnemigo() {
        return tipo;
    }
}