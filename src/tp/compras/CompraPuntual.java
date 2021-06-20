package tp.compras;

import java.time.LocalDate;
import java.time.LocalTime;

import tp.PuntoDeVenta;

public class CompraPuntual extends Compra {

	private int cantDeHoras;

	public CompraPuntual(long numeroDeControl, PuntoDeVenta puntoDeVenta, LocalDate fechaDeCompra, LocalTime horaDeCompra,int cantDeHoras) {
		super(numeroDeControl, puntoDeVenta,fechaDeCompra,horaDeCompra);
		this.cantDeHoras = cantDeHoras;
	}
	
	
}
