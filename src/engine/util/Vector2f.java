package engine.util;

public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f v)
    {
        this.x = v.x;
        this.y = v.y;
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2f r)
    {
        return x * r.x + y * r.y;
    }

    public Vector2f normalize()
    {
        float length = length();

        x /= length;
        y /= length;

        return this;
    }

    public Vector2f add(Vector2f r)
    {
        return new Vector2f(this.x + r.x, this.y + r.y);
    }

    public Vector2f add(float r)
    {
        return new Vector2f(this.x + r, this.y + r);
    }

    public Vector2f sub(Vector2f r)
    {
        return new Vector2f(this.x - r.x, this.y - r.y);
    }

    public Vector2f sub(float r)
    {
        return new Vector2f(this.x - r, this.y - r);
    }

    public Vector2f mul(Vector2f r)
    {
        return new Vector2f(this.x * r.x, this.y * r.y);
    }

    public Vector2f mul(float r)
    {
        return new Vector2f(this.x * r, this.y * r);
    }

    public Vector2f div(Vector2f r)
    {
        return new Vector2f(this.x / r.x, this.y / r.y);
    }

    public Vector2f div(float r)
    {
        return new Vector2f(this.x / r, this.y / r);
    }

    public String toString()
    {
        return "[" + this.x + "," + this.y + "]";
    }



}
