package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import java.awt.Font;


public class HudAppState extends AbstractAppState   {

    private Ray ray = new Ray();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    private Node guiNode;
    Picture startgame;
    Picture white;
    Picture textbox;
    Picture logo;
    BitmapText hp;
    BitmapText timeleft;
    BitmapText endingtxt;
    float endtm = 0;
    int dialnum = 1;
    int endingscene;
    int endscene = 1;
    
    @Override
    public void update(float tpf) {
        timeleft.setText("Time left: " + (200-Math.round(app.getStateManager().getState(EnemyAppState.class).endtimer)));
        hp.setText("IT'S HP: " + Math.round(app.getStateManager().getState(MapAppState.class).ithp)); 
        //pointess prob
        if(app.getStateManager().getState(LogicAppState.class).stage == 1) {
            guiNode.detachChild(textbox);
            guiNode.detachChild(timeleft);
            guiNode.detachChild(hp);
            guiNode.attachChild(startgame);
            guiNode.attachChild(logo);
            textbox.setImage(assetManager, "Interface/dialogue/dialogue1.png", true);
            guiNode.detachChild(white);
            guiNode.detachChild(endingtxt);
        } else if(app.getStateManager().getState(LogicAppState.class).stage == 2) {
            guiNode.detachChild(startgame);
            guiNode.detachChild(timeleft);
            guiNode.detachChild(hp);
            guiNode.attachChild(textbox);
            guiNode.detachChild(white);
            guiNode.detachChild(logo);
            guiNode.detachChild(startgame);
            guiNode.detachChild(endingtxt);
        } else if (app.getStateManager().getState(LogicAppState.class).stage == 3) {
            guiNode.detachChild(startgame);
            guiNode.detachChild(textbox);
            guiNode.attachChild(timeleft);
            guiNode.attachChild(hp);
            guiNode.detachChild(white);
            guiNode.detachChild(logo);
            guiNode.detachChild(startgame);
            guiNode.detachChild(endingtxt);
        } else if (app.getStateManager().getState(LogicAppState.class).stage == 4){
            guiNode.detachChild(startgame);
            guiNode.detachChild(textbox);
            guiNode.detachChild(hp);
            guiNode.detachChild(timeleft);
            guiNode.detachChild(logo);
            guiNode.detachChild(startgame);
            guiNode.attachChild(white);
            guiNode.attachChild(endingtxt);
        }
        
        
        if (app.getStateManager().getState(LogicAppState.class).stage == 4){
            endtm = endtm + tpf;
            
            if(endtm > 2) {
                endtm = 0;
                endscene += 1;
                if(endscene == 1) {
                    endingtxt.setText("IT HAS RISEN.");
                }
                if (endscene == 2) {
                    endingtxt.setText("IT HAS ESCAPED.");
                }
                if(endscene == 3) {
                    endingtxt.setText("IT IS BEAUTIFUL.");
                }
                if(endscene == 4) {
                    endingtxt.setText("IT BLINDS US.");
                }
                if(endscene == 4) {
                    endingtxt.setText("ALL SHALL SEE IT.");
                }
                if(endscene == 5) {
                    endingtxt.setText("ALL SHALL CHERISH IT.");
                }
                if(endscene == 6) {
                    endingtxt.setText("ALL SHALL WORSHIP IT.");
                }
                if(endscene == 7) {
                    endingtxt.setText("OPEN YOUR EYES.");
                }
                if(endscene == 8) {
                    endingtxt.setText("AND SEE IT.");
                }
                if(endscene == 9) {
                    endingtxt.setText("FOR IT IS ALIVE");
                }
                if(endscene == 10) {
                    endingtxt.setText("AND IT SHALL ENCHANT US");
                }
                if(endscene == 11) {
                    endingtxt.setText("OPEN YOUR EYES");
                }
                if(endscene == 12) {
                    endingtxt.setText("IT IS ALIVE");
                }
                if(endscene == 13) {
                    app.stop();
                }
            }
        }
        
         
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.cam = this.app.getCamera();
        this.guiNode = this.app.getGuiNode();
        
        startgame = new Picture("startgame");
        startgame.setImage(assetManager, "Interface/startbutton.png", true);
        startgame.setWidth(200);
        startgame.setHeight(80);
        startgame.setPosition(0,0);
        guiNode.attachChild(startgame);
        
        logo = new Picture("logo");
        logo.setImage(assetManager, "Interface/logo.png", true);
        logo.setWidth(150);
        logo.setHeight(100);
        logo.setPosition(490,-25);
        guiNode.attachChild(logo);
        
        textbox = new Picture("textbox");
        textbox.setImage(assetManager, "Interface/dialogue/dialogue1.png", true);
        textbox.setWidth(640);
        textbox.setHeight(150);
        textbox.setPosition(0, 0);
        
        white = new Picture("bg");
        white.setImage(assetManager, "Interface/white.jpg", true);
        white.setWidth(640);
        white.setHeight(480);
        white.setPosition(0, 0);
        
        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Impact.fnt");
        hp = new BitmapText(myFont, false);
        hp.setSize(30);      // font size
        hp.setColor(ColorRGBA.Red);                             // font color
        hp.setText("IT'S HP: " + app.getStateManager().getState(MapAppState.class).ithp);             // the text
        hp.setLocalTranslation(0, 480 , 0); // position
       
        
        
        timeleft = new BitmapText(myFont, false);
        timeleft.setSize(30);      // font size
        timeleft.setColor(ColorRGBA.Red);                             // font color
        timeleft.setText("Time left: " + (200-app.getStateManager().getState(EnemyAppState.class).endtimer));             // the text
        timeleft.setLocalTranslation(0, 440 , 0); // position
        
        endingtxt = new BitmapText(myFont, false);
        endingtxt.setSize(30);      // font size
        endingtxt.setColor(ColorRGBA.Red);     
        
        endingtxt.setText("IT HAS RISEN.");            
        endingtxt.setLocalTranslation(200, 200 , 0); 
    
        
    }

   
    
    @Override
    public void cleanup() {
        super.cleanup();
        
    }

   
    

}