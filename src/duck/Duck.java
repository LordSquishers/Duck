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

        var modelData = OBJFileLoader.loadOBJ("stall.obj");
        var rawModel = loader.loadToVAO(modelData);
        ModelTexture texture = null;
        try {
            texture = new ModelTexture(loader.loadTexture("stallTexture.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        var fullModel = new TexturedModel(rawModel, texture);
        var entity = new Entity(fullModel, new Vector3f(0, 0, -10), new Vector3f(90).mul(Maths.Y_AXIS), new Vector3f(1));
        var camera = new Camera();

        Logger.INSTANCE.info(DUCK, "Environment initialised!");
        while (!display.isCloseRequested()) {
            // movement
            camera.move();

            // Entities
            eRen.prepare();

            eShader.start();
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
