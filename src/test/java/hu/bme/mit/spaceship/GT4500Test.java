package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

	private TorpedoStore primaryTorpedoStoreMock;
	private TorpedoStore secondaryTorpedoStoreMock;
  private GT4500 ship;

  @Before
  public void init(){
	  primaryTorpedoStoreMock = mock(TorpedoStore.class);
	  secondaryTorpedoStoreMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryTorpedoStoreMock, secondaryTorpedoStoreMock);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(1)).isEmpty();
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStoreMock, times(1)).isEmpty();
    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
    verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }

  
  @Test
  public void fireTorpedos_AllEmpty() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
	  
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE);
	  
	  assertEquals(false, result);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(0)).fire(1);
	  verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedos_Twice_WithCooling() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  
	  boolean firstShootResult = ship.fireTorpedos(FiringMode.SINGLE);

	  assertEquals(true, firstShootResult);	
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(1)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(0)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
	  
	  boolean secondShootResult = ship.fireTorpedos(FiringMode.SINGLE);

	  assertEquals(true, secondShootResult);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(1)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(1)).fire(1);
	  
  }
  
  @Test
  public void fireTorpedos_firstTorpedoShootsFirst() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE);
	  
	  assertEquals(true, result);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(1)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(0)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedos_firstTorpedoIsEmpty() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE);
	  
	  assertEquals(true, result);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(0)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(1)).fire(1);
  }
  
  @Test
  public void fireTorpedos_firstTorpedoFails_then_secondWontBeTried() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(false);
	  
	  boolean result = ship.fireTorpedos(FiringMode.SINGLE);
	  
	  assertEquals(false, result);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(1)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(0)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedos_ALL_OneTorpedoStoreIsEmpty() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  
	  boolean result = ship.fireTorpedos(FiringMode.ALL);
	  
	  assertEquals(false, result);
	  verify(primaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(0)).fire(1);  
	  //verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedos_secondaryIsEmpty_TryFirstAgain() {
	  when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
	  when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
	  when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);

	  boolean firstShootResult = ship.fireTorpedos(FiringMode.SINGLE);
	  boolean secondShootResult = ship.fireTorpedos(FiringMode.SINGLE);

	  assertEquals(true, firstShootResult);
	  assertEquals(true, secondShootResult);
	  verify(primaryTorpedoStoreMock, times(2)).isEmpty();
	  verify(primaryTorpedoStoreMock, times(2)).fire(1);  
	  verify(secondaryTorpedoStoreMock, times(1)).isEmpty();
	  verify(secondaryTorpedoStoreMock, times(0)).fire(1);
  }
}
