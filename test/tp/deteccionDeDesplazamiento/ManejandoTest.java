package tp.deteccionDeDesplazamiento;


import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import tp.apps.AppUsuario;
import tp.movementSensor.Modo;

class ManejandoTest {

	AppUsuario app = mock(AppUsuario.class);
	Modo modo = mock(Modo.class);
	Manejando estado = new Manejando();
	
	@Test
	void testDriving() {
		estado.driving(app);
		verifyNoInteractions(app);
	}
	
	@Test
	void testWalking() {
		when(app.getModo()).thenReturn(modo);
		estado.walking(app);
		verify(app).setEstado(any(Caminando.class));
		verify(modo).inicioEstacionamiento(app);
	}

}
