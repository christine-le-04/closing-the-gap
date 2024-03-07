package app;

public class Population {
   //  private int lga_code;

    private int count16;

    private int count21;

    private String state;
 
    public Population(String state, int count16, int count21) {
      //  this.lga_code = lga_code;
       this.state = state;
       this.count16 = count16;
       this.count21 = count21;
    }
    
   public int getCount16() {
      return count16;
   }

   public int getCount21() {
      return count21;
   }

   public String getState() {
      return state;
   }
 }