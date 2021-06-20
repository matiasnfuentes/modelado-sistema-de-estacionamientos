package tp.estacionamientos;

import java.time.Clock;
import java.time.LocalDateTime;
import tp.administraciones.AdministracionDeCelulares;
import tp.apps.AppUsuario;

public class EstacionamientoPorApp extends Estacionamiento {
	
	private AdministracionDeCelulares admCel;
	private AppUsuario appUsuario;
	private boolean finalizado;
	private double precioEstacionamiento;
	
	public EstacionamientoPorApp(String patente,double precioEstacionamiento,AppUsuario appUsuario,AdministracionDeCelulares admCel,Clock reloj){
		super(patente,reloj);
		this.appUsuario = appUsuario;
		this.precioEstacionamiento = precioEstacionamiento;
		this.admCel = admCel;
	}

	@Override
	public double getPrecioEstacionamiento() {
		return this.precioEstacionamiento;
	}

	public boolean perteneceAlNumero(long nroAsociado) {
		return appUsuario.getNumeroAsociado()==nroAsociado;
	}

	@Override
	public void finalizarEstacionamiento() {
		this.setHoraFin(LocalDateTime.now(this.reloj));
		this.finalizado=true;
	}
	
	public int horasDisponibles(long numero) { 
		return (int) Math.round(admCel.consultarCredito(numero) / this.getPrecioEstacionamiento());
	}
	
	public LocalDateTime horaMaxima() {
		return this.getHoraInicio().plusHours(this.horasDisponibles(appUsuario.getNumeroAsociado()));
	}
	
	@Override
	public boolean estaVigente() {
		return !this.finalizado && LocalDateTime.now(this.reloj).isBefore(this.horaMaxima());
	}
	 
}
