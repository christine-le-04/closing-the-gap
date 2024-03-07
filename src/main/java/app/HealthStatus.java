package app;

public class HealthStatus {
    private int lga_code;

    private String lganame;

    private int count_21;

    private int count_16;

    private int lgaTot;

    // private String column;

    private String sort;
 
    public HealthStatus(int lga_code, String lganame, int lgaTot, int count_21, int count_16) {
       this.lga_code = lga_code;
       this.lganame = lganame;
       this.lgaTot = lgaTot;
       this.count_21 = count_21;
       this.count_16 = count_16;
    }

    public HealthStatus(int lga_code, String lganame, int count_21, int count_16) {
       this.lga_code = lga_code;
       this.lganame = lganame;
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
        return lganame;
    }

    // public String getColumn() {
    //     return column;
    // }

    public String getSort() {
        return sort;
    }

    public int getLGATotal() {
        return lgaTot;
     }

}
