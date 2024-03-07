package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    public static void sortEducationStatusList(ArrayList<Education> searchedData) {
        Collections.sort(searchedData, new Comparator<Education>() {
            @Override
            public int compare(Education e1, Education e2) {
                return Integer.compare(e2.getCount(), e1.getCount()); // Compare in reverse order
            }
        });
    }

    public static void sortByPercentage(ArrayList<Education> educationData) {
        Collections.sort(educationData, new Comparator<Education>() {
            @Override
            public int compare(Education e1, Education e2) {
                if (e1.getLGATotal() == 0 && e2.getLGATotal() == 0) {
                    return 0; // If both are N/A, maintain the order
                } else if (e1.getLGATotal() == 0) {
                    return 1; // e1 is N/A, so move it down
                } else if (e2.getLGATotal() == 0) {
                    return -1; // e2 is N/A, so move it down
                }
                
                double percentage1 = (e1.getCount() / (double) e1.getLGATotal()) * 100;
                double percentage2 = (e2.getCount() / (double) e2.getLGATotal()) * 100;
                return Double.compare(percentage2, percentage1);
            }
        });
    }

    @Override
    public void handle(Context context) throws Exception {
        

        JDBCConnection jdbc = new JDBCConnection();
        
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <ul>
                    <li><a href='/'>Closing the Gap</a></li>
                    <li><a href='mission.html'>Our Mission</a></li>
                    <li><a href='page2A.html'>Age/Health Data</a></li>
                    <li><a href='page2B.html'>School/Non-School Data</a></li>
                    <li><a href='page3A.html'>Data on The Gap</a></li>
                    <li><a href='page3B.html'>Similar LGAs</a></li>
                </ul>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header-img2'>
                <div class = 'header-txt'>
                    <h1>Data on Long-Term Health Conditions (Outcome 1) and Age Ranges in 2021</h1>
                </div>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        
        

        html += "<section id ='data'>";
        html += "<div class='container'>";
        
        html += "<h1>Choose Specified Filters</h1>";
        html = html + "<form action='/page2B.html' method='post'>";
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='lga_search'>Search by LGA Code:</label>";
        html = html + "      <input type='text' id='lga_search' name='lga_search'>";
        html = html + "   </div>";
        html = html + "      <label for='edlevel_drop'>Select the Highest Level of Education:</label>";
        html = html + "      <select id='edlevel_drop' name='edlevel_drop'>";
        ArrayList<String> nonSchool = jdbc.getAllSchoolComp();
            for (String nonCompletion : nonSchool) {
                html += "<option>" + nonCompletion + "</option>";
        }

        html = html + "      </select>";
        html += "         </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='statustype2_drop'>Select the Indigenous Status:</label>";
        html = html + "      <select id='statustype2_drop' name='statustype2_drop'>";
        ArrayList<String> indigTypes = jdbc.getStatus();
        for (String type : indigTypes) {
            html += "<option>" + type + "</option>";
        }

        html = html + "      </select>";
        html += "         </div>";
                
        html += "         <div class='form-group'>";
        html = html + "      <label for='gender_drop'>Select the Gender:</label>";
        html = html + "      <select id='gender_drop' name='gender_drop'>";
        
        ArrayList<String> gender = jdbc.getGender();
        for (String type : gender) {
            html += "<option>" + type + "</option>";
        }

        html = html + "      </select>";
        html += "         </div>";

        html += "         <div class='form-group'>";
        html = html + "      <label for='sort_drop'>Sort By:</label>";
        html = html + "      <select id='sort_drop' name='sort_drop'>";
        
        html += "<option>" + "LGA Code" + "</option>";
        html += "<option>" + "Raw Data" + "</option>";
        html += "<option>" + "Proportional Data" + "</option>";

        html = html + "      </select>";

        html += "         </div><br></br>";
        html = html + "<button type='submit' class='btn btn-primary'>Filter Data</button>";
        html = html + "</form><br></br>";


        String status = context.formParam("statustype2_drop");
        String edname = context.formParam("edlevel_drop");
        String selectsex = context.formParam("gender_drop");
        String sort = context.formParam("sort_drop");
        ArrayList<Education> EducationData = jdbc.getEducationStatus(status, edname, selectsex);

        String lgaCode = context.formParam("lga_search");
        ArrayList<Education> searchedData = jdbc.getEducationStatusByLGA(status, edname, selectsex, lgaCode);
        int raw = searchedData.get(0).getCount();
        int total = (searchedData.get(0).getLGATotal())-raw;

        html += "<div class='container3'>";
        html += "<div class='box3'>";
        html += "<html><head>";
        html += "<title>Pie Chart Example</title>";
        html += "<script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>";
        html += "</head><body>";
        html += "<canvas id=\"pieChart\" width=\"400\" height=\"400\"></canvas>";
        html += "<script>";
        html += "var ctx = document.getElementById('pieChart').getContext('2d');";
        html += "var data = {";
        html += "labels: ['Filtered', 'Unfiltered'],";
        html += "datasets: [{";
        html += "data: [" + raw + ", " + total + "],";
        html += "backgroundColor: ['#ff6384', '#36a2eb']";
        html += "}]};";
        html += "var options = {";
        html += "responsive: true";
        html += "};";
        html += "var pieChart = new Chart(ctx, {";
        html += "type: 'pie',";
        html += "data: data,";
        html += "options: options";
        html += "});";
        html += "</script>";
        html += "</body></html>";
        html += "</div>";
        html += "</div>";

        html = html + "<h1>Outcome 5: Reaching full learning potential via Education Level Statistics</h1>";
        html = html + "<table class='educationTable'>";
        html += "<th>LGA Code</th>";
        html += "<th>LGA State</th>";
        html += "<th>Indigenous Status</th>";
        html += "<th>Highest Education Level</th>";
        html += "<th>Raw Data</th>";
        html += "<th>Proportional Data</th>";
        
        if (sort != null) {
            if (sort.equals("Raw Data")) {
                sortEducationStatusList(EducationData);
            } else if (sort.equals("Proportional Data")) {
                sortByPercentage(EducationData);
            }
        }

        for (Education hcStat : EducationData) {
            html = html + "<tr class='item'><td>" + hcStat.getCode() + "</td>";
            html += "<td>" + hcStat.getState() + "</td>";
            html += "<td>" + hcStat.getIndStatus() + "</td>";
            html += "<td>" + hcStat.getName() + "</td>";
            html += "<td>" + hcStat.getCount() + "</td>";
            if (hcStat.getLGATotal() == 0) {
                html += "<td>N/A</td>";
            } else {
                double percentage = ((double) hcStat.getCount() / hcStat.getLGATotal()) * 100;
                String decimalPercent = String.format("%.2f", percentage);
                html += "<td>" + decimalPercent + "%</td></tr>";
            }
        }
        html = html + "</table>";
        html = html + "<br></br>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class ='footer'>
                <div class ='column'>
                    <h3>Acknowledgement</h3>
                    <p>We acknowledge Aboriginal and Torres Strait Islander people as the Traditional 
                    Custodians of the land and acknowledge and pay respect to their Elders, past and present.</p>
                </div>
                <div class ='column'>
                    <h3>Contact Us</h3>
        """;

        ArrayList<Contact> contactInfo = jdbc.getContact();
        html += "<ul>";

        for (Contact conStat : contactInfo) {
            html += "<li>" + conStat.getName() + ": s" + conStat.getID() + "@student.rmit.edu.au" + "</li>";
        }

        html += "</ul></div>";

        html += """
                    </div>
                </div>
            </div>
        </section>
        """;

        html += "<script>";
        html += """
        document.addEventListener('DOMContentLoaded', function(event) { 
            var scrollpos = localStorage.getItem('scrollpos');
            if (scrollpos) window.scrollTo(0, scrollpos);
        });

        window.onbeforeunload = function(e) {
            localStorage.setItem('scrollpos', window.scrollY);
        };


        """;
        html += "</script>";


        html = html + "</body>" + "</html>";



        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
