/*
Anderson Lu
Cse 143 AN with May Wang
Homework 2, GuitarHero
GuitarString represents the vibration of the guitar
strings when they are pucked.
*/
import java.util.*;

public class GuitarString {
   //Creates the queue for the ring buffer of a guitar stroke, "buf".
   //Creates a class constant for the decay factor of the vibrations.
   private Queue<Double> buf;
   public static final double DECAY = 0.996;
   
   /*
   Takes a Double "frequency" as a parameter and creates
   a guitar string based on a requested frequency. Sets all
   of the guitar string values to 0, representing the strings
   at rest.
   
   Pre: "frequency" >= 1.0 and ring buffer size >= 2.0.
   Throws an IllegalArgumentException if preconditions are not met.
   
   Post: Initializes the guitar strings at rest
   by setting all values of the queue to 0.
   */
   public GuitarString (double frequency) {
      //Calculates the size of ring buffer
      int size = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      //Checks if the frequency is 0 or negative.
      //Checks if ringbuffer size is below 2.
      if (frequency < 1.0 || size < 2.0) {
         throw new IllegalArgumentException();
      }
      buf = new LinkedList<Double>();
      //Enqueue 0s to the string values.
      for (int i = 0; i < size; i++) {
         buf.add(0.0);
      }
   }
   
   /*
   Takes a Double[] "init" as a parameter and adds the
   values in the array "init" to the ring buffer queue "buf".
   
   Pre: The length of "init" >= 2.
   Throws an IllegalArgumentException if precondition is not met.
   
   Post: Adds the values in the array "init" to the queue "buf".
   */
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      buf = new LinkedList<Double>();
      //Adds the content in the array to the ring buffer.
      for (int i = 0; i < init.length; i++) {
         buf.add(init[i]);
      }
   }
   
   /*
   Replaces the values in the queue of the ring buffer, "buf"
   with random values ranging from -0.5 and 0.5. Including
   -0.5 and excluding 0.5 in the range. -0.5 <= value < 0.5
   */
   public void pluck() {
      Random rand = new Random();
      for (int i = 0; i < buf.size(); i++) {
         //Calculates a random value from -0.5 <= value < 0.5
         double random = rand.nextDouble() - 0.5;
         //Adds random value to end of queue while removing value at front
         buf.add(random);
         buf.remove();
      }
   }
   
   /*
   Adds the average of the first 2 samples queued in the
   ring buffer multiplied by the decay factor, to the end of
   the ring buffer. While deleting first sample at the front
   of the ring buffer.
   */
   public void tic () {
      //Declares the samples while removing the first sample.
      double sample1 = buf.remove();
      double sample2 = buf.peek();
      //Averages the samples and multiplies by decay factor.
      double avg = ((sample1 + sample2) / 2) * DECAY;
      buf.add(avg);
   }
   
   //Returns the current sample.
   public double sample () {
      return buf.peek();
   }
}