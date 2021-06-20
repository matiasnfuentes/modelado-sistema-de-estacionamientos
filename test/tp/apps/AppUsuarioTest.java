package tp.apps;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import tp.Sem;
import tp.deteccionDeDesplazamiento.EstadoDelUsuario;
import tp.movementSensor.Modo;
import tp.movementSensor.ModoAutomatico;
import tp.movementSensor.ModoManual;

class AppUsuarioTest {
	
	Modo modo = mock(Modo.class);
	Sem sem = mock(Sem.class);
	EstadoDelUsuario estado = mock(EstadoDelUsuario.class);
	ArrayList<String> notificaciones = spy(new ArrayList<String>());
	AppUsuario app = new AppUsuario(15222,"ABC-123", sem, modo,estado,notificaciones);
	
	// Test del constructor
	@Test
	void testAppUsuario() {
		assertNotNull(app);
	} 
	
	// Test getPatente
	@Test
	void testGetPatente() {
		assertEquals("ABC-123", app.getPatente());
	} 
	
	// Test setPatente
	@Test
	void testSetPatente() {
		app.setPatente("ABC-124");
		assertEquals("ABC-124", app.getPatente());
	} 
	
	// Test setEstado
	@Test
	void testEstado() {
		EstadoDelUsuario estado2 = mock(EstadoDelUsuario.class);
		app.setEstado(estado2);
		assertEquals(estado2, app.getEstado());
	} 
	
	// Test getModo
	@Test
	void testModo() {
		assertEquals(modo, app.getModo());
	} 
	
	// Test getNumeroAsociado
	@Test
	void testGetNumeroAsociado() {
		assertEquals(15222, app.getNumeroAsociado());
	} 
	
	
	
	// Test ConsultarSaldo
	@Test
	void testConsultarSaldo() {
		app.consultarSaldo();
		verify(sem).consultarCredito(app);
	}
	
	// Test IniciarEstacionamiento
	@Test
	void testIniciarEstacionamiento() {
		app.iniciarEstacionamiento();
		verify(sem).iniciarEstacionamiento("ABC-123", app);
	} 
	
	// Test FinalizarEstacionamientoExitoso
	@Test
	void testFinalizarEstacionamientoExitoso() {
		when(sem.consultarVigencia(app)).thenReturn(true);
		app.finalizarEstacionamiento();
		verify(sem).finalizarEstacionamiento(app);
	} 
	
	//Test informar
	@Test
	void testInformar() {
		//Excercise
		app.informar("Prueba informe");
		
		//Verify
		verify(notificaciones).add("Prueba informe");
	} 
	
	@Test
	void testCambiarAModoManual() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		//Excercise
		app.cambiarAModoManual();
		Field campo= app.getClass().getDeclaredField("modo");
		campo.setAccessible(true);
		Modo verificacionDeModo = (Modo) campo.get(app);
		
		
		//Verify
		assertTrue(verificacionDeModo instanceof ModoManual);
	}
	
	@Test
	void testCambiarAAutomatico() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		//Excercise
		app.cambiarAModoAutomatico();;
		Field campo= app.getClass().getDeclaredField("modo");
		campo.setAccessible(true);
		Modo verificacionDeModo = (Modo) campo.get(app);
		
		//Verify
		assertTrue(verificacionDeModo instanceof ModoAutomatico);
	}
	
	// Test driving exitoso
	
	@Test
	void testDriving() {
		//Set Up
		app.activarDeteccionDeDesplazamiento();

		//Excercise
		app.driving();
		
		//Verify
		verify(estado).driving(app);;
	}
	
	
	// Test driving fallido porque estaba desactivada la deteccion de desplazamiento
	@Test
	void testDrivingFallidoPorDeteccionDeDesplazamientoDesactivada() {
		//Set Up
		app.desactivarDeteccionDeDesplazamiento();
		
		//Excercise
		app.driving();
		
		//Verify
		// El estado de caminando no cambia porque la deteccion de dezplazamiento esta desactivada
		
		verify(estado,never()).driving(app);
	}
		
	
	// Test walking exitoso
	
	@Test
	void testWalking() {
		//Set Up
		app.activarDeteccionDeDesplazamiento();
		
		//Excercise
		app.walking();
		
		//Verify
		verify(estado).walking(app);;
	}
	
	
	// Test walking fallido porque estaba desactivada la deteccion de desplazamiento
	@Test
	void testWalkingFallidoPorDeteccionDeDesplazamientoDesactivada() {
		//Set Up
		app.desactivarDeteccionDeDesplazamiento();
		
		//Excercise
		app.walking();
		
		//Verify
		// El estado de caminando no cambia porque la deteccion de dezplazamiento esta desactivada
		verify(estado,never()).walking(app);
	}
	
} 
