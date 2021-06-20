package tp.estacionamientos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class EstacionamientoPuntualTest {
	
	LocalDateTime horario = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
	ZoneId defaultZone = ZoneId.systemDefault();
    Clock reloj= Clock.fixed(horario.atZone(defaultZone).toInstant(), defaultZone);
	EstacionamientoPuntual estacionamiento = new EstacionamientoPuntual("ABC-123",5,reloj);
	
	//Test constructor
	@Test
	void testEstacionamientoPuntual() {
		assertNotNull(estacionamiento);
	}
	
	//Test pertenece al numero
	@Test
	void testPerteneceAlNumero() {
		assertFalse(estacionamiento.perteneceAlNumero(1598475132));
	
	}
	
	// testGetPrecioEstacionamiento
	@Test
	void testGetPrecioEstacionamiento() {
		assertEquals(0, estacionamiento.getPrecioEstacionamiento(),0);

	}
	
	// testFinalizarEstacionamieto
	@Test
	void testFinalizarEstacionamieto() {
		EstacionamientoPuntual spyEstacionamiento = spy(estacionamiento);
		spyEstacionamiento.finalizarEstacionamiento();
		verify(spyEstacionamiento).finalizarEstacionamiento();
		verifyNoMoreInteractions(spyEstacionamiento);
	}
	
	// test Esta vigente exitoso porque el estacionamiento termina a las 17 , y son las 12
	@Test
	void testEstaVigente() {
		assertTrue(estacionamiento.estaVigente());
	}
}

