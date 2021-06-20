package tp.monitoreoDeEstacionamientos;

import java.util.ArrayList;
import java.util.function.Consumer;
import tp.administraciones.AdministracionDeCelulares;
import tp.apps.AppUsuario;
import tp.estacionamientos.Estacionamiento;

public class AlarmaEstacionamiento {
	
	private ArrayList<Observador> observadores;
	
	public AlarmaEstacionamiento(ArrayList<Observador> observadores) {
		this.observadores = observadores;
	}

	public void agregarObservador(Observador o) {
		observadores.add(o);
	}
	
	public void eliminarObservador(Observador o) {
		observadores.remove(o);
	}
	
	// Notificacion de estacionamiento puntual
	public void notificar(Estacionamiento estacionamiento) {
		this.notificar(o -> o.actualizar(estacionamiento));
	}
	
	// Notificacion de inicio/fin de estacionamiento por app
	public void notificar(Estacionamiento estacionamiento,AppUsuario appSolicitante,AdministracionDeCelulares admCel) {
		this.notificar(o -> o.actualizar(estacionamiento,appSolicitante,admCel));
	}
	
	// Notificacion de recarga de credito
	public void notificar(Long numero,double monto,AdministracionDeCelulares admCel) {
		this.notificar(o -> o.actualizar(numero,monto,admCel));
	}
	
	public void notificar(Consumer<Observador> action) {
		observadores.stream().forEach(action);
	}

}
