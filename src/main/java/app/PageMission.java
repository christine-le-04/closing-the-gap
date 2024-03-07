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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
            <div class="hero-image">
                <div class="hero-text">
                    <span>Our</span>
                    <span>Mission</span>
                </div>
            </div>
        """;
        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
        <section id ='about'>
            <div class='container'>
                <h2>What is our Mission?</h2>
                <p>Closing the gap is an Australian movement seeking to aid Aboriginal and Torres Straight Islanders in improving their overall quality of life, more specifically their health and education. Our site seeks to further educate anyone interested on a matter people around Australia face, and something that should be talked about more. We provide a source for up to date, relevant and accurate data to ensure that users get the most out of our site. A multitude of graphs, ranging in design and purpose, which are created by us delve deep into the problem our country faces and the struggles indigenous Australians face. We also provide a place for user to get custom data based off their own searches on our pages, getting a tailored experience.</p>
            </div>
        </section>
        """;   

        html += """
            </div>
        </section>

        <section id ='outcomes2'>
            <div class='container'>
                <h2>Personas</h2>
            <div class="container2">
                <div class="box">
                    <img src="Picture1.png" alt="Picture 1">
                    </br></br>
                    <h3>James Nguyen</h3>
                    <h3>Background/Description</h3>
                    <p>- 22 year old male</p>
                    <p>- Volunteer apart of Volunteering Victoria</p>
                    <p>- No knowledge regarding the health status and living standard of Aboriginal and Tores Strait Islander people</p>
                    <h3>Goals/Needs</h3>
                    <p>- Hoping to be involved in the Community First Development Organisation that revolves around providing resources to areas with limited education, health care, and employment opportunities</p>
                    <p>- Meaning he is interested in improving the lives of Aboriginal people and would like to start by expanding his knowledge&nbsp;</p>
                    <p>- Wants to raise awareness and encourage others to help take action - provide his assistance via volunteering to contribute towards a better living standard for Aboriginal people</p>
                    <p>- Being well informed about the current state of Aboriginal and Torres Strait Islander peoples</p>
                    <p>- He wants to get general knowledge on what Closing the Gap is and to have an understanding of how large the gap is currently between Aboriginal and non-Indigneous people</p>
                    <h3>Skills/Experience</h3>
                    <p>- Tech literate</p>
                    <p>- Has previous experience with volunteering to support international students in Australia</p>
                </div>
                <div class="box">
                    <img src="Picture2.png" alt="Picture 2">
                    </br></br>
                    <h3>Alice Wilson</h3>
                    <h3>Background/Description</h3>
                    <p>- 42 year old female</p>
                    <p>- Social service worker with experience working with marginalised communities</p>
                    <p>- Often busy with an unmanageable workload and doesn't have enough time to fulfil her duties in a desired manner</p>
                    <h3>Goals/Needs</h3>
                    <p>- Be able to get a better understanding of the progress on closing the gaps and utilising that information to improve her clients health and wellbeing, whether physical, spiritual, mental, emotional, or social.</p>
                    <p>- To provide the most eective support</p>
                    <p>- Due to lack of time, she needs a resource that she can easily use to gather reliable information to reach her above goal</p>
                    <p>- Wants to limit her time searching through multiple sources for the desired information</p>
                    <p>- Via well-designed and easy-to-navigate website that provides the necessary information</p>
                    <p>- Wants to be well-informed about the programs and initiative in place to promote closing the gap</p>
                    <p>- Wants to retrieve in-depth info to possible delve into the root problem, rather than solely focusing on the surface level issues</p>
                    <h3>Skills/Experience</h3>
                    <p>- Knowledgable about support services and community resources for her clients    </p>
                    <p>- Comfortable with technology and has previously used it to gather resources for her clients</p>
                </div>
                <div class="box">
                    <img src="Picture3.jpg" alt="Picture 3">
                    </br></br>
                    <h3>Noel Williams</h3>
                    <h3>Background/Description</h3>
                    <p> - 45 year old male</p>
                    <p> - A teacher in rural areas</p>
                    <p> - Is an Indigenous Australian </p>
                    <p> - Lives in an area with a high indigenous density</p>
                    <p> - Has 3 kids and a wife</p>
                    <h3>Goals/Needs</h3>
                    <p>- Needs to relocate family to another state due to work</p>
                    <p>- Looking for a place to live that is similar to where he is currently in regard to indigenous population and culture.</p>
                    <p>- Wanting his kids to still have access to quality health and education systems to allow them to seek higher education after school.</p>
                    <p>- The area needs to be successfully &ldquo;closing the gap&rdquo;, to ensure his kids in the future are somewhere where equality is key.</p>
                    <h3>Skills/Experience</h3>
                    <p>- Is knowledgeable about indigenous culture and living in areas with a high indigenous density.</p>
                    <p>- Is confident with his technological skills, and is a frequent user of google and accessing websites/data.</p>
                </div>
            </div>
        </section>
        </section>
            </br>
            </br>
        </section>
        """;   


        // This example uses JDBC to lookup the LGAs
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        ArrayList<LGA> lgas = jdbc.getLGAs2016();

/*         // Add HTML for the LGA list
        html = html + "<h1>All 2016 LGAs in the CTG database (using JDBC Connection)</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        for (LGA lga : lgas) {
            html = html + "<li>" + lga.getCode()
                        + " - " + lga.getName() + "</li>";
        }

        // Finish the List HTML
        html = html + "</ul>"; */


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
