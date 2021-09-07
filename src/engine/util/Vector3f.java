package engine.util;

public class Vector3f {

    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3f(float value)
    {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3f r)
    {
        return x * r.x + y * r.y + z * r.z;
    }

    public Vector3f cross(Vector3f r)
    {
        float x = y * r.z - z * r.y;
        float y = z * r.x - this.x * r.z;
        float z = this.x * r.y - this.y * r.x;

        return new Vector3f(x,y,z);
    }

    public Vector3f normalize()
    {
        float length = this.length();

        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    public Vector3f add(Vector3f r)
    {
        return new Vector3f(this.x + r.x, this.y + r.y, this.z + r.z);
    }

    public Vector3f add(float r)
    {
        return new Vector3f(this.x + r, this.y + r, this.z + r);
    }

    public Vector3f sub(Vector3f r)
    {
        return new Vector3f(this.x - r.x, this.y - r.y, this.z - r.z);
    }

    public Vector3f sub(float r)
    {
        return new Vector3f(this.x - r, this.y - r, this.z - r);
    }

    public Vector3f mul(Vector3f r)
    {
        return new Vector3f(this.x * r.x, this.y * r.y, this.z * r.z);
    }

    public Vector3f mul(float x, float y, float z)
    {
        return new Vector3f(this.x * x, this.y * y, this.z * z);
    }

    public Vector3f mul(float r)
    {
        return new Vector3f(this.x * r, this.y * r, this.z * r);
    }

    public Vector3f div(Vector3f r)
    {
        return new Vector3f(this.x / r.x, this.y / r.y, this.z / r.z);
    }

    public Vector3f div(float r)
    {
        return new Vector3f(this.x / r, this.y / r, this.z / r);
    }

    public Vector3f abs()
    {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public boolean equals(Vector3f v)
    {
        if (x == v.x && y == v.y && z == v.z)
            return true;
        else return false;
    }

    public String toString()
    {
        return "[" + this.x + "," + this.y + "," + this.z + "]";
    }



}