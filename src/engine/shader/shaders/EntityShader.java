package engine.shader.shaders;

import engine.shader.ShaderProgram;
import engine.shader.uniform.Uniform1F;
import engine.shader.uniform.Uniform3F;
import engine.shader.uniform.Uniform4F;
import engine.util.Logger;

public class EntityShader extends ShaderProgram {

    public Uniform4F transformMat, projectionMat, viewMat;
    public Uniform3F pointLightPos, pointLightColor;
    public Uniform1F minBrightness, shineDamper, reflectivity;

    public EntityShader() {
        super(ShaderType.ENTITY);
        Logger.INSTANCE.info(Logger.Source.SHADERS, "Entity shader initialised");
    }

    @Override
    protected void initUniforms() {
        transformMat = new Uniform4F("transformMat", programID);
        projectionMat = new Uniform4F("projectionMat", programID);
        viewMat = new Uniform4F("viewMat", programID);

        pointLightPos = new Uniform3F("pointLightPos", programID);
        pointLightColor = new Uniform3F("pointLightColor", programID);

        minBrightness = new Uniform1F("minBrightness", programID);
        reflectivity = new Uniform1F("reflectivity", programID);
        shineDamper = new Uniform1F("shineDamper", programID);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "pos");
        super.bindAttribute(1, "texCoords");
        super.bindAttribute(2, "normal");
    }

}
