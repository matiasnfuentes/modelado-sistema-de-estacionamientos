package tp.administraciones;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import tp.Celular;
import tp.monitoreoDeEstacionamientos.AlarmaEstacionamiento;

class AdministracionDeCelularesTest {
	
	
	ArrayList<Celular> celulares = new ArrayList<Celular>();
	AlarmaEstacionamiento alarma = mock(AlarmaEstacionamiento.class);
	Celular celular = mock(Celular.class);
	

	//Test del constructor
	@Test
	public void testConstructor() {
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		assertNotNull(admCel);
	} 
	
	
	//Test agregarCelular
	@Test
	public void testAgregarCelular(){
		//Set up
		ArrayList<Celular> mockCelulares = mock(ArrayList.class);
		AdministracionDeCelulares admCelu = new AdministracionDeCelulares(mockCelulares,alarma);
		Celular nuevoCel = mock(Celular.class);
		
		//Excercise
		admCelu.agregarCelular(nuevoCel);
		
		//Set up
		verify(mockCelulares).add(nuevoCel);
	} 
	
	//Test buscarCelular exitoso
	@Test
	public void testBuscarCelularExitoso(){
		// Set up
		celulares.add(celular);
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		when(celular.getNro()).thenReturn((long)15222);
		
		//Excersice 
		Celular celularBuscado = admCel.buscarCelular(15222);
		
		// Verify
		assertTrue(celularBuscado==celular);
	}
	
	//Test buscarCelularNuevo  (no fue agregado anteriormente)
	@Test
	public void testBuscarCelularNuevo(){
		//set up
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		
		//Excersice 
		Celular celularBuscado = admCel.buscarCelular(15555);
		
		// Verify
		assertNotNull(celularBuscado);
		assertEquals(15555, celularBuscado.getNro());
	} 
	
	//Test consultarCredito
	@Test
	public void testConsultaCreditoExitoso(){
		// SetUp 
		celulares.add(celular);
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		when(celular.getNro()).thenReturn((long) 15222);
		
		admCel.consultarCredito(15222);
		verify(celular).getSaldo();
	} 
	
	//Test consultarFallido
	@Test
	public void testConsultaCreditoFallido(){
		// SetUp 
		celulares.add(celular);
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		when(celular.getNro()).thenReturn((long) 15222);
		
		admCel.consultarCredito(15223);
		assertEquals(0, admCel.consultarCredito(15223),0);
	} 
	
	
	//Test cargarCredito a celularExistente
	@Test
    public void testCargarCreditoACelularExistente(){
		// Set up 
		celulares.add(celular);
		AdministracionDeCelulares admCel = new AdministracionDeCelulares(celulares,alarma);
		when(celular.getNro()).thenReturn((long) 15222);
		
		// Excercise
		admCel.cargarCredito(15222, 20.00);
		
		//Verify
		verify(celular).cargarSaldo(20.00);
		verify(alarma).notificar((long) 15222, 20.00, admCel);
	} 
	
	//Test cargarCredito a celularNuevo
	@Test
    public void testCargarCreditoACelularNuevo(){
		// Set Up
		AdministracionDeCelulares admCel = spy(new AdministracionDeCelulares(celulares,alarma));
		
		// Excercise
		admCel.cargarCredito(15222, 20.00);
		
		//Verify
		verify(admCel).agregarCelular(any(Celular.class));
		verify(alarma).notificar((long) 15222, 20.00, admCel);
	} 
	
	//Test descontarCredito
	@Test
	public void testDescontarCredito() throws Exception{
		// Excercise
		celulares.add(celular);
		AdministracionDeCelulares admCel = spy(new AdministracionDeCelulares(celulares,alarma));
		when(celular.getNro()).thenReturn((long) 15222);
		
		// Excercise
		admCel.descontarCredito(15222, 40.00);
		
		//Verify
		verify(celular).descontarSaldo(40.00);
	} 
	

}