package tp;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import tp.compras.Compra;

import static org.mockito.Mockito.*;

class PuntoDeVentaTest {
	
	//Set up general 
	LocalDateTime horario = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
	ZoneId defaultZone = ZoneId.systemDefault();
    Clock reloj= Clock.fixed(horario.atZone(defaultZone).toInstant(), defaultZone);
	Sem sem = mock(Sem.class);
	PuntoDeVenta puntoDeVenta = new PuntoDeVenta(sem,reloj);
	
	//Test del constructor con reloj
	@Test
	void testDelConstructor() { 
		assertNotNull(puntoDeVenta);
	} 
	
	//Test del constructor sin reloj
	@Test
	void testDelConstructorSinReloj() {
		// Excersice
		PuntoDeVenta puntoDeVentaSinReloj = new PuntoDeVenta(sem);
		
		//Verify
		assertNotNull(puntoDeVentaSinReloj);
	}
	
	
	//Test cargarCredito
	@Test
	void testCargaCredito() { 
		// Excercise
		puntoDeVenta.cargarCredito(1551408598, 300.0);
		
		//Verify
		verify(sem).registrarCompra(any(Compra.class),eq((long) 1551408598),eq(300.0));
	} 
	
	// Test inciarEstacionamiento exitoso (Con hora local 12:00)
	@Test
	void testIniciarEstacionamiento() throws Exception {
		// Set up especifico
		when(sem.getHoraDeFinDeFranja()).thenReturn(LocalTime.of(20, 0));
		
		// Excercise
		puntoDeVenta.iniciarEstacionamientoPuntual("TRE475", 3);
		
		// Verify
		verify(sem).registrarCompra(any(Compra.class),eq("TRE475"),eq(3));
	}
	
	// Test inciarEstacionamiento Fallido porque se pasa del limite de franja Con hora local 12:00)
	@Test
	void testIniciarFallido() throws Exception {
		// Set up especifico
		when(sem.getHoraDeFinDeFranja()).thenReturn(LocalTime.of(20, 0));
		
		// Excersice - Verify
		assertThrows(Exception.class, () -> puntoDeVenta.iniciarEstacionamientoPuntual("TRE475", 9));
	}
	
}
