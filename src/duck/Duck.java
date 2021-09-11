package duck;

import engine.entity.Entity;
import engine.model.Loader;
import engine.render.Camera;
import engine.render.MasterRenderer;
import engine.render.display.DisplayManager;
import engine.terrain.Terrain;
import engine.util.Creator;
import engine.util.Logger;
import engine.util.Maths;
import light.PointLight;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static engine.util.Logger.Source.*;

public class Duck {

    public static void main(String[] args) {
        var display = new DisplayManager();
        display.create();

        Logger.INSTANCE.info(DUCK, "Initialising environment...");
        var loader = new Loader();
        Creator.setLoader(loader);

        // Renderers
        var renderer = new MasterRenderer();

        // Entities
        List<Entity> entities = new ArrayList<>();

        var entity = Creator.createEntity("dragon.obj", "blue.png", new Vector3f(0, 0, -25), new Vector3f(90).mul(Maths.Y_AXIS), new Vector3f(1));
        entities.add(entity);

        // Terrains
        //TODO- implement chunk/world system
        List<Terrain> terrains = new ArrayList<>();

        var terrain = Creator.createTerrain(new Vector2f(-1, -1), "blue.png");
        var terrain2 = Creator.createTerrain(new Vector2f(0, -1), "blue.png");
        terrains.add(terrain);
        terrains.add(terrain2);

        // Global Objects
        var camera = new Camera();
        camera.setPosition(new Vector3f(0, 5, 0));

        //TODO- implement directional lights
        var light = new PointLight(new Vector3f(0, 50, -10), new Vector3f(1));

        Logger.INSTANCE.info(DUCK, "Environment initialised!");
        while (!display.isCloseRequested()) {
            // movement
            camera.move();

            // Rendering
            renderer.processEntities(entities);
            renderer.processTerrains(terrains);
            renderer.render(light, camera);

            display.update();
        }

        // Cleanup
        Logger.INSTANCE.info(DUCK, "Cleaning up...");

        renderer.clean();
        loader.clean();

        Logger.INSTANCE.info(DUCK, "Done!");
        display.close();
        Logger.INSTANCE.info(DUCK, "Exited.");
    }

}
