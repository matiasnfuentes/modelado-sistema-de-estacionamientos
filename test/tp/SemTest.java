package tp;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import tp.administraciones.AdministracionDeCelulares;
import tp.administraciones.AdministracionDeEstacionamientos;
import tp.apps.AppUsuario;
import tp.compras.Compra;

class SemTest {
	
	// Set up 
	// Constucción del Sem a traves del constructor complejo
	AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
	ArrayList<PuntoDeVenta> puntosDeVenta = spy(new ArrayList<PuntoDeVenta>());
	AdministracionDeEstacionamientos admEstacionamientos = mock(AdministracionDeEstacionamientos.class);
	ArrayList<Compra> compras = spy(new ArrayList<Compra>());
	ArrayList<Infraccion> infracciones = spy(new ArrayList<Infraccion>());
	
	Sem sem = new Sem(admCel, puntosDeVenta, admEstacionamientos, compras, infracciones, 40.00, LocalTime.of(20, 0));
	

	// Test del constructor simple
	@Test
	void testSem() {
		Sem semSimple = new Sem(40.00, LocalTime.of(20, 0));
		assertNotNull(semSimple);
	}
	
	// Test del constructor complejo
	@Test
	void testSemComplejo() {
		Sem sem = new Sem(40.00, LocalTime.of(20, 0));
		assertNotNull(sem);
	}
	
	// Test getHoraDeFinDeFranja
	@Test
	void testGetHoraDeFinDeFranja() {
		assertEquals(LocalTime.of(20, 0), sem.getHoraDeFinDeFranja());
	}
	
	// Test setHoraDeFinDeFranja
	@Test
	void testsetHoraDeFinDeFranja() {
		//Set up
		LocalTime nuevaHora = LocalTime.of(22, 0);
		
		//Excercise
		sem.setHoraDeFinDeFranja(nuevaHora);
		
		//Verify
		assertEquals(LocalTime.of(22, 0), sem.getHoraDeFinDeFranja());
		verify(admEstacionamientos).setFinDeFranja(nuevaHora);
	}
	
	// Test getPrecioDeEstacionamientos
	@Test
	void testGetPrecioDeEstacionamientos() {
		assertEquals(40.00, sem.getPrecioDeEstacionamientos(),0);
	}
	
	// Test setHoraDeFinDeFranja
	@Test
	void testsetPrecioDeEstacionamientos() {
		sem.setPrecioDeEstacionamientos(30.00);
		assertEquals(30.00, sem.getPrecioDeEstacionamientos(),0);
	}
	
	// Test addPuntoDeVenta
	@Test
	void testAddPuntoDeVenta() {
		//Set up
		PuntoDeVenta puntoDeVenta = mock(PuntoDeVenta.class);
		
		// Excercise
		sem.addPuntoDeVenta(puntoDeVenta);
		
		//Verify
		verify(puntosDeVenta).add(puntoDeVenta);
	}
	
	// Test iniciarEstacionamiento puntual
	@Test
	void testIniciarEstacionamientoPuntual() {
		//Excercise
		sem.iniciarEstacionamiento("ABC-123", 5);
		
		//Verify
		verify(admEstacionamientos).agregarEstacionamiento("ABC-123", 5);;
	}
	
	// Test iniciarEstacionamiento por app exitoso
	@Test
	void testIniciarEstacionamientoPorAppExitoso() {
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15222444);
		when(admCel.consultarCredito(15222444)).thenReturn(50.00);
		
		//Excercise
		sem.iniciarEstacionamiento("ABC-123", app);
		
		//Verify
		verify(admEstacionamientos).agregarEstacionamiento("ABC-123", 40.00,app,admCel);
	}
	
	// Test iniciarEstacionamiento por app fallido por falta de credito
	@Test
	void testIniciarEstacionamientoPorAppFallido() {
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15222444);
		when(admCel.consultarCredito(15222444)).thenReturn(20.00);
		
		//Excercise
		sem.iniciarEstacionamiento("ABC-123", app);
		
		//Verify
		verify(app).informar("Saldo insuficiente. Estacionamiento no permitido");
	}
	
	// Test registrarCompra
	@Test
	void testRegistrarCompra() {
		//Set up
		Compra compra = mock(Compra.class);
		
		//Excercise
		sem.registrarCompra(compra,(long) 15222,40.0);
		
		//Verify
		verify(compras).add(compra);
		verify(admCel).cargarCredito((long) 15222, 40.0);
	}
	
	// Test registrarCompra2
	@Test
	void testRegistrarCompra2() {
		//Set up
		Sem semSpy = spy(sem);
		Compra compra = mock(Compra.class);
		
		//Excercise
		semSpy.registrarCompra(compra,"abc-123",3);
		
		//Verify
		verify(compras).add(compra);
		verify(semSpy).iniciarEstacionamiento("abc-123", 3);
	}
	
	// Test cargarCredito
	@Test
	void testCargarCredito() {
		//Excercise
		sem.cargarCredito(15222444, 40.00);
		
		//Verify
		verify(admCel).cargarCredito(15222444, 40.00);
	}
	
	// Test consultarCredito
	@Test
	void testConsultarCredito() {
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		when(app.getNumeroAsociado()).thenReturn((long) 15222);
		when(admCel.consultarCredito(15222)).thenReturn(20.00);
	
		//Excercise
		sem.consultarCredito(app);
		
		//Verify
		verify(app).informar("Su saldo es:" + 20.00);
	}
	
	
	// Test FinalizarEstacionamiento
	@Test
	void testFinalizarEstacionamiento() {
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		
		//Excercise
		sem.finalizarEstacionamiento(app);
		
		//Verify
		verify(admEstacionamientos).finalizarEstacionamiento(app, admCel);
	}
	
	// Test dispararFinDeEstacionamientos
	@Test
	void testDispararFinDeEstacionamientos() throws Exception{
		//Excercise
		sem.dispararFinDeEstacionamientos();
		
		//Verify
		verify(admEstacionamientos).dispararFinDeEstacionamientos();
	}
	
	// Test registrarInfraccion
	@Test
	void testRegistrarInfraccion(){
		// Set up
		Infraccion infraccion = mock(Infraccion.class);
	
		//Excercise
		sem.registrarInfraccion(infraccion);
		
		//Verify
		verify(infracciones).add(infraccion);
	}
	
	// Test consultarVigencia por patente
	@Test
	void testConsultarVigenciaPatente(){
		//Set Up
		when(admEstacionamientos.consultarVigencia("ABC-123")).thenReturn(true);
		
		//Excercise
		sem.consultarVigencia("ABC-123");
		
		//Verify
		verify(admEstacionamientos).consultarVigencia("ABC-123");
		assertTrue(admEstacionamientos.consultarVigencia("ABC-123"));
	}
	
	// Test consultarVigencia por app
	@Test
	void testConsultarVigenciaApp(){
		//Set up
		AppUsuario app = mock(AppUsuario.class);
		when(admEstacionamientos.consultarVigencia(app)).thenReturn(true);
		
		//Excercise
		sem.consultarVigencia(app);
		
		//Verify
		verify(admEstacionamientos).consultarVigencia(app);
		assertTrue(admEstacionamientos.consultarVigencia(app));
	}
	
} 

