/**
 * Interactive Organic Volcano built using Daniel Shiffman's Simple Particle System & minim Audio Library 
 * by Uttam Grandhi (@2kool2resist) with Prayas Abhinav (@prayasabhinav) #Digital-Organic #Lucid .
 * 
 * Particles are generated with the sound input through the microphone.
 * They fall with gravity and fade out over time.
 * Initial size of each particle is based on the amplitude of Input Frequency.
 * The particles pulsate in a sine curve throughout. 
 */
 

//importing audio & video libraries
import ddf.minim.*;
import ddf.minim.analysis.*;
import processing.video.*;

MovieMaker mm;

Minim minim;
AudioInput in;
FFT fft;

ParticleSystem ps;

 float r1, reds;
 float g1 = 255, greens;
 float b1, blues;
   
void setup() {
  size(640, 360);
  colorMode(RGB, 255, 255, 255, 100);
  ps = new ParticleSystem(1, new PVector(width/2,height/2,0));
  smooth();
  
     
   minim = new Minim(this);
   minim.debugOn();
  
   // get a line in from Minim, default bit depth is 16
   in = minim.getLineIn(Minim.STEREO, 512);
   
   fft = new FFT(in.bufferSize(),in.sampleRate());
   mm = new MovieMaker(this, width, height, "volcano-ug.mov");
}

void draw() {
  
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
   float pos = 1.5*fft.getFreq(i);
   
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
    mm.addFrame();
}

void keyPressed() {
  if (key == ' ') {
    // Finish the movie if space bar is pressed
    mm.finish();
    // Quit running the sketch once the file is written
    exit();
  }
}


