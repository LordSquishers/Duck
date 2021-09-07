package engine.shader.uniform;

import engine.util.Vector3f;
import org.lwjgl.opengl.GL20;

public class Uniform3F extends Uniform<Vector3f> {

    public Uniform3F(String uniformName, int programID) {
        super(uniformName, programID);
    }

    @Override
    public void load(Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }
}
