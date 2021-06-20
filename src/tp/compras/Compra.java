package tp.compras;

import java.time.LocalDate;
import java.time.LocalTime;

import tp.PuntoDeVenta;

public abstract class Compra {
	private long numeroDeControl;
	private PuntoDeVenta puntoDeVenta;
	private LocalDate fechaDeCompra;
	private LocalTime horaDeCompra;
	
	public Compra(long numeroDeControl, PuntoDeVenta puntoDeVenta, LocalDate fechaDeCompra, LocalTime horaDeCompra) {
		this.numeroDeControl = numeroDeControl;
		this.puntoDeVenta = puntoDeVenta;
		this.fechaDeCompra = fechaDeCompra;
		this.horaDeCompra = horaDeCompra;
	}
	

}

