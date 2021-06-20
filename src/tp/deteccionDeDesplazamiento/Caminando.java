package tp.deteccionDeDesplazamiento;

import tp.apps.AppUsuario;

public class Caminando implements EstadoDelUsuario {

	@Override
	public void driving(AppUsuario app) {
		app.setEstado(new Manejando());
		app.getModo().finEstacionamiento(app);
	}

	@Override
	public void walking(AppUsuario app) {}

}
