package engine.model;

import engine.shader.ShaderProgram;
import engine.texture.ModelTexture;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class TexturedModel {

    private final RawModel model;
    private final ModelTexture texture;
    private ShaderProgram.ShaderType shaderType;

    public TexturedModel(RawModel model, ModelTexture texture, ShaderProgram.ShaderType shaderType) {
        this.model = model;
        this.texture = texture;
        this.shaderType = shaderType;
    }

    public ShaderProgram.ShaderType getShaderType() {
        return shaderType;
    }

    public void setShaderType(ShaderProgram.ShaderType shaderType) {
        this.shaderType = shaderType;
    }

    public RawModel getRawModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }


}
