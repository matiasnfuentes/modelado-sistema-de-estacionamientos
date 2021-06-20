package tp.administraciones;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import tp.apps.AppUsuario;
import tp.estacionamientos.Estacionamiento;
import tp.estacionamientos.EstacionamientoNulo;
import tp.estacionamientos.EstacionamientoPorApp;
import tp.estacionamientos.EstacionamientoPuntual;
import tp.monitoreoDeEstacionamientos.AlarmaEstacionamiento;

public class AdministracionDeEstacionamientos {
		
	private ArrayList<Estacionamiento> estacionamientos;
	private AlarmaEstacionamiento alarma;
	private LocalTime finDeFranja;
	private Clock reloj;
	

	public AdministracionDeEstacionamientos(Clock reloj, AlarmaEstacionamiento alarma, ArrayList<Estacionamiento> estacionamientos) {
		this.estacionamientos = estacionamientos;
		this.alarma = alarma;
		this.finDeFranja = LocalTime.of(20, 00);
		this.reloj = reloj;
	}
	
	public void setFinDeFranja(LocalTime nuevoFin) {
		this.finDeFranja = nuevoFin;
	}
	
	public LocalTime getFinDeFranja() {
		return this.finDeFranja;
	}

	public void agregarEstacionamiento(String patente, int cantDeHoras) {
		Estacionamiento nuevoEstacionamiento = new EstacionamientoPuntual(patente,cantDeHoras,reloj);
		estacionamientos.add(nuevoEstacionamiento);
		alarma.notificar(nuevoEstacionamiento);
	}

	public void agregarEstacionamiento(String patente, double precioEstacionamiento, AppUsuario appSolicitante,AdministracionDeCelulares admCel) {
		EstacionamientoPorApp estacionamiento = new EstacionamientoPorApp(patente,precioEstacionamiento,appSolicitante,admCel,reloj);
		estacionamientos.add(estacionamiento);
		alarma.notificar(estacionamiento,appSolicitante,admCel);
		this.informarInicio(appSolicitante, estacionamiento);	
	}
	
	public void informarInicio(AppUsuario appSolicitante, EstacionamientoPorApp estacionamiento){
		DateTimeFormatter formato = DateTimeFormatter.ISO_LOCAL_TIME;
		String inicio = estacionamiento.getHoraInicio().format(formato);
		String fin = estacionamiento.horaMaxima().format(formato);
		if(estacionamiento.horaMaxima().toLocalTime().isAfter(this.getFinDeFranja())) {
			fin = this.getFinDeFranja().format(formato);	
		}
		appSolicitante.informar("Se está iniciando el estacionamiento solicitado"
								+ "\n Su hora de inicio fue: "  + inicio
								+ "\n Su estacionamiento finalizara a las: " + fin);
	}
	
	public void finalizarEstacionamiento(AppUsuario appSolicitante, AdministracionDeCelulares admCel) {
		Estacionamiento estacionamiento = this.buscaEstacionamientoVigentePorNumero(appSolicitante.getNumeroAsociado()); 
		int horasTranscurridas = (int) ChronoUnit.HOURS.between(estacionamiento.getHoraInicio(),LocalDateTime.now(reloj));
		double costo = horasTranscurridas * estacionamiento.getPrecioEstacionamiento();
		
		try {
			admCel.descontarCredito(appSolicitante.getNumeroAsociado(), costo);	
		}
		catch(Exception e){
			appSolicitante.informar("No posee crédito disponible para finalizar el estacionamiento");
		}
		
		estacionamiento.finalizarEstacionamiento();
		alarma.notificar(estacionamiento);
		this.informarFin(appSolicitante,estacionamiento,horasTranscurridas,costo);	
	}
	
	public void informarFin(AppUsuario appSolicitante, Estacionamiento estacionamiento, int horasTranscurridas, double costo) {
		DateTimeFormatter formato = DateTimeFormatter.ISO_LOCAL_TIME;
		String fin = LocalTime.now(reloj).format(formato);
		String inicio = estacionamiento.getHoraInicio().format(formato);
		
		appSolicitante.informar("Se está finalizando el estacionamiento solicitado"
								+ "\n Su hora de inicio fue: "  + inicio
								+ "\n Su hora de fin fue: " + fin
								+ "\n La duración de su estacionamiento fue: " + horasTranscurridas
								+ "\n El costo de su estacionamiento fue: " + costo);
	}
	

	public Estacionamiento buscaEstacionamientoVigentePorNumero(long nro) {
	    Estacionamiento estacionamiento  = estacionamientos
	    												.stream()
	    												.filter(e -> e.perteneceAlNumero(nro) && e.estaVigente())
	    												.findFirst()
	    												.orElse(new EstacionamientoNulo(reloj));
	    return estacionamiento;
	}
	
	public boolean consultarVigencia(String patente) {
		return this.estacionamientos.stream().anyMatch(e -> e.getPatente()==patente & e.estaVigente());
	}
	
	public boolean consultarVigencia(AppUsuario appUsuario) {
		return this.estacionamientos.stream().anyMatch(e -> e.perteneceAlNumero(appUsuario.getNumeroAsociado()) && e.estaVigente());
	}
	
	public void dispararFinDeEstacionamientos() throws Exception {
		if(LocalTime.now(reloj).isAfter(this.getFinDeFranja())) {
			estacionamientos
							.stream()
							.forEach(e -> e.finalizarEstacionamiento());	
		}else {
			throw new Exception("Todavía no es la hora de fin de franja horaria");
		}
	}
	
}
