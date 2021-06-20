package tp.apps;

import java.util.ArrayList;

import tp.Sem;
import tp.deteccionDeDesplazamiento.EstadoDelUsuario;
import tp.movementSensor.Modo;
import tp.movementSensor.ModoAutomatico;
import tp.movementSensor.ModoManual;
import tp.movementSensor.MovementSensor;

public class AppUsuario implements MovementSensor{
	
	private long numeroAsociado;
	private String patente;
	private Sem sem;
	private Boolean deteccionDesplazamiento;
	private Modo modo;
	private EstadoDelUsuario estado;
	private ArrayList<String> notificaciones;
	
	public AppUsuario(long numeroAsociado,String patente, Sem sem, Modo modo, EstadoDelUsuario estado, ArrayList<String> notificaciones) {
		this.numeroAsociado = numeroAsociado;
		this.patente = patente;
		this.sem = sem;
		this.deteccionDesplazamiento = false;
		this.estado = estado;
		this.modo = modo;
		this.notificaciones = notificaciones;
	}
	// Getters y setters 

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public void setEstado(EstadoDelUsuario estado) {
		this.estado = estado;
	}
	
	public EstadoDelUsuario getEstado() {
		return this.estado;
	}
	
	public long getNumeroAsociado() {
		return this.numeroAsociado;
	}
	
	public Modo getModo() {
		return modo;
	}
	
	public void cambiarAModoAutomatico() {
		this.modo = new ModoAutomatico();
	}
	
	public void cambiarAModoManual() {
		this.modo = new ModoManual();
	}
	
	// Metodos AppUsuario
	
	public void consultarSaldo() {
		sem.consultarCredito(this);
	}
	
	public void iniciarEstacionamiento() {
		sem.iniciarEstacionamiento(this.getPatente(),this);
	} 
	
	public void finalizarEstacionamiento(){
		sem.finalizarEstacionamiento(this);	
	}

	public void informar(String informe) {
		notificaciones.add(informe);
	}
	
	public void activarDeteccionDeDesplazamiento() {
		this.deteccionDesplazamiento = true;
	}
	
	public void desactivarDeteccionDeDesplazamiento() {
		this.deteccionDesplazamiento = false;
	}
	
	public void driving() {
		if(this.deteccionDesplazamiento) {
			estado.driving(this);
		}
	}
	
	public void walking(){
		if(this.deteccionDesplazamiento) {
			estado.walking(this);
		}
	}
}
