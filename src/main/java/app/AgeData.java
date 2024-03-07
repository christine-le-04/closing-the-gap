package app;

public class AgeData {
   private int lga_code;

   private int count_21;

   private int count_16;

   private String name;
 
   private int lgaTot;

   public AgeData (int lga_code, int lgaTot, int count_21, String name) {
      this.lga_code = lga_code;
      this.lgaTot = lgaTot;
      this.count_21 = count_21;
      this.name = name;
   }

   public AgeData (String name, int count_21, int lga_code, int count_16) {
      this.name = name;
      this.lga_code = lga_code;
      this.count_21 = count_21;
      this.count_16 = count_16;
   }

   public int getCode() {
      return lga_code;
   }

  public int getCount21() {
      return count_21;
  }

  public int getCount16() {
      return count_16;
  }

   public String getLGAName() {
      return name;
   }
   
   public int getLGATotal() {
      return lgaTot;
   }


 }
