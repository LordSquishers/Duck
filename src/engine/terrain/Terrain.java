package engine.terrain;

import engine.model.Loader;
import engine.model.TexturedModel;
import engine.model.obj.ModelData;
import engine.shader.ShaderProgram;
import engine.texture.ModelTexture;
import org.joml.Vector2f;

public class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    private Vector2f position;
    private TexturedModel model;

    public Terrain(Vector2f chunkPosition, Loader loader, ModelTexture texture) {
        this.position = chunkPosition.mul(SIZE);
        this.model = generateTerrain(loader, texture);
    }

    private TexturedModel generateTerrain(Loader loader, ModelTexture texture){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        var modelData = new ModelData(vertices, textureCoords, normals, indices, -1);
        return new TexturedModel(loader.loadToVAO(modelData), texture, ShaderProgram.ShaderType.TERRAIN);
    }

    public Vector2f getPosition() {
        return position;
    }

    public TexturedModel getModel() {
        return model;
    }
}
