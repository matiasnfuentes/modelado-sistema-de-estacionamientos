package tp.movementSensor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import tp.apps.AppUsuario;

class ModoAutomaticoTest {
	
	//Set up
	AppUsuario app = mock(AppUsuario.class);
	ModoAutomatico modo = new ModoAutomatico();

	// Test del constructor
	@Test
	void testModoAutomatico() {
		assertNotNull(modo);
	}
	
	// Test del InicioEstacionamiento
	@Test
	void testInicioEstacionamiento() {
		modo.inicioEstacionamiento(app);
		verify(app).iniciarEstacionamiento();
		verify(app).informar("Se ha iniciado el estacionamiento en forma automatica");
	}
	
	// Test fin del estacionamiento
	@Test
	void testFinEstacionamiento() {
		modo.finEstacionamiento(app);
		verify(app).finalizarEstacionamiento();
		verify(app).informar("Se ha finalizado el estacionamiento en forma automatica");
	}

}
