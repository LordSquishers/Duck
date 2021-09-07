package engine.util;

public class Matrix4f {

    private float[][] m;

    public Matrix4f()
    {
        setM(new float[4][4]);
    }

    public Matrix4f zero()
    {
        m[0][0] = 0; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 0; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 0; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 0;

        return this;
    }

    public Matrix4f identity()
    {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f translation(Vector3f translation)
    {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = translation.x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = translation.y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = translation.z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f rotation(Vector3f rotation)
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        float x = (float)Math.toRadians(rotation.x);
        float y = (float)Math.toRadians(rotation.y);
        float z = (float)Math.toRadians(rotation.z);

        rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); 	 rz.m[0][2] = 0; 				   rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  	 rz.m[1][2] = 0; 				   rz.m[1][3] = 0;
        rz.m[2][0] = 0; 				 rz.m[2][1] = 0; 				   	 rz.m[2][2] = 1; 				   rz.m[2][3] = 0;
        rz.m[3][0] = 0; 				 rz.m[3][1] = 0; 				   	 rz.m[3][2] = 0; 				   rz.m[3][3] = 1;

        rx.m[0][0] = 1; 				 rx.m[0][1] = 0;					 rx.m[0][2] = 0; 				   rx.m[0][3] = 0;
        rx.m[1][0] = 0; 				 rx.m[1][1] = (float)Math.cos(x); 	 rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0; 				 rx.m[2][1] = (float)Math.sin(x); 	 rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0; 				 rx.m[3][1] = 0; 				 	 rx.m[3][2] = 0;				   rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; 					 ry.m[0][2] = (float)Math.sin(y);  ry.m[0][3] = 0;
        ry.m[1][0] = 0; 				 ry.m[1][1] = 1; 				 	 ry.m[1][2] = 0; 				   ry.m[1][3] = 0;
        ry.m[2][0] = -(float)Math.sin(y);ry.m[2][1] = 0;					 ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
        ry.m[3][0] = 0; 				 ry.m[3][1] = 0; 					 ry.m[3][2] = 0; 				   ry.m[3][3] = 1;

        m =  rz.mul(ry.mul(rx)).getM();

        return this;
    }

    public Matrix4f scale(Vector3f scaling)
    {
        m[0][0] = scaling.x; 	m[0][1] = 0; 				m[0][2] = 0; 				m[0][3] = 0;
        m[1][0] = 0; 			 	m[1][1] = scaling.y;	m[1][2] = 0; 				m[1][3] = 0;
        m[2][0] = 0; 				m[2][1] = 0; 				m[2][2] = scaling.z; 	m[2][3] = 0;
        m[3][0] = 0; 				m[3][1] = 0; 				m[3][2] = 0; 				m[3][3] = 1;

        return this;
    }

    public Matrix4f orthographicProjection(float l, float r, float b, float t, float n, float f){

        m[0][0] = 2.0f/(r-l); 	m[0][1] = 0; 			m[0][2] = 0; 			m[0][3] = -(r+l)/(r-l);
        m[1][0] = 0;			m[1][1] = 2.0f/(t-b); 	m[1][2] = 0; 			m[1][3] = -(t+b)/(t-b);
        m[2][0] = 0; 			m[2][1] = 0; 			m[2][2] = 2.0f/(f-n); 	m[2][3] = -(f+n)/(f-n);
        m[3][0] = 0; 			m[3][1] = 0; 			m[3][2] = 0; 			m[3][3] = 1;

        return this;
    }

    public Matrix4f perspectiveProjection(float fovY, float width, float height, float zNear, float zFar)
    {
        float tanFOV = (float) Math.tan(Math.toRadians(fovY/2));
        float aspectRatio = width/height;

        m[0][0] = 1/(tanFOV*aspectRatio); m[0][1] = 0; 		 	   m[0][2] = 0; 				m[0][3] = 0;
        m[1][0] = 0; 					  m[1][1] = 1/tanFOV; 	   m[1][2] = 0; 			 	m[1][3] = 0;
        m[2][0] = 0; 				 	  m[2][1] = 0; 		 	   m[2][2] = zFar/(zFar-zNear);	m[2][3] = zFar*zNear /(zFar-zNear);
        m[3][0] = 0; 				 	  m[3][1] = 0; 		 	   m[3][2] = 1; 				m[3][3] = 1;

        return this;
    }

    public Matrix4f view(Vector3f forward, Vector3f up)
    {
        Vector3f f = forward;
        Vector3f u = up;
        Vector3f r = u.cross(f);

        m[0][0] = r.x; m[0][1] = r.y; m[0][2] = r.z; m[0][3] = 0;
        m[1][0] = u.x; m[1][1] = u.y; m[1][2] = u.z; m[1][3] = 0;
        m[2][0] = f.x;	m[2][1] = f.y; m[2][2] = f.z; m[2][3] = 0;
        m[3][0] = 0; 		m[3][1] = 0; 		m[3][2] = 0; 		m[3][3] = 1;

        return this;
    }


    public Matrix4f mul(Matrix4f r){

        Matrix4f res = new Matrix4f();

        for (int i=0; i<4; i++)
        {
            for (int j=0; j<4; j++)
            {
                res.set(i, j, m[i][0] * r.get(0, j) +
                        m[i][1] * r.get(1, j) +
                        m[i][2] * r.get(2, j) +
                        m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public Matrix4f transpose()
    {
        Matrix4f result = new Matrix4f();

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                result.set(i, j, get(j,i));
            }
        }
        return result;
    }

    public Matrix4f invert()
    {
        float s0 = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        float s1 = get(0, 0) * get(1, 2) - get(1, 0) * get(0, 2);
        float s2 = get(0, 0) * get(1, 3) - get(1, 0) * get(0, 3);
        float s3 = get(0, 1) * get(1, 2) - get(1, 1) * get(0, 2);
        float s4 = get(0, 1) * get(1, 3) - get(1, 1) * get(0, 3);
        float s5 = get(0, 2) * get(1, 3) - get(1, 2) * get(0, 3);

        float c5 = get(2, 2) * get(3, 3) - get(3, 2) * get(2, 3);
        float c4 = get(2, 1) * get(3, 3) - get(3, 1) * get(2, 3);
        float c3 = get(2, 1) * get(3, 2) - get(3, 1) * get(2, 2);
        float c2 = get(2, 0) * get(3, 3) - get(3, 0) * get(2, 3);
        float c1 = get(2, 0) * get(3, 2) - get(3, 0) * get(2, 2);
        float c0 = get(2, 0) * get(3, 1) - get(3, 0) * get(2, 1);


        float div = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
        if (div == 0) System.err.println("not invertible");

        float invdet = 1.0f / div;

        Matrix4f invM = new Matrix4f();

        invM.set(0, 0, (get(1, 1) * c5 - get(1, 2) * c4 + get(1, 3) * c3) * invdet);
        invM.set(0, 1, (-get(0, 1) * c5 + get(0, 2) * c4 - get(0, 3) * c3) * invdet);
        invM.set(0, 2, (get(3, 1) * s5 - get(3, 2) * s4 + get(3, 3) * s3) * invdet);
        invM.set(0, 3, (-get(2, 1) * s5 + get(2, 2) * s4 - get(2, 3) * s3) * invdet);

        invM.set(1, 0, (-get(1, 0) * c5 + get(1, 2) * c2 - get(1, 3) * c1) * invdet);
        invM.set(1, 1, (get(0, 0) * c5 - get(0, 2) * c2 + get(0, 3) * c1) * invdet);
        invM.set(1, 2, (-get(3, 0) * s5 + get(3, 2) * s2 - get(3, 3) * s1) * invdet);
        invM.set(1, 3, (get(2, 0) * s5 - get(2, 2) * s2 + get(2, 3) * s1) * invdet);

        invM.set(2, 0, (get(1, 0) * c4 - get(1, 1) * c2 + get(1, 3) * c0) * invdet);
        invM.set(2, 1, (-get(0, 0) * c4 + get(0, 1) * c2 - get(0, 3) * c0) * invdet);
        invM.set(2, 2, (get(3, 0) * s4 - get(3, 1) * s2 + get(3, 3) * s0) * invdet);
        invM.set(2, 3, (-get(2, 0) * s4 + get(2, 1) * s2 - get(2, 3) * s0) * invdet);

        invM.set(3, 0, (-get(1, 0) * c3 + get(1, 1) * c1 - get(1, 2) * c0) * invdet);
        invM.set(3, 1, (get(0, 0) * c3 - get(0, 1) * c1 + get(0, 2) * c0) * invdet);
        invM.set(3, 2, (-get(3, 0) * s3 + get(3, 1) * s1 - get(3, 2) * s0) * invdet);
        invM.set(3, 3, (get(2, 0) * s3 - get(2, 1) * s1 + get(2, 2) * s0) * invdet);

        return invM;
    }

    public boolean equals(Matrix4f m){
        if (this.m[0][0] == m.getM()[0][0] && this.m[0][1] == m.getM()[0][1] &&
                this.m[0][2] == m.getM()[0][2] && this.m[0][3] == m.getM()[0][3] &&
                this.m[1][0] == m.getM()[1][0] && this.m[1][1] == m.getM()[1][1] &&
                this.m[1][2] == m.getM()[1][2] && this.m[1][3] == m.getM()[1][3] &&
                this.m[2][0] == m.getM()[2][0] && this.m[2][1] == m.getM()[2][1] &&
                this.m[2][2] == m.getM()[2][2] && this.m[2][3] == m.getM()[2][3] &&
                this.m[3][0] == m.getM()[3][0] && this.m[3][1] == m.getM()[3][1] &&
                this.m[3][2] == m.getM()[3][2] && this.m[3][3] == m.getM()[3][3])
            return true;
        else
            return false;
    }

    public void set(int x, int y, float value)
    {
        this.m[x][y] = value;
    }

    public float get(int x, int y)
    {
        return  this.m[x][y];
    }

    public float [][] getM() {
        return m;
    }

    public void setM(float [][] m) {
        this.m = m;
    }

    public String toString() {

        return 	"|" + m[0][0] + " " + m[0][1] + " " + m[0][2] + " " + m[0][3] + "|\n" +
                "|" + m[1][0] + " " + m[1][1] + " " + m[1][2] + " " + m[1][3] + "|\n" +
                "|" + m[2][0] + " " + m[2][1] + " " + m[2][2] + " " + m[2][3] + "|\n" +
                "|" + m[3][0] + " " + m[3][1] + " " + m[3][2] + " " + m[3][3] + "|";
    }

    public float[] getMatrixFlat() {
        float[] output = new float[16];
        int i = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                output[i] = m[x][y];
                i++;
            }
        }

        return output;
    }
}