package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.List;


public class ShootAppState extends AbstractAppState implements ActionListener {

    private Ray ray = new Ray();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    Node bullets;
    @Override
    public void update(float tpf) {
        
        
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.cam = this.app.getCamera();
        this.inputManager = this.app.getInputManager();
        
        initKeys();
        bullets = new Node("buls");
        rootNode.attachChild(bullets);
       
    }

   
    
    @Override
    public void cleanup() {
        super.cleanup();
        
    }

    private void initKeys() {
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Shoot");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Shoot") && !isPressed) {
            if(app.getStateManager().getState(LogicAppState.class).stage == 1) {
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                
                CollisionResults results = new CollisionResults();

                Ray ray = new Ray(new Vector3f(click2d.x, click2d.y, 0f), dir);

                app.getGuiNode().collideWith(ray, results);

                
             

                if (results.size() > 0) {

                    CollisionResult closest = results.getClosestCollision();
                    String hit = closest.getGeometry().getName();
                    if (hit == "startgame") {
                       app.getStateManager().getState(LogicAppState.class).stage = 2;
                       System.out.println("dasdasfasfadfas");
                       app.getStateManager().getState(SoundAppState.class).phonePHONE.play();
                       //ah yes, the most advanced debug print, dasdasfasfadfas.
                    } 

                }
            }
            
           
            
            if(app.getStateManager().getState(LogicAppState.class).stage == 3) {
                
                Vector3f origin1 = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
                Vector3f direction1 = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
                direction1.subtractLocal(origin1).normalizeLocal();
                
                Ray ray = new Ray(origin1, direction1);
                CollisionResults results = new CollisionResults();
                app.getStateManager().getState(MapAppState.class).map.collideWith(ray, results);
                if (results.size() > 0) {
                    CollisionResult closest = results.getClosestCollision();
                    
                    Box b = new Box(1, 1, 1);
                    Geometry geom = new Geometry("Box", b);

                    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                    mat.setColor("Color", ColorRGBA.Yellow);
                    geom.setMaterial(mat);
                    geom.scale(.1f);
                    geom.setShadowMode(RenderQueue.ShadowMode.Cast);
                    geom.setLocalTranslation(cam.getLocation());

                    Quaternion a = new Quaternion();
                    a.fromAngleAxis(FastMath.QUARTER_PI, Vector3f.UNIT_X);
                    
                    geom.lookAt(closest.getContactPoint(), Vector3f.UNIT_Y);

                    BoxCollisionShape bull = new BoxCollisionShape(geom.getLocalScale());
                    GhostControl bul = new GhostControl(bull);

                    bul.setPhysicsRotation(geom.getLocalRotation());
                    bul.setPhysicsLocation(geom.getLocalTranslation());
                    geom.addControl(bul);

                    bullets.attachChild(geom);
                    geom.addControl(new ShootControl(geom.getWorldRotation().mult(Vector3f.UNIT_Z)));
                  
                    app.getStateManager().getState(MapAppState.class).bulletAppState.getPhysicsSpace().add(bul);
                    app.getStateManager().getState(SoundAppState.class).gunpow.playInstance();
                    
                  
                    System.out.println(bullets.getChildren());
                }
               
                
                
               
                    /*
                    originally, the game was going to have a raycast based gun, but i decided to swap it out with a physics-based gun for dificulty and other issues 
                    */
                    
                     /*
                    Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
                    Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
                    direction.subtractLocal(origin).normalizeLocal();

                    Ray ray = new Ray(origin, direction);
                    CollisionResults results = new CollisionResults();
                    app.getStateManager().getState(MapAppState.class).map.collideWith(ray, results);

                    if (results.size() > 0) {
                        CollisionResult closest = results.getClosestCollision();
                        Spatial geo = assetManager.loadModel("Models/bullethole.j3o");
                        rootNode.attachChild(geo);
                        geo.scale(.5f);
                        Quaternion q = new Quaternion();
                        q.lookAt(closest.getContactNormal(), Vector3f.UNIT_Y);
                        geo.setLocalRotation(q);

                        geo.setLocalTranslation(closest.getContactPoint());

                        geo.addControl(new Bulletclass());
                        geo.move(closest.getContactNormal().mult(0.0001f));
                        app.getStateManager().getState(SoundAppState.class).gunpow.playInstance();

                        rootNode.attachChild(geo);

                        ParticleEmitter debris
                                = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
                        Material debris_mat = new Material(assetManager,
                                "Common/MatDefs/Misc/Particle.j3md");
                        debris_mat.setTexture("Texture", assetManager.loadTexture(
                                "Textures/debris2.png"));

                        debris_mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                        debris.setMaterial(debris_mat);
                        debris.setImagesX(3);
                        debris.setImagesY(3); // 3x3 texture animation
                        debris.setRotateSpeed(4);
                        debris.setSelectRandomImage(true);
                        debris.getParticleInfluencer().setInitialVelocity(closest.getContactNormal().mult(3));
                        debris.setFaceNormal(null);
                        debris.setCullHint(Spatial.CullHint.Never);

                        debris.setStartColor(ColorRGBA.White);
                        debris.setEndColor(ColorRGBA.White);
                        debris.setGravity(0, 0, 0);
                        debris.setLocalTranslation(geo.getLocalTranslation());
                        debris.getParticleInfluencer().setVelocityVariation(.60f);
                        debris.setStartSize(0.1f);
                        debris.setEndSize(0f);
                        debris.setHighLife(.7f);
                        debris.setLowLife(.7f);
                        debris.setQueueBucket(RenderQueue.Bucket.Transparent);
                        debris.setNumParticles(60);
                        rootNode.attachChild(debris);
                        debris.emitAllParticles();
                        debris.setInWorldSpace(true);
                        debris.setParticlesPerSec(0);

                 }
                */       
                
            }
                   
            if (app.getStateManager().getState(LogicAppState.class).stage == 2) {
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();

                CollisionResults results = new CollisionResults();

                Ray ray = new Ray(new Vector3f(click2d.x, click2d.y, 0f), dir);

                app.getGuiNode().collideWith(ray, results);

                if (results.size() > 0) {
                    // oH mY gOd wHy dIdNt U uSe aN aRrAy??!!?121
                    //oH mY gOd wHy dId U uSe pNg'S, jUsT uSe bItmApTeXt sMh
                    CollisionResult closest = results.getClosestCollision();
                    String hit = closest.getGeometry().getName();
                    if (hit == "textbox") {
                        app.getStateManager().getState(HudAppState.class).dialnum += 1;

                        if (app.getStateManager().getState(HudAppState.class).dialnum == 2) {
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue2.png", true);
                            app.getStateManager().getState(SoundAppState.class).phonePHONE.stop();
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 3) {
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue3.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 4) {
                            app.getStateManager().getState(SoundAppState.class).line1.play();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue4.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 5) {
                            app.getStateManager().getState(SoundAppState.class).line1.stop();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue5.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 6) {
                            app.getStateManager().getState(SoundAppState.class).line2.play();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue6.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 7) {
                            app.getStateManager().getState(SoundAppState.class).line2.stop();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue7.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 8) {
                            app.getStateManager().getState(SoundAppState.class).line3.play();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue8.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 9) {
                            app.getStateManager().getState(SoundAppState.class).line3.stop();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue9.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 10) {
                            app.getStateManager().getState(SoundAppState.class).line4.play();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue10.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 11) {
                            app.getStateManager().getState(SoundAppState.class).line4.stop();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue11.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 12) {
                            app.getStateManager().getState(SoundAppState.class).line5.play();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue12.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 13) {
                            app.getStateManager().getState(SoundAppState.class).line5.stop();
                            app.getStateManager().getState(HudAppState.class).textbox.setImage(assetManager, "Interface/dialogue/dialogue13.png", true);
                        }
                        if (app.getStateManager().getState(HudAppState.class).dialnum == 14) {
                            app.getStateManager().getState(SoundAppState.class).music.play();
                            app.getStateManager().getState(LogicAppState.class).stage = 3;
                            System.out.println("GDFGSFG");
                        }
                    }

                }
            }
        }
           
    }

}