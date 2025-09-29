import java.util.*;

public abstract class Combatiente {
    private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private List<Habilidad> habilidades;
    private int vidaMaxima;
    private String[] frases = {
        "¡Por la gloria!",
        "¡A la carga!",
        "¡No me rendiré!",
        "¡Siente mi furia!",
        "¡Esto es solo el comienzo!",
        "¡Por el honor!",
        "¡La batalla me espera!",
        "¡Mi espada no fallará!",
        "¡Nada nos detendrá!",
        "¡Hoy escribiremos historia!",
        "¡El valor guía mi camino!",
        "¡Que comience el combate!",
        "¡Mi destino está en mis manos!",
        "¡La victoria será nuestra!",
        "¡Adelante, sin temor!",
        "¡Mi espíritu es inquebrantable!",
        "¡Por los que luchan conmigo!",
        "¡El miedo es para los débiles!",
        "¡Mi corazón late con fuerza!",
        "¡Este es mi momento!"
    };
    private String[] frasesMuerte = {
        "He fallado...",
        "No... puede ser...",
        "Mi fin ha llegado...",
        "Adiós, mundo cruel...",
        "Lo di todo...",
        "Me han vencido...",
        "Esta es mi última batalla...",
        "Cuiden de los míos...",
        "Mi legado perdurará...",
        "Al menos luché con honor...",
        "Me reiré en el infierno...",
        "No me arrepiento de nada...",
        "Mi vida se apaga...",
        "Vengan por mí, amigos...",
        "He visto el final...",
        "Que mi sacrificio tenga sentido...",
        "La oscuridad me llama...",
        "Fue un buen combate...",
        "Mi deber está cumplido...",
        "Hasta la próxima vida..."
    };
    private String[] frasesVictoria = {
        "¡Victoria!",
        "¡Lo logramos!",
        "¡Somos invencibles!",
        "¡Otra batalla ganada!",
        "¡El triunfo es nuestro!",
        "¡Nada puede detenernos!",
        "¡La gloria es nuestra!",
        "¡Hemos prevalecido!",
        "¡El enemigo ha caído!",
        "¡Por nuestro esfuerzo!",
        "¡El día es nuestro!",
        "¡Justicia ha sido servida!",
        "¡Nuestro valor nos llevó a la victoria!",
        "¡El poder del bien triunfa!",
        "¡Hoy somos leyenda!",
        "¡Nuestro nombre será recordado!",
        "¡El sacrificio valió la pena!",
        "¡La luz vence a la oscuridad!",
        "¡Por cada caído, esta victoria!",
        "¡El futuro nos sonríe!"
    };

    public Combatiente(String nombre, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.habilidades = new java.util.ArrayList<>();
        this.vidaMaxima = vida;
    }
    public boolean estaVivo() {
        return this.vida > 0;
    }
    public int getDefensa() {
        return defensa;
    }
    public void addHabilidad(Habilidad habilidad) {
        this.habilidades.add(habilidad);
    }
    public List<Habilidad> getHabilidades() {
        return new ArrayList<>(habilidades);
    }

    public  int atacar(Combatiente objetivo)
    {
        objetivo.recibirDanio(this.ataque);
        return this.ataque;
    }
    public void recibirDanio(int danio) {
        int danioFinal = danio - defensa;
        if(danioFinal < 0)
        {
            danioFinal = 0;
        } 
        this.vida -= danioFinal;
        if (this.vida < 0) {
            this.vida = 0; // La vida no puede ser negativa
        }
    }
    public void rendirse() {
        this.vida = 0;
    }
    public void agregarDefensa(int cantidad) {
        this.defensa += cantidad;
    }
    public void desplegarMensaje(String mensaje) {
        System.out.println(this.nombre + ": " + mensaje);
    }

    public void agregarVida(int cantidad) {
        this.vida = Math.min(vida + cantidad, vidaMaxima);
    }

    public String getNombre() {
        return nombre;
    }
    public int getVida() {
        return vida;
    }
    public int getVidaMaxima() {
        return vidaMaxima;
    }
    public int getAtaque() {
        return ataque;
    }
    public void agregarAtaque(int cantidad) {
        this.ataque += cantidad;
    }
    public String getFraseAleatoria() {
        int indice = (int) (Math.random() * frases.length);
        return frases[indice];
    }
    public String getFraseMuerteAleatoria() {
        int indice = (int) (Math.random() * frasesMuerte.length);
        return frasesMuerte[indice];
    }
    public String getFraseVictoriaAleatoria() {
        int indice = (int) (Math.random() * frasesVictoria.length);
        return frasesVictoria[indice];
    }
    public String decirFrase()
    {
        String frase = getFraseAleatoria();
        desplegarMensaje(frase);
        return frase;
    }
    public String decirFraseMuerte()
    {
        String frase = getFraseMuerteAleatoria();
        desplegarMensaje(frase);
        return frase;
    }
    public String decirFraseVictoria()
    {
        String frase = getFraseVictoriaAleatoria();
        desplegarMensaje(frase);
        return frase;
    }
}