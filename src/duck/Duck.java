package duck;

import engine.model.Loader;
import engine.model.TexturedModel;
import engine.render.EntityRenderer;
import engine.render.display.DisplayManager;
import engine.shader.shaders.EntityShader;
import engine.texture.ModelTexture;
import engine.util.Logger;

import java.io.IOException;

import static engine.util.Logger.Source.*;

public class Duck {

    public static void main(String[] args) {
        var display = new DisplayManager();
        display.create();

        Logger.INSTANCE.info(DUCK, "Initialising environment...");
        var loader = new Loader();
        var eRen = new EntityRenderer();

        // Shaders
        var eShader = new EntityShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };

        float[] texCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        var triModel = loader.loadToVAO(vertices, indices, texCoords);
        ModelTexture texture = null;
        try {
            texture = new ModelTexture(loader.loadTexture("square.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var fullModel = new TexturedModel(triModel, texture);

        Logger.INSTANCE.info(DUCK, "Environment initialised!");
        while (!display.isCloseRequested()) {
            // Entities
            eRen.prepare();

            eShader.start();
            eRen.render(fullModel);
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
