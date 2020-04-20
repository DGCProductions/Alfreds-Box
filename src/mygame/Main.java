package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.Display;
/*
Ludum Dare 46
Theme: Keep it alive

Warning: The code in this project is VERY bad. 
Don't bother to use my project as an example, it will just make your work worse.
*/
public class Main extends SimpleApplication {

   
    MapAppState mapAppState;
    ShootAppState shootAppState;
    SoundAppState soundAppState;
    LogicAppState logicAppState;
    HudAppState hudAppState;
    EnemyAppState enemyAppState;
    public static void main(String[] args) throws IOException {
        AppSettings settings = new AppSettings(true);
        
        settings.setTitle("Alfred's Box");
        settings.setFullscreen(false);
        settings.setResolution(640, 480);
        settings.setVSync(true);
        BufferedImage[] icons = new BufferedImage[] {
        ImageIO.read( Main.class.getResource("256x.jpg" ) ),
        ImageIO.read( Main.class.getResource("128x.jpg" ) ),
        ImageIO.read( Main.class.getResource( "32x.jpg") ),
        ImageIO.read( Main.class.getResource( "16x.jpg" ) )
          };
        settings.setIcons(icons);
             
        Main app = new Main();
        app.setPauseOnLostFocus(false);
        app.setSettings(settings);
        
        app.showSettings = false;
        app.start();
    }

    @Override
    public void simpleInitApp() {
       setDisplayFps(false);
       setDisplayStatView(false);
       ScreenshotAppState screenShotState = new ScreenshotAppState();
       this.stateManager.attach(screenShotState);
       mapAppState = new MapAppState();
       stateManager.attach(mapAppState);
       
       shootAppState = new ShootAppState();
       stateManager.attach(shootAppState);
       
       soundAppState = new SoundAppState();
       stateManager.attach(soundAppState);
       
       logicAppState = new LogicAppState();
       stateManager.attach(logicAppState);
       
       hudAppState = new HudAppState();
       stateManager.attach(hudAppState);
       
       enemyAppState = new EnemyAppState();
       stateManager.attach(enemyAppState);
       
       flyCam.setMoveSpeed(20);
       cam.setLocation(new Vector3f(-13.763247f, 6.4726195f, -0.24625406f));
       cam.setRotation(new Quaternion(0.14883424f, 0.69083345f, -0.14861742f, 0.69174445f));
       flyCam.setEnabled(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
