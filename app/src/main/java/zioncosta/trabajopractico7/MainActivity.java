package zioncosta.trabajopractico7;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity
{
   CCGLSurfaceView VistaPrincipal;
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
	  super.onCreate(savedInstanceState);
	  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	  VistaPrincipal = new CCGLSurfaceView(this);
	  setContentView(VistaPrincipal);
	  
   }
   @Override
   protected void onStart()
   {
	  super.onStart();
	  clsJuego MiJuegazo = new clsJuego(VistaPrincipal);
	  MiJuegazo.ComenzarJuego();
   }
}
