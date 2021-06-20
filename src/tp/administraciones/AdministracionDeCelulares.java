package tp.administraciones;

import java.util.ArrayList;
import java.util.Optional;

import tp.Celular;
import tp.monitoreoDeEstacionamientos.AlarmaEstacionamiento;

public class AdministracionDeCelulares {
	
	private ArrayList<Celular> celularesRegistrados;
	private AlarmaEstacionamiento alarma;
	
	public AdministracionDeCelulares(ArrayList<Celular> celulares,AlarmaEstacionamiento alarma) {
		this.celularesRegistrados = celulares;
		this.alarma = alarma;
	}
	
	public void agregarCelular(Celular celular) {
		celularesRegistrados.add(celular);
	}
	
	public Celular buscarCelular(long nro) {
		Optional<Celular> busqueda = celularesRegistrados
														.stream()
														.filter(c -> c.getNro()==nro)
														.findFirst();
		Celular celularBuscado;
		if(busqueda.isPresent()) {
			celularBuscado = busqueda.get();
		}
		else {
			celularBuscado = new Celular(nro,0);
			this.agregarCelular(celularBuscado);
		}
		return celularBuscado;
	}
	
	public double consultarCredito(long numero) {
		Celular celularAConsultar = this.buscarCelular(numero);
		return celularAConsultar.getSaldo();
	}
	
	public void cargarCredito(long numero, double monto){
		Celular celularACargar = this.buscarCelular(numero);
		celularACargar.cargarSaldo(monto);
		alarma.notificar(numero,monto,this);
	}
	
	public void descontarCredito(long numero, double monto) throws Exception {
		Celular celular= this.buscarCelular(numero);
		celular.descontarSaldo(monto);
	}
	

	
}

