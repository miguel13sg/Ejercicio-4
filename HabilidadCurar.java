import java.util.List;

public class HabilidadCurar extends Habilidad{
    private int cantidadCuracion;

    public HabilidadCurar(String nombre, int usosMaximos, int curacion) {
        super(nombre, usosMaximos);
        this.cantidadCuracion = curacion;
    }

    @Override
    public void ejecutar(Combatiente usuario, List<Combatiente> objetivos) {
        if(!puedeUsar())
        {
            return;
        }        
        for(Combatiente objetivo : objetivos) {
            objetivo.agregarVida(cantidadCuracion);
        }
        reducirUso();
    }
}