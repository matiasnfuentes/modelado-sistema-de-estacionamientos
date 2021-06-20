package tp.deteccionDeDesplazamiento;

import tp.apps.AppUsuario;

public class Manejando implements EstadoDelUsuario {

	@Override
	public void driving(AppUsuario app) {}

	@Override
	public void walking(AppUsuario app) {
		app.setEstado(new Caminando());
		app.getModo().inicioEstacionamiento(app);
	}

}
