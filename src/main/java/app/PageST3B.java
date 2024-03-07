package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.2</title>";

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
            <div class='header-image2'>
                <div class ='header-text'>
                    <h1>The Changing Gap Between Indigenous and Non-Indigenous People from 2016 to 2021</h1>
                </div>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html += "<section id ='data'>";
        html += "<div class='container3'>";
        html += "<h1>Choose Specified Filters</h1>";
        html = html + "<form action='/page3B.html' method='post'>";
        html += "         <div class='form-group'>";
        html = html + "      <label for='table_drop'>Select the Table:</label>";
        html = html + "      <select id='table_drop' name='table_drop'>";
        html += "            <option></option>";
        html += "            <option value='age_table'>Age Range Table</option>";
        html += "            <option value='health_table'>Health Condition Table</option>";
        html += "            <option value='education_table'>Highest Education Table</option>";        
        html = html + "      </select>";
        html += "         </div>";
        html = html + "<br></br>";
        
        html = html + "<button type='submit' class='btn btn-primary'>Choose a Table</button>";
        html = html + "</form>";

        String lgaCode = context.formParam("lga_search");

        String selectedTable = context.formParam("table_drop"); 
        String status = context.formParam("statustype_drop");
        String condname = context.formParam("condname_drop");
        

        String indigstatus = context.formParam("statustype_drop");
        String agerange = context.formParam("agerange_drop");
        

        String indigstatus2 = context.formParam("statustype_drop");
        String heducation = context.formParam("edlevel_drop");

        String LGAnum = context.formParam("lga_amount");
        
        ///////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////
        
        if (selectedTable != null && !selectedTable.isEmpty()) {
            if (selectedTable.equals("age_table")) { 
            html = html + "<form action='/page3B.html' method='post'>";
            html += "         <div class='form-group'>";
            html = html + "      <label for='agerange_drop'>Select the Age Range:</label>";
            html = html + "      <select id='agerange_drop' name='agerange_drop'>";
            ArrayList<String> ageRange = jdbc.getAgeRange();
            for (String range : ageRange) {
                html += "<option>" + range + "</option>";
            }
            html = html + "      </select>";
            html += "         </div>";
            
            html = html + "   <div class='form-group'>";
            html = html + "      <label for='statustype_drop'>Select the Indigenous Status:</label>";
            html = html + "      <select id='statustype_drop' name='statustype_drop'>";
            ArrayList<String> indigTypes = jdbc.getStatus();
            for (String type : indigTypes) {
                html += "<option>" + type + "</option>";
            }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "<div class='form-group'>";
            html = html + "   <label for='lga_amount'>Amount of LGA's:</label>";
            html = html + "   <select id='lga_amount' name='lga_amount'>";
            String[] options = {"5", "6", "7", "8", "9", "10"};
            for (String option : options) {
                html = html + "      <option value='" + option + "'>" + option + "</option>";
            }
            html = html + "   </select>";
            html = html + "</div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='lga_search'>Search by LGA Code:</label>";
            html = html + "      <input type='text' id='lga_search' name='lga_search'>";
            html = html + "   </div>";
            html = html + "<br></br>";

            html = html + "<button type='submit' class='btn btn-primary'>Show Data</button>";
            html = html + "</form><br></br>";

            ///////////////////////////////////////////////////////////////////////////////////////

            } else if (selectedTable.equals("health_table")) {
            html = html + "<form action='/page3B.html' method='post'>";
            html = html + "   <div class='form-group'>";
            html = html + "      <label for='condname_drop'>Select the Health Condition:</label>";
            html = html + "      <select id='condname_drop' name='condname_drop'>";
            ArrayList<String> conditionName = jdbc.getCondName();
                for (String name : conditionName) {
                    html += "<option>" + name + "</option>";
            }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='statustype_drop'>Select the Indigenous Status:</label>";
            html = html + "      <select id='statustype_drop' name='statustype_drop'>";
            ArrayList<String> indigTypes = jdbc.getStatus();
            for (String type : indigTypes) {
                html += "<option>" + type + "</option>";
            }
            html = html + "      </select>";
            html += "         </div>";
            html = html + "<div class='form-group'>";
            html = html + "   <label for='lga_amount'>Amount of LGA's:</label>";
            html = html + "   <select id='lga_amount' name='lga_amount'>";
            String[] options = {"5", "6", "7", "8", "9", "10"};
            for (String option : options) {
                html = html + "      <option value='" + option + "'>" + option + "</option>";
            }
            html = html + "   </select>";
            html = html + "</div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='lga_search'>Search by LGA Code:</label>";
            html = html + "      <input type='text' id='lga_search' name='lga_search'>";
            html = html + "   </div>";
            html = html + "<br></br>";
            html = html + "<button type='submit' class='btn btn-primary'>Show Data</button>";
            html = html + "</form><br></br>";
            }
            
            ///////////////////////////////////////////////////////////////////////////////////////

            else if (selectedTable.equals("education_table")) {
            html = html + "<form action='/page3B.html' method='post'>";
            html = html + "   <div class='form-group'>";
            html = html + "      <label for='edlevel_drop'>Select the Highest Level of Education:</label>";
            html = html + "      <select id='edlevel_drop' name='edlevel_drop'>";
            ArrayList<String> nonSchool = jdbc.getAllSchoolComp();
                for (String nonCompletion : nonSchool) {
                    html += "<option>" + nonCompletion + "</option>";
            }

            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='statustype_drop'>Select the Indigenous Status:</label>";
            html = html + "      <select id='statustype_drop' name='statustype_drop'>";
            ArrayList<String> indigTypes = jdbc.getStatus();
            for (String type : indigTypes) {
                html += "<option>" + type + "</option>";
            }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "<div class='form-group'>";
            html = html + "   <label for='lga_amount'>Amount of LGA's:</label>";
            html = html + "   <select id='lga_amount' name='lga_amount'>";
            String[] options = {"5", "6", "7", "8", "9", "10"};
            for (String option : options) {
                html = html + "      <option value='" + option + "'>" + option + "</option>";
            }
            html = html + "   </select>";
            html = html + "</div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='lga_search'>Search by LGA Code:</label>";
            html = html + "      <input type='text' id='lga_search' name='lga_search'>";
            html = html + "   </div>";
            html = html + "<br></br>";
            html = html + "<button type='submit' class='btn btn-primary'>Show Data</button>";
            html = html + "</form><br></br>";
            }
        }
        html += "</div>";

        ///////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////

        if ((indigstatus != null) && (agerange != null)) {
            ArrayList<AgeStatus> asData = jdbc.getAgeLGA(indigstatus, agerange, lgaCode);
            html = html + "<h1>Age Demographic Statistics</h1>";
            html += "<p>The below table shows the results for the selected indigenous status of " + indigstatus + " and focuses on the age range of " + agerange + ". The raw data represents the total amount of people that fall under the Indigenous status and age range chosen within the specific LGA represented in each row. The most similar LGA's are presented underneath, and are based off of the difference between each LGA's filtered data, shown in its own column.</p>";
            html = html + "<table id='ageTable'>";
            html += "<th>LGA Code</th>";
            html += "<th>LGA State</th>";
            html += "<th>Indigenous Status </th>";
            html += "<th>Age Range</th>";
            html += "<th>Count</th>";
            html += "<th>Count Difference</th>";

                int i = 0;
                int num = Integer.parseInt(LGAnum);
                
            for (AgeStatus hcStat : asData) {
                while (i<num) {
                html = html + "<tr class='item'><td>" + hcStat.getCode() + "</td>";
                html += "<td>" + hcStat.getState() + "</td>";
                html += "<td>" + hcStat.getStatus() + "</td>";
                html += "<td>" + hcStat.getRange() + "</td>";
                html += "<td>" + hcStat.getCount21() + "</td>";
                html += "<td>" + hcStat.getLGATotal() + "</td></tr>";
                i += 1;
                break;
                }
            }
            html = html + "</table><br></br>";
        }

        if ((indigstatus != null) && (condname != null)) {
                ArrayList<HealthStatus2> healthData = jdbc.getHealthLGA(status, condname, lgaCode);
                html = html + "Health Condition Statistics</h1>";
                html += "<p>The below table shows the results for the selected indigenous status of " + indigstatus + " and focuses on the long term health condition of " + condname + ". The raw data represents the total amount of people that fall under the Indigenous status and long term health condition chosen within the specific LGA represented in each row. The most similar LGA's are presented underneath, and are based off of the difference between each LGA's filtered data, shown in its own column.</p>";
                html = html + "<table class='healthTable'>";
                html += "<th>LGA Code</th>";
                html += "<th>LGA State</th>";
                html += "<th>Indigenous Status </th>";
                html += "<th>Health Condition</th>";
                html += "<th>Count</th>";
                html += "<th>Count Difference</th>";
                
                int i = 0;
                int num = Integer.parseInt(LGAnum);
                
            for (HealthStatus2 hcStat : healthData) {
                while (i<num) {
                html = html + "<tr class='item'><td>" + hcStat.getCode() + "</td>";
                html += "<td>" + hcStat.getState() + "</td>";
                html += "<td>" + hcStat.getIndStatus() + "</td>";
                html += "<td>" + hcStat.getName() + "</td>";
                html += "<td>" + hcStat.getCount21() + "</td>";
                html += "<td>" + hcStat.getLGATotal() + "</td></tr>";
                i += 1;
                break;
                }
            }
            html = html + "</table>";
            html = html + "<br></br>";
        }
        
        if ((indigstatus2 != null) && (heducation != null)) {
                
                ArrayList<Education> searchedData = jdbc.getEducationLGA(status, heducation, lgaCode);

                html = html + "<h1>Highest Education Statistics</h1>";
                html += "<p>The below table shows the results for the selected indigenous status of " + indigstatus + " and focuses on the highest education level achieved being " + heducation + ". The raw data represents the total amount of people that fall under the Indigenous status and education level chosen within the specific LGA represented in each row. The most similar LGA's are presented underneath, and are based off of the difference between each LGA's filtered data, shown in its own column.</p>";
                html = html + "<table class='healthTable'>";
                html += "<th>LGA Code</th>";
                html += "<th>LGA State</th>";
                html += "<th>Indigenous Status </th>";
                html += "<th>Education Level</th>";
                html += "<th>Count</th>";
                html += "<th>Count Difference</th>";

                int i = 0;
                int num = Integer.parseInt(LGAnum);

            for (Education hcStat : searchedData) {
                while (i<num) {
                html = html + "<tr class='item'><td>" + hcStat.getCode() + "</td>";
                html += "<td>" + hcStat.getState() + "</td>";
                html += "<td>" + hcStat.getIndStatus() + "</td>";
                html += "<td>" + hcStat.getName() + "</td>";
                html += "<td>" + hcStat.getCount() + "</td>";
                html += "<td>" + hcStat.getLGATotal() + "</td></tr>";
                i += 1;
                break;
                }
            }
            html = html + "</table>";
            html = html + "<br></br>";
        }

        html = html + "</section>";
        

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
