package tp;

public class Inspector {
	
	private Zona zona;

	public Zona getZona() {
			return zona;
	}
	
	public void asignarZona(Zona zona) {
		this.zona = zona;
		zona.asignarInspector(this);
	}
	
}
