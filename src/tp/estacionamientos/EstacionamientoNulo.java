package tp.estacionamientos;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EstacionamientoNulo extends Estacionamiento {
	
	public EstacionamientoNulo(Clock reloj) {
		super("",reloj);
	}

	@Override
	public LocalDateTime getHoraInicio() {
		return LocalDateTime.now(reloj);
	}
	
	@Override
	public LocalDateTime getHoraFin() {
		return LocalDateTime.now(reloj);
	}
	
	@Override
	public void setHoraFin(LocalDateTime horaFin) {	}

	@Override
	public boolean estaVigente() {return false;	}

}
