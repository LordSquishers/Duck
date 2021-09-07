package engine.shader.uniform;

import engine.util.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIMatrix4x4;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class Uniform4F extends Uniform<Matrix4f> {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Uniform4F(String name, int programID) {
        super(name, programID);
    }

    @Override
    public void load(Matrix4f value) {
        matrixBuffer.put(value.getMatrixFlat());
        matrixBuffer.flip();

        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }
}
