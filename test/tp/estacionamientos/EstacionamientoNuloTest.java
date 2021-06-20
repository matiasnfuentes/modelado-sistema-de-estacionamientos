package tp.estacionamientos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class EstacionamientoNuloTest {

	LocalDateTime horario = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
	ZoneId defaultZone = ZoneId.systemDefault();
    Clock reloj= Clock.fixed(horario.atZone(defaultZone).toInstant(), defaultZone);
	EstacionamientoNulo estacionamiento = new EstacionamientoNulo(reloj);

	// TestHoraInicio
	@Test
	void testHoraInicio() {
		assertEquals(LocalDateTime.now(reloj),estacionamiento.getHoraInicio());
	}
	
	// TestHoraFin
	@Test
	void testHoraFin() {
		assertEquals(LocalDateTime.now(reloj),estacionamiento.getHoraFin());
	}
	
	// TestSetHoraFin
	@Test
	void testSetHoraFin() {
		estacionamiento.setHoraFin(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 00)));
		assertEquals(LocalDateTime.now(reloj),estacionamiento.getHoraFin());
	}	
	
	// TestEstaVigente
	@Test
	void testEstaVigente() {
		assertFalse(estacionamiento.estaVigente());
	}

}
