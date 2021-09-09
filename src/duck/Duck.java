package duck;

import engine.entity.Entity;
import engine.model.Loader;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.model.obj.OBJFileLoader;
import engine.render.Camera;
import engine.render.EntityRenderer;
import engine.render.display.DisplayManager;
import engine.shader.shaders.EntityShader;
import engine.texture.ModelTexture;
import engine.util.Logger;
import engine.util.Maths;
import light.PointLight;
import org.joml.Vector3f;

import java.io.IOException;

import static engine.util.Logger.Source.*;

public class Duck {

    public static void main(String[] args) {
        var display = new DisplayManager();
        display.create();

        Logger.INSTANCE.info(DUCK, "Initialising environment...");
        var loader = new Loader();

        // Shaders
        var eShader = new EntityShader();

        // Renderers
        var eRen = new EntityRenderer(eShader);

        var modelData = OBJFileLoader.loadOBJ("dragon.obj");
        var rawModel = loader.loadToVAO(modelData);
        ModelTexture texture = null;
        try {
            texture = new ModelTexture(loader.loadTexture("blue.png"));
            texture.setReflectivity(1);
            texture.setShineDamper(10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var fullModel = new TexturedModel(rawModel, texture);
        var entity = new Entity(fullModel, new Vector3f(0, 0, -25), new Vector3f(90).mul(Maths.Y_AXIS), new Vector3f(1));
        var camera = new Camera();
        camera.setPosition(new Vector3f(0, 5, 0));

        var light = new PointLight(new Vector3f(0, 5, -10), new Vector3f(1));

        Logger.INSTANCE.info(DUCK, "Environment initialised!");
        while (!display.isCloseRequested()) {
            // movement
            camera.move();

            // Entities
            eRen.prepare();

            eShader.start();
            eShader.minBrightness.load(0.15f);
            eShader.pointLightPos.load(light.getPosition());
            eShader.pointLightColor.load(light.getColor());
            eRen.render(camera, entity, eShader);
            eShader.stop();

            display.update();
        }

        Logger.INSTANCE.info(DUCK, "Cleaning up...");
        // Shaders
        eShader.clean();

        loader.clean();
        Logger.INSTANCE.info(DUCK, "Done!");
        display.close();
        Logger.INSTANCE.info(DUCK, "Exited.");
    }

}
