package app;

public class Education {
    private int lga_code;
 
    private String indigenous_status;

    private String name;

    private int count;

    private String state;

    private int lgaTot;
 
    public Education(int lga_code, String state, String indigenous_status, String name, int count, int lgaTot) {
       this.lga_code = lga_code;
       this.state = state;
       this.indigenous_status = indigenous_status;
       this.name = name;
       this.count = count;
       this.lgaTot = lgaTot;
    }

    public int getCode() {
        return lga_code;
    }

    public String getIndStatus() {
        return indigenous_status;
    }

    public int getCount() {
        return count;
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
}
