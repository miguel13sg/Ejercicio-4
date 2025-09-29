import java.util.List;

public class HabilidadDaño extends Habilidad{
    private int cantidadDaño;

    public HabilidadDaño(String nombre, int usosMaximos, int daño) {
        super(nombre, usosMaximos);
        this.cantidadDaño = daño;
    }

    @Override
    public void ejecutar(Combatiente usuario, List<Combatiente> objetivos) {
        if(!puedeUsar())
        {
            return;
        }
        for(Combatiente objetivo : objetivos) {
            objetivo.recibirDanio(cantidadDaño);
        }
        reducirUso();
    }    
    public int getCantidadDaño()
    {
        return cantidadDaño;
    }
}