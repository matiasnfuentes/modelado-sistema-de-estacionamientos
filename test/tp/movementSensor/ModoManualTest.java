package tp.movementSensor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import tp.apps.AppUsuario;

class ModoManualTest {
	
	//Set up
	AppUsuario app = mock(AppUsuario.class);
	ModoManual modo = new ModoManual();

	// Test del constructor
	@Test
	void testModoAutomatico() {
		assertNotNull(modo);
	}
	
	// Test del InicioEstacionamiento
	@Test
	void testInicioEstacionamiento() {
		modo.inicioEstacionamiento(app);
		verify(app).informar("Se ha detectado el inicio de un estacionamiento");
	}
	
	// Test fin del estacionamiento
	@Test
	void testFinEstacionamiento() {
		modo.finEstacionamiento(app);
		verify(app).informar("Se ha detectado el fin de un estacionamiento.");
	}

}
