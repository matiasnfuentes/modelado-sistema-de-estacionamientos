package tp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ZonaTest {
	
	
	Zona capital = new Zona("capital",new ArrayList<PuntoDeVenta>());
	
	// Test del constructor con nombre y lista de Puntos de ventas
		@Test
		void testZona2() {
			assertNotNull(capital);
	}

	// Test getNombre
	@Test
	void testGetNombre() {
		assertEquals("capital", capital.getNombre());
	}
	
	// Test agregarPuntoDeventa
	@Test
	void testAgregarPuntoDeVenta() {
		//Set up
		ArrayList<PuntoDeVenta> puntosDeVenta = mock(ArrayList.class);
		PuntoDeVenta puntoDeVenta = mock(PuntoDeVenta.class);
		Zona bsas = new Zona("bsas",puntosDeVenta);
		
		//Excercise
		bsas.agregarPuntoDeVenta(puntoDeVenta);

		//Verif
		verify(puntosDeVenta).add(puntoDeVenta);
	}
	
	
	// Test que verifica la asignacion de un inspector
	
	@Test
	void testAsignarInspector() {
		//Set up
		Inspector inspector = mock(Inspector.class);
		
		//Excercise
		capital.asignarInspector(inspector);
		
		//Verify
		assertEquals(inspector, capital.getInspectorAsociado());
	}
	
} 
