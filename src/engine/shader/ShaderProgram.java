package engine.shader;

import engine.util.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static engine.util.Logger.Source.SHADERS;

public abstract class ShaderProgram {

    public enum ShaderType {
        ENTITY("entity"),
        TERRAIN("terrain"),
        SINGLE_COLOR("singleColor");

        public String name;
        ShaderType(String _name) {
            name = _name;
        }
    }

    public enum ShaderFileType {
        VERTEX("VSH", GL20.GL_VERTEX_SHADER), FRAGMENT("FSH", GL20.GL_FRAGMENT_SHADER), GEOMETRY("GSH", GL32.GL_GEOMETRY_SHADER);

        public String name;
        public int id;
        ShaderFileType(String _name, int _id) {
            name = _name;
            id = _id;
        }
    }

    protected int programID;
    private final int vshID;
    private final int fshID;

    protected ShaderType type;

    public ShaderProgram(ShaderType shaderType) {
        vshID = loadShader(shaderType, ShaderFileType.VERTEX);
        fshID = loadShader(shaderType, ShaderFileType.FRAGMENT);
        programID = GL20.glCreateProgram();

        GL20.glAttachShader(programID, vshID);
        GL20.glAttachShader(programID, fshID);

        bindAttributes();

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        initUniforms();
    }

    protected abstract void initUniforms();

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void clean() {
        stop();

        GL20.glDetachShader(programID, vshID);
        GL20.glDetachShader(programID, fshID);
        GL20.glDeleteShader(vshID);
        GL20.glDeleteShader(fshID);

        GL20.glDeleteProgram(programID);

        Logger.INSTANCE.info(SHADERS, "Shader program cleaned");
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String var) {
        GL20.glBindAttribLocation(programID, attribute, var);
    }

    private static int loadShader(ShaderType type, ShaderFileType fileType) {
        var shaderSource = new StringBuilder();
        try {
            var reader = new BufferedReader(new FileReader("res/shaders/" + type.name + fileType.name + ".glsl"));
            String line;
            while((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            Logger.INSTANCE.error(SHADERS, "Could not read shader file " + type.name + fileType.name + ".glsl!");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(fileType.id);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            Logger.INSTANCE.info(SHADERS, GL20.glGetShaderInfoLog(shaderID, 500));
            Logger.INSTANCE.error(SHADERS, "Could not compile shader " + type.name + fileType.name + ".glsl!");
            System.exit(-1);
        }

        return shaderID;
    }

    public ShaderType getType() {
        return type;
    }
}
