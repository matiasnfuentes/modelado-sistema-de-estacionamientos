package tp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class InspectorTest {

	//Test del constructor
	@Test
	void testInspector() {
		Inspector inspector = new Inspector();
		assertNotNull(inspector);
	}
	
	//Test Asignar zona
	@Test
	void testAsignarZona() {
		Zona zona = mock(Zona.class);
		Inspector inspector = new Inspector();
		inspector.asignarZona(zona);
		assertEquals(zona, inspector.getZona());
	}
	
	

}
