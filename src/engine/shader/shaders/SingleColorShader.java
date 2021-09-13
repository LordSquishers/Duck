package engine.shader.shaders;

import engine.shader.ShaderProgram;
import engine.shader.uniform.Uniform1F;
import engine.shader.uniform.Uniform3F;
import engine.shader.uniform.Uniform4F;
import engine.shader.uniform.UniformBoolean;
import engine.util.Logger;

public class SingleColorShader extends ShaderProgram {

    public Uniform4F transformMat, projectionMat, viewMat;
    public Uniform3F pointLightPos, pointLightColor, skyColor, renderColor;
    public Uniform1F minBrightness, shineDamper, reflectivity, fogDensity, fogGradient;
    public UniformBoolean useFakeLighting;

    public SingleColorShader() {
        super(ShaderType.SINGLE_COLOR);
        Logger.INSTANCE.info(Logger.Source.SHADERS, "Single Color shader initialised");
    }

    @Override
    protected void initUniforms() {
        transformMat = new Uniform4F("transformMat", programID);
        projectionMat = new Uniform4F("projectionMat", programID);
        viewMat = new Uniform4F("viewMat", programID);

        pointLightPos = new Uniform3F("pointLightPos", programID);
        pointLightColor = new Uniform3F("pointLightColor", programID);

        skyColor = new Uniform3F("skyColor", programID);

        minBrightness = new Uniform1F("minBrightness", programID);
        reflectivity = new Uniform1F("reflectivity", programID);
        shineDamper = new Uniform1F("shineDamper", programID);

        fogDensity = new Uniform1F("fogDensity", programID);
        fogGradient = new Uniform1F("gradient", programID);

        useFakeLighting = new UniformBoolean("useFakeLighting", programID);

        renderColor = new Uniform3F("renderColor", programID);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "pos");
        super.bindAttribute(1, "normal");
    }

}
