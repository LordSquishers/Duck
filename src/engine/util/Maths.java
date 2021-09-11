package engine.util;

import engine.render.Camera;
import engine.render.display.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {

    public static final Vector3f
            UP = new Vector3f(0, 1, 0),
            DOWN = new Vector3f(0, -1, 0),
            LEFT = new Vector3f(-1, 0, 0),
            RIGHT = new Vector3f(1, 0, 0),
            FORWARD = new Vector3f(0, 0, 1),
            BACK = new Vector3f(0, 0, -1);

    public static final Vector3f
            X_AXIS = RIGHT,
            Y_AXIS = UP,
            Z_AXIS = FORWARD;

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        var matrix = new Matrix4f();
        matrix.identity();
        matrix.translation(translation);
        matrix.rotate(rotation.x, X_AXIS);
        matrix.rotate(rotation.y, Y_AXIS);
        matrix.rotate(rotation.z, Z_AXIS);
        matrix.scale(scale);

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        var viewMat = new Matrix4f();
        viewMat.identity();

        viewMat.rotate((float) Math.toRadians(camera.getRotation().x), X_AXIS);
        viewMat.rotate((float) Math.toRadians(camera.getRotation().y), Y_AXIS);
        viewMat.rotate((float) Math.toRadians(camera.getRotation().z), Z_AXIS);

        var negCameraPos = new Vector3f(camera.getPosition()).negate();
        viewMat.translate(negCameraPos);

        return viewMat;
    }

    public static Matrix4f createProjectionMatrix(float fov, float nearPlane, float farPlane) {
        var projectionMat = new Matrix4f();
        float aspectRatio = (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT;
        float yScale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        projectionMat = new Matrix4f();
        projectionMat.m00(xScale);
        projectionMat.m11(yScale);
        projectionMat.m22(-(farPlane + nearPlane) / frustum_length);
        projectionMat.m23(-1);
        projectionMat.m32(-(2 * nearPlane * farPlane) / frustum_length);
        projectionMat.m33(0);

        return projectionMat;
    }

}
