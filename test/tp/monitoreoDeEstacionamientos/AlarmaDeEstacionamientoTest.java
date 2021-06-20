package tp.monitoreoDeEstacionamientos;


import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;

import tp.administraciones.AdministracionDeCelulares;
import tp.apps.AppUsuario;
import tp.estacionamientos.EstacionamientoPorApp;
import tp.estacionamientos.EstacionamientoPuntual;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

class AlarmaDeEstacionamientoTest {
	
	ArrayList<Observador> observadores = spy(new ArrayList<Observador>());
	AlarmaEstacionamiento alarma = new AlarmaEstacionamiento(observadores);
	Observador observador = mock(Observador.class);
	Observador observador2 = mock(Observador.class);
	
	// Test del constructor
	@Test
	void testAlarmaEstacionamiento() {
		assertNotNull(alarma);
	}
	
	// Test agregarObservador
	@Test
	void testAgregarObservador() {
		alarma.agregarObservador(observador);
		verify(observadores).add(observador);
	}
	
	// Test eliminarObservador
	@Test
	void testEliminarObservador() {
		alarma.agregarObservador(observador);
		alarma.eliminarObservador(observador);
		verify(observadores).remove(observador);
	}
	
	// Test notificar estacionamientoPuntual
	@Test
	void testNotificarEstacionamientoPuntual() {
		// Set up
		EstacionamientoPuntual estacionamientoPuntual = mock(EstacionamientoPuntual.class);
		alarma.agregarObservador(observador);
		alarma.agregarObservador(observador2);
		
		// Excercise
		alarma.notificar(estacionamientoPuntual);
		
		//Verify
		verify(observador).actualizar(estacionamientoPuntual);
		verify(observador2).actualizar(estacionamientoPuntual);
	}
	
	// Test notificar estacionamiento por app
	@Test
	void testNotificarEstacionamientoPorApp() {
		// Set up
		AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
		AppUsuario app = mock(AppUsuario.class);
		EstacionamientoPorApp estacionamientoPorApp = mock(EstacionamientoPorApp.class);
		alarma.agregarObservador(observador);
		alarma.agregarObservador(observador2);
		
		// Excercise
		alarma.notificar(estacionamientoPorApp,app,admCel);
		
		//Verify
		verify(observador).actualizar(estacionamientoPorApp,app,admCel);
		verify(observador2).actualizar(estacionamientoPorApp,app,admCel);
	}
	
	// Test notificar recargaDeCredito
	@Test
	void testRecargaDeCredito() {
		// Set up
		AdministracionDeCelulares admCel = mock(AdministracionDeCelulares.class);
		alarma.agregarObservador(observador);
		alarma.agregarObservador(observador2);
		
		// Excercise
		alarma.notificar((long) 15222,40.00,admCel);
		
		//Verify
		verify(observador).actualizar((long) 15222,40.00,admCel);
		verify(observador2).actualizar((long) 15222,40.00,admCel);
	}

}
