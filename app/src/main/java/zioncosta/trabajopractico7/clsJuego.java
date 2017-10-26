package zioncosta.trabajopractico7;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class clsJuego
{
   CCGLSurfaceView _VistaDelJuego;
   CCSize PantallaDelDevice;
   Sprite Androidcito;
   Sprite Apple;
   Sprite Fondo;
   boolean AndroidcitoToqueteado;
   boolean AppleToqueteado;
   
   public clsJuego(CCGLSurfaceView VistaDelJuego)
   {
	  _VistaDelJuego = VistaDelJuego;
   }
   
   public void ComenzarJuego()
   {
	  Director.sharedDirector().attachInView(_VistaDelJuego);
	  PantallaDelDevice = Director.sharedDirector().displaySize();
	  Director.sharedDirector().runWithScene(EscenaDelJuego());
	  AndroidcitoToqueteado = false;
	  AppleToqueteado = false;
   }
   
   private Scene EscenaDelJuego()
   {
	  Scene EscenaADevolver = Scene.node();
	  
	  CapaDelFondo MiCapaDelFondo = new CapaDelFondo();
	  
	  CapaDelFrente MiCapaDelFrente = new CapaDelFrente();
	  
	  EscenaADevolver.addChild(MiCapaDelFondo, -10);
	  EscenaADevolver.addChild(MiCapaDelFrente, 10);
	  
	  return EscenaADevolver;
   }
   
   class CapaDelFondo extends Layer
   {
	  public CapaDelFondo()
	  {
		 PonerImagenFondo();
	  }
	  
	  private void PonerImagenFondo()
	  {
		 Fondo = Sprite.sprite("samsungs8.png");
		 Float FactorAncho, FactorAlto;
		 FactorAncho = PantallaDelDevice.width / Fondo.getWidth();
		 FactorAlto = PantallaDelDevice.height / Fondo.getHeight();
		 
		 Fondo.setPosition(PantallaDelDevice.width / 2, PantallaDelDevice.height / 2);
		 Fondo.runAction(ScaleBy.action(0.01f, FactorAncho, FactorAlto));
		 super.addChild(Fondo);
	  }
   }
   
   class CapaDelFrente extends Layer
   {
	  public CapaDelFrente()
	  {
		 PonerSprites();
		 this.setIsTouchEnabled(true);
	  }
	  
	  private void PonerSprites()
	  {
		 Androidcito = Sprite.sprite("android.png");
		 Apple = Sprite.sprite("apple.png");
		 
		 Random GeneradorDePosiciones = new Random();
		 do
		 {
			CCPoint PosicionInicial1 = new CCPoint();
			CCPoint PosicionInicial2 = new CCPoint();
			Float AlturaSprite1, AnchuraSprite1, AlturaSprite2, AnchuraSprite2;
			
			AnchuraSprite1 = Androidcito.getWidth();
			AlturaSprite1 = Androidcito.getHeight();
			
			AnchuraSprite2 = Apple.getWidth();
			AlturaSprite2 = Apple.getHeight();
			
			PosicionInicial1.x = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.width - AnchuraSprite1));
			PosicionInicial1.x += AnchuraSprite1 / 2;
			PosicionInicial1.y = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.height - AlturaSprite1));
			PosicionInicial1.y += AlturaSprite1 / 2;
			
			PosicionInicial2.x = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.width - AnchuraSprite2));
			PosicionInicial2.x += AnchuraSprite2 / 2;
			PosicionInicial2.y = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.height - AlturaSprite2));
			PosicionInicial2.y += AlturaSprite2 / 2;
			
			Androidcito.setPosition(PosicionInicial1.x, PosicionInicial1.y);
			Apple.setPosition(PosicionInicial2.x, PosicionInicial2.y);
			
		 } while (InterseccionEntreSprites(Androidcito, Apple));
		 super.addChild(Androidcito);
		 super.addChild(Apple);
		 
	  }
	  
	  @Override
	  public boolean ccTouchesBegan(MotionEvent event)
	  {
		 Log.d("Toque comienza", "X: " + event.getX() + " - Y: " + event.getY());
		 //Ahora me fijo cuál de los dos sprites toca el usuario
		 if (EstaEntre((int) event.getX(),
				  (int) (Androidcito.getPositionX() - Androidcito.getWidth() / 2),
				  (int) (Androidcito.getPositionX() + Androidcito.getWidth() / 2)))
		 {
			if (EstaEntre((int) (PantallaDelDevice.getHeight() - event.getY() - Androidcito.getHeight() / 2),
					 (int) (Androidcito.getPositionY() - Androidcito.getHeight() / 2),
					 (int) (Androidcito.getPositionY() + Androidcito.getHeight() / 2)))
			{
			   AndroidcitoToqueteado = true;
			}
		 }
		 
		 if (EstaEntre((int) event.getX(),
				  (int) (Apple.getPositionX() - Apple.getWidth() / 2),
				  (int) (Apple.getPositionX() + Apple.getWidth() / 2)))
		 {
			if (EstaEntre((int) (PantallaDelDevice.getHeight() - event.getY() - Apple.getHeight() / 2),
					 (int) (Apple.getPositionY() - Apple.getHeight() / 2),
					 (int) (Apple.getPositionY() + Apple.getHeight() / 2)))
			{
			   AppleToqueteado = true;
			}
		 }
		 return true;
	  }
	  
	  @Override
	  public boolean ccTouchesMoved(MotionEvent event)
	  {
		 Log.d("Toque mueve", "X: " + event.getX() + " - Y: " + event.getY());
		 if (AndroidcitoToqueteado)
		 {
			MoverSprite(Androidcito, event.getX(), PantallaDelDevice.getHeight() - event.getY());
		 }
		 if (AppleToqueteado)
		 {
			MoverSprite(Apple, event.getX(), PantallaDelDevice.getHeight() - event.getY());
		 }
		 if (InterseccionEntreSprites(Androidcito, Apple))
		 {
			IntervalAction Intervalo = Sequence.actions(MoveBy.action(0.25f, 150f, 0f), MoveBy.action(0.25f, 0f, -150f), MoveBy.action(0.25f, -150f, 0f),
					 					MoveBy.action(0.25f, 0f, 150f));
			
			float NoTeVayasHorizontal, NoTeVayasVertical;
			if (AndroidcitoToqueteado)
			{
			   Apple.runAction(Intervalo);
			   NoTeVayasHorizontal = Apple.getPositionX();
			   NoTeVayasVertical = Apple.getPositionY();
			   MantenerDentroDeLaPantalla(Apple, NoTeVayasHorizontal, NoTeVayasVertical);
			}
			else
			{
			   Androidcito.runAction(Intervalo);
			   NoTeVayasHorizontal = Androidcito.getPositionX();
			   NoTeVayasVertical = Androidcito.getPositionY();
			   MantenerDentroDeLaPantalla(Androidcito, NoTeVayasHorizontal, NoTeVayasVertical);
			}
		 }
		 return true;
	  }
	  
	  void MoverSprite(Sprite SpriteAMover, float DestinoX, float DestinoY)
	  {
		 SpriteAMover.setPosition(DestinoX, DestinoY);
		 MantenerDentroDeLaPantalla(SpriteAMover, DestinoX, DestinoY);
	  }
	  
	  void MantenerDentroDeLaPantalla(Sprite SpriteCheto, float PosicionFinalX, float PosicionFinalY)
	  {
		 if (PosicionFinalX < SpriteCheto.getWidth() / 2)
		 {
			//Se fue a la izquierda
			PosicionFinalX = SpriteCheto.getWidth() / 2;
		 }
		 if (PosicionFinalX > PantallaDelDevice.getWidth() - SpriteCheto.getWidth() / 2)
		 {
			//Se fue a la derecha
			PosicionFinalX = PantallaDelDevice.getWidth() - SpriteCheto.getWidth() / 2;
		 }
		 if (PosicionFinalY < SpriteCheto.getHeight() / 2)
		 {
			//Se fue por abajo
			PosicionFinalY = SpriteCheto.getHeight() / 2;
		 }
		 if (PosicionFinalY > PantallaDelDevice.getHeight() - SpriteCheto.getHeight() / 2)
		 {
			//Se fue por arriba
			PosicionFinalY = PantallaDelDevice.getHeight() - SpriteCheto.getHeight() / 2;
		 }
		 SpriteCheto.setPosition(PosicionFinalX, PosicionFinalY);
	  }
	  
	  @Override
	  public boolean ccTouchesEnded(MotionEvent event)
	  {
		 Log.d("Toque termina", "X: " + event.getX() + " - Y: " + event.getY());
		 AndroidcitoToqueteado = false;
		 AppleToqueteado = false;
		 return true;
	  }
	  
	  
   }
   
   boolean InterseccionEntreSprites(Sprite Sprite1, Sprite Sprite2)
   {
	  int Sprite1Izquierda, Sprite1Derecha, Sprite1Arriba, Sprite1Abajo;
	  int Sprite2Izquierda, Sprite2Derecha, Sprite2Arriba, Sprite2Abajo;
	  
	  Sprite1Izquierda = (int) (Sprite1.getPositionX() - Sprite1.getWidth() / 2);
	  Sprite1Derecha = (int) (Sprite1.getPositionX() + Sprite1.getWidth() / 2);
	  Sprite1Abajo = (int) (Sprite1.getPositionY() - Sprite1.getHeight() / 2);
	  Sprite1Arriba = (int) (Sprite1.getPositionY() + Sprite1.getHeight() / 2);
	  
	  Sprite2Izquierda = (int) (Sprite2.getPositionX() - Sprite2.getWidth() / 2);
	  Sprite2Derecha = (int) (Sprite2.getPositionX() + Sprite2.getWidth() / 2);
	  Sprite2Abajo = (int) (Sprite2.getPositionY() - Sprite2.getHeight() / 2);
	  Sprite2Arriba = (int) (Sprite2.getPositionY() + Sprite2.getHeight() / 2);
	  
	  if (EstaEntre(Sprite1Izquierda, Sprite2Izquierda, Sprite2Derecha) &&
			   EstaEntre(Sprite1Abajo, Sprite2Arriba, Sprite2Abajo))
	  {
		 //Borde inferior izquierdo del Sprite1, dentro del Sprite2
		 return true;
	  }
	  if (EstaEntre(Sprite1Izquierda, Sprite2Izquierda, Sprite2Derecha) &&
			   EstaEntre(Sprite1Arriba, Sprite2Abajo, Sprite2Arriba))
	  {
		 //Borde superior izquierdo del Sprite1, dentro del Sprite2
		 return true;
	  }
	  if (EstaEntre(Sprite1Derecha, Sprite2Izquierda, Sprite2Derecha) &&
			   EstaEntre(Sprite1Abajo, Sprite2Abajo, Sprite2Arriba))
	  {
		 //Borde inferior derecho del Sprite1, dentro del Sprite2
		 return true;
	  }
	  if (EstaEntre(Sprite1Derecha, Sprite2Izquierda, Sprite2Derecha) &&
			   EstaEntre(Sprite1Arriba, Sprite2Abajo, Sprite2Arriba))
	  {
		 //Borde superior derecho del Sprite1, dentro del Sprite2
		 return true;
	  }
   
	  /*----YA SIENTO EL CAMBIO----*/
	  
	  if (EstaEntre(Sprite2Izquierda, Sprite1Izquierda, Sprite1Derecha) &&
			   EstaEntre(Sprite2Abajo, Sprite1Arriba, Sprite1Abajo))
	  {
		 //Borde inferior izquierdo del Sprite2, dentro del Sprite1
		 return true;
	  }
	  if (EstaEntre(Sprite2Izquierda, Sprite1Izquierda, Sprite1Derecha) &&
			   EstaEntre(Sprite2Arriba, Sprite1Abajo, Sprite1Arriba))
	  {
		 //Borde superior izquierdo del Sprite2, dentro del Sprite1
		 return true;
	  }
	  if (EstaEntre(Sprite2Derecha, Sprite1Izquierda, Sprite1Derecha) &&
			   EstaEntre(Sprite2Abajo, Sprite1Abajo, Sprite1Arriba))
	  {
		 //Borde inferior derecho del Sprite2, dentro del Sprite1
		 return true;
	  }
	  if (EstaEntre(Sprite2Derecha, Sprite1Izquierda, Sprite1Derecha) &&
			   EstaEntre(Sprite2Arriba, Sprite1Abajo, Sprite1Arriba))
	  {
		 //Borde superior derecho del Sprite2, dentro del Sprite1
		 return true;
	  }
	  
	  else
	  {
		 return false;
	  }
   }
   
   boolean EstaEntre(int NumeroAComparar, int NumeroMenor, int NumeroMayor)
   {
	  if (NumeroMenor > NumeroMayor)
	  {
		 int Auxiliar = NumeroMayor;
		 NumeroMayor = NumeroMenor;
		 NumeroMenor = Auxiliar;
	  }
	  if (NumeroAComparar >= NumeroMenor && NumeroAComparar <= NumeroMayor)
	  {
		 return true;
	  }
	  else
	  {
		 return false;
	  }
   }
}
