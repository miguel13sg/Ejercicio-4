import java.util.*;

public class Controlador {
    private Vista vista;
    private Batalla batalla;
    private Random random;

    public Controlador() {
        this.vista = new Vista();
        this.random = new Random();
    }

    public void iniciarJuego() {
        try {            
            // 1. Crear jugadores de forma interactiva
            List<Jugador> jugadores = crearJugadores();
            
            // 2. Crear enemigos de forma automática
            List<Enemigo> enemigos = generarEnemigos(jugadores.size());
            
            // 3. Iniciar batalla con ambos
            batalla = new Batalla(jugadores, enemigos);
            vista.mostrarInicioBatalla(batalla.getJugadoresVivos(), batalla.getEnemigosVivos());
            
            // 4. Ciclo principal de batalla
            ejecutarCicloBatalla();
            
            // 5. Mostrar resultado final
            vista.mostrarResultadoFinal(batalla.getGanador());
            mostrarFrasesVictoria(batalla.getGanador());
        } finally {
            vista.cerrar();
        }
    }

    private List<Jugador> crearJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        
        int cantidadJugadores = vista.pedirCantidadJugadores();
        
        for (int i = 0; i < cantidadJugadores; i++) {
            Jugador jugador = vista.crearJugador(i + 1);
            agregarHabilidadesCompletas(jugador);
            jugadores.add(jugador);
        }
        
        return jugadores;
    }

    private void agregarHabilidadesCompletas(Jugador jugador) {
        boolean esGuerrero = jugador.isEsGuerrero();
        agregarItemBase(jugador);
        TipoJugador tipo = jugador.getTipoJugador();
        agregarHabilidadesEspecificas(jugador, tipo, esGuerrero);
    }

    private void agregarItemBase(Jugador jugador) {
        if(jugador.isEsGuerrero()) {
            jugador.addHabilidad(new HabilidadCurar("Poción Grande (Curar)", 2, 30));
            jugador.addHabilidad(new HabilidadFuerza("Poción de Fuerza (Fuerza)", 1, 15));
        } else {
            jugador.addHabilidad(new HabilidadCurar("Poción Pequeña (Curar)", 3, 20));
            jugador.addHabilidad(new HabilidadFuerza("Poción de Fuerza (Fuerza)", 2, 10));
            jugador.addHabilidad(new HabilidadDefensa("Poción Defensa (Defensa)", 2, 8));
        }
    }

    private void agregarHabilidadesEspecificas(Jugador jugador, TipoJugador tipo, boolean esGuerrero) {
        if(esGuerrero) {
            switch(tipo) {
                case CABALLERO:
                    jugador.addHabilidad(new HabilidadFuerza("Recarga Poderosa (Fuerza)", 2, 25));
                    jugador.addHabilidad(new HabilidadDefensa("Muro de Escudos (Defensa)", 1, 20));
                    break;
                case MAGO:
                    jugador.addHabilidad(new HabilidadDaño("Bola de Fuego (Daño)", 2, 30));
                    jugador.addHabilidad(new HabilidadCurar("Curación Menor (Curar)", 2, 15));
                    break;
                case ARQUERO:
                    jugador.addHabilidad(new HabilidadDaño("Tiro Preciso (Daño)", 2, 20));
                    jugador.addHabilidad(new HabilidadDefensa("Evasión (Defensa)", 1, 15));
                    break;
                case PRINCIPE:
                    jugador.addHabilidad(new HabilidadFuerza("Supercarga Real (Fuerza)", 2, 22));
                    jugador.addHabilidad(new HabilidadCurar("Bendición (Curar)", 2, 18));
                    break;
                case BOMBARDERO:
                    jugador.addHabilidad(new HabilidadDaño("Explosión (Daño)", 2, 28));
                    jugador.addHabilidad(new HabilidadDefensa("Barricada (Defensa)", 1, 25));
                    break;
            }
        } else {
            switch(tipo) {
                case MAGO:
                    jugador.addHabilidad(new HabilidadFuerza("Rayo Arcano (Fuerza)", 3, 35));
                    jugador.addHabilidad(new HabilidadCurar("Curación Mayor (Curar)", 2, 25));
                    jugador.addHabilidad(new HabilidadDefensa("Escudo Mágico (Defensa)", 2, 20));
                    break;
                case ARQUERO:
                    jugador.addHabilidad(new HabilidadDaño("Lluvia de Flechas (Daño)", 3, 30));
                    jugador.addHabilidad(new HabilidadDefensa("Evasión Mejorada (Defensa)", 2, 20));
                    jugador.addHabilidad(new HabilidadCurar("Curación Menor (Curar)", 2, 15));
                    break;
                case PRINCIPE:
                    jugador.addHabilidad(new HabilidadFuerza("Mandato Real (Fuerza)", 3, 28));
                    jugador.addHabilidad(new HabilidadDaño("Lanza Mayor (Daño)", 2, 22));
                    jugador.addHabilidad(new HabilidadDefensa("Guardia Real (Defensa)", 2, 18));
                    break;
                case BOMBARDERO:
                    jugador.addHabilidad(new HabilidadDaño("Gran Explosión (Daño)", 3, 33));
                    jugador.addHabilidad(new HabilidadFuerza("Barricada Mejorada (Fuerza)", 2, 30));
                    jugador.addHabilidad(new HabilidadCurar("Curación Menor", 2, 15));
                    break;
                case CABALLERO:
                    jugador.addHabilidad(new HabilidadFuerza("Embate (Fuerza)", 3, 27));
                    jugador.addHabilidad(new HabilidadDefensa("Muro de Escudos Mejorado (Defensa)", 2, 25));
                    jugador.addHabilidad(new HabilidadCurar("Curación Menor (Curar)", 2, 15));
                    break;
            }
        }
    }

    private List<Enemigo> generarEnemigos(int cantidadJugadores) {
        List<Enemigo> enemigos = new ArrayList<>();
        int cantidadEnemigos = calcularCantidadEnemigos(cantidadJugadores);
        
        for (int i = 0; i < cantidadEnemigos; i++) {
            Enemigo enemigo = Enemigo.crearEnemigoAleatorio(i + 1, cantidadJugadores, random);
            agregarHabilidadesEnemigo(enemigo);
            enemigos.add(enemigo);
        }
        return enemigos;
    }

    private int calcularCantidadEnemigos(int cantidadJugadores) {
        int minEnemigos = cantidadJugadores;
        int maxEnemigos = cantidadJugadores * 2;
        return random.nextInt(maxEnemigos - minEnemigos + 1) + minEnemigos;
    }

    private void agregarHabilidadesEnemigo(Enemigo enemigo) {
        TipoEnemigo tipo = enemigo.getTipoEnemigo();
        boolean esJefe = enemigo.isEsJefe();
        
        if (esJefe) {
            agregarHabilidadesJefe(enemigo, tipo);
        } else {
            agregarHabilidadesEnemigoNormal(enemigo, tipo);
        }
    }

    private void agregarHabilidadesJefe(Enemigo enemigo, TipoEnemigo tipo) {
        switch(tipo) {
            case ESQUELETO:
                enemigo.addHabilidad(new HabilidadDaño("Golpe Óseo", 3, 25));
                enemigo.addHabilidad(new HabilidadDefensa("Escudo de Huesos", 2, 15));
                break;
            case ZOMBIE:
                enemigo.addHabilidad(new HabilidadDaño("Contagio Zombie", 3, 20));
                enemigo.addHabilidad(new HabilidadCurar("Regeneración", 2, 30));
                break;
            case GOBLIN:
                enemigo.addHabilidad(new HabilidadDaño("Ataque Furtivo", 4, 18));
                enemigo.addHabilidad(new HabilidadFuerza("Grito de Guerra", 2, 12));
                break;
            case ORCO:
                enemigo.addHabilidad(new HabilidadDaño("Mazazo Brutal", 3, 35));
                enemigo.addHabilidad(new HabilidadFuerza("Furia Orca", 2, 20));
                break;
            case BRUJA:
                enemigo.addHabilidad(new HabilidadDaño("Hechizo Oscuro", 3, 28));
                enemigo.addHabilidad(new HabilidadCurar("Poción de Vida", 2, 25));
                enemigo.addHabilidad(new HabilidadDefensa("Barrera Mágica", 2, 18));
                break;
        }
    }

    private void agregarHabilidadesEnemigoNormal(Enemigo enemigo, TipoEnemigo tipo) {
        switch(tipo) {
            case ESQUELETO:
                enemigo.addHabilidad(new HabilidadDaño("Flecha Ósea", 2, 15));
                break;
            case ZOMBIE:
                enemigo.addHabilidad(new HabilidadDaño("Mordida", 2, 12));
                break;
            case GOBLIN:
                enemigo.addHabilidad(new HabilidadDaño("Ataque Rápido", 3, 10));
                break;
            case ORCO:
                enemigo.addHabilidad(new HabilidadDaño("Golpe Pesado", 2, 18));
                break;
            case BRUJA:
                enemigo.addHabilidad(new HabilidadDaño("Rayo Débil", 2, 16));
                break;
        }
    }
    private void ejecutarCicloBatalla() {
        int turno = 1;
        try {
            while (batalla.estaEnCurso() && hayCombatientesVivos()) {
            
                Combatiente combatienteActual = batalla.getCombatienteActual();
                combatienteActual.decirFrase();
                
                if (combatienteActual == null || !combatienteActual.estaVivo()) {
                    manejarCombatienteInvalido(combatienteActual);
                    batalla.siguienteTurno();
                    continue;
                }
            
                vista.mostrarEstadoBatalla(batalla.getCombatientes(), batalla.getAcciones());
            
                if (combatienteActual instanceof Jugador) {
                    turnoJugador((Jugador) combatienteActual);
                } else {
                    turnoEnemigo((Enemigo) combatienteActual);
                }
            
                batalla.siguienteTurno();
                turno++;
                
                try { 
                    Thread.sleep(1500); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; 
                }
            }
            vista.mostrarMensaje("La batalla ha terminado después de " + (turno - 1) + " turnos.");
        } catch (PartidaTerminadaException e) {
            vista.mostrarMensaje("Partida finalizada en el turno " + turno);
        }
    }

    private boolean hayCombatientesVivos() {
        List<Combatiente> vivos = batalla.getCombatientes().stream()
            .filter(Combatiente::estaVivo)
            .toList();
        return !vivos.isEmpty();
    }

    private void manejarCombatienteInvalido(Combatiente combatiente) {
        if (combatiente == null) {
            vista.mostrarMensaje("Error: No se pudo obtener combatiente actual.");
        } else {
            vista.mostrarMensaje(combatiente.getNombre() + " está muerto. Pasando turno...");
        }
    }
    private void turnoJugador(Jugador jugador) {
        boolean puedeAnadirJugador = batalla.getJugadores().size() < 4;
        int opcion = vista.mostrarMenuJugador(jugador, puedeAnadirJugador);
        switch (opcion) {
            case 1: 
                atacarJugador(jugador);
                break;
            case 2: 
                usarHabilidadJugador(jugador);
                break;
            case 3: 
                pasarTurnoJugador(jugador);
                break;
            case 4:
                if (puedeAnadirJugador) {
                    anadirNuevoJugador();
                } else {
                    rendirseJugador(jugador);
                }
                break;
            case 5: 
                if (puedeAnadirJugador) {
                    rendirseJugador(jugador);
                } else {
                    terminarPartida();  
                }
                break;
            case 6: 
                terminarPartida();
                break;
        }
    }
private void terminarPartida() {
    boolean confirmado = vista.confirmarAccion(
        "¿Estás seguro de que quieres terminar la partida?",
        "Todos los progresos se perderán y la partida finalizará."
        );
        if (!confirmado) {
            vista.mostrarAccionCancelada();
            return;
        }
        vista.mostrarMensaje("Partida terminada por el jugador.");
        batalla.agregarAccionRegistro("Partida terminada por decisión del jugador");
    
    // Forzar fin de la batalla
        batalla.terminarBatalla();
        vista.mostrarResultadoFinal("Partida terminada");    
    // Salir del ciclo de batalla
        throw new PartidaTerminadaException();
    }

    private void anadirNuevoJugador() {
        boolean confirmado = vista.confirmarAccion(
            "¿Estás seguro de que quieres añadir un nuevo jugador?",
            "Esta acción terminará tu turno actual."
        );
        
        if (!confirmado) {
            vista.mostrarAccionCancelada();
            return;
        }
        
        try {
            int numeroNuevo = batalla.getJugadores().size() + 1;
            Jugador nuevoJugador = vista.crearJugador(numeroNuevo);
            agregarHabilidadesCompletas(nuevoJugador);
            batalla.agregarJugador(nuevoJugador);
            
            vista.mostrarJugadorUnido(nuevoJugador.getNombre());
            batalla.agregarAccionRegistro("Se unió nuevo jugador: " + nuevoJugador.getNombre());
            
        } catch (Exception e) {
            vista.mostrarError("Error al añadir jugador: " + e.getMessage());
        }
    }

    private void atacarJugador(Jugador jugador) {
        List<Combatiente> objetivos = batalla.getObjetivosValidos(jugador);
        
        if (objetivos.isEmpty()) {
            vista.mostrarSinObjetivos();
            return;
        }
        
        Combatiente objetivo = vista.seleccionarObjetivo(objetivos);
        int danio = jugador.atacar(objetivo);
        
        String accion = jugador.getNombre() + " atacó a " + objetivo.getNombre();
        batalla.agregarAccionRegistro(accion);
        vista.mostrarAtaque(jugador, objetivo, danio);
        
        if (!objetivo.estaVivo()) {
            batalla.agregarAccionRegistro(objetivo.getNombre() + " fue derrotado");
            vista.mostrarDerrota(objetivo, jugador);
        }
    }

    private void usarHabilidadJugador(Jugador jugador) {
        Habilidad habilidad = vista.seleccionarHabilidad(jugador);
        if (habilidad == null || !habilidad.puedeUsar()) {
            if (habilidad != null) vista.mostrarHabilidadAgotada(habilidad.getNombre());
            return;
        }
        
        int usosDeseados = vista.preguntarCantidadUsos(habilidad.getUsosRestantes());
        List<Combatiente> objetivos = obtenerObjetivosParaHabilidad(jugador, habilidad);
        
        if (objetivos.isEmpty()) {
            vista.mostrarSinObjetivos();
            return;
        }
        
        List<Combatiente> objetivosSeleccionados = vista.seleccionarMultiplesObjetivos(objetivos, usosDeseados);
        if (objetivosSeleccionados.isEmpty()) {
            vista.mostrarSeleccionCancelada();
            return;
        }
        
        ejecutarHabilidadEnObjetivos(jugador, habilidad, objetivosSeleccionados, usosDeseados);
    }

    private List<Combatiente> obtenerObjetivosParaHabilidad(Jugador jugador, Habilidad habilidad) {
        return esHabilidadOfensiva(habilidad) 
            ? batalla.getObjetivosValidos(jugador) 
            : batalla.getAliadosVivos(jugador);
    }

    private void ejecutarHabilidadEnObjetivos(Jugador jugador, Habilidad habilidad, List<Combatiente> objetivosSeleccionados, int usosDeseados) {
        int usosRealizados = 0;
        List<Combatiente> objetivosAfectados = new ArrayList<>();
        
        for (Combatiente objetivo : objetivosSeleccionados) {
            if (usosRealizados >= usosDeseados || !habilidad.puedeUsar()) break;
            
            habilidad.ejecutar(jugador, Collections.singletonList(objetivo));
            usosRealizados++;
            objetivosAfectados.add(objetivo);
            
            vista.mostrarUsoHabilidadIndividual(jugador, habilidad, objetivo);
            
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        
        if (usosRealizados > 0) {
            registrarYMostrarResultado(jugador, habilidad, usosRealizados, objetivosAfectados);
        }
    }

    private void registrarYMostrarResultado(Jugador jugador, Habilidad habilidad, 
                                           int usosRealizados, List<Combatiente> objetivosAfectados) {
        String accion = jugador.getNombre() + " usó " + habilidad.getNombre() + 
                       " en " + usosRealizados + " objetivo(s)";
        batalla.agregarAccionRegistro(accion);
        
        vista.mostrarResumenUsoHabilidad(jugador, habilidad, usosRealizados, objetivosAfectados);
        
        if (habilidad.habilidadAgotada()) {
            vista.mostrarHabilidadAgotada(habilidad.getNombre());
        } else {
            vista.mostrarUsosRestantes(habilidad.getNombre(), habilidad.getUsosRestantes());
        }
    }

    private boolean esHabilidadOfensiva(Habilidad habilidad) {
        return habilidad instanceof HabilidadDaño;
    }

    private void pasarTurnoJugador(Jugador jugador) {
        batalla.agregarAccionRegistro(jugador.getNombre() + " pasó el turno");
    }

    private void rendirseJugador(Jugador jugador) {
        jugador.rendirse();
        batalla.agregarAccionRegistro(jugador.getNombre() + " se rindió");
    }

    private void turnoEnemigo(Enemigo enemigo) {
        List<Combatiente> objetivos;
        
        if (enemigo.isEsJefe()) {
            List<Habilidad> habilidadesUsables = enemigo.getHabilidades().stream()
                .filter(Habilidad::puedeUsar)
                .toList();
                
            if (!habilidadesUsables.isEmpty()) {
                Habilidad habilidad = habilidadesUsables.get(random.nextInt(habilidadesUsables.size()));
                
                if(esHabilidadOfensiva(habilidad)) {
                    objetivos = batalla.getObjetivosValidos(enemigo);
                } else {
                    objetivos = batalla.getAliadosVivos(enemigo);
                }
                
                if (objetivos.isEmpty()) {
                    objetivos = batalla.getObjetivosValidos(enemigo);
                    if (!objetivos.isEmpty()) {
                        ejecutarAtaqueEnemigo(enemigo, objetivos);
                    } else {
                        batalla.agregarAccionRegistro(enemigo.getNombre() + " pasó el turno");
                    }
                    return;
                }
                
                Combatiente objetivo = objetivos.get(random.nextInt(objetivos.size()));
                List<Combatiente> objetivoLista = Collections.singletonList(objetivo);
                
                habilidad.ejecutar(enemigo, objetivoLista);
                
                String accion = enemigo.getNombre() + " usó " + habilidad.getNombre();
                batalla.agregarAccionRegistro(accion);
                vista.mostrarHabilidadUsada(enemigo, habilidad, objetivo);
                
                if (enemigo.isEsJefe()) {
                    vista.mostrarMensaje("¡El jefe " + enemigo.getNombre() + " usa su poder!");
                }
                
                verificarMuertes(objetivos, enemigo);
                return;
            }
        }
        
        List<Habilidad> habilidadesUsables = enemigo.getHabilidades().stream()
            .filter(Habilidad::puedeUsar)
            .toList();
        
        boolean usarHabilidad = !habilidadesUsables.isEmpty() && random.nextDouble() < 0.3;
        
        if (usarHabilidad) {
            Habilidad habilidad = habilidadesUsables.get(random.nextInt(habilidadesUsables.size()));
            
            if(esHabilidadOfensiva(habilidad)) {
                objetivos = batalla.getObjetivosValidos(enemigo);
            } else {
                objetivos = batalla.getAliadosVivos(enemigo);
            }
            
            if (objetivos.isEmpty()) {
                objetivos = batalla.getObjetivosValidos(enemigo);
                if (!objetivos.isEmpty()) {
                    ejecutarAtaqueEnemigo(enemigo, objetivos);
                }
                return;
            }
            
            Combatiente objetivo = objetivos.get(random.nextInt(objetivos.size()));
            List<Combatiente> objetivoLista = Collections.singletonList(objetivo);
            
            habilidad.ejecutar(enemigo, objetivoLista);
            
            String accion = enemigo.getNombre() + " usó " + habilidad.getNombre();
            batalla.agregarAccionRegistro(accion);
            vista.mostrarHabilidadUsada(enemigo, habilidad, objetivo);
        } else {
            objetivos = batalla.getObjetivosValidos(enemigo);
            if (!objetivos.isEmpty()) {
                ejecutarAtaqueEnemigo(enemigo, objetivos);
            }
        }
        
        if (objetivos != null && !objetivos.isEmpty()) {
            verificarMuertes(objetivos, enemigo);
        }
    }

    private void ejecutarAtaqueEnemigo(Enemigo enemigo, List<Combatiente> objetivos) {
        Combatiente objetivo = objetivos.get(random.nextInt(objetivos.size()));
        int danio = enemigo.atacar(objetivo);
        
        String accion = enemigo.getNombre() + " atacó a " + objetivo.getNombre();
        batalla.agregarAccionRegistro(accion);
        vista.mostrarAtaque(enemigo, objetivo, danio);
    }

    private void verificarMuertes(List<Combatiente> objetivos, Combatiente atacante) {
        for (Combatiente objetivo : objetivos) {
            if (!objetivo.estaVivo()) {
                batalla.agregarAccionRegistro(objetivo.getNombre() + " fue derrotado");
                vista.mostrarDerrota(objetivo, atacante);
            }
        }
    }

    private void mostrarFrasesVictoria(String ganador) {
        if (ganador.equals("Jugadores")) {
            for (Jugador jugador : batalla.getJugadoresVivos()) {
                jugador.decirFraseVictoria();
            }
        } else if (ganador.equals("Enemigos")) {
            for (Enemigo enemigo : batalla.getEnemigosVivos()) {
                enemigo.decirFraseVictoria();
            }
        }
    }
}