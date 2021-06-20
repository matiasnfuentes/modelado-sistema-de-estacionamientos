package tp;

import java.time.LocalDate;
import java.time.LocalTime;

public class Infraccion {
	
	private String patente;
	private LocalDate fecha;
	private LocalTime hora;
	private Inspector inspector;
	private Zona zona;
	
	public Infraccion(String patente, LocalDate fecha, LocalTime hora, Inspector inspector, Zona zona) {
		this.patente = patente;
		this.fecha = fecha;
		this.hora = hora;
		this.inspector = inspector;
		this.zona = zona;
	}

	public String getPatente() {
		return patente;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public Inspector getInspector() {
		return inspector;
	}

	public Zona getZona() {
		return zona;
	}
	
	
}

  