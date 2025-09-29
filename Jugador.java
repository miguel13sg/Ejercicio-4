public class Jugador extends Combatiente {
    private boolean esGuerrero;
    private TipoJugador tipo;

    public Jugador(String nombre, int vida, int ataque, int defensa, boolean esGuerrero, TipoJugador tipo) {
        super(nombre, vida, ataque, defensa);
        this.esGuerrero = esGuerrero;
        this.tipo = tipo;

        if(esGuerrero)
        {
            this.agregarAtaque(ataque / 2);
            this.agregarVida(vida / 2);
            this.agregarDefensa(defensa / 2);
        }
    }
    public boolean isEsGuerrero()
    {
        return esGuerrero;
    }
    public TipoJugador getTipoJugador() {
        return tipo;
    }
}