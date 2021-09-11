package engine.model;

import engine.texture.ModelTexture;

public class TexturedModel {

    private final RawModel model;
    private final ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
