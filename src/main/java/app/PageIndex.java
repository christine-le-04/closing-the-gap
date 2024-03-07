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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Closing the Gap</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<link href='https://fonts.googleapis.com/css2?family=IBM+Plex+Mono&family=Ribeye&display=swap' rel='stylesheet'>";
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
            <div class='hero-image'>
                <div class='hero-text'>
                    <span>Closing</span>
                    <span>the Gap</span>
                </div>
            </div>
        """;
        
        // img src='logo.png'
        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
        <section id ='about'>
            <div class='container'>
                <h2>About Closing the Gap</h2>
                <p>Closing the Gap is an initiative that was created to address the vast gap and difference in life 
                outcomes between Indigenous and non-Indigenous people. With the primary goal of attaining equality for 
                Indigenous Australians, the Australian Government collaborates with a variety of groups, organisations, 
                sectors, and governments to address the disadvantage that Indigenous peoples face.</p>
            </div>
        </section>

        <section id ='population'>
            <div class='container'>
                <h2>Australia's Population</h2>  
                <p>If you would like to sort the data, you can click each of the table headers to sort based on the column.<p>  
        """;   
        
        ArrayList<Population> stateData = jdbc.getStatePop();
        
        html = html + "<table id='popTable' class='sortable'>";
        html += "<th>LGA State</th>";
        html += "<th>2016 Population</th>";
        html += "<th>2021 Population</th>";

        for (Population popStat : stateData) {
            String commaCount16 = String.format("%,d", popStat.getCount16());
            html = html + "<tr class='item'><td>" + popStat.getState() + "</td>";
            html += "<td>" + commaCount16 + "</td>";
            String commaCount21 = String.format("%,d", popStat.getCount21());
            html += "<td>" + commaCount21 + "</td></tr>";
        }
        html = html + "</table>";

        html += """
            </div>
        </section>

        <section id ='outcomes'>
            <div class='container'>
                <h2>Socio-Economic Outcomes of Closing the Gap</h2>
                <span>Alongside Aboriginal and Torres Strait Islander peak representatives, the Australian Government
                worked together to develop a National Agreement on Closing the Gap to expedite improvements in the life
                outcomes for Indigenous peoples. As part of the National Agreement, there are 17 socio-economic targets 
                that relate to life outcomes that tend to impact Indigenous peoples. By monitoring the progress towards 
                these targets, this will provide a better insight on efficiency of the contributions towards the progress 
                of Closing the Gap.<br></br>Below are the 17 socio-economic target outcomes of Closing the Gap, which can 
                show the areas of focus that Governments want to focus on improving for Indigenous peoples. The focus of 
                this website will be outcomes 1 and 5, therefore, you can click on those outcomes to be redirected towards 
                another page that provides relevant data.</span>
                <div class='outcome-box'>
                    <div class='outcome-desc'><a href='/page2A.html'>1: Everyone enjoys long and healthy lives</a></div>
                    <div class='outcome-desc'><p>2: Children are born healthy and strong</p></div>
                    <div class='outcome-desc'><p>3: Children are engaged in high quality, culturally appropriate early childhood education in their early years</p></div>
                    <div class='outcome-desc'><p>4: Children thrive in their early years.</p></div>
                    <div class='outcome-desc'><a href='/page2B.html'>5: Students achieve their full learning potential</a></div>
                    <div class='outcome-desc'><p>6: Students reach their full potential through further education pathways</p></div>
                    <div class='outcome-desc'><p>7: Youth are engaged in employment or education</p></div>
                    <div class='outcome-desc'><p>8: Strong economic participation and development of people and their communities</p></div>
                    <div class='outcome-desc'><p>9: People can secure appropriate, affordable housing that is aligned with their priorities and need</p></div>
                    <div class='outcome-desc'><p>10: Adults are not overrepresented in the criminal justice system</p></div>
                    <div class='outcome-desc'><p>11: Young people are not overrepresented in the criminal justice system</p></div>
                    <div class='outcome-desc'><p>12: Children are not overrepresented in the child protection system</p></div>
                    <div class='outcome-desc'><p>13: Families and households are safe</p></div>
                    <div class='outcome-desc'><p>14: People enjoy high levels of social and emotional wellbeing</p></div>
                    <div class='outcome-desc'><p>15: People maintain a distinctive cultural, spiritual, physical and economic relationship with their land and waters</p></div>
                    <div class='outcome-desc'><p>16: Cultures and languages are strong, supported and flourishing</p></div>
                    <div class='outcome-desc'><p>17: People have access to information and services enabling participation in informed decision-making regarding their own lives</p></div>
                    <div class='outcome-desc'><p>Focus: 1 and 5</p></div>
                </div>
            </div>
        </section>
        """;


        // Close Content div
        html = html + "</div>";

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

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
