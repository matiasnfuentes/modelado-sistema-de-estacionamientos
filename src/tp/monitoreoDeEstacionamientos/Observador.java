package tp.monitoreoDeEstacionamientos;
import tp.administraciones.AdministracionDeCelulares;
import tp.apps.AppUsuario;
import tp.estacionamientos.Estacionamiento;

public interface Observador {
	
	public void actualizar(Estacionamiento estacionamiento);
	public void actualizar(Estacionamiento estacionamiento,AppUsuario appSolicitante,AdministracionDeCelulares admCel);
	public void actualizar(Long numero,double monto,AdministracionDeCelulares admCel);
	
}
