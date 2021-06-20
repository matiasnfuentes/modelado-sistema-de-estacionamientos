package tp.estacionamientos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import tp.administraciones.AdministracionDeCelulares;
import tp.apps.AppUsuario;
import static org.mockito.Mockito.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

class EstacionamientoPorAppTest {
	
	LocalDateTime horario = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
	ZoneId defaultZone = ZoneId.systemDefault();
    Clock reloj= Clock.fixed(horario.atZone(defaultZone).toInstant(), defaultZone);
	AppUsuario app = mock(AppUsuario.class);
	AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
	EstacionamientoPorApp estacionamiento = new EstacionamientoPorApp("ABC-123",40.00,app,admCel,reloj);
	
	//Test del constructor
	@Test
	void testEstacionamientoPorApp() {
		assertNotNull(estacionamiento);
	}
	
	//Test getPatente
	@Test
	void testGetPatente() {
		assertEquals("ABC-123", estacionamiento.getPatente());
	}
	
	//Test getPrecioEstacionamiento
	@Test
	void testGetPrecioEstacionamiento() {
		assertEquals(40.00, estacionamiento.getPrecioEstacionamiento(),0);
	}
	
	//Test testPerteneceAlNumero
	@Test
	void testPerteneceAlNumero() {
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		assertTrue(estacionamiento.perteneceAlNumero(15222));
	}
	
	//Test testPerteneceAlNumeroFallido
	@Test
	void testPerteneceAlNumeroFallido() {
		when(app.getNumeroAsociado()).thenReturn((long) 152223);
		assertFalse(estacionamiento.perteneceAlNumero(15222));
	}
	

	
	//Test FinalizarEstacionamiento
	@Test
	void testFinalizarEstacionamiento() {
		estacionamiento.finalizarEstacionamiento();
		assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), estacionamiento.getHoraFin());
		assertFalse(estacionamiento.estaVigente());
	}
	
	//Test Horas Disponibles
	@Test
	void testHorasDisponibles() {
		when(admCel.consultarCredito(15222)).thenReturn(87.5);
		int horasDisponibles = estacionamiento.horasDisponibles(15222);
		assertEquals(2, horasDisponibles);
	}
	
	//Test testHoraMaxima
	@Test
	void testHoraMaxima() {
		//Credito para 2 horasDisponibles
		when(admCel.consultarCredito(15222)).thenReturn(87.5);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		assertEquals(LocalTime.of(14, 0), estacionamiento.horaMaxima().toLocalTime());
	}
	
	//Test EstaVigente porque tiene credito hasta las 14 HS y la hora actual son las 12.
	@Test
	void testEstaVigente() {
		//Credito para 2 horasDisponibles
		when(admCel.consultarCredito(15222)).thenReturn(87.5);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		assertTrue(estacionamiento.estaVigente());
	}
	
	//Test EstaVigente Fallido porque tiene credito hasta las 14 HS y al no tener credito, la hora de fin es la misma.
	// Por lo tanto el estacionamiento no esta vigente.
	@Test
	void testEstaVigenteFallido(){
		//Credito para 2 horasDisponibles
		when(admCel.consultarCredito(15222)).thenReturn(0.0);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		assertFalse(estacionamiento.estaVigente());
	}
		 
}
