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
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";


    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Outcome 1 and 5 2021 Data</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";
        html += "<script src='https://www.kryogenix.org/code/browser/sorttable/sorttable.js'></script>";


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
            <div class='header-img'>
                <div class = 'header-txt'>
                    <h1>Data on Long-Term Health Conditions (Outcome 1) and Age Ranges in 2021</h1>
                </div>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        // html = html + """
        //     <section id='state-territory'>
        //         <div class='container'>
        //             <h2>Currently a placeholder. Might get rid of this section</h2>
        //             <div class='row-align'>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Australia</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">New South Wales</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Victoria</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Queensland</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">South Australia</a></h3>
        //                     </div>
        //                 </div>
        //             </div>

        //             <div class='row-align'>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Western Australia</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Tasmania</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Northern Territories</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Australian Capital Territories</a></h3>
        //                     </div>
        //                 </div>
        //                 <div class='box-padding'>
        //                     <div class='box-text'>
        //                         <h3><a href="/test.html">Other Territories</a></h3>
        //                     </div>
        //                 </div>
        //             </div>
        //         </div>
        //     </section> 
        //     """;
        
        html += "<section id ='data'>";
        html += "<div class='container'>";
        html += "<h2>Data</h2>";
        html += """
            <p>The data in the table below focuses on the 2021 statistics that revolve around the 1st socio-economic 
            outcome of a long and healthy life, focusing on health conditions, and statistics that revolve around an individual's age. 
            Regarding the section about proportional data, it is calculated via either the Indigenous or non-Indigenous population of 
            the specific LGA.</p>
            """;
        
        html += "<h1>Choose Specified Filters</h1>";
        html = html + "<form action='/page2A.html' method='post'>";
        html += "         <div class='form-group'>";
        html = html + "      <label for='table_drop'>Select the Table:</label>";
        html = html + "      <select id='table_drop' name='table_drop'>";
        html += "            <option></option>";
        html += "            <option value='age_table'>Age Range Table</option>";
        html += "            <option value='health_table'>Health Condition Table</option>";
        html = html + "      </select>";
        html += "         </div><br></br>";
        html = html + "<button type='submit' class='btn btn-primary'>Choose a Table</button>";
        html = html + "</form><br></br>";

        String selectedTable = context.formParam("table_drop"); 
        String status = context.formParam("statustype_drop");
        String condname = context.formParam("condname_drop");
        String agerange = context.formParam("agerange_drop");
        String state = context.formParam("state_drop");
        String sort = context.formParam("sort_drop");

        jdbc.getAgeData21(status, agerange, state, sort);        
        jdbc.getHealthStatus21(status, condname, state, sort);
        
        if (selectedTable != null && !selectedTable.isEmpty()) {
            if (selectedTable.equals("age_table")) { 
            html = html + "<form action='/page2A.html' method='post'>";
            html += "         <div class='form-group'>";
            html = html + "      <label for='state_drop'>Select the State/Territory:</label>";
            html = html + "      <select id='state_drop' name='state_drop'>";
                ArrayList<String> stateName = jdbc.getStateDrop();
                for (String stname : stateName) {
                    html += "<option>" + stname + "</option>";
                }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='statustype_drop'>Select the Indigenous Status:</label>";
            html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                for (String type : indigTypes) {
                    if (type.equals("indig")) {
                        html += "<option value= 'indig'>Indigenous</option>";
                    } else if (type.equals("non_indig")) {
                        html += "<option value='non_indig'>Non-Indigenous</option>";
                    } else {
                        html += "<option value='indig_ns'>Status Not Stated</option>";
                    }
                }
            html = html + "      </select>";
            html += "         </div>";

            html += "         <div class='form-group'>";
            html = html + "      <label for='agerange_drop'>Select the Age Range:</label>";
            html = html + "      <select id='agerange_drop' name='agerange_drop'>";
                ArrayList<String> ageRange = jdbc.getAgeRange();
                for (String range : ageRange) {
                    if (range.equals("_0_4")) {
                        html += "<option value = '_0_4'>0 - 4 years old</option>";
                    } else if (range.equals("_5_9")) {
                        html += "<option value = '_5_9'>5 - 9 years old</option>";
                    } else if (range.equals("_10_14")) {
                        html += "<option value = '_10_14'>10 - 14 years old</option>";
                    } else if (range.equals("_15_19")) {
                        html += "<option value = '_15_19'>15 - 19 years old</option>";
                    } else if (range.equals("_20_24")) {
                        html += "<option value = '_20_24'>20 - 24 years old</option>";
                    } else if (range.equals("_25_29")) {
                        html += "<option value = '_25_29'>25 - 29 years old</option>";
                    } else if (range.equals("_30_34")) {
                        html += "<option value = '_30_34'>30 - 34 years old</option>";
                    } else if (range.equals("_35_39")) {
                        html += "<option value = '_35_39'>35 - 39 years old</option>";
                    } else if (range.equals("_40_44")) {
                        html += "<option value = '_40_44'>40 - 44 years old</option>";
                    } else if (range.equals("_45_49")) {
                        html += "<option value = '_45_49'>45 - 49 years old</option>";
                    } else if (range.equals("_50_54")) {
                        html += "<option value = '_50_54'>50 - 54 years old</option>";
                    } else if (range.equals("_55_59")) {
                        html += "<option value = '_55_59'>55 - 59 years old</option>";
                    } else if (range.equals("_60_64")) {
                        html += "<option value = '_60_64'>60 - 64 years old</option>";
                    } else {
                        html += "<option value = '_65_yrs_ov'>65+ years old</option>";
                    }
                }
            html = html + "      </select>";
            html += "         </div>";
            
            html = html + "   <div class='form-group'>";
            html = html + "      <label for='sort_drop'>Sort the Table By:</label>";
            html = html + "      <select id='sort_drop' name='sort_drop'>";
            html += "<option>Default (LGA Ascending)</option>";
            html += "<option>Raw Data Ascending</option>";
            html += "<option>Raw Data Descending</option>";
            html += "<option>Proportional Data Ascending</option>";
            html += "<option>Proportional Data Descending</option>";
            html = html + "      </select>";
            html += "         </div><br></br>";

            html = html + "<button type='submit' class='btn btn-primary'>Show Data</button>";
            html = html + "</form>";
            } else if (selectedTable.equals("health_table")) {
            html = html + "<form action='/page2A.html' method='post'>";
            html += "         <div class='form-group'>";
            html = html + "      <label for='state_drop'>Select the State/Territory:</label>";
            html = html + "      <select id='state_drop' name='state_drop'>";
                ArrayList<String> stateName = jdbc.getStateDrop();
                for (String stname : stateName) {
                    html += "<option>" + stname + "</option>";
                }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='statustype_drop'>Select the Indigenous Status:</label>";
            html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                for (String type : indigTypes) {
                    if (type.equals("indig")) {
                        html += "<option value= 'indig'>Indigenous</option>";
                    } else if (type.equals("non_indig")) {
                        html += "<option value='non_indig'>Non-Indigenous</option>";
                    } else {
                        html += "<option value='indig_ns'>Status Not Stated</option>";
                    }
                }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='condname_drop'>Select the Health Condition:</label>";
            html = html + "      <select id='condname_drop' name='condname_drop'>";
                ArrayList<String> conditionName = jdbc.getCondName();
                    for (String name : conditionName) {
                        if (name.equals("arthritis")) {
                            html += "<option value = 'arthritis'>Arthritis</option>";
                        } else if (name.equals("asthma")) {
                            html += "<option value = 'asthma'>Asthma</option>";
                        } else if (name.equals("cancer")) {
                            html += "<option value = 'cancer'>Cancer</option>";
                        } else if (name.equals("dementia")) {
                            html += "<option value = 'dementia'>Dementia</option>";
                        } else if (name.equals("diabetes")) {
                            html += "<option value = 'diabetes'>Diabetes</option>";
                        } else if (name.equals("heartdisease")) {
                            html += "<option value = 'heartdisease'>Heart Disease</option>";
                        } else if (name.equals("kidneydisease")) {
                            html += "<option value = 'kidneydisease'>Kidney Disease</option>";
                        } else if (name.equals("lungcondition")) {
                            html += "<option value = 'lungcondition'>Lung Condition</option>";
                        } else if (name.equals("mentalhealth")) {
                            html += "<option value = 'mentalhealth'>Mental Health</option>";
                        } else if (name.equals("stroke")) {
                            html += "<option value = 'stroke'>Stroke</option>";
                        } else {
                            html += "<option value = 'other'>Other</option>";
                        } 
                    }
            html = html + "      </select>";
            html += "         </div>";

            html = html + "   <div class='form-group'>";
            html = html + "      <label for='sort_drop'>Sort the Table By:</label>";
            html = html + "      <select id='sort_drop' name='sort_drop'>";
            html += "<option>Default (LGA Ascending)</option>";
            html += "<option>Raw Data Ascending</option>";
            html += "<option>Raw Data Descending</option>";
            html += "<option>Proportional Data Ascending</option>";
            html += "<option>Proportional Data Descending</option>";
            html = html + "      </select>";
            html += "         </div><br></br>";

            html = html + "<button type='submit' class='btn btn-primary'>Show Data</button>";
            html = html + "</form>";
            }
        }
        
        if ((status != null) && (agerange != null) && (state != null)) {
            ArrayList<AgeData> asData = jdbc.getAgeData21 (status, agerange, state, sort);
            html = html + "<h1>Age Demographic Statistics</h1>";
            if (status.equals("indig")) {
                status = "Indigenous";
            } else if (status.equals("non_indig")) {
                status = "Non-Indigenous";
            } else {
                status = "Not Stated";
            }

            if (agerange.equals("_0_4")) {
                agerange = "0 - 4 years old";
            } else if (agerange.equals("_5_9")) {
                agerange = "5 - 9 years old";
            } else if (agerange.equals("_10_14")) {
                agerange = "10 - 14 years old";
            } else if (agerange.equals("_15_19")) {
                agerange = "15 - 19 years old";
            } else if (agerange.equals("_20_24")) {
                agerange = "20 - 24 years old";
            } else if (agerange.equals("_25_29")) {
                agerange = "25 - 29 years old";
            } else if (agerange.equals("_30_34")) {
                agerange = "30 - 34 years old";
            } else if (agerange.equals("_35_39")) {
                agerange = "35 - 39 years old";
            } else if (agerange.equals("_40_44")) {
                agerange = "40 - 44 years old";
            } else if (agerange.equals("_45_49")) {
                agerange = "45 - 49 years old";
            } else if (agerange.equals("_50_54")) {
                agerange = "50 - 54 years old";
            } else if (agerange.equals("_55_59")) {
                agerange = "55 - 59 years old";
            } else if (agerange.equals("_60_64")) {
                agerange = "60 - 64 years old";
            } else {
                agerange = "65+ years old";
            }
            html += "<p>The below table shows the results for the selected filters:<br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;State/Territory: <b>" + state + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Indigenous Status: <b>" + status + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Age Range: <b>" + agerange + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Table is Sorted By: <b>" + sort + "</b><br></br>";
            html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
            html += "The raw data represents the total amount of people that fall under the Indigenous status and age range chosen within the specific LGA represented in each row. The proportional data is calculated via the raw data divided by the total " + status + " population in each LGA. Due to this and the fact that the percentages are rounded to two decimal points, the percentages shown will not equal to 100%.";
            html += " Note: You can also click on the table headers to sort the data based on the columns.</p>";
            html = html + "<table id='ageTable' class='sortable'>";
            html += "<th>#</th>";
            html += "<th>LGA Code (Name)</th>";
            html += "<th>Raw Data</th>";
            html += "<th>Proportional Data</th>";
            int rowNumber = 1;

            for (AgeData asStat : asData) {
                html = html + "<tr class='item'><td>" + rowNumber + "</td>";
                html = html + "<td>" + asStat.getCode() + " (" + asStat.getLGAName() + ")</td>";
                html += "<td>" + asStat.getCount21() + "</td>";
                if (asStat.getLGATotal() == 0) {
                    html += "<td>N/A</td>";
                } else {
                    double percentage = ((double) asStat.getCount21() / asStat.getLGATotal()) * 100;
                    String decimalPercent = String.format("%.2f", percentage);
                    html += "<td>" + decimalPercent + "%</td></tr>";
                }
                rowNumber++;
            }
            html = html + "</table><br></br>";
        }

        if ((status != null) && (condname != null) && (state != null)) {
                ArrayList<HealthStatus> healthData = jdbc.getHealthStatus21(status, condname, state, sort);
                html = html + "<h1>Outcome 1: Long and Healthy Life via Health Condition Statistics</h1>";
                if (status.equals("indig")) {
                    status = "Indigenous";
                } else if (status.equals("non_indig")) {
                    status = "Non-Indigenous";
                } else {
                    status = "Not Stated";
                }

                if (condname.equals("arthritis")) {
                    condname = "Arthritis";
                } else if (condname.equals("asthma")) {
                    condname = "Asthma";
                } else if (condname.equals("cancer")) {
                    condname = "Cancer";
                } else if (condname.equals("dementia")) {
                    condname = "Dementia";
                } else if (condname.equals("diabetes")) {
                    condname = "Diabetes";
                } else if (condname.equals("heartdisease")) {
                    condname = "Heart Disease";
                } else if (condname.equals("kidneydisease")) {
                    condname = "Kidney Disease";
                } else if (condname.equals("lungcondition")) {
                    condname = "Lung Condition";
                } else if (condname.equals("mentalhealth")) {
                    condname = "Mental Health";
                } else if (condname.equals("stroke")) {
                    condname = "Stroke";
                } else {
                    condname = "Other";
                } 

                html += "<p>The below table shows the results for the selected filters:<br></br>";
                html += "&nbsp;&nbsp;&nbsp;&nbsp;State/Territory: <b>" + state + "</b><br></br>";
                html += "&nbsp;&nbsp;&nbsp;&nbsp;Indigenous Status: <b>" + status + "</b><br></br>";
                html += "&nbsp;&nbsp;&nbsp;&nbsp;Long-Term Health Condition: <b>" + condname + "</b><br></br>";
                html += "&nbsp;&nbsp;&nbsp;&nbsp;Table is Sorted By: <b>" + sort + "</b><br></br>";
                html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
                html += "The raw data represents the total amount of people that fall under the Indigenous status and long term health condition chosen within the specific LGA represented in each row. The proportional data is calculated via the raw data divided by the total " + status + " population in each LGA. Due to this and the fact that the percentages are rounded to two decimal points, the percentages shown will not equal to 100%.";
                html += " Note: You can also click on the table headers to sort the data based on the columns.</p>";
                html = html + "<table id='healthTable' class='sortable'>";
                html += "<th>#</th>";
                html += "<th>LGA Code (Name)</th>";
                html += "<th>Raw Data</th>";
                html += "<th>Proportional Data</th>";
                int rowNumber = 1;

            for (HealthStatus hcStat : healthData) {
                html = html + "<tr class='item'><td>" + rowNumber + "</td>";
                html = html + "<td>" + hcStat.getCode() + " (" + hcStat.getLGAName() + ")</td>";
                html += "<td>" + hcStat.getCount21() + "</td>";
                if (hcStat.getLGATotal() == 0) {
                    html += "<td>N/A</td>";
                } else {
                    double percentage = ((double) hcStat.getCount21() / hcStat.getLGATotal()) * 100;
                    String decimalPercent = String.format("%.2f", percentage);
                    html += "<td>" + decimalPercent + "%</td></tr>";
                }
                rowNumber++;
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

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}