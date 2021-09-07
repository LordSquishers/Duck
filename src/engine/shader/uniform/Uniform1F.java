package engine.shader.uniform;

import org.lwjgl.opengl.GL20;

public class Uniform1F extends Uniform<Float> {

    public Uniform1F(String uniformName, int programID) {
        super(uniformName, programID);
    }

    @Override
    public void load(Float value) {
        GL20.glUniform1f(location, value);
    }
}
