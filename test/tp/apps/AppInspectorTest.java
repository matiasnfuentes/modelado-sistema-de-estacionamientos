package tp.apps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import tp.Infraccion;
import tp.Inspector;
import tp.Sem;


class AppInspectorTest { 

	Sem sem = mock(Sem.class);
	Inspector inspector = mock(Inspector.class);
	AppInspector appInspector = new AppInspector(sem, inspector);	

	// Test VerificarVigencia
	@Test
	void testConsultarVigencia() {
		//Set up especifico
		when(sem.consultarVigencia("ABC-234")).thenReturn(true);
		
		//Verify 
		assertTrue(appInspector.consultarEstacionamiento("ABC-234"));
		verify(sem).consultarVigencia("ABC-234");
	}
	
	// Test VerificarVigencia
		@Test
	void testAltaDeInfracción() {
		//Set up especifico
		when(sem.consultarVigencia("ABC-234")).thenReturn(true);
		
		// Excercise
		appInspector.altaDeInfracción("FSA345");
		
		//Verify 
		verify(sem).registrarInfraccion(any(Infraccion.class));
	}
		
}
