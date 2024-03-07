package app;

public class AgeStatus {

   private int count_21;

   private int count_16;

   private String state;

   private String indigenous_status;

   private String range;

   private int lga_code;
 
   private int lgaTot;

   private String gender;

   public AgeStatus(String state, int count_21, String indigenous_status, String range, int lga_code, int lgaTot, int count_16, String gender) {
      this.state = state;
      this.count_21 = count_21;
      this.indigenous_status = indigenous_status;
      this.range = range;
      this.lga_code = lga_code;
      this.lgaTot = lgaTot;
      this.count_16 = count_16;
      this.gender = gender;
   }

   public int getCode() {
      return lga_code;
   }

   public String getStatus() {
      return indigenous_status;
  }

  public int getCount21() {
      return count_21;
  }

  public int getCount16() {
      return count_16;
  }

  public String getRange() {
      return range;
  }

   public String getState() {
      return state;
   }
   
   public int getLGATotal() {
      return lgaTot;
   }

   public String getGender() {
      return gender;
   }

 }
