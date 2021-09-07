package engine.render;

import engine.entity.Entity;
import engine.render.display.DisplayManager;
import engine.shader.shaders.EntityShader;
import engine.util.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class EntityRenderer {

    private static final float FOV = 70, NEAR_PLANE = 0.1f, FAR_PLANE = 1000f;

    private Matrix4f projectionMat;

    public EntityRenderer(EntityShader shader) {
        createProjectionMatrix();

        shader.start();
        shader.projectionMat.load(projectionMat);
        shader.stop();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.75f, 0.75f, 0.75f, 1f);
    }

    public void render(Camera camera, Entity entity, EntityShader shader) {
        var texModel = entity.getModel();
        var model = texModel.getModel();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Matrix4f transformMat = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.transformMat.load(transformMat);

        shader.viewMat.load(Maths.createViewMatrix(camera));

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texModel.getTexture().getTextureID());

        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT;
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMat = new Matrix4f();
        projectionMat.m00(xScale);
        projectionMat.m11(yScale);
        projectionMat.m22(-(FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMat.m23(-1);
        projectionMat.m32(-(2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMat.m33(0);
    }

}
