package mygame;



import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
//an unused class, used with old gun system
public class Bulletclass extends AbstractControl {
    float time;
    private SimpleApplication app;
    Bulletclass() {
        
    }

    @Override
    protected void controlUpdate(float tpf) {
       time = time + tpf * 1;
       if(time > 5) {
           this.spatial.getParent().detachChild(this.spatial);
       }
    }


public void initialize(AppStateManager stateManager, Application app) {



this.app = (SimpleApplication) app;

}
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    
}