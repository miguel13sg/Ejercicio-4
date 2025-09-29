import java.util.List;
import java.util.ArrayList;

public class Batalla {
    private List<Jugador> jugadores;
    private int turnoActual; // Índice del jugador cuyo turno es actualmente
    private boolean enCurso;
    private List<String> acciones;
    private List<Enemigo> enemigos;

    public Batalla(List<Jugador> jugadores, List<Enemigo> enemigos) {
        this.jugadores = new ArrayList<>(jugadores);
        this.enemigos = new ArrayList<>(enemigos);
        this.acciones = new ArrayList<>();
        this.turnoActual = 0;
        this.enCurso = true;
        
    }
    
    public List<Combatiente> getCombatientes()
    {
        List<Combatiente> todos = new ArrayList<>();
        todos.addAll(jugadores);
        todos.addAll(enemigos);
        return todos;
    }
    public Combatiente getCombatienteActual()
    {
        List<Combatiente> vivos = getCombatientesVivos();

        if(vivos.isEmpty())
        {
            return null;
        }
        int indice = turnoActual % vivos.size();
        return vivos.get(indice);
    }
    public List<Combatiente> getCombatientesVivos() {
        return getCombatientes().stream()
            .filter(Combatiente::estaVivo)
            .toList();
    }
    public void verificarFinBatalla()
    {
        boolean todosJugadoresMuertos = jugadores.stream().noneMatch(Combatiente :: estaVivo);
        boolean todosEnemigosMuertos = enemigos.stream().noneMatch(Combatiente :: estaVivo);
        enCurso = !(todosJugadoresMuertos || todosEnemigosMuertos);
    }
    public void siguienteTurno()
    {
        turnoActual++;
        verificarFinBatalla();
    }
    public boolean estaEnCurso()
    {
        return enCurso;
    }
    public List<Jugador> getJugadoresVivos()
    {
        return jugadores.stream().filter(Combatiente::estaVivo).toList();
    }
    public List<Combatiente> getAliadosVivos(Combatiente solicitante)
    {
        List<Combatiente> aliados = new ArrayList<>();
        if(solicitante instanceof Jugador)
        {
            aliados.addAll(getJugadoresVivos());
        }
        else
        {
            aliados.addAll(getEnemigosVivos());
        }
        if(solicitante.estaVivo() && !aliados.contains(solicitante)){
            aliados.add(solicitante);
        }
        return aliados;
    }
    public List<Enemigo> getEnemigosVivos()
    {
        return enemigos.stream().filter(Combatiente::estaVivo).toList();
    }
    public void agregarAccionRegistro(String accion)
    {
        acciones.add(accion);
        if(acciones.size() > 10)
        {
            acciones.remove(0);
        }
    }
    public List<String> getAcciones()
    {
        return new ArrayList<>(acciones);
    }
    public String getGanador()
    {
        if(getJugadoresVivos().isEmpty() && !getEnemigosVivos().isEmpty())
        {
            return "Enemigos";
        }
        else if(!getJugadoresVivos().isEmpty() && getEnemigosVivos().isEmpty())
        {
            return "Jugadores";
        }
        return "Batalla en curso";
    }
    public List<Combatiente> getObjetivosValidos(Combatiente atacante)
    {
        if(atacante instanceof Jugador)
        {
            return new ArrayList<>(getEnemigosVivos());
        }
        else
        {
            return new ArrayList<>(getJugadoresVivos());
        }
    }
    public List<Jugador> getJugadores()
    {
        return new ArrayList<>(jugadores);
    }
    public void agregarJugador(Jugador jugador)
    {
        this.jugadores.add(jugador);
    }
    public void terminarBatalla() {
        this.enCurso = false;
    }
}