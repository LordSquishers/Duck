package engine.render;

import engine.util.Keyboard;
import org.joml.Vector3f;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();

    public void move() {
        float mult = 1.0f;
        if(Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT)) mult = 8f;

        if(Keyboard.isKeyDown(GLFW_KEY_W)) {
            translate(new Vector3f(0, 0, -0.02f * mult));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_S)) {
            translate(new Vector3f(0, 0, 0.02f * mult));
        }

        if(Keyboard.isKeyDown(GLFW_KEY_A)) {
            translate(new Vector3f(-0.02f * mult, 0, 0));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_D)) {
            translate(new Vector3f(0.02f * mult, 0, 0));
        }

        if(Keyboard.isKeyDown(GLFW_KEY_Q)) {
            translate(new Vector3f(0, -0.02f * mult, 0));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_E)) {
            translate(new Vector3f(0, 0.02f * mult, 0));
        }
    }

    public void translate(Vector3f translation) {
        position.add(translation);
    }

    public void rotate(Vector3f _rotation) {
        rotation.add(_rotation);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
