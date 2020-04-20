package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
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


public class SoundAppState extends AbstractAppState {

    private Ray ray = new Ray();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    
    AudioNode gunpow;
    AudioNode zap;
    AudioNode whispers;
    AudioNode escape;
    AudioNode boom;
    AudioNode line1;
    AudioNode line2;
    AudioNode line3;
    AudioNode line4;
    AudioNode line5;
    AudioNode music;
    AudioNode phonePHONE; //hey yo princess, pick up your own phone! i'm kinda busy saving the world over here!
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
        
        initAudio();
       
    }

   
    
    @Override
    public void cleanup() {
        super.cleanup();
        
    }

    public void initAudio() {
        
        //many reused sounds from previous games
        gunpow = new AudioNode(assetManager, "Sounds/pow.ogg", false);
        gunpow.setPositional(false);
        gunpow.setLooping(false);
        gunpow.setVolume(1);
        rootNode.attachChild(gunpow);
        
        zap = new AudioNode(assetManager, "Sounds/zap.ogg", false);
        zap.setPositional(false);
        zap.setLooping(false);
        zap.setVolume(1);
        rootNode.attachChild(zap);
        
        whispers = new AudioNode(assetManager, "Sounds/whispers.ogg", false);
        whispers.setPositional(false);
        whispers.setLooping(false);
        whispers.setVolume(1);
        rootNode.attachChild(whispers);
        
        phonePHONE = new AudioNode(assetManager, "Sounds/phonePHONE.ogg", false);
        phonePHONE.setPositional(false);
        phonePHONE.setLooping(true);
        phonePHONE.setVolume(.5f);
        rootNode.attachChild(phonePHONE);
        
        escape = new AudioNode(assetManager, "Sounds/escape.ogg", false);
        escape.setPositional(false);
        escape.setLooping(false);
        escape.setVolume(1);
        rootNode.attachChild(escape);
        
        music = new AudioNode(assetManager, "Sounds/music.ogg", false);
        music.setPositional(false);
        music.setLooping(false);
        music.setVolume(.5f);
        rootNode.attachChild(music);
                
        boom = new AudioNode(assetManager, "Sounds/kaboom.ogg", false);
        boom.setPositional(false);
        boom.setLooping(false);
        boom.setVolume(0.3f);
        rootNode.attachChild(boom);
        
        line1 = new AudioNode(assetManager, "Sounds/lines/line1.ogg", false);
        line1.setPositional(false);
        line1.setLooping(false);
        line1.setVolume(0.3f);
        rootNode.attachChild(line1);
        line2 = new AudioNode(assetManager, "Sounds/lines/line2.ogg", false);
        line2.setPositional(false);
        line2.setLooping(false);
        line2.setVolume(0.3f);
        rootNode.attachChild(line2);
        line3 = new AudioNode(assetManager, "Sounds/lines/line3.ogg", false);
        line3.setPositional(false);
        line3.setLooping(false);
        line3.setVolume(0.3f);
        rootNode.attachChild(line3);
        line4 = new AudioNode(assetManager, "Sounds/lines/line4.ogg", false);
        line4.setPositional(false);
        line4.setLooping(false);
        line4.setVolume(0.3f);
        rootNode.attachChild(line4);
        line5 = new AudioNode(assetManager, "Sounds/lines/line5.ogg", false);
        line5.setPositional(false);
        line5.setLooping(false);
        line5.setVolume(0.3f);
        rootNode.attachChild(line5);
        
    }

}