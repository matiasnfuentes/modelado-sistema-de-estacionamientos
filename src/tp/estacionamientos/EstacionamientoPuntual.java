package tp.estacionamientos;

import java.time.Clock;
import java.time.LocalDateTime;

public class EstacionamientoPuntual extends Estacionamiento {
	
	public EstacionamientoPuntual(String patente,int cantidadDeHoras,Clock reloj){
		super(patente,reloj);
		this.setHoraFin(LocalDateTime.now(reloj).plusHours(cantidadDeHoras)); 
	}

	@Override
	public boolean estaVigente() {
		return this.getHoraFin().isAfter(LocalDateTime.now(reloj));
	}
	
}
