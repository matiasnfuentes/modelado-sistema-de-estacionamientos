package tp;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


class InfraccionTest {
	
		Inspector inspector = mock(Inspector.class);
		Zona zona = mock(Zona.class);
		Infraccion infraccion = new Infraccion("ABC-123", LocalDate.of(2000, 1, 1), LocalTime.of(20, 0),inspector,zona);
		
	// Test del constructor
	@Test
	void test() {
		assertNotNull(infraccion);
	}
	
	// Test getPatente
	@Test
	void testGetPatente() {
		assertEquals("ABC-123", infraccion.getPatente());
	}

	// Test getFecha
	@Test
	void testGetFecha() {
		assertEquals(LocalDate.of(2000, 1, 1), infraccion.getFecha());
	}

	// Test getHora
	@Test
	void testGetHora() {
		assertEquals(LocalTime.of(20, 0), infraccion.getHora());
	}
	
	// Test Inspector
	@Test
	void testGetInspector() {
		assertEquals(inspector, infraccion.getInspector());
	}
	
	// Test Zona
	@Test
	void testGetZona() {
		assertEquals(zona, infraccion.getZona());
	}
	
	
}
