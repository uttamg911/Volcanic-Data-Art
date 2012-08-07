// A simple Particle class

class Particle {
  PVector loc;
  PVector vel;
  PVector acc;
  float r;
  float timer;
  //float r1;
  //float g1 = 255;
  //float b1;
  float angle;
  
  // Another constructor (the one we are using here)
  Particle(float r1, float g1, float b1, float rad, PVector l) {
    acc = new PVector(0,0.05,0); // gravity
    vel = new PVector(random(-2,2),random(-2,0),0);
    loc = l.get();
    r = rad; // sets the radius
    timer = 100.0; // sets the life time of each particle
  }

  void run() {
    update();
    render();
  }

  // Method to update location
  void update() {
    vel.add(acc);
    loc.add(vel);
    timer -= 1.0;
  }

  // Method to display
  void render() {
    ellipseMode(CENTER);
    //stroke(255,0,0,timer);
    fill(r1, g1, b1, timer);
    ellipse(loc.x,loc.y,r,r);
    //displayVector(vel,loc.x,loc.y,10);
    r = sin(angle)+r;
    angle+=0.2;
  }
  
  void render(float r1, float g1, float b1) {
    ellipseMode(CENTER);
    //stroke(255,0,0,timer);
    fill(255, 255, 255, timer);
    ellipse(loc.x,loc.y,r,r);
    //displayVector(vel,loc.x,loc.y,10);
    r = sin(angle)+r;
    angle+=0.2;
  }
  
  // Is the particle still useful?
  boolean dead() {
    if (timer <= 0.0) {
      return true;
    } else {
      return false;
    }
  }
  
   void displayVector(PVector v, float x, float y, float scayl) {
    pushMatrix();
    float arrowsize = 4;
    // Translate to location to render vector
    translate(x,y);
    stroke(255);
    // Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
    rotate(v.heading2D());
    // Calculate length of vector & scale it to be bigger or smaller if necessary
    float len = v.mag()*scayl;
    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
    line(0,0,len,0);
    line(len,0,len-arrowsize,+arrowsize/2);
    line(len,0,len-arrowsize,-arrowsize/2);
    popMatrix();
  } 

}

