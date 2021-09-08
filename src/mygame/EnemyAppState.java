package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.light.PointLight;
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
import com.jme3.shadow.PointLightShadowFilter;
import com.jme3.shadow.PointLightShadowRenderer;
import java.util.Random;


public class EnemyAppState extends AbstractAppState {

    private Ray ray = new Ray();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    private int wave = 1;
    private float constant = 2;
    Node enemies;
    private float timer = 0;
    float endtimer = 0;
    private int ememylimit = 15;
   
    
    
    public void restartStats() {
       wave = 1;
       constant = 2;
       timer = 0;
       ememylimit = 15;
       endtimer = 0;
    }
    @Override
    public void update(float tpf) {
       
        
        if(app.getStateManager().getState(LogicAppState.class).stage == 3)  {
             endtimer = endtimer + tpf;
             System.out.println(endtimer);
             if (endtimer >= 200) {
                app.getStateManager().getState(LogicAppState.class).stage = 4;
                for (int c = 0; c < app.getStateManager().getState(EnemyAppState.class).enemies.getQuantity(); c++) {
                    app.getStateManager().getState(EnemyAppState.class).enemies.getChild(c).getControl(CharacterControl.class).setEnabled(false);
                }
                for (int d = 0; d < app.getStateManager().getState(ShootAppState.class).bullets.getQuantity(); d++) {
                    app.getStateManager().getState(ShootAppState.class).bullets.getChild(d).getControl(GhostControl.class).setEnabled(false);
                }
                app.getStateManager().getState(EnemyAppState.class).enemies.detachAllChildren();
                app.getStateManager().getState(EnemyAppState.class).restartStats();
                app.getStateManager().getState(ShootAppState.class).bullets.detachAllChildren();
                app.getStateManager().getState(SoundAppState.class).escape.playInstance();
                app.getStateManager().getState(SoundAppState.class).whispers.playInstance();
                app.getStateManager().getState(SoundAppState.class).music.stop();

            }
            timer = timer + tpf;
            if(timer >= constant && enemies.getQuantity() < ememylimit) {
                constant = constant - 0.01f;
                Random random = new Random();
                int a = random.nextInt(17);
                Node monster = (Node) assetManager.loadModel("Models/techn0robot.j3o"); //the robot from this game is from techn0haunt, another game I made
                monster.setLocalScale(.5f);
                monster.rotate(0, 0, 0);
                
               // ah yes, copy and pasting
                if(a == 0) {
                    monster.setLocalTranslation(0, 2, -20);
                    
                } else if(a == 1) {
                    monster.setLocalTranslation(5, 2, -20);
                } else if(a == 2) {
                    monster.setLocalTranslation(10, 2, -20);
                } else if(a == 3) {
                    monster.setLocalTranslation(15, 2, -20);
                } else if(a == 4) {
                    monster.setLocalTranslation(20, 2, -20);
                } else if(a == 5) {
                    monster.setLocalTranslation(20, 2, -15);
                } else if(a == 6) {
                    monster.setLocalTranslation(20, 2, -10);
                } else if(a == 7) {
                    monster.setLocalTranslation(20, 2, -5);
                } else if(a == 8) {
                    monster.setLocalTranslation(20, 2, 0);
                } else if(a == 9) {
                    monster.setLocalTranslation(20, 2, 5);
                } else if(a == 10) {
                    monster.setLocalTranslation(20, 2, 10);
                } else if(a == 11) {
                    monster.setLocalTranslation(20, 2, 15);
                } else if(a == 12) {
                    monster.setLocalTranslation(20, 2, 20);
                } else if(a == 13) {
                    monster.setLocalTranslation(15, 2, 20);
                } else if(a == 14) {
                    monster.setLocalTranslation(10, 2, 20);
                } else if(a == 15) {
                    monster.setLocalTranslation(5, 2, 20);
                } else if(a == 16) {
                    monster.setLocalTranslation(0, 2, 20);
                }
                
                

                CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, .7f, 1);
                CharacterControl monster_phys = new CharacterControl(capsuleShape, 0.05f);

                monster_phys.setJumpSpeed(20f);

                monster_phys.setFallSpeed(30);
                //omfg more copy and pasting, such a waste of time
                if (a == 0) {
                    monster_phys.setPhysicsLocation(new Vector3f(0, 2, -20));

                } else if (a == 1) {
                    monster_phys.setPhysicsLocation(new Vector3f(5, 2, -20));
                } else if (a == 2) {
                    monster_phys.setPhysicsLocation(new Vector3f(10, 2, -20));
                } else if (a == 3) {
                    monster_phys.setPhysicsLocation(new Vector3f(15, 2, -20));
                } else if (a == 4) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, -20));
                } else if (a == 5) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, -15));
                } else if (a == 6) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, -10));
                } else if (a == 7) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, -5));
                } else if (a == 8) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, 0));
                } else if (a == 9) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, 5));
                } else if (a == 10) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, 10));
                } else if (a == 11) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, 15));
                } else if (a == 12) {
                    monster_phys.setPhysicsLocation(new Vector3f(20, 2, 20));
                } else if (a == 13) {
                    monster_phys.setPhysicsLocation(new Vector3f(15, 2, 20));
                } else if (a == 14) {
                    monster_phys.setPhysicsLocation(new Vector3f(10, 2, 20));
                } else if (a == 15) {
                    monster_phys.setPhysicsLocation(new Vector3f(5, 2, 20));
                } else if (a == 16) {
                    monster_phys.setPhysicsLocation(new Vector3f(0, 2, 20));
                }
                monster_phys.setGravity(new Vector3f(0, -30f, 0));
                monster.addControl(monster_phys);
                monster.addControl(new FollowControl(new Vector3f(0,0,0)));
                monster_phys.setUseViewDirection(false);
                app.getStateManager().getState(MapAppState.class).bulletAppState.getPhysicsSpace().add(monster_phys);
                enemies.attachChild(monster);
                timer = 0;
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
        enemies = new Node();
        rootNode.attachChild(enemies);
       
    }
    public void makeExplosion(Vector3f loc) {
        
         
         ParticleEmitter fire =
              new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
      Material mat_red = new Material(assetManager,
              "Common/MatDefs/Misc/Particle.j3md");
      mat_red.setTexture("Texture", assetManager.loadTexture(
              "Materials/Explosion/flame.png"));
      fire.setLocalTranslation(loc);
      fire.setMaterial(mat_red);
      fire.setImagesX(2);
      fire.setImagesY(2); // 2x2 texture animation
      fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
      fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
      fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
      fire.setStartSize(1.5f);
      fire.setEndSize(0.4f);
      fire.setGravity(0, 1, 0);
      fire.setLowLife(1f);
      fire.setHighLife(3f);
      fire.setRandomAngle(true);
      fire.getParticleInfluencer().setVelocityVariation(0.3f);
      fire.addControl(new FireControl());
      rootNode.attachChild(fire);
        
       
      } 

   
    
    @Override
    public void cleanup() {
        super.cleanup();
        
    }

}
