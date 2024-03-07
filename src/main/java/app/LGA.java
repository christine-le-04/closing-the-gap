package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class LGA {
   // LGA Code
   private int lga_code;

   // LGA Name
   private String name;

   private String state;

   // LGA Year
   private int year;

   /**
    * Create an LGA and set the fields
    */
   public LGA(int lga_code, String state, String name, int year) {
      this.lga_code = lga_code;
      this.state = state;
      this.name = name;
      this.year = year;
   }

   public int getCode() {
      return lga_code;
   }

   public String getName() {
      return name;
   }

   public String getState() {
      return state;
   }
   
   public int getYear() {
      return year;
   }
}
