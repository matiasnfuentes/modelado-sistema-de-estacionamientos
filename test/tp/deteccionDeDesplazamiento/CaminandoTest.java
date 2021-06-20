package tp.deteccionDeDesplazamiento;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import tp.apps.AppUsuario;
import tp.movementSensor.Modo;

class CaminandoTest {

	AppUsuario app = mock(AppUsuario.class);
	Modo modo = mock(Modo.class);
	Caminando estado = new Caminando();
	
	@Test
	void testDriving() {
		when(app.getModo()).thenReturn(modo);
		estado.driving(app);
		verify(app).setEstado(any(Manejando.class));
		verify(modo).finEstacionamiento(app);
	}
	
	@Test
	void testWalking() {
		estado.walking(app);
		verifyNoInteractions(app);
	}

}
