package tp.administraciones;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import tp.apps.AppUsuario;
import tp.estacionamientos.Estacionamiento;
import tp.estacionamientos.EstacionamientoNulo;
import tp.estacionamientos.EstacionamientoPorApp;
import tp.estacionamientos.EstacionamientoPuntual;
import tp.monitoreoDeEstacionamientos.AlarmaEstacionamiento;

class AdministracionDeEstacionamientoTest {
	
	LocalDateTime horario = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
	ZoneId defaultZone = ZoneId.systemDefault();
    Clock reloj= Clock.fixed(horario.atZone(defaultZone).toInstant(), defaultZone);
    AlarmaEstacionamiento alarma = mock(AlarmaEstacionamiento.class);
    ArrayList<Estacionamiento> estacionamientos = spy(new ArrayList<Estacionamiento>());
    
	AdministracionDeEstacionamientos admEstacionamientos = new AdministracionDeEstacionamientos(reloj,alarma,estacionamientos);
	

	
	
	// Test del constructor
	@Test
	void testAdministracionDeEstacionamientos() {
		assertNotNull(admEstacionamientos);
	} 
	
	//Test getFinDeFranja
	@Test
	void testGetFinDeFranja() {
		assertEquals(LocalTime.of(20, 0), admEstacionamientos.getFinDeFranja());
	} 
	
	//Test setFinDeFranja
	@Test
	void testSetFinDeFranja() {
		admEstacionamientos.setFinDeFranja(LocalTime.of(22, 0));
		assertEquals(LocalTime.of(22, 0), admEstacionamientos.getFinDeFranja());
	} 
	
	//Test agregarEstacionamiento (puntual)
	@Test
	void testAgregarEstacionamientoPuntual() {
		admEstacionamientos.agregarEstacionamiento("ABC-123", 5);
		verify(estacionamientos).add(any(EstacionamientoPuntual.class));
		verify(alarma).notificar(any(EstacionamientoPuntual.class));
	}
	
	//Test agregarEstacionamiento (Por app)
		@Test
		void testAgregarEstacionamientoPorApp() {
			//Set up
			AdministracionDeEstacionamientos spyAdm = spy(admEstacionamientos);
			AppUsuario app = mock(AppUsuario.class);
			AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
			//Excercise
			spyAdm.agregarEstacionamiento("ABC-123", 40.00,app,admCel);
			//Verify
			verify(estacionamientos).add(any(EstacionamientoPorApp.class));
			verify(alarma).notificar(any(EstacionamientoPorApp.class), eq(app), eq(admCel));
			verify(spyAdm).informarInicio(eq(app), any(EstacionamientoPorApp.class));
		}	
	
	
	// Test informarFin
	@Test
	void informarFin() {
		AppUsuario app = mock(AppUsuario.class);
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getHoraInicio()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(10,0)));
		admEstacionamientos.informarFin(app, estacionamiento, 2, 80.00);
		
		verify(app).informar("Se está finalizando el estacionamiento solicitado"
				+ "\n Su hora de inicio fue: "  + "10:00:00"
				+ "\n Su hora de fin fue: " + "12:00:00"
				+ "\n La duración de su estacionamiento fue: " + 2
				+ "\n El costo de su estacionamiento fue: " + 80.0);
	}	
	

	// Test buscaEstacionamientoVigentePorNumero
	@Test
	void testBuscaEstacionamientoVigentePorNumero() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(true);
		estacionamientos.add(estacionamiento);
		assertNotNull(admEstacionamientos.buscaEstacionamientoVigentePorNumero(15222));
	}	
	
	// Test buscaEstacionamientoVigentePorNumero
	@Test
	void testBuscaEstacionamientoVigentePorNumeroFallidoPorNoEstarVigente() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(false);
		estacionamientos.add(estacionamiento);
		
		// Excercise 
		Estacionamiento estacionamientoBuscado = admEstacionamientos.buscaEstacionamientoVigentePorNumero(15222);
		
		//Verify
		assertTrue(estacionamientoBuscado instanceof EstacionamientoNulo);
	}
	
	// Test buscaEstacionamientoVigentePorNumero
	@Test
	void testBuscaEstacionamientoVigentePorNumeroFallidoPorNoPertenecerAlNumero() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(false);
		when(estacionamiento.estaVigente()).thenReturn(true);
		estacionamientos.add(estacionamiento);
		
		// Excercise 
		Estacionamiento estacionamientoBuscado = admEstacionamientos.buscaEstacionamientoVigentePorNumero(15222);
		
		//Verify
		assertTrue(estacionamientoBuscado instanceof EstacionamientoNulo);
	}
	
	// Test consultarVigencia por patente exitoso
	@Test
	void testConsultarVigenciaPorPatenteExitoso() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getPatente()).thenReturn("ABC-123");
		when(estacionamiento.estaVigente()).thenReturn(true);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertTrue(admEstacionamientos.consultarVigencia("ABC-123"));
	}
	
	// Test consultarVigencia por patente Fallido por no estar vigente
	@Test
	void testConsultarVigenciaPorPatenteFallidoPorNoEstarVigente() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getPatente()).thenReturn("ABC-123");
		when(estacionamiento.estaVigente()).thenReturn(false);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertFalse(admEstacionamientos.consultarVigencia("ABC123"));
	}
	
	// Test consultarVigencia por patente Fallido por no pertenecer a la pantete indicada
	@Test
	void testConsultarVigenciaPorPatenteFallidoPorNoPertenecerALaPatenteIndicada() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getPatente()).thenReturn("ABD-123");
		when(estacionamiento.estaVigente()).thenReturn(true);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertFalse(admEstacionamientos.consultarVigencia("ABC-123"));
	}
	
	// Test consultarVigencia por patente Fallido por no pertenecer a la pantete indicada ni estar vigente
	@Test
	void testConsultarVigenciaPorPatenteFallidoPorAmbasCondiciones() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getPatente()).thenReturn("ABD-123");
		when(estacionamiento.estaVigente()).thenReturn(false);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertFalse(admEstacionamientos.consultarVigencia("ABC-123"));
	}
		
	// Test consultarVigencia Exitoso con 2 estacionamientos
	@Test
	void testConsultarVigenciaporPatenteExitosoConDosEstacionamientos() {
		
		//Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		EstacionamientoPorApp estacionamiento2 = mock(EstacionamientoPorApp.class);
		when(estacionamiento.getPatente()).thenReturn("ABD-123");
		when(estacionamiento.estaVigente()).thenReturn(true);
		when(estacionamiento2.getPatente()).thenReturn("ABC-123");
		when(estacionamiento2.estaVigente()).thenReturn(true);
		estacionamientos.add(estacionamiento2);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertTrue(admEstacionamientos.consultarVigencia("ABD-123"));
	}
	
	
	// Test consultarVigencia por app exitoso
	@Test
	void testConsultarVigencia() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(true);
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertTrue(admEstacionamientos.consultarVigencia(app));
	}
	
	// Test consultarVigencia por app Fallido (por no pertenecer al numero)
	@Test
	void testConsultarVigenciaFallidoPorNoPertenecerAlNumero() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(true);
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15224);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertFalse(admEstacionamientos.consultarVigencia(app));
	}
	
	// Test consultarVigenciaPorApp Fallido (por no estar vigente)
	@Test
	void testConsultarVigenciaFallidoPorNoEstarVigente() {
		// Set Up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(false);
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		estacionamientos.add(estacionamiento);
		
		//Verify
		assertFalse(admEstacionamientos.consultarVigencia(app));
	}
	
	// Test Disparar fin de estacionamientos Fallido (todavia no es el horario de fin de franja
	@Test
	void testDispararFinDeEstacionamientosFallido() {
		assertThrows(Exception.class, () -> admEstacionamientos.dispararFinDeEstacionamientos());
	}
	
	// Test Disparar fin de estacionamientos exitoso (cambiamos el fin de franja a las 11 am y son las 12 pm.
	@Test
	void testDispararFinDeEstacionamientosExitoso() throws Exception {
		
		//Set up
		admEstacionamientos.setFinDeFranja(LocalTime.of(11, 0));
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		EstacionamientoPorApp estacionamiento1 = mock(EstacionamientoPorApp.class);
		estacionamientos.add(estacionamiento);
		estacionamientos.add(estacionamiento1);
		
		//Excercise
		admEstacionamientos.dispararFinDeEstacionamientos();
		
		//Verify 
		verify(estacionamiento).finalizarEstacionamiento();
		verify(estacionamiento1).finalizarEstacionamiento();
	}
	
	//Test finalizarEstacionamiento
	@Test
	void testFinalizarEstacionamiento() throws Exception {
		
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
		Estacionamiento estacionamiento = mock(Estacionamiento.class);
		estacionamientos.add(estacionamiento);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		when(estacionamiento.getPrecioEstacionamiento()).thenReturn(40.00);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(true);
		when(estacionamiento.getHoraInicio()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(10,0)));
		
		//Excercise
		admEstacionamientos.finalizarEstacionamiento(app, admCel);
		
		//Verify 
		verify(admCel).descontarCredito(15222, 80.00);
		verify(estacionamiento).finalizarEstacionamiento();
		verify(alarma).notificar(estacionamiento);
		verify(app).informar(any(String.class));
	}
	
	//Test finalizarEstacionamiento fallido por falta de credito
	@Test
	void testFinalizarFallido() throws Exception {
		
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
		Estacionamiento estacionamiento = mock(Estacionamiento.class);
		estacionamientos.add(estacionamiento);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		when(estacionamiento.getPrecioEstacionamiento()).thenReturn(40.00);
		when(estacionamiento.perteneceAlNumero(15222)).thenReturn(true);
		when(estacionamiento.estaVigente()).thenReturn(true);
		when(estacionamiento.getHoraInicio()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(10,0)));
		
		doThrow(new Exception()).when(admCel).descontarCredito((long) 15222, 80.00);
		
		//Excercise
		admEstacionamientos.finalizarEstacionamiento(app, admCel);
		
		//Verify 
		verify(app).informar("No posee crédito disponible para finalizar el estacionamiento");
	}
	
	// Test informar inicio con hora (maxima = fin de franja)
	@Test
	void testInformarInicioHoraMaxima() {
		//Set up
		EstacionamientoPorApp estacionamiento = mock(EstacionamientoPorApp.class);
		AppUsuario app = mock(AppUsuario.class);
		when(estacionamiento.getHoraInicio()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
		when(estacionamiento.horaMaxima()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0)));
		
		// Excercise
		admEstacionamientos.informarInicio(app,estacionamiento);
		
		//
		verify(app).informar("Se está iniciando el estacionamiento solicitado"
						    + "\n Su hora de inicio fue: "  +"12:00:00"
							+ "\n Su estacionamiento finalizara a las: " + "20:00:00");
	} 

 
}
