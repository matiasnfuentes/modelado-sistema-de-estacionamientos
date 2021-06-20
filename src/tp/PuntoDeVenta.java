package tp;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

import tp.compras.CompraPuntual;
import tp.compras.RecargaDeCelular;

public class PuntoDeVenta {
	
	private Sem sem;
	private Clock reloj;
	
	public PuntoDeVenta(Sem sem,Clock reloj) {
		this.sem = sem;
		this.reloj = reloj;
	}
	public PuntoDeVenta(Sem sem) {
		this.sem = sem;
		this.reloj = Clock.systemDefaultZone();
	}
	
	public void iniciarEstacionamientoPuntual(String patente,int cantDeHoras) throws Exception {
		if(LocalTime.now(reloj).plusHours(cantDeHoras).isBefore(sem.getHoraDeFinDeFranja())) {
			sem.registrarCompra(new CompraPuntual(this.hashCode(), this, LocalDate.now(reloj),LocalTime.now(reloj),cantDeHoras), patente, cantDeHoras);
		}
		else { 
			throw new Exception("Su compra excede la franja horaria.");
		}	
	}
	
	public void cargarCredito(long nroCelular,double monto){
		sem.registrarCompra(new RecargaDeCelular(this.hashCode(), this, LocalDate.now(reloj),LocalTime.now(reloj), monto, nroCelular), nroCelular, monto);
	}
	
	
}


