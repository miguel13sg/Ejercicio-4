import java.util.List;

public class HabilidadDefensa extends Habilidad{
    private int cantidadDefensa;

    public HabilidadDefensa(String nombre, int usosMaximos, int defensa) {
        super(nombre, usosMaximos);
        this.cantidadDefensa = defensa;
    }

    @Override
    public void ejecutar(Combatiente usuario, List<Combatiente> objetivos) {
        if(!puedeUsar())
        {
            return;
        }
        for(Combatiente objetivo : objetivos) {
            objetivo.agregarDefensa(cantidadDefensa);
        }
        reducirUso();
    }    
    public int getCantidadDefensa()
    {
        return cantidadDefensa;
    }
}