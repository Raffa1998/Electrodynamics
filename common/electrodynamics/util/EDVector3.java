package electrodynamics.util;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EDVector3
{
  public float x;
  public float y;
  public float z;

  public EDVector3(double x, double y, double z)
  {
    this.x = ((float)x);
    this.y = ((float)y);
    this.z = ((float)z);
  }

  public EDVector3(TileEntity tile)
  {
    this.x = (tile.xCoord + 0.5F);
    this.y = (tile.yCoord + 0.5F);
    this.z = (tile.zCoord + 0.5F);
  }

  public EDVector3(Vec3 vec) {
    this.x = ((float)vec.xCoord);
    this.y = ((float)vec.yCoord);
    this.z = ((float)vec.zCoord);
  }

  public EDVector3(Entity entity)
  {
    this(entity.posX, entity.posY, entity.posZ);
  }

  public EDVector3 add(EDVector3 vec)
  {
    this.x += vec.x;
    this.y += vec.y;
    this.z += vec.z;
    return this;
  }

  public EDVector3 sub(EDVector3 vec)
  {
    this.x -= vec.x;
    this.y -= vec.y;
    this.z -= vec.z;
    return this;
  }

  public EDVector3 scale(float scale)
  {
    this.x *= scale;
    this.y *= scale;
    this.z *= scale;
    return this;
  }

  public EDVector3 scale(float scalex, float scaley, float scalez)
  {
    this.x *= scalex;
    this.y *= scaley;
    this.z *= scalez;
    return this;
  }

  public EDVector3 modulo(float divisor) {
    this.x %= divisor;
    this.y %= divisor;
    this.z %= divisor;
    return this;
  }

  public EDVector3 normalize()
  {
    float length = length();
    this.x /= length;
    this.y /= length;
    this.z /= length;
    return this;
  }

  public float length()
  {
    return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  public float lengthPow2()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  public EDVector3 copy()
  {
    return new EDVector3(this.x, this.y, this.z);
  }

  public static EDVector3 crossProduct(EDVector3 vec1, EDVector3 vec2)
  {
    return new EDVector3(vec1.y * vec2.z - vec1.z * vec2.y, vec1.z * vec2.x - vec1.x * vec2.z, vec1.x * vec2.y - vec1.y * vec2.x);
  }

  public static EDVector3 xCrossProduct(EDVector3 vec)
  {
    return new EDVector3(0.0D, vec.z, -vec.y);
  }

  public static EDVector3 zCrossProduct(EDVector3 vec)
  {
    return new EDVector3(-vec.y, vec.x, 0.0D);
  }

  public static float dotProduct(EDVector3 vec1, EDVector3 vec2)
  {
    return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
  }

  public static float angle(EDVector3 vec1, EDVector3 vec2)
  {
    return anglePreNorm(vec1.copy().normalize(), vec2.copy().normalize());
  }

  public static float anglePreNorm(EDVector3 vec1, EDVector3 vec2)
  {
    return (float)Math.acos(dotProduct(vec1, vec2));
  }

  public EDVector3 rotate(float angle, EDVector3 axis)
  {
    return EDMatrix4.rotationMat(angle, axis).translate(this);
  }

  public String toString()
  {
    return "[" + this.x + "," + this.y + "," + this.z + "]";
  }

  public Vec3 toVec3D()
  {
    return Vec3.createVectorHelper(this.x, this.y, this.z);
  }

  public static EDVector3 getPerpendicular(EDVector3 vec)
  {
    if (vec.z == 0.0F) {
      return zCrossProduct(vec);
    }
    return xCrossProduct(vec);
  }

  public boolean isZero()
  {
    return (this.x == 0.0F) && (this.y == 0.0F) && (this.z == 0.0F);
  }

  public boolean isWithinRange(float min, float max)
  {
    return (this.x >= min) && (this.x <= max) && (this.y >= min) && (this.y <= max) && (this.z >= min) && (this.z <= max);
  }

  public double distanceTo(EDVector3 target) {
    double var2 = target.x - this.x;
    double var4 = target.y - this.y;
    double var6 = target.z - this.z;
    return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof EDVector3)) {
      EDVector3 comp = (EDVector3)obj;
      return (comp.x == this.x) && (comp.y == this.y) && (comp.z == this.z);
    }
    return false;
  }
}