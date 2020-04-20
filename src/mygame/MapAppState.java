package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;


public class MapAppState extends AbstractAppState implements PhysicsCollisionListener {

    private Ray ray = new Ray();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    Node map;
    Spatial box;
    BulletAppState bulletAppState;
    RigidBodyControl landscape;
    float ithp = 100;
    boolean takingdamage = false;
    @Override
    
    //holy moly, why did i not just make appstate variables, wouldn've been so much easier, but NOOOOOOOOO
    public void update(float tpf) {
        System.out.println(ithp);
        
        for (int c = 0; c < app.getStateManager().getState(EnemyAppState.class).enemies.getQuantity(); c++) {
            
            if(app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class).getPhysicsLocation().distance(new Vector3f(0, 1, 0)) <= 2f) {
                takingdamage = true;
                ithp = ithp - tpf *50;
                app.getStateManager().getState(SoundAppState.class).zap.play();
            } else {
                takingdamage = false;
            }
        }
        if(ithp <= 0) {
             for (int c = 0; c < app.getStateManager().getState(EnemyAppState.class).enemies.getQuantity(); c++) {
                 app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class).setEnabled(false);
             }
              for (int d = 0; d < app.getStateManager().getState(ShootAppState.class).bullets.getQuantity(); d++) {
                 app.getStateManager().getState(ShootAppState.class).bullets.getChild(d).getControl(GhostControl.class).setEnabled(false);
             }
              app.getStateManager().getState(SoundAppState.class).music.stop();
            app.getStateManager().getState(EnemyAppState.class).enemies.detachAllChildren();
             app.getStateManager().getState(EnemyAppState.class).restartStats();
            app.getStateManager().getState(ShootAppState.class).bullets.detachAllChildren();
            ithp = 100;
            app.getStateManager().getState(HudAppState.class).dialnum = 1;
            app.getStateManager().getState(LogicAppState.class).stage = 1;
        }
        
        
        
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.cam = this.app.getCamera();
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(.5f));
        rootNode.addLight(al);
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);

        /* Drop shadows */
        final int SHADOWMAP_SIZE = 1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        dlsr.setShadowIntensity(0.1f);
        app.getViewPort().addProcessor(dlsr);

        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
         app.getViewPort().addProcessor(fpp);
        
        
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //bulletAppState.setDebugEnabled(true);
        map = (Node) assetManager.loadModel("Models/maptest-2.j3o");
        Node map2 = (Node) assetManager.loadModel("Models/maptest-2_1.j3o");
        rootNode.attachChild(map2);
        
        CollisionShape sceneShape
                = CollisionShapeFactory.createMeshShape(map);
        landscape = new RigidBodyControl(sceneShape, 0);
        map.addControl(landscape);
        
        rootNode.attachChild(map);
        bulletAppState.getPhysicsSpace().add(landscape);
        
        box = assetManager.loadModel("Models/thebox.j3o");
        box.setLocalTranslation(0, .8f, 0);
        box.scale(.5f);
        rootNode.attachChild(box);
        
      
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    public void collision(PhysicsCollisionEvent pce) {
        PhysicsCollisionObject a = pce.getObjectA();
        PhysicsCollisionObject b = pce.getObjectB();
        for (int i = 0; i < app.getStateManager().getState(ShootAppState.class).bullets.getQuantity(); i++) {

            

            if (a == app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class) && b == landscape || a == landscape && b == app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class)) {
                app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class).setEnabled(false);
                app.getStateManager().getState(ShootAppState.class).bullets.detachChildAt(i);
            }
            try {
                for (int c = 0; c < app.getStateManager().getState(EnemyAppState.class).enemies.getQuantity(); c++) {
                    
                    
                    if (a == app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class) && b == app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class) || a == app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class) && b == app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class)) {
                        app.getStateManager().getState(EnemyAppState.class).makeExplosion(app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class).getPhysicsLocation());

                        app.getStateManager().getState(ShootAppState.class).bullets.getChild(i).getControl(GhostControl.class).setEnabled(false);
                        app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class).setEnabled(false);
                        app.getStateManager().getState(EnemyAppState.class).enemies.detachChildAt(c);
                        app.getStateManager().getState(ShootAppState.class).bullets.detachChildAt(i);
                        app.getStateManager().getState(SoundAppState.class).boom.playInstance();
                        
                    }
                    
                     
                }
            } catch (Exception e) {

            }
        }
       
    }
    @Override
    public void cleanup() {
        super.cleanup();
        
    }

}