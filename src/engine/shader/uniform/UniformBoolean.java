package engine.shader.uniform;

import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform<Boolean> {

    public UniformBoolean(String name, int programID) {
        super(name, programID);
    }

    @Override
    public void load(Boolean value) {
        GL20.glUniform1f(location, value ? 1 : 0);
    }
}
