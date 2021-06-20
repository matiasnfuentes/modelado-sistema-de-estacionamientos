package tp.movementSensor;

import tp.apps.AppUsuario;

public class ModoAutomatico implements Modo{
	
	public void inicioEstacionamiento(AppUsuario app) {
		app.informar("Se ha iniciado el estacionamiento en forma automatica");
		app.iniciarEstacionamiento();
	}
	
	public void finEstacionamiento(AppUsuario app){
		app.informar("Se ha finalizado el estacionamiento en forma automatica");
		app.finalizarEstacionamiento();
	}
}