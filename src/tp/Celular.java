package tp;

public class Celular {
	
	private final long nro;
	private double saldo;
	
	public Celular(long nro, double saldo) {
		this.nro = nro;
		this.saldo = saldo;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public long getNro() {
		return nro;
	}
	
	public void cargarSaldo(double saldo) {
		this.saldo += saldo;
	}
 
	public void descontarSaldo(double saldo) throws Exception {
		if (this.saldo>=saldo) {
			this.saldo -= saldo;	
		}
		else {
			throw new Exception("No posee saldo suficiente");
		}
	}
}
