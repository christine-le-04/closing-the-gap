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
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

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
            <div class='header-image'>
                <div class ='header-text'>
                    <h1>The Changing Gap Between Indigenous and Non-Indigenous People from 2016 to 2021</h1>
                </div>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <section id='tableFilter'>
                <div class='container'>
                <h2>Categorisable Data</h2>
        """;

        html = html + "<form action='/page3A.html' method='post'>";

        // datasets 1 - 4 ... must separate 
        html += "         <div class='form-group'>";
        html = html + "      <label for='dataset_drop'>Dataset:</label>";
        html = html + "      <select id='dataset_drop' name='dataset_drop'>";
        html += "<option value='higher_ed'>Higher Education Completion</option>";
        html += "<option value='hs'>School Completion (Pre-Higher Education):</option>";
        html += "<option value='health'>Long-Term Health Issues</option>";
        html += "<option value='age'>Indigenous Status by Age</option>";
        html = html + "      </select>";
        html += "         </div><br></br>";
        html = html + "<button type='submit' class='btn btn-primary'>Choose a Dataset</button>";
        html = html + "</form><br></br>";

        String selectedData = context.formParam("dataset_drop"); 
        String status = context.formParam("statustype_drop");
        String edulvl = context.formParam("nonschool_drop");
        String state = context.formParam("state_drop");
        String hslvl = context.formParam("schoolyr_drop");
        String condname = context.formParam("condname_drop");
        String agerange = context.formParam("agerange_drop");
        String gender = context.formParam("gendertype_drop");
        jdbc.getAgeBothData(status, agerange, state, gender);
        jdbc.getHealthStatusBoth(status, condname, state, gender);
        jdbc.getNonSchool(status, edulvl, state, gender);
        jdbc.getSchool(status, hslvl, state, gender);

        if (selectedData != null && !selectedData.isEmpty()) {
            if (selectedData.equals("higher_ed")) {
                html = html + "<form action='/page3A.html' method='post'>";
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
                html = html + "      <label for='statustype_drop'>Indigenous Status:</label>";
                html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                    for (String type : indigTypes) {
                        if (type.equals("indig")) {
                            html += "<option value='indig'>Indigenous</option>";
                        } else if (type.equals("non_indig")) {
                            html += "<option value='non_indig'>Non-Indigenous</option>";
                        } else if (type.equals("indig_ns")) {
                            html += "<option value='indig_ns'>Status Not Stated</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";
                html = html + "   <div class='form-group'>";
                html = html + "      <label for='gendertype_drop'>Sex:</label>";
                html = html + "      <select id='gendertype_drop' name='gendertype_drop'>";
                ArrayList<String> sgender = jdbc.getGender();
                    for (String sex : sgender) {
                        if (sex.equals("f")) {
                            html += "<option value= 'f'>Female</option>";
                        } else {
                            html += "<option value= 'm'>Male</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";

                html = html + "   <div class='form-group'>";
                html = html + "      <label for='nonschool_drop'>Non-School Completion:</label>";
                html = html + "      <select id='nonschool_drop' name='nonschool_drop'>";
                ArrayList<String> nonCompletion = jdbc.getNonSchoolComp();
                    for (String category : nonCompletion) {
                        if (category.equals("pd_gd_gc")) {
                            html += "<option value='pd_gd_gc'>Postgraduate Degree Level, Graduate Diploma and Graduate Certificate Level</option>";
                        } else if (category.equals("bd")) {
                            html += "<option value='bd'>Bachelor Degree Level</option>";
                        } else if (category.equals("adip_dip")) {
                            html += "<option value='adip_dip'>Advanced Diploma and Diploma Level</option>";
                        } else if (category.equals("ct_i_ii")) {
                            html += "<option value='ct_i_ii'>Certificate I & II Level</option>";
                        } else {
                            html += "<option value='ct_iii_iv'>Certificate III & IV Level</option>";
                        }  
                    }
                html = html + "      </select>";
                html += "         </div><br></br>";
                html = html + "<button type='submit' class='btn btn-primary'>Filter Results</button>";
                html = html + "</form>";
            } 

            else if (selectedData.equals("hs")) {
                html = html + "<form action='/page3A.html' method='post'>";
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

                html = html + "      <label for='statustype_drop'>Indigenous Status:</label>";
                html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                    for (String type : indigTypes) {
                        if (type.equals("indig")) {
                            html += "<option value='indig'>Indigenous</option>";
                        } else if (type.equals("non_indig")) {
                            html += "<option value='non_indig'>Non-Indigenous</option>";
                        } else if (type.equals("indig_ns")) {
                            html += "<option value='indig_ns'>Status Not Stated</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";
                html = html + "   <div class='form-group'>";

                html = html + "      <label for='gendertype_drop'>Sex:</label>";
                html = html + "      <select id='gendertype_drop' name='gendertype_drop'>";
                ArrayList<String> sgender = jdbc.getGender();
                    for (String sex : sgender) {
                        if (sex.equals("f")) {
                            html += "<option value = 'f'>Female</option>";
                        } else {
                            html += "<option value = 'm'>Male</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";

                html = html + "   <div class='form-group'>";
                html = html + "      <label for='schoolyr_drop'>School Completion:</label>";
                html = html + "      <select id='schoolyr_drop' name='schoolyr_drop'>";
                ArrayList<String> schoolComp = jdbc.getSchoolComp();
                    for (String category : schoolComp) {
                        if (category.equals("did_not_go_to_school")) {
                            html += "<option value= 'did_not_go_to_school'>Did Not Go To School</option>";
                        } else if (category.equals("y8_below")) {
                            html += "<option value= 'y8_below'>Year 8 or Below</option>";
                        } else if (category.equals("y9_equivalent")) {
                            html += "<option value= 'y9_equivalent'>Year 9 Equivalent</option>";
                        } else if (category.equals("y10_equivalent")) {
                            html += "<option value= 'y10_equivalent'>Year 10 Equivalent</option>";
                        } else if (category.equals("y11_equivalent")) {
                            html += "<option value= 'y11_equivalent'>Year 11 Equivalent</option>";
                        } else {
                            html += "<option value= 'y12_equivalent'>Year 12 Equivalent</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div><br></br>";
                html = html + "<button type='submit' class='btn btn-primary'>Filter Results</button>";
                html = html + "</form>";
            }

            else if (selectedData.equals("health")) {
                html = html + "<form id='health_table' action='/page3A.html' method='post'>";
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
                html = html + "      <label for='statustype_drop'>Indigenous Status:</label>";
                html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                    for (String type : indigTypes) {
                        if (type.equals("indig")) {
                            html += "<option value= 'indig'>Indigenous</option>";
                        } else if (type.equals("non_indig")) {
                            html += "<option value='non_indig'>Non-Indigenous</option>";
                        } else if (type.equals("indig_ns")) {
                            html += "<option value='indig_ns'>Status Not Stated</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";

                html = html + "   <div class='form-group'>";
                html = html + "      <label for='gendertype_drop'>Sex:</label>";
                html = html + "      <select id='gendertype_drop' name='gendertype_drop'>";
                ArrayList<String> sgender = jdbc.getGender();
                    for (String sex : sgender) {
                        if (sex.equals("f")) {
                            html += "<option value= 'f'>Female</option>";
                        } else {
                            html += "<option value= 'm'>Male</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";

                html += "<div class='form-group'>";
                html = html + "      <label for='condname_drop'>Long-Term Health Condition:</label>";
                html = html + "      <select id='condname_drop' name='condname_drop'>";
                ArrayList<String> conditionName = jdbc.getCondName();
                    for (String name : conditionName) {
                        if (name.equals("arthritis")) {
                            html += "<option value= 'arthritis'>Arthritis</option>";
                        } else if (name.equals("asthma")) {
                            html += "<option value= 'asthma'>Asthma</option>";
                        } else if (name.equals("cancer")) {
                            html += "<option value= 'cancer'>Cancer</option>";
                        } else if (name.equals("dementia")) {
                            html += "<option value= 'dementia'>Dementia</option>";
                        } else if (name.equals("diabetes")) {
                            html += "<option value= 'diabetes'>Diabetes</option>";
                        } else if (name.equals("heartdisease")) {
                            html += "<option value= 'heartdisease'>Heart Disease</option>";
                        } else if (name.equals("kidneydisease")) {
                            html += "<option value= 'kidneydisease'>Kidney Disease</option>";
                        } else if (name.equals("lungcondition")) {
                            html += "<option value= 'lungcondition'>Lung Condition</option>";
                        } else if (name.equals("mentalhealth")) {
                            html += "<option value= 'mentalhealth'>Mental Health</option>";
                        } else if (name.equals("stroke")) {
                            html += "<option value= 'stroke'>Stroke</option>";
                        } else {
                            html += "<option value= 'other'>Other</option>";
                        } 
                    }
                html = html + "      </select>";
                html += "         </div><br></br>";
                html = html + "<button type='submit' class='btn btn-primary'>Filter Results</button>";
                html = html + "</form>";
               
            } else if (selectedData.equals("age")) {
                html = html + "<form action='/page3A.html' method='post'>";
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
                html = html + "      <label for='statustype_drop'>Indigenous Status:</label>";
                html = html + "      <select id='statustype_drop' name='statustype_drop'>";
                ArrayList<String> indigTypes = jdbc.getStatus();
                    for (String type : indigTypes) {
                        if (type.equals("indig")) {
                            html += "<option value= 'indig'>Indigenous</option>";
                        } else if (type.equals("non_indig")) {
                            html += "<option value='non_indig'>Non-Indigenous</option>";
                        } else if (type.equals("indig_ns")) {
                            html += "<option value='indig_ns'>Status Not Stated</option>";
                        }
                    }
                html = html + "      </select>";
                html += "         </div>";
                html = html + "   <div class='form-group'>";
                html = html + "      <label for='gendertype_drop'>Sex:</label>";
                html = html + "      <select id='gendertype_drop' name='gendertype_drop'>";
                ArrayList<String> sgender = jdbc.getGender();
                    for (String sex : sgender) {
                        if (sex.equals("f")) {
                            html += "<option value= 'f'>Female</option>";
                        } else {
                            html += "<option value= 'm'>Male</option>";
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
                        html += "<option value= '_0_4'>0 - 4 years old</option>";
                    } else if (range.equals("_5_9")) {
                        html += "<option value= '_5_9'>5 - 9 years old</option>";
                    } else if (range.equals("_10_14")) {
                        html += "<option value= '_10_14'>10 - 14 years old</option>";
                    } else if (range.equals("_15_19")) {
                        html += "<option value= '_15_19'>15 - 19 years old</option>";
                    } else if (range.equals("_20_24")) {
                        html += "<option value= '_20_24'>20 - 24 years old</option>";
                    } else if (range.equals("_25_29")) {
                        html += "<option value= '_25_29'>25 - 29 years old</option>";
                    } else if (range.equals("_30_34")) {
                        html += "<option value= '_30_34'>30 - 34 years old</option>";
                    } else if (range.equals("_35_39")) {
                        html += "<option value= '_35_39'>35 - 39 years old</option>";
                    } else if (range.equals("_40_44")) {
                        html += "<option value= '_40_44'>40 - 44 years old</option>";
                    } else if (range.equals("_45_49")) {
                        html += "<option value= '_45_49'>45 - 49 years old</option>";
                    } else if (range.equals("_50_54")) {
                        html += "<option value= '_50_54'>50 - 54 years old</option>";
                    } else if (range.equals("_55_59")) {
                        html += "<option value= '_55_59'>55 - 59 years old</option>";
                    } else if (range.equals("_60_64")) {
                        html += "<option value= '_60_64'>60 - 64 years old</option>";
                    } else {
                        html += "<option value= '_65_yrs_ov'>65+ years old</option>";
                    }
                }
                html = html + "      </select>";
                html += "         </div><br></br>";
                html = html + "<button type='submit' class='btn btn-primary'>Filter Results</button>";
                html = html + "</form><br></br>";
            }
        }
        // higher ed
        if ((status != null) && (edulvl != null) && (state != null) && (gender != null)) {
            html = html + "<h1>Higher-Education Completion Data on the Gap</h1>";
            ArrayList<EducationStatus> nonData = jdbc.getNonSchool(status, edulvl, state, gender);
            if (status.equals("indig")) {
                status = "Indigenous";
            } else if (status.equals("non_indig")) {
                status = "Non-Indigenous";
            } else {
                status = "Not Stated";
            }

            if (gender.equals("f")) {
                gender = "Female";
            } else {
                gender = "Male";
            }
            
            if (edulvl.equals("pd_gd_gc")) {
                edulvl = "Postgraduate Degree Level, Graduate Diploma and Graduate Certificate Level";
            } else if (edulvl.equals("bd")) {
                edulvl = "Bachelor Degree Level";
            } else if (edulvl.equals("adip_dip")) {
                edulvl = "Advanced Diploma and Diploma Level";
            } else if (edulvl.equals("ct_i_ii")) {
                edulvl = "Certificate I & II Level";
            } else {
                edulvl = "Certificate III & IV Level";
            }  

            html += "<p>The below table shows the results for the selected filters:<br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;State/Territory: <b>" + state + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Indigenous Status: <b>" + status + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Sex: <b>" + gender + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Higher Education Completion: <b>" + edulvl + "</b><br></br>";
            html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
            html += "The Gap is calculated via subtracting the 2021 data from the 2016 data. However, it is good to note that there are ";
            html += "differences in the LGAs in 2016 and 2021, as either they went under a different LGA code, name, or were entirely removed. Therefore, ";
            html += "for some columns that have a 0 and/or a gap that shows the same number as the 2016/2021 data, then this is an indication that the LGA ";
            html += "code has been modified  between the two years. Note: You can also click on the table headers to sort the data based on the columns.<br></br>";
            html += "To find the LGA with the best improvement or worst decline, you can click the Gap header. The higher the number, the larger the Gap, indicating the worse the decline. The smaller the number, the smaller the Gap, indicating the best improvement.</p><br></br>";
            html = html + "<table id='higher_ed' class='sortable'>";
            html += "<th>LGA Code</th>";
            html += "<th>2016 Data</th>";
            html += "<th>2021 Data</th>";
            html += "<th>Gap</th>";
            
            for (EducationStatus nsStat : nonData) {
                html = html + "<tr class='item'><td>" + nsStat.getCode() + " (" + nsStat.getStateName() + ")</td>";
                html += "<td>" + nsStat.getCount16() + "</td>";
                html += "<td>" + nsStat.getCount21() + "</td>";
                html += "<td>" + (nsStat.getCount21() - nsStat.getCount16()) + "</td>";
            }
            html = html + "</table>";
            html = html + "<br></br>";
        }

        // hs below
        if ((status != null) && (hslvl != null) && (state != null) && (gender != null)) {
            html = html + "<h1>School Completion Data on the Gap</h1>";
            ArrayList<EducationStatus> hsData = jdbc.getSchool(status, hslvl, state, gender);
            if (status.equals("indig")) {
                status = "Indigenous";
            } else if (status.equals("non_indig")) {
                status = "Non-Indigenous";
            } else {
                status = "Not Stated";
            }

            if (gender.equals("f")) {
                gender = "Female";
            } else {
                gender = "Male";
            }

            if (hslvl.equals("did_not_go_to_school")) {
                hslvl = "Did Not Go To School";
            } else if (hslvl.equals("y8_below")) {
                hslvl = "Year 8 or Below";
            } else if (hslvl.equals("y9_equivalent")) {
                hslvl = "Year 9 or Equivalent";
            } else if (hslvl.equals("y10_equivalent")) {
                hslvl = "Year 10 or Equivalent";
            } else if (hslvl.equals("y11_equivalent")) {
                hslvl = "Year 11 or Equivalent";
            } else {
                hslvl = "Year 12 or Equivalent";
            }

            html += "<p>The below table shows the results for the selected filters:<br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;State/Territory: <b>" + state + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Indigenous Status: <b>" + status + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Sex: <b>" + gender + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;School Completion: <b>" + hslvl + "</b><br></br>";
            html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
            html += "The Gap is calculated via subtracting the 2021 data from the 2016 data. However, it is good to note that there are ";
            html += "differences in the LGAs in 2016 and 2021, as either they went under a different LGA code, name, or were entirely removed. Therefore, ";
            html += "for some columns that have a 0 and/or a gap that shows the same number as the 2016/2021 data, then this is an indication that the LGA ";
            html += "code has been modified  between the two years. Note: You can also click on the table headers to sort the data based on the columns.<br></br>";
            html += "To find the LGA with the best improvement or worst decline, you can click the Gap header. The higher the number, the larger the Gap, indicating the worse the decline. The smaller the number, the smaller the Gap, indicating the best improvement.</p><br></br>";
            html = html + "<table id='hs' class='sortable'>";
            html += "<th>LGA Code</th>";
            html += "<th>2016 Data</th>";
            html += "<th>2021 Data</th>";
            html += "<th>Gap</th>";

            for (EducationStatus hsStat : hsData) {
                html = html + "<tr class='item'><td>" + hsStat.getCode() + " (" + hsStat.getStateName() + ")</td>";
                html += "<td>" + hsStat.getCount16() + "</td>";
                html += "<td>" + hsStat.getCount21() + "</td>";
                html += "<td>" + (hsStat.getCount21() - hsStat.getCount16()) + "</td>";
            }
            
            html = html + "</table>";
            html = html + "<br></br>";
        }

        // health 
        if ((status != null) && (state != null) && (condname != null) && (gender != null)) {
            html = html + "<h1>Long-Term Health Condition Data on the Gap</h1>";
            ArrayList<HealthStatus> healthData = jdbc.getHealthStatusBoth(status, condname, state, gender);
            if (status.equals("indig")) {
                status = "Indigenous";
            } else if (status.equals("non_indig")) {
                status = "Non-Indigenous";
            } else {
                status = "Not Stated";
            }

            if (gender.equals("f")) {
                gender = "Female";
            } else {
                gender = "Male";
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
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Sex: <b>" + gender + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Long-Term Health Condition: <b>" + condname + "</b><br></br>";
            html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
            html += "The Gap is calculated via subtracting the 2021 data from the 2016 data. However, it is good to note that there are ";
            html += "differences in the LGAs in 2016 and 2021, as either they went under a different LGA code, name, or were entirely removed. Therefore, ";
            html += "for some columns that have a 0 and/or a gap that shows the same number as the 2016/2021 data, then this is an indication that the LGA ";
            html += "code has been modified  between the two years. Note: You can also click on the table headers to sort the data based on the columns.<br></br>";
            html += "To find the LGA with the best improvement or worst decline, you can click the Gap header. The higher the number, the larger the Gap, indicating the worse the decline. The smaller the number, the smaller the Gap, indicating the best improvement.</p><br></br>";
            html = html + "<table id='health' class='sortable'>";
            html += "<th>LGA Code</th>";
            html += "<th>2016 Data</th>";
            html += "<th>2021 Data</th>";
            html += "<th>Gap</th>";

            for (HealthStatus hcStat : healthData) {
                html = html + "<tr class='item'><td>" + hcStat.getCode() + " (" + hcStat.getLGAName() + ")</td>";
                html += "<td>" + hcStat.getCount16() + "</td>";
                html += "<td>" + hcStat.getCount21() + "</td>";
                html += "<td>" + (hcStat.getCount21() - hcStat.getCount16()) + "</td>";
            }
            
            html = html + "</table>";
            html = html + "<br></br>";
        }

        // age
        if ((status != null) && (agerange != null) && (state != null) && (gender != null)) {
            ArrayList<AgeData> asData = jdbc.getAgeBothData(status, agerange, state, gender);
            html = html + "<h1>Indigenous Status by Age Range Data on the Gap</h1>";
            if (status.equals("indig")) {
                status = "Indigenous";
            } else if (status.equals("non_indig")) {
                status = "Non-Indigenous";
            } else {
                status = "Not Stated";
            }

            if (gender.equals("f")) {
                gender = "Female";
            } else {
                gender = "Male";
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
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Sex: <b>" + gender + "</b><br></br>";
            html += "&nbsp;&nbsp;&nbsp;&nbsp;Age Range: <b>" + agerange + "</b><br></br>";
            html += "LGA stands for Local Government Areas, which are essentially divisions of a state or territory in Australia.";
            html += "The Gap is calculated via subtracting the 2021 data from the 2016 data. However, it is good to note that there are ";
            html += "differences in the LGAs in 2016 and 2021, as either they went under a different LGA code, name, or were entirely removed. Therefore, ";
            html += "for some columns that have a 0 and/or a gap that shows the same number as the 2016/2021 data, then this is an indication that the LGA ";
            html += "code has been modified  between the two years. Note: You can also click on the table headers to sort the data based on the columns.<br></br>";
            html += "To find the LGA with the best improvement or worst decline, you can click the Gap header. The higher the number, the larger the Gap, indicating the worse the decline. The smaller the number, the smaller the Gap, indicating the best improvement.</p><br></br>";
            html = html + "<table id='age' class='sortable'>";
            html += "<th>LGA Code</th>";
            html += "<th>2016 Data</th>";
            html += "<th>2021 Data</th>";
            html += "<th>Gap</th>";

            for (AgeData asStat : asData) {
                html = html + "<tr class='item'><td>" + asStat.getCode() + " (" + asStat.getLGAName() + ")</td>";
                html += "<td>" + asStat.getCount16() + "</td>";
                html += "<td>" + asStat.getCount21() + "</td>";
                html += "<td>" + (asStat.getCount21() - asStat.getCount16()) + "</td>";
            }
            
            html = html + "</table>";
            html = html + "<br></br>";
        }

        // Close Content div
        html = html + "</div></section>";

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

        html += """
            <script>
            document.addEventListener('DOMContentLoaded', function(event) { 
            var scrollpos = localStorage.getItem('scrollpos');
            if (scrollpos) window.scrollTo(0, scrollpos);
            });

            window.onbeforeunload = function(e) {
                localStorage.setItem('scrollpos', window.scrollY);
            };
            </script>

        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
