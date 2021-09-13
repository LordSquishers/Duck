package engine.render;

import engine.entity.Entity;
import engine.model.TexturedModel;
import engine.shader.shaders.EntityShader;
import engine.shader.shaders.SingleColorShader;
import engine.util.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

public class SingleColorRenderer {

    public SingleColorShader shader;

    public SingleColorRenderer() {
        this.shader = new SingleColorShader();
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {
        for(var model: entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);

            for(var entity: batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        var rawModel = model.getRawModel();
        var texture = model.getTexture();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        MasterRenderer.setBackfaceCulling(texture.isOpaque());

        shader.useFakeLighting.load(texture.usesFakeLighting());
        shader.shineDamper.load(texture.getShineDamper());
        shader.reflectivity.load(texture.getReflectivity());

        shader.renderColor.load(texture.getSingleColor());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformMat = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.transformMat.load(transformMat);
    }
}
