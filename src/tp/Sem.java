package tp;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import tp.administraciones.AdministracionDeCelulares;
import tp.administraciones.AdministracionDeEstacionamientos;
import tp.apps.AppUsuario;
import tp.compras.Compra;
import tp.estacionamientos.Estacionamiento;
import tp.monitoreoDeEstacionamientos.AlarmaEstacionamiento;
import tp.monitoreoDeEstacionamientos.Observador;

public class Sem {
	
	private AdministracionDeCelulares admCel;
	private ArrayList<PuntoDeVenta> puntosDeVenta;
	public AdministracionDeEstacionamientos admEstacionamientos; //
	private ArrayList<Compra> compras;
	public ArrayList<Infraccion> infracciones;
	private double precioDeEstacionamientos; 
	private LocalTime horaDeFinDeFranja;
	
	public Sem(double precioDeEstacionamientos,LocalTime horaDeFinDeFranja) {
		AlarmaEstacionamiento alarma = new AlarmaEstacionamiento(new ArrayList<Observador>());
		this.horaDeFinDeFranja = horaDeFinDeFranja;
		this.precioDeEstacionamientos = precioDeEstacionamientos;
		this.admCel = new AdministracionDeCelulares(new ArrayList<Celular>(),alarma);
		this.puntosDeVenta = new ArrayList<PuntoDeVenta>();
		this.admEstacionamientos = new AdministracionDeEstacionamientos(Clock.systemDefaultZone(),alarma, new ArrayList<Estacionamiento>());
		this.compras = new ArrayList<Compra>();
		this.infracciones = new ArrayList<Infraccion>();
	}

	public Sem(AdministracionDeCelulares admCel, ArrayList<PuntoDeVenta> puntosDeVenta,
			AdministracionDeEstacionamientos admEstacionamientos, ArrayList<Compra> compras,
			ArrayList<Infraccion> infracciones, double precioDeEstacionamientos, LocalTime horaDeFinDeFranja) {
		this.admCel = admCel;
		this.puntosDeVenta = puntosDeVenta;
		this.admEstacionamientos = admEstacionamientos;
		this.compras = compras;
		this.infracciones = infracciones;
		this.precioDeEstacionamientos = precioDeEstacionamientos;
		this.horaDeFinDeFranja = horaDeFinDeFranja;
	}



	public LocalTime getHoraDeFinDeFranja() {
		return horaDeFinDeFranja;
	}

	public void setHoraDeFinDeFranja(LocalTime nuevoFin) {
		this.horaDeFinDeFranja = nuevoFin;
		this.admEstacionamientos.setFinDeFranja(nuevoFin);
	}

	public double getPrecioDeEstacionamientos() {
		return precioDeEstacionamientos;
	}

	public void setPrecioDeEstacionamientos(double precioDeEstacionamientos) {
		this.precioDeEstacionamientos = precioDeEstacionamientos;
	}


	public void addPuntoDeVenta(PuntoDeVenta puntoDeVenta) {
		puntosDeVenta.add(puntoDeVenta);
	}

	public void iniciarEstacionamiento(String patente, int cantDeHoras) {
		admEstacionamientos.agregarEstacionamiento(patente,cantDeHoras);
	}
	
	public void iniciarEstacionamiento(String patente, AppUsuario appSolicitante) {
		if(admCel.consultarCredito(appSolicitante.getNumeroAsociado()) > this.getPrecioDeEstacionamientos()){
			admEstacionamientos.agregarEstacionamiento(patente, this.getPrecioDeEstacionamientos(),appSolicitante,admCel);
		}
		else
		{
			appSolicitante.informar("Saldo insuficiente. Estacionamiento no permitido");
		}
	} 
	
	public void registrarCompra(Compra compra, String patente, int cantDeHoras) {
		this.iniciarEstacionamiento(patente,cantDeHoras);
		this.compras.add(compra);
	}
	
	public void registrarCompra(Compra compra, Long nroCelular, Double monto) {
		this.cargarCredito(nroCelular,monto);
		this.compras.add(compra);
	}
	
	public void cargarCredito(long numero, double monto){
			admCel.cargarCredito(numero, monto);
	}
	 
	public void consultarCredito(AppUsuario celular) {
		celular.informar( "Su saldo es:" + admCel.consultarCredito(celular.getNumeroAsociado()) );
	}

	public void finalizarEstacionamiento(AppUsuario appSolicitante){
		admEstacionamientos.finalizarEstacionamiento(appSolicitante,admCel);
	}
		
	public void dispararFinDeEstacionamientos() throws Exception {
		admEstacionamientos.dispararFinDeEstacionamientos();
	}

	public void registrarInfraccion(Infraccion infraccion) {
		this.infracciones.add(infraccion);
	} 

	public boolean consultarVigencia(String patente) {
		return admEstacionamientos.consultarVigencia(patente);
	}
	
	public boolean consultarVigencia(AppUsuario appUsuario) {
		return admEstacionamientos.consultarVigencia(appUsuario);
	}

}
