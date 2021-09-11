package engine.render;

import engine.entity.Entity;
import engine.model.TexturedModel;
import engine.terrain.Terrain;
import engine.util.Maths;
import engine.light.PointLight;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static duck.Constants.*;

public class MasterRenderer {

    private static final float FOV = 70, NEAR_PLANE = 0.1f, FAR_PLANE = 1000f;

    private EntityRenderer entity = new EntityRenderer();
    private TerrainRenderer terrain = new TerrainRenderer();

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();
    private Matrix4f projectionMat;

    public MasterRenderer() {
        setBackfaceCulling(true);

        projectionMat = Maths.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);

        entity.shader.start();
        entity.shader.projectionMat.load(projectionMat);
        entity.shader.stop();

        terrain.shader.start();
        terrain.shader.projectionMat.load(projectionMat);
        terrain.shader.stop();
    }

    public void render(PointLight sun, Camera camera) {
        prepare();

        /* ENTITIES */
        entity.shader.start();

        entity.shader.pointLightPos.load(sun.getPosition());
        entity.shader.pointLightColor.load(sun.getColor());
        entity.shader.minBrightness.load(MINIMUM_BRIGHTNESS);
        entity.shader.skyColor.load(SKY_COLOR);

        entity.shader.fogDensity.load(FOG_DENSITY);
        entity.shader.fogGradient.load(FOG_GRADIENT);

        entity.shader.viewMat.load(Maths.createViewMatrix(camera));

        entity.render(entities);

        entity.shader.stop();
        entities.clear();

        /* TERRAINS */
        terrain.shader.start();

        terrain.shader.pointLightPos.load(sun.getPosition());
        terrain.shader.pointLightColor.load(sun.getColor());
        terrain.shader.minBrightness.load(MINIMUM_BRIGHTNESS);
        terrain.shader.skyColor.load(SKY_COLOR);

        terrain.shader.fogDensity.load(FOG_DENSITY);
        terrain.shader.fogGradient.load(FOG_GRADIENT);

        terrain.shader.viewMat.load(Maths.createViewMatrix(camera));

        terrain.render(terrains);

        terrain.shader.stop();
        terrains.clear();
    }

    public static void setBackfaceCulling(boolean enabled) {
        if(enabled) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
        } else {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
    }

    public void processEntity(Entity e) {
        var entityModel = e.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null) {
            batch.add(e);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(e);
            entities.put(entityModel, newBatch);
        }
    }

    public void processEntities(List<Entity> es) {
        for (var e: es) processEntity(e);
    }

    public void processTerrain(Terrain t) {
        terrains.add(t);
    }

    public void processTerrains(List<Terrain> ts) {
        terrains.addAll(ts);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_COLOR.x, SKY_COLOR.y, SKY_COLOR.z, 1f);
    }

    public void clean() {
        entity.shader.clean();
        terrain.shader.clean();
    }

}
