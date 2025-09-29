import java.util.*;

public class Vista {
    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }
    
    public int pedirCantidadJugadores() {
        System.out.print("¿Cuántos jugadores participarán? (1-4): ");
        return leerEntero(1, 4);
    }
    
    public Jugador crearJugador(int numero) {
        System.out.println("\n--- Creando Jugador " + numero + " ---");
        
        System.out.print("Nombre del jugador: ");
        String nombre = scanner.nextLine();
        
        if (nombre.trim().isEmpty()) {
            nombre = "Jugador " + numero;
        }
        
        TipoJugador tipo = seleccionarTipoJugador();
        boolean esGuerrero = seleccionarRol();
        
        int vida = calcularVidaBase(tipo, esGuerrero);
        int ataque = calcularAtaqueBase(tipo, esGuerrero);
        int defensa = calcularDefensaBase(tipo, esGuerrero);
        
        return new Jugador(nombre, vida, ataque, defensa, esGuerrero, tipo);
    }
    
    private TipoJugador seleccionarTipoJugador() {
        System.out.println("Selecciona el tipo de personaje:");
        System.out.println("1. Caballero (Fuerza, Defensa)");
        System.out.println("2. Mago (Magia, Habilidades)");
        System.out.println("3. Arquero (Precisión, Velocidad)");
        System.out.println("4. Príncipe (Balanceado)");
        System.out.println("5. Bombardero (Daño, Explosiones)");
        System.out.print("Opción (1-5): ");
        
        int opcion = leerEntero(1, 5);
        return TipoJugador.values()[opcion - 1];
    }
    
    private boolean seleccionarRol() {
        System.out.println("Selecciona el rol:");
        System.out.println("1. Guerrero (Más vida/ataque, menos habilidades)");
        System.out.println("2. Explorador (Balanceado, más habilidades)");
        System.out.print("Opción (1-2): ");
        
        int opcion = leerEntero(1, 2);
        return opcion == 1;
    }
    
    private int calcularVidaBase(TipoJugador tipo, boolean esGuerrero) {
        int base = switch(tipo) {
            case CABALLERO -> 80;
            case MAGO -> 60;
            case ARQUERO -> 65;
            case PRINCIPE -> 70;
            case BOMBARDERO -> 75;
        };
        return esGuerrero ? (int)(base * 1.3) : base;
    }
    
    private int calcularAtaqueBase(TipoJugador tipo, boolean esGuerrero) {
        int base = switch(tipo) {
            case CABALLERO -> 14;
            case MAGO -> 12;
            case ARQUERO -> 13;
            case PRINCIPE -> 12;
            case BOMBARDERO -> 16;
        };
        return esGuerrero ? (int)(base * 1.2) : base;
    }
    
    private int calcularDefensaBase(TipoJugador tipo, boolean esGuerrero) {
        int base = switch(tipo) {
            case CABALLERO -> 10;
            case MAGO -> 4;
            case ARQUERO -> 6;
            case PRINCIPE -> 8;
            case BOMBARDERO -> 9;
        };
        return esGuerrero ? (int)(base * 1.25) : base;
    }

    public void mostrarEstadoBatalla(List<Combatiente> combatientes, List<String> acciones) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println(" ESTADO DE LA BATALLA");
        System.out.println("═".repeat(60));
        
        System.out.println("\n JUGADORES:");
        combatientes.stream()
            .filter(c -> c instanceof Jugador)
            .forEach(c -> {
                String estado = c.estaVivo() ? "VIVO" : "MUERTO";
                System.out.printf("  %s %s: %d/%d HP | Ataque: %d | Defensa: %d%n",
                    estado, c.getNombre(), c.getVida(), c.getVidaMaxima(), c.getAtaque(), c.getDefensa());
            });
        
        System.out.println("\n ENEMIGOS:");
        combatientes.stream()
            .filter(c -> c instanceof Enemigo)
            .forEach(c -> {
                String estado = c.estaVivo() ? "VIVO" : "MUERTO";
                Enemigo enemigo = (Enemigo) c;
                String tipoJefe = enemigo.isEsJefe() ? " [JEFE]" : "";
                System.out.printf("  %s %s%s: %d/%d HP | Ataque: %d | Defensa: %d%n",
                    estado, c.getNombre(), tipoJefe, c.getVida(), c.getVidaMaxima(), c.getAtaque(), c.getDefensa());
            });
        
        System.out.println("\n ÚLTIMAS ACCIONES:");
        int inicio = Math.max(0, acciones.size() - 3);
        for (int i = inicio; i < acciones.size(); i++) {
            System.out.println("  • " + acciones.get(i));
        }
    }
    public int mostrarMenuJugador(Jugador jugador, boolean puedeAnadirJugador) {
        System.out.println("\n" + "─".repeat(40));
        System.out.println(" TURNO DE: " + jugador.getNombre().toUpperCase());
        System.out.println("─".repeat(40));
        System.out.println("1. Atacar");
        System.out.println("2. Usar habilidad/ítem");
        System.out.println("3. Pasar turno");    
        if (puedeAnadirJugador) {
            System.out.println("4. Añadir nuevo jugador");
            System.out.println("5. Rendirse");
            System.out.println("6. 🔥 Terminar partida");  // 🔥 NUEVA OPCIÓN
            System.out.print("Selecciona una opción (1-6): ");
            return leerEntero(1, 6);
        } else {
            System.out.println("4. Rendirse");
            System.out.println("5. 🔥 Terminar partida");  // 🔥 NUEVA OPCIÓN
            System.out.print("Selecciona una opción (1-5): ");
            return leerEntero(1, 5);
        }
    }

    public boolean confirmarAccion(String mensaje, String advertencia) {
        System.out.println("\n!!  " + mensaje);
        System.out.println("!!  " + advertencia);
        System.out.print("Confirmar (s/n): ");
        
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }

    public void mostrarAccionCancelada() {
        System.out.println("X Acción cancelada.");
    }

    public void mostrarJugadorUnido(String nombreJugador) {
        System.out.println("INVOCANDO ¡" + nombreJugador + " se ha unido a la batalla!");
    }

    public void mostrarError(String mensajeError) {
        System.out.println("ERROR " + mensajeError);
    }

    public int preguntarCantidadUsos(int usosDisponibles) {
        System.out.println("\nUsos disponibles: " + usosDisponibles);
        if (usosDisponibles == 1) {
            System.out.println("Solo puedes usar esta habilidad en 1 objetivo");
            return 1;
        }
        
        System.out.print("¿En cuántos objetivos quieres usar esta habilidad? (1-" + usosDisponibles + "): ");
        return leerEntero(1, usosDisponibles);
    }

    public List<Combatiente> seleccionarMultiplesObjetivos(List<Combatiente> objetivos, int maxObjetivos) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        SELECCIÓN DE OBJETIVOS        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Máximo de objetivos: " + maxObjetivos);
        System.out.println();
        
        for (int i = 0; i < objetivos.size(); i++) {
            Combatiente objetivo = objetivos.get(i);
            String estadoVida = objetivo.getVida() + "/" + objetivo.getVidaMaxima() + " HP";
            System.out.printf("%d. %-15s %s%n", i + 1, objetivo.getNombre(), estadoVida);
        }
        System.out.printf("%d. CONFIRMAR SELECCIÓN (%d objetivo%s)%n", 
                         objetivos.size() + 1, maxObjetivos, maxObjetivos != 1 ? "s" : "");
        
        List<Combatiente> seleccionados = new ArrayList<>();
        Set<Integer> indicesSeleccionados = new HashSet<>();
        
        while (seleccionados.size() < maxObjetivos) {
            System.out.println("\n► Objetivos seleccionados: " + seleccionados.size() + "/" + maxObjetivos);
            if (!seleccionados.isEmpty()) {
                System.out.print("  ");
                for (Combatiente sel : seleccionados) {
                    System.out.print(sel.getNombre() + " ");
                }
                System.out.println();
            }
            
            System.out.print("Selecciona un objetivo (1-" + objetivos.size() + 
                            ") o " + (objetivos.size() + 1) + " para confirmar: ");
            
            int opcion = leerEntero(1, objetivos.size() + 1);
            
            if (opcion == objetivos.size() + 1) {
                if (seleccionados.isEmpty()) {
                    System.out.println("X Debes seleccionar al menos un objetivo");
                    continue;
                }
                System.out.println("Selección confirmada con " + seleccionados.size() + " objetivo(s)");
                break;
            }
            
            int indiceObjetivo = opcion - 1;
            Combatiente seleccionado = objetivos.get(indiceObjetivo);
            
            if (indicesSeleccionados.contains(indiceObjetivo)) {
                System.out.print("? " + seleccionado.getNombre() + " ya está seleccionado. " +
                               "¿Quieres removerlo? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (respuesta.equals("s") || respuesta.equals("si")) {
                    seleccionados.remove(seleccionado);
                    indicesSeleccionados.remove(indiceObjetivo);
                    System.out.println("X " + seleccionado.getNombre() + " removido");
                }
            } else {
                seleccionados.add(seleccionado);
                indicesSeleccionados.add(indiceObjetivo);
                System.out.println("✓ " + seleccionado.getNombre() + " agregado");
                
                if (seleccionados.size() >= maxObjetivos) {
                    System.out.println("Máximo de objetivos alcanzado (" + maxObjetivos + ")");
                    break;
                }
            }
        }
        
        return seleccionados;
    }

    public void mostrarSeleccionCancelada() {
        System.out.println("Selección cancelada.");
    }

    public void mostrarUsoHabilidadIndividual(Combatiente usuario, Habilidad habilidad, Combatiente objetivo) {
        System.out.println("✓ " + usuario.getNombre() + " usó " + habilidad.getNombre() + " en " + objetivo.getNombre());
    }

    public void mostrarResumenUsoHabilidad(Combatiente usuario, Habilidad habilidad, int usosRealizados, List<Combatiente> objetivos) {
        System.out.println("\nRESUMEN: " + usuario.getNombre() + " usó " + habilidad.getNombre() + 
                          " en " + usosRealizados + " objetivo(s)");
    }

    public void mostrarUsosRestantes(String nombreHabilidad, int usosRestantes) {
        System.out.println("Usos restantes de " + nombreHabilidad + ": " + usosRestantes);
    }

    public void mostrarInicioBatalla(List<Jugador> jugadores, List<Enemigo> enemigos) {
        System.out.println("=".repeat(40));
        System.out.println("           COMIENZA LA BATALLA!");
        System.out.println("=".repeat(40));
        System.out.println("Jugadores: " + jugadores.size());
        System.out.println("Enemigos: " + enemigos.size());
        System.out.println("=".repeat(40));
    }
    
    public void mostrarResultadoFinal(String ganador) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           BATALLA TERMINADA!");
        System.out.println("=".repeat(40));
        
        if (ganador.equals("Jugadores")) {
            System.out.println(" ¡VICTORIA! Los jugadores han ganado.");
        } else if (ganador.equals("Enemigos")) {
            System.out.println(" DERROTA! Los enemigos han ganado.");
        } else {
            System.out.println(" Resultado indeterminado.");
        }
        System.out.println("=".repeat(40));
    }
    
    public void mostrarMensaje(String mensaje) {
        System.out.println(" >> " + mensaje);
    }
    
    public void mostrarSinObjetivos() {
        System.out.println(" No hay objetivos disponibles!");
    }
    
    public Combatiente seleccionarObjetivo(List<Combatiente> objetivos) {
        System.out.println("\n SELECCIONA UN OBJETIVO:");
        
        for (int i = 0; i < objetivos.size(); i++) {
            Combatiente objetivo = objetivos.get(i);
            System.out.printf("%d. %s (%d HP)%n", i + 1, objetivo.getNombre(), objetivo.getVida());
        }
        
        System.out.print("Selecciona un objetivo (1-" + objetivos.size() + "): ");
        int opcion = leerEntero(1, objetivos.size());
        return objetivos.get(opcion - 1);
    }
    
    public Habilidad seleccionarHabilidad(Jugador jugador) {
        List<Habilidad> habilidades = jugador.getHabilidades();
        List<Habilidad> habilidadesUsables = new ArrayList<>();
        for (Habilidad habilidad : habilidades) {
            if (habilidad.puedeUsar()) {
                habilidadesUsables.add(habilidad);
            }
        }
            
        if (habilidadesUsables.isEmpty()) {
            System.out.println(" No tienes habilidades/ítems disponibles.");
            return null;
        }
        
        System.out.println("\n HABILIDADES/ÍTEMS DISPONIBLES:");
        for (int i = 0; i < habilidadesUsables.size(); i++) {
            Habilidad habilidad = habilidadesUsables.get(i);
            String usos = habilidad.habilidadAgotada() ? "AGOTADA" : habilidad.getUsosRestantes() + " usos";
            System.out.printf("%d. %s - %s%n", i + 1, habilidad.getNombre(), usos);
        }
        
        System.out.print("Selecciona una habilidad (1-" + habilidadesUsables.size() + "): ");
        int opcion = leerEntero(1, habilidadesUsables.size());
        return habilidadesUsables.get(opcion - 1);
    }
    
    public void mostrarAtaque(Combatiente atacante, Combatiente objetivo, int danio) {
        System.out.println(atacante.getNombre() + " atacó a " + objetivo.getNombre() + " (" + danio + " daño)");
    }
    
    public void mostrarHabilidadUsada(Combatiente usuario, Habilidad habilidad, Combatiente objetivo) {
        System.out.println(usuario.getNombre() + " usó " + habilidad.getNombre() + " en " + objetivo.getNombre());
    }
    
    public void mostrarDerrota(Combatiente derrotado, Combatiente atacante) {
        derrotado.decirFraseMuerte();
        System.out.println(derrotado.getNombre() + " ha sido derrotado por " + atacante.getNombre() + "!");
    }
    
    public void mostrarHabilidadAgotada(String nombreHabilidad) {
        System.out.println("¡" + nombreHabilidad + " se ha agotado!");
    }

    public void mostrarHabilidadAgotadaDuranteUso(String nombreHabilidad) {
        System.out.println("¡" + nombreHabilidad + " se agotó durante el uso!");
    }

    private int leerEntero(int min, int max) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= min && opcion <= max) {
                    return opcion;
                }
                System.out.printf(" Por favor ingresa un número entre %d y %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf(" Entrada inválida. Ingresa un número entre %d y %d: ", min, max);
            }
        }
    }

    public void cerrar() {
        scanner.close();
    }
}