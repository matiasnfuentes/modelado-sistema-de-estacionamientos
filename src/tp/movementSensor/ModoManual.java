package tp.movementSensor;

import tp.apps.AppUsuario;

public class ModoManual implements Modo{
	
	public void inicioEstacionamiento(AppUsuario app) {
		app.informar("Se ha detectado el inicio de un estacionamiento");
	}
	
	public void finEstacionamiento(AppUsuario app) {
		app.informar("Se ha detectado el fin de un estacionamiento.");
	}
}
