import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class do_volcano_4 extends PApplet {

/**
 * Interactive Organic Volcano built using Daniel Shiffman's Simple Particle System & minim Audio Library 
 * by Uttam Grandhi.  
 * 
 * Particles are generated with the sound input through the microphone. draw(),
 * fall with gravity and fade out over time
 * Initial Size of each particle is based on the amplitude of Input Frequency
 * The particles pulsate in a sine curve throughout. 
 * A ParticleSystem object manages a variable size (ArrayList) 
 * list of particles. 
 */
 

//importing audio libraries



Minim minim;
AudioInput in;
FFT fft;

ParticleSystem ps;

public void setup() {
  size(640, 360);
  colorMode(RGB, 255, 255, 255, 100);
  ps = new ParticleSystem(1, new PVector(width/2,height/2,0));
  smooth();
  
     
   minim = new Minim(this);
   minim.debugOn();
  
   // get a line in from Minim, default bit depth is 16
   in = minim.getLineIn(Minim.STEREO, 512);
   
   fft = new FFT(in.bufferSize(),in.sampleRate());
}

   float r1, reds;
   float g1 = 255, greens;
   float b1, blues;

public void draw() {
  
  background(0);
      
  beginShape();
    fill(224,175,30);
    noStroke();
     curveVertex(width/5,height);
     curveVertex(width/4,height);
     curveVertex(width/2,height/2);
     curveVertex(3*width/4,height);
     curveVertex(4*width/5,height);
     rotate(in.left.get(0)/25);
  endShape();
  
  for ( int i =0 ; i < in.bufferSize() - 1; ) {
  fft.forward(in.left);
  if ( in.left.get(i)*1000 > 50 ) {
   float pos = 1.5f*fft.getFreq(i);
   
   if ( pos > 20 ) {
     pos = 20;  
   }
   
   ps.addParticle(reds, greens, blues, pos, width/2, height/2-pos);
  }
  i = i + 100;
  
    //println(fft.getBand(i)*5);
    println(fft.getFreq(i));
  }
  
    ps.run();
}



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
    acc = new PVector(0,0.05f,0); // gravity
    vel = new PVector(random(-2,2),random(-2,0),0);
    loc = l.get();
    r = rad; // sets the radius
    timer = 100.0f; // sets the life time of each particle
  }

  public void run() {
    update();
    render();
  }

  // Method to update location
  public void update() {
    vel.add(acc);
    loc.add(vel);
    timer -= 1.0f;
  }

  // Method to display
  public void render() {
    ellipseMode(CENTER);
    //stroke(255,0,0,timer);
    fill(r1, g1, b1, timer);
    ellipse(loc.x,loc.y,r,r);
    //displayVector(vel,loc.x,loc.y,10);
    r = sin(angle)+r;
    angle+=0.2f;
  }
  
  public void render(float r1, float g1, float b1) {
    ellipseMode(CENTER);
    //stroke(255,0,0,timer);
    fill(255, 255, 255, timer);
    ellipse(loc.x,loc.y,r,r);
    //displayVector(vel,loc.x,loc.y,10);
    r = sin(angle)+r;
    angle+=0.2f;
  }
  
  // Is the particle still useful?
  public boolean dead() {
    if (timer <= 0.0f) {
      return true;
    } else {
      return false;
    }
  }
  
   public void displayVector(PVector v, float x, float y, float scayl) {
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

// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles 
float rad = 3.0f;

class ParticleSystem {

  ArrayList particles;    // An arraylist for all the particles
  PVector origin;        // An origin point for where particles are born

  ParticleSystem(int num, PVector v) {
    particles = new ArrayList();              // Initialize the arraylist
    origin = v.get();                        // Store the origin point
    for (int i = 0; i < num; i++) {
        particles.add(new Particle(r1, g1, b1, rad, origin));    // Add "num" amount of particles to the arraylist
    }
  }

  public void run() {
    // Cycle through the ArrayList backwards b/c we are deleting
    for (int i = particles.size()-1; i >= 0; i--) {
      Particle p = (Particle) particles.get(i);
      p.run();
      if (p.dead()) {
        particles.remove(i);
      }
    }
  }

  public void addParticle() {
    particles.add(new Particle(r1, g1, b1, rad, origin));
  }
  
    public void addParticle(float r1, float g1, float b1, float rad, float x, float y) {
    particles.add(new Particle(r1, g1, b1, rad, new PVector(x,y)));
   
  }

  public void addParticle(Particle p) {
    particles.add(p);
  }

  // A method to test if the particle system still has particles
  public boolean dead() {
    if (particles.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "do_volcano_4" });
  }
}
