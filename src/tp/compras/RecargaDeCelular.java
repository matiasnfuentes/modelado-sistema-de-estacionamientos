package tp.compras;

import java.time.LocalDate;
import java.time.LocalTime;

import tp.PuntoDeVenta;

public class RecargaDeCelular extends Compra {
	
	private double monto;
	private long nroCelular;

	public RecargaDeCelular(long numeroDeControl, PuntoDeVenta puntoDeVenta, LocalDate fechaDeCompra, LocalTime horaDeCompra,double monto, long nroCelular) {
		super(numeroDeControl, puntoDeVenta,fechaDeCompra,horaDeCompra);
		this.monto = monto;
		this.nroCelular = nroCelular;
	}
}
