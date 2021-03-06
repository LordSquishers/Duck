package engine.render;

import engine.entity.Entity;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
import engine.shader.shaders.EntityShader;
import engine.util.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

    public EntityShader shader;

    public EntityRenderer() {
        this.shader = new EntityShader();
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
        GL20.glEnableVertexAttribArray(2);

        if(!texture.isOpaque()) MasterRenderer.setBackfaceCulling(false);
        else MasterRenderer.setBackfaceCulling(true);

        shader.useFakeLighting.load(texture.usesFakeLighting());
        shader.shineDamper.load(texture.getShineDamper());
        shader.reflectivity.load(texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformMat = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shader.transformMat.load(transformMat);
    }

}
