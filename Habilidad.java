import java.util.List;

//Clase abstracta que representa una habilidad genérica que puede ser utilizada por cualquier combatiente
//(enemigo o jugador).
public abstract class Habilidad {
    protected String nombre;
    protected int usosRestantes;
    protected int usosMaximos;

    public Habilidad(String nombre, int usosMaximos) {
        this.nombre = nombre;
        this.usosMaximos = usosMaximos;
        this.usosRestantes = usosMaximos;
    }
    public abstract void ejecutar(Combatiente usuario, List<Combatiente> objetivos);

    public String getNombre() {
        return nombre;
    }
    public int getUsosRestantes() {
        return usosRestantes;
    }
    public int getUsosMaximos() {
        return usosMaximos;
    }

    public boolean puedeUsar() {
        boolean resultado = usosRestantes > 0;
        return resultado;
    }
    public void reducirUso() {
        if (usosRestantes > 0) {
            usosRestantes--;
        }
    }
    public boolean habilidadAgotada() {
        return usosRestantes <= 0;
    }
    public void restaurarUsos() {
        usosRestantes = usosMaximos;
    }
}