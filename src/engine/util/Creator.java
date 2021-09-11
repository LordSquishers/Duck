package engine.util;

import engine.entity.Entity;
import engine.model.Loader;
import engine.model.TexturedModel;
import engine.model.obj.OBJFileLoader;
import engine.terrain.Terrain;
import engine.texture.ModelTexture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.IOException;

public class Creator {

    private static Loader loader;

    public static Entity createEntity(String modelName, String textureName, Vector3f position, Vector3f rotation, Vector3f scale) {
        var modelData = OBJFileLoader.loadOBJ(modelName);
        var rawModel = loader.loadToVAO(modelData);
        var texture = createTexture(textureName);
        var fullModel = new TexturedModel(rawModel, texture);
        return new Entity(fullModel, position, rotation, scale);
    }

    public static Terrain createTerrain(Vector2f chunkPosition, String textureName) {
        return new Terrain(chunkPosition, loader, createTexture(textureName));
    }

    public static ModelTexture createTexture(String textureName) {
        ModelTexture texture = null;
        try {
            texture = new ModelTexture(loader.loadTexture(textureName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texture;
    }

    public static void setLoader(Loader loader) {
        Creator.loader = loader;
    }
}
