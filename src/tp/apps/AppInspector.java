package tp.apps;

import java.time.LocalDate;
import java.time.LocalTime;

import tp.Infraccion;
import tp.Inspector;
import tp.Sem;

public class AppInspector {
	
	private Sem sem;
	private Inspector inspectorAsociado;
	
	public AppInspector(Sem sem,Inspector inspectorAsociado) {
		this.sem = sem;
		this.inspectorAsociado = inspectorAsociado;
	}
	public boolean consultarEstacionamiento(String patente){
		return sem.consultarVigencia(patente);
	}
	
	public void altaDeInfracción(String patente) {
		sem.registrarInfraccion(new Infraccion(patente, LocalDate.now(), LocalTime.now(), inspectorAsociado, inspectorAsociado.getZona()));
	}
 
}
 