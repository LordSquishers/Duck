package engine.shader.uniform;

import org.lwjgl.opengl.GL20;

public abstract class Uniform<T> {

    protected String name;
    protected int programID, location;

    public Uniform(String name, int programID) {
        this.name = name;
        this.programID = programID;

        location = getUniformLocation();
    }

    private int getUniformLocation() {
        return GL20.glGetUniformLocation(programID, name);
    }

    public abstract void load(T value);

}
