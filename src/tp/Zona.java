package tp;

import java.util.ArrayList;


public class Zona {
	
	private ArrayList<PuntoDeVenta> puntosDeVentas;
	private Inspector inspectorAsociado;
	private String nombre;

	public Zona(String nombre,ArrayList<PuntoDeVenta> zonas) {
		this.puntosDeVentas = zonas; 
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void agregarPuntoDeVenta(PuntoDeVenta puntoDeVenta) {
		this.puntosDeVentas.add(puntoDeVenta);
	}

	public void asignarInspector(Inspector inspector) {
		this.inspectorAsociado = inspector;
	}
	
	public Inspector getInspectorAsociado() {
		return this.inspectorAsociado;
	}

}
