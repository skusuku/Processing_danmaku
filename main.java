int bulletMax = 1024;
ArrayList<Bullet> bullet;
int px = 250;
int py = 400;


void setup() {
  size(500, 600);
  frameRate(60);
  bullet = new ArrayList<Bullet>();
}

float getPlayerAngle(float x, float y) {
  float vx = px - x;
  float vy = py - y;
  float l = sqrt(vx*vx + vy*vy);
  float r;
  if (vx > 0) {
    r = asin(vy/l);
  } else {
    r = asin(-vy/l) + PI;
  }
  return r;
}

void circle(float x0, float y0, float r, int way, float v, int interval) {
  if (frameCount % interval == 0) {
    for(int i = 0; i < way; i++) {
      bullet.add(new Bullet(x0,y0,r+2*PI*i/way,v));
    }
  }
}

void fan(float x0, float y0, float r, float wide ,int way, float v, int interval) {
  if (frameCount % interval == 0) {
    for(int i = 0; i < way; i++) {
      bullet.add(new Bullet(x0,y0,r-wide/2+wide*i/(way-1),v));
    }
  }
}

void removeBullet() {
  for (int i = 0; i < bullet.size(); i++) {
    if (bullet.get(i).x > width || bullet.get(i).x < 0 || bullet.get(i).y > height || bullet.get(i).y < 0) {
      bullet.remove(i);
    }
  }
}

void modfan(float x0,float y0) {
  fan(x0,y0,getPlayerAngle(x0,y0)+radians(2*sin(PI*frameCount/8)),radians(210),8,10,1);
}

void syozikimononoshi(float x0,float y0){
  fan(x0,y0,getPlayerAngle(x0,y0),radians(70),15,4,6);
  modfan(x0+80,y0+20);
  modfan(x0-80,y0+20);
  modfan(x0+140,y0-60);
  modfan(x0-140,y0-60);
}
void draw() {
  background(0);
  px = mouseX;
  py = mouseY;
//  circle(200+random(100),200+random(100),random(360),21,4,10);
  syozikimononoshi(250,150);
  for (int i = bullet.size()-1; i >= 0; i--) {
    bullet.get(i).draw();
  }
  if (frameCount % 60 == 0) {
    removeBullet();
  }
  text(bullet.size(), 10, 35);
}

class Bullet {
  float r;
  float x, y, x0, y0;
  float v;
  
  public Bullet(float x0, float y0, float r, float v) {
    this.x0 = x0;
    this.y0 = y0;
    this.x = x0;
    this.y = y0;
    this.r = r;
    this.v = v;
  }
  
  public void draw() {
    if (x < width && y < height) {
      x += v*cos(r);
      y += v*sin(r);
      ellipse(x, y, 10, 10);
    }
  }
}

class Vector2d {
   float x;
   float y;
   Vector2d(float x, float y) {
      this.x = x;
      this.y = y;
   }
}
