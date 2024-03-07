package app;

public class HealthStatus2 {
    private int lga_code;
 
    private String indigenous_status;

    private String name;

    private int count_21;

    private int count_16;

    private String state;

    private int lgaTot;

    private String gender;
 
    public HealthStatus2(int lga_code, String state, String indigenous_status, String name, int count_21, int lgaTot, int count_16, String gender) {
       this.lga_code = lga_code;
       this.state = state;
       this.indigenous_status = indigenous_status;
       this.name = name;
       this.count_21 = count_21;
       this.lgaTot = lgaTot;
       this.count_16 = count_16;
       this.gender = gender;
    }

    public int getCode() {
        return lga_code;
    }

    public String getIndStatus() {
        return indigenous_status;
    }

    public int getCount21() {
        return count_21;
    }

    public int getCount16() {
        return count_16;
    }

    public String getName() {
        return name;
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
