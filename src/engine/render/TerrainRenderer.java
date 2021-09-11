package engine.render;

import engine.shader.shaders.TerrainShader;
import engine.terrain.Terrain;
import engine.util.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class TerrainRenderer {

    public TerrainShader shader;

    public TerrainRenderer() {
        this.shader = new TerrainShader();
    }

    public void render(List<Terrain> terrains) {
        for(var t: terrains) {
            prepareTerrain(t);
            GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepareTerrain(Terrain terrain) {
        var rawModel = terrain.getModel().getRawModel();
        var texture = terrain.getModel().getTexture();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        shader.useFakeLighting.load(texture.usesFakeLighting());
        if(!texture.isOpaque()) MasterRenderer.setBackfaceCulling(false);
        else MasterRenderer.setBackfaceCulling(true);

        shader.shineDamper.load(texture.getShineDamper());
        shader.reflectivity.load(texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        Matrix4f transformMat = Maths.createTransformationMatrix(new Vector3f(terrain.getPosition().x, 0, terrain.getPosition().y), new Vector3f(), new Vector3f(1));
        shader.transformMat.load(transformMat);
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
