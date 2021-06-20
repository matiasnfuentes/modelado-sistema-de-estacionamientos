package tp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CelularTest {
	
	Celular celular = new Celular(1551408526, 50.0);
	
	// Test Constructor
	@Test
	void testCelular() {
		assertNotNull(celular);
	}
	
	// Test getSaldo
	@Test
	void testGetSaldo() {
		assertEquals(50.0, celular.getSaldo(),0);
	}
	
	// Test getNumero
	@Test 
	void testGetNro() {
		assertEquals(1551408526, celular.getNro());
	}
	
	// Test cargarSaldo
	@Test 
	void testCargarSaldo() {
		celular.cargarSaldo(100);
		assertEquals(150, celular.getSaldo(),0);
	}
	
	// Test descontarSaldo exitoso
	@Test 
	void testDescontarSaldoExitoso() throws Exception {
		celular.descontarSaldo(25);
		assertEquals(25, celular.getSaldo(),0);
	}
	
	// Test descontarSaldo fallido
	@Test 
	void testDescontarSaldofallido() throws Exception{
		assertThrows(Exception.class, () -> celular.descontarSaldo(55.00));
	}
	
}
