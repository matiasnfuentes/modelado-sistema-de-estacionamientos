package tp.estacionamientos;

import java.time.Clock;
import java.time.LocalDateTime;

public abstract class Estacionamiento {
	
	private String patente;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFin;
	protected Clock reloj;
	
	public Estacionamiento(String patente,Clock reloj) {
		this.patente = patente;
		this.horaInicio = LocalDateTime.now(reloj);
		this.reloj = reloj;
	}

	public String getPatente() {
		return this.patente;
	}
 
	public LocalDateTime getHoraInicio() {
		return this.horaInicio;
	}
	
	public LocalDateTime getHoraFin() {
		return this.horaFin;
	}
	
	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}
	
	public boolean perteneceAlNumero(long numeroAsociado) {
		return false;
	}
	
	public void finalizarEstacionamiento(){}
	
	public double getPrecioEstacionamiento(){
		return 0;
	}
	
	public abstract boolean estaVigente();
	
	
}
