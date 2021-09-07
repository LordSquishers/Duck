package engine.util;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        var matrix = new Matrix4f();
        matrix.identity();
        matrix.translation(translation);
        matrix.rotation(rotation);
        matrix.scale(new Vector3f(scale));

        return matrix;
    }

}
