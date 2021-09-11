package engine.texture;

public class ModelTexture {

    private final int textureID;

    private float shineDamper = 1f, reflectivity = 0f;
    private boolean isOpaque = true, useFakeLighting = false;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean isOpaque() {
        return isOpaque;
    }

    public void setOpaque(boolean opaque) {
        isOpaque = opaque;
    }

    public boolean usesFakeLighting() {
        return useFakeLighting;
    }

    public void useFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}
