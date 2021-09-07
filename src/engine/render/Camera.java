package engine.render;

import engine.util.Keyboard;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();

    public void move() {
        if(Keyboard.isKeyDown(GLFW_KEY_W)) {
            translate(new Vector3f(0, 0, -0.02f));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_S)) {
            translate(new Vector3f(0, 0, 0.02f));
        }

        if(Keyboard.isKeyDown(GLFW_KEY_A)) {
            translate(new Vector3f(-0.02f, 0, 0));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_D)) {
            translate(new Vector3f(0.02f, 0, 0));
        }

        if(Keyboard.isKeyDown(GLFW_KEY_Q)) {
            translate(new Vector3f(0, -0.02f, 0));
        }
        if(Keyboard.isKeyDown(GLFW_KEY_E)) {
            translate(new Vector3f(0, 0.02f, 0));
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
