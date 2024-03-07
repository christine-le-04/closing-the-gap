package app;

public class EducationStatus {

   private int count_21;

   private int count_16;

   private int lga_code;

   private String name;

   public EducationStatus(String name, int lga_code, String indigenous_status, String edulvl, int count_21, int count_16) {
      this.name = name; 
      this.count_21 = count_21;
      this.lga_code = lga_code;
      this.count_16 = count_16;
   }

   public int getCode() {
      return lga_code;
   }

   public String getStateName() {
      return name;
   }

  public int getCount21() {
      return count_21;
  }

  public int getCount16() {
      return count_16;
  }

 }
