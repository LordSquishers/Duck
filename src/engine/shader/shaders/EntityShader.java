package engine.shader.shaders;

import engine.shader.ShaderProgram;
import engine.shader.uniform.Uniform4F;
import engine.util.Logger;

public class EntityShader extends ShaderProgram {

    public Uniform4F transformMat;

    public EntityShader() {
        super(ShaderType.ENTITY);
        Logger.INSTANCE.info(Logger.Source.SHADERS, "Entity shader initialised");
    }

    @Override
    protected void initUniforms() {
        transformMat = new Uniform4F("transformMat", programID);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "pos");
        super.bindAttribute(1, "texCoords");
    }

}
