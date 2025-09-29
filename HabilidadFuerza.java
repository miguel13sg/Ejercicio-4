import java.util.List;

public class HabilidadFuerza extends Habilidad{
    private int cantidadFuerza;

    public HabilidadFuerza(String nombre, int usos, int fuerza) {
        super(nombre, usos);
        this.cantidadFuerza = fuerza;
    }

    @Override
    public void ejecutar(Combatiente usuario, List<Combatiente> objetivos) {
        if(!puedeUsar())
        {
            return;
        }
        for(Combatiente objetivo : objetivos) {
            objetivo.agregarAtaque(cantidadFuerza);
        }
        reducirUso();
    }    
    public int getCantidadFuerza()
    {
        return cantidadFuerza;
    }
}