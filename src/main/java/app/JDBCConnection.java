package app;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/finalctg.db";
    // public static final String DATABASE = "jdbc:sqlite:database/climate.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs2016() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas16 = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2016'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int lga_code     = results.getInt("lga_code");
                String name  = results.getString("name");
                String state = results.getString("state");

                // Create a LGA Object
                LGA lga = new LGA(lga_code, state, name, 2016);

                // Add the lga object to the array
                lgas16.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas16;
    }

    // TODO: Add your required methods here

    // gets all data on 2021 lgas
    public ArrayList<LGA> getLGAs2021() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas21 = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2021'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int lga_code     = results.getInt("lga_code");
                String name  = results.getString("name");
                String state = results.getString("state");

                // Create a LGA Object
                LGA lga = new LGA(lga_code, state, name, 2021);

                // Add the lga object to the array
                lgas21.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas21;
    }

    // returns population by state
    public ArrayList<Population> getStatePop() {
        ArrayList<Population> statePop = new ArrayList<Population>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT state, SUM(CASE WHEN a.year = '2016' AND l.year = '2016' THEN count END) AS count16, SUM(CASE WHEN a.year = '2021' AND l.year = '2021' THEN count END) AS count21 FROM age_range_statistic a JOIN lga l ON a.lga_code = l.lga_code WHERE (a.year = '2016' AND l.year = '2016') OR (a.year = '2021' AND l.year = '2021') GROUP BY state";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String state = results.getString("state");
                int count16 = results.getInt("count16");
                int count21 = results.getInt("count21");

                Population stateStat = new Population(state, count16, count21);

                statePop.add(stateStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return statePop;
    }
    
    // fills dropdown options
    public ArrayList<String> getStatus() {
        ArrayList<String> indStatus = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select distinct indigenous_status from age_range_statistic";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String status = results.getString("indigenous_status");
                indStatus.add(status);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return indStatus;
    }

    // fills dropdown
    public ArrayList<String> getStateDrop() {
        ArrayList<String> stateName = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select distinct state from lga";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String sterritory = results.getString("state");
                stateName.add(sterritory);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return stateName;
    }


    // fills the dropdown options
    public ArrayList<String> getCondName() {
        ArrayList<String> conditionName = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select distinct name from health_condition_statistic";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String name = results.getString("name");
                conditionName.add(name);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return conditionName;
    }

    // fills the dropdown options
    public ArrayList<String> getAgeRange() {
        ArrayList<String> ageRange = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select distinct age_range from age_range_statistic";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String range = results.getString("age_range");
                ageRange.add(range);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return ageRange;
    }

    // fills dropdown options
    public ArrayList<String> getGender() {
        ArrayList<String> gender = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select distinct sex from age_range_statistic";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String type = results.getString("sex");
                gender.add(type);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return gender;
    }

    // fills dropdown options
    public ArrayList<String> getNonSchoolComp() {
        ArrayList<String> nonSchool = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct education_level from education_statistic where education_level = 'pd_gd_gc' or education_level = 'bd' or education_level = 'adip_dip' or education_level = 'ct_iii_iv' or education_level = 'ct_i_ii'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String nonCompletion = results.getString("education_level");
                nonSchool.add(nonCompletion);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return nonSchool;
    }

    // fills dropdown
    public ArrayList<String> getSchoolComp() {
        ArrayList<String> schoolComp = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct education_level from education_statistic where education_level = 'did_not_go_to_school' or education_level = 'y8_below' or education_level = 'y9_equivalent' or education_level = 'y10_equivalent' or education_level = 'y11_equivalent' or education_level = 'y12_equivalent'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String completion = results.getString("education_level");
                schoolComp.add(completion);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return schoolComp;
    }

    public ArrayList<String> getAllSchoolComp() {
        ArrayList<String> nonSchool = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct education_level from education_statistic";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String nonCompletion = results.getString("education_level");
                nonSchool.add(nonCompletion);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return nonSchool;
    }

    public ArrayList<HealthStatus2> getHealthStatus2(String status, String condname) {
        ArrayList<HealthStatus2> healthStat = new ArrayList<HealthStatus2>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, sex, state, indigenous_status, h.name, SUM(CASE WHEN h.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN h.year = '2016' THEN count END) AS count_16, (SELECT SUM(count) FROM health_condition_statistic WHERE year = '2021' AND indigenous_status = '" + status + "' AND lga_code = h.lga_code) AS lgaTot FROM health_condition_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE (h.year = '2021' or h.year = '2016') and (l.year = '2021' or l.year = '2021') AND indigenous_status = '" + status + "' AND h.name = '" + condname + "' GROUP BY h.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("name");
                int count_21 = results.getInt("count_21");
                int lgaTot = results.getInt("lgaTot");
                int count_16 = results.getInt("count_16");
                String gender = results.getString("sex");
                

                HealthStatus2 conditionStat = new HealthStatus2(lga_code, state, indigenous_status, name, count_21, lgaTot, count_16, gender);
                
                // Add the lga object to the array
                healthStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return healthStat;
    }

    // health data we want to display with chosen categories
    public ArrayList<HealthStatus> getHealthStatus21(String status, String condname, String territory, String sort) {
        ArrayList<HealthStatus> healthStat = new ArrayList<HealthStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, l.name as lname, SUM(CASE WHEN h.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN h.year = '2016' and l.year = '2016' THEN count END) AS count_16, ((select sum(count) from age_range_statistic where year = '2021' and indigenous_status = '" + status + "' and lga_code = h.lga_code)) as lgaTot FROM health_condition_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE indigenous_status = '" + status + "' AND h.name = '" + condname + "' and state = '" + territory + "' GROUP BY h.lga_code";
            
            if (sort != null) {
                if (sort.equals("Raw Data Ascending")) {
                    query += " ORDER BY count_21 ASC";
                } else if (sort.equals("Raw Data Descending")) {
                    query += " ORDER BY count_21 DESC";
                } if (sort.equals("Proportional Data Ascending")) {
                    query += " ORDER BY ((count_21 / lgaTot) * 100) ASC";
                } else if (sort.equals("Proportional Data Descending")) {
                    query += " ORDER BY ((count_21 / lgaTot) * 100) DESC";
                }
            }

            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String lname = results.getString("lname");
                int lgaTot = results.getInt("lgaTot");
                int count_21 = results.getInt("count_21");
                int count_16 = results.getInt("count_16");
                
                HealthStatus conditionStat = new HealthStatus(lga_code, lname, lgaTot, count_21, count_16);
                
                // Add the lga object to the array
                healthStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return healthStat;
    }

    public ArrayList<HealthStatus> getHealthStatusBoth(String status, String condname, String territory, String gender) {
        ArrayList<HealthStatus> healthStat = new ArrayList<HealthStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, l.name as lname, SUM(CASE WHEN h.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN h.year = '2016' and l.year = '2016' THEN count END) AS count_16, ((select sum(count) from age_range_statistic where year = '2021' and indigenous_status = '" + status + "' and lga_code = h.lga_code)) as lgaTot FROM health_condition_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE indigenous_status = '" + status + "' AND h.name = '" + condname + "' and state = '" + territory + "' and sex = '" + gender + "' GROUP BY h.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String lname = results.getString("lname");
                int count_21 = results.getInt("count_21");
                int count_16 = results.getInt("count_16");
                
                HealthStatus conditionStat = new HealthStatus(lga_code, lname, count_21, count_16);
                
                // Add the lga object to the array
                healthStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return healthStat;
    }

    // age data we want to display with chosen categories
    public ArrayList<AgeData> getAgeData21 (String indigstatus, String agerange, String state, String sort) {
        ArrayList<AgeData> rangeStatus = new ArrayList<AgeData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "select a.lga_code, name, sum(count) as count_21, ((select sum(count) from age_range_statistic where year = '2021' and indigenous_status = '" + indigstatus + "' and lga_code = a.lga_code)) as lgaTot from age_range_statistic a join lga l on a.lga_code = l.lga_code where a.year = '2021' and l.year = '2021' and indigenous_status = '" + indigstatus + "' and age_range = '" + agerange + "' and state = '" + state + "' group by a.lga_code";
            // Get Result
            
            if (sort != null) {
                if (sort.equals("Raw Data Ascending")) {
                    query += " ORDER BY count_21 ASC";
                } else if (sort.equals("Raw Data Descending")) {
                    query += " ORDER BY count_21 DESC";
                } if (sort.equals("Proportional Data Ascending")) {
                    query += " ORDER BY ((count_21 / lgaTot) * 100) ASC";
                } else if (sort.equals("Proportional Data Descending")) {
                    query += " ORDER BY ((count_21 / lgaTot) * 100) DESC";
                }
            }

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                int lgaTot = results.getInt("lgaTot");
                int count_21 = results.getInt("count_21");
                String name = results.getString("name");

                AgeData rangeAgeStat = new AgeData (lga_code, lgaTot, count_21, name);
                
                // Add the lga object to the array
                rangeStatus.add(rangeAgeStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return rangeStatus;
    }

    public ArrayList<AgeData> getAgeBothData (String indigstatus, String agerange, String state, String gender) {
        ArrayList<AgeData> ageBoth = new ArrayList<AgeData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT a.lga_code, name, SUM(CASE WHEN a.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN a.year = '2016' and l.year = '2016' THEN count END) AS count_16 FROM age_range_statistic a JOIN lga l ON a.lga_code = l.lga_code WHERE indigenous_status = '" + indigstatus + "' AND age_range = '" + agerange + "' AND state = '" + state + "' AND sex = '" + gender + "' GROUP BY a.lga_code";
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                int count_21 = results.getInt("count_21");
                int lga_code = results.getInt("lga_code");
                int count_16 = results.getInt("count_16");
            
                AgeData rangeAgeStat = new AgeData (name, count_21, lga_code, count_16);
                
                // Add the lga object to the array
                ageBoth.add(rangeAgeStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return ageBoth;
    }

    public ArrayList<EducationStatus> getNonSchool(String status, String edulvl, String state, String gender) {
        ArrayList<EducationStatus> nonSchoolStat = new ArrayList<EducationStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT e.lga_code, name, sex, indigenous_status, (select distinct education_level from education_statistic where education_level = 'pd_gd_gc' or education_level = 'bd' or education_level = 'adip_dip' or education_level = 'ct_iii_iv' or education_level = 'ct_i_ii') as nonschoollvl, SUM(CASE WHEN e.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN e.year = '2016' and l.year = '2016' THEN count END) AS count_16 FROM education_statistic e JOIN lga l ON e.lga_code = l.lga_code WHERE indigenous_status = '" + status + "' AND education_level = '" + edulvl + "' and state = '" + state + "' AND sex = '" + gender + "' GROUP BY e.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                int lga_code = results.getInt("lga_code");
                String indigenous_status = results.getString("indigenous_status");
                String nonschoollvl = results.getString("nonschoollvl");
                int count_21 = results.getInt("count_21");
                int count_16 = results.getInt("count_16");
                
                EducationStatus nonStat = new EducationStatus(name, lga_code, indigenous_status, nonschoollvl, count_21, count_16);
                
                // Add the lga object to the array
                nonSchoolStat.add(nonStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        return nonSchoolStat;
    }

    public ArrayList<EducationStatus> getSchool(String status, String hslvl, String state, String gender) {
        ArrayList<EducationStatus> schoolStat = new ArrayList<EducationStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT e.lga_code, name, sex, indigenous_status, (select distinct education_level from education_statistic where education_level = 'did_not_go_to_school' or education_level = 'y8_below' or education_level = 'y9_equivalent' or education_level = 'y10_equivalent' or education_level = 'y11_equivalent' or education_level = 'y12_equivalent') as schoollvl, SUM(CASE WHEN e.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN e.year = '2016' and l.year = '2016' THEN count END) AS count_16 FROM education_statistic e JOIN lga l ON e.lga_code = l.lga_code WHERE indigenous_status = '" + status + "' AND education_level = '" + hslvl + "' and state = '" + state + "' AND sex = '" + gender + "' GROUP BY e.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                int lga_code = results.getInt("lga_code");
                String indigenous_status = results.getString("indigenous_status");
                String schoollvl = results.getString("schoollvl");
                int count_21 = results.getInt("count_21");
                int count_16 = results.getInt("count_16");
                
                EducationStatus scompStat = new EducationStatus(name, lga_code, indigenous_status, schoollvl, count_21, count_16);
                
                // Add the lga object to the array
                schoolStat.add(scompStat);
            }
            
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return schoolStat;
    }

    public ArrayList<Education> getEducationStatus(String status, String condname, String gender) {
        ArrayList<Education> edStat = new ArrayList<Education>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "select h.lga_code, state, indigenous_status, h.education_level, sum(count) as count, ( ( select sum(count) from education_statistic where year = '2016' and education_level = '" + condname + "' and lga_code = h.lga_code ) ) as lgaTot from education_statistic h join lga l on h.lga_code = l.lga_code where h.year = '2016' and l.year = '2016' and indigenous_status = '" + status + "' and h.education_level = '" + condname + "' and h.sex = '" + gender + "' group by h.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("education_level");
                int count = results.getInt("count");
                int lgaTot = results.getInt("lgaTot");
                

                Education conditionStat = new Education(lga_code, state, indigenous_status, name, count, lgaTot);
                
                // Add the lga object to the array
                edStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return edStat;
    }

    public ArrayList<Education> getEducationStatus2(String status, String condname) {
        ArrayList<Education> edStat = new ArrayList<Education>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "select h.lga_code, state, indigenous_status, h.education_level, sum(count) as count, ( ( select sum(count) from education_statistic where year = '2016' and education_level = '" + condname + "' and lga_code = h.lga_code ) ) as lgaTot from education_statistic h join lga l on h.lga_code = l.lga_code where h.year = '2016' and l.year = '2016' and indigenous_status = '" + status + "' and h.education_level = '" + condname + "' group by h.lga_code";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("education_level");
                int count = results.getInt("count");
                int lgaTot = results.getInt("lgaTot");
                

                Education conditionStat = new Education(lga_code, state, indigenous_status, name, count, lgaTot);
                
                // Add the lga object to the array
                edStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return edStat;
    }

    public ArrayList<Education> getEducationStatusByLGA(String status, String condname, String gender, String Code) {
        ArrayList<Education> edStat = new ArrayList<Education>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "select h.lga_code, state, indigenous_status, h.education_level, sum(count) as count, ( ( select sum(count) from education_statistic where year = '2016' and education_level = '" + condname + "' and lga_code = h.lga_code ) ) as lgaTot from education_statistic h join lga l on h.lga_code = l.lga_code where h.year = '2016' and l.year = '2016' and indigenous_status = '" + status + "' and h.education_level = '" + condname + "' and h.sex = '" + gender + "' and h.lga_code = '" + Code + "' ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("education_level");
                int count = results.getInt("count");
                int lgaTot = results.getInt("lgaTot");
                

                Education conditionStat = new Education(lga_code, state, indigenous_status, name, count, lgaTot);
                
                // Add the lga object to the array
                edStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return edStat;
    }    

    public ArrayList<AgeStatus> getAgeStatus(String indigstatus, String agerange) {
        ArrayList<AgeStatus> rangeStatus = new ArrayList<AgeStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT a.lga_code, state, sex, indigenous_status, age_range, SUM(CASE WHEN a.year = '2021' and l.year = '2021' THEN count END) AS count_21, SUM(CASE WHEN a.year = '2016' and l.year = '2016' THEN count END) AS count_16, (SELECT SUM(count) FROM age_range_statistic WHERE year = '2021' AND indigenous_status = '" + indigstatus + "' AND lga_code = a.lga_code) AS lgaTot FROM age_range_statistic a JOIN lga l ON a.lga_code = l.lga_code WHERE indigenous_status = '" + indigstatus + "' AND age_range = '" + agerange + "' GROUP BY a.lga_code";
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String state = results.getString("state");
                int count_21 = results.getInt("count_21");
                String indigenous_status = results.getString("indigenous_status");
                String range = results.getString("age_range");
                int lga_code = results.getInt("lga_code");
                int lgaTot = results.getInt("lgaTot");
                int count_16 = results.getInt("count_16");
                String gender = results.getString("sex");

                AgeStatus rangeAgeStat = new AgeStatus(state, count_21, indigenous_status, range, lga_code, lgaTot, count_16, gender);
                
                // Add the lga object to the array
                rangeStatus.add(rangeAgeStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return rangeStatus;
    }
     public ArrayList<Contact> getContact() {
        ArrayList<Contact> contactStat = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "select * from team_member";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String student_id = results.getString("student_id");

                Contact contactInfo = new Contact(name, student_id);
                
                // Add the lga object to the array
                contactStat.add(contactInfo);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return contactStat;
    }

    public ArrayList<Education> getEducationLGA(String status, String condname, String Code) {
        ArrayList<Education> edStat = new ArrayList<Education>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, l.state, h.indigenous_status, h.education_level, SUM(h.count) AS count, ABS(SUM(h.count) - (SELECT SUM(count) FROM education_statistic WHERE year = '2016' AND education_level = '" + condname + "'  AND lga_code = '" + Code + "' AND indigenous_status = '" + status + "')) AS count_difference FROM education_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE h.year = '2016' AND l.year = '2016' AND h.indigenous_status = '" + status + "' AND h.education_level = '" + condname + "'  GROUP BY h.lga_code, l.state, h.indigenous_status, h.education_level ORDER BY count_difference, h.lga_code <> '" + Code + "' ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("education_level");
                int count = results.getInt("count");
                int lgaTot = results.getInt("count_difference");
                

                Education conditionStat = new Education(lga_code, state, indigenous_status, name, count, lgaTot);
                
                // Add the lga object to the array
                edStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return edStat;
    }

    public ArrayList<HealthStatus2> getHealthLGA(String status, String condname, String LGAcode) {
        ArrayList<HealthStatus2> healthStat = new ArrayList<HealthStatus2>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, l.state, h.indigenous_status, h.NAME, Sum(h.count) AS count, Abs(Sum(h.count) - (SELECT Sum(count) FROM health_condition_statistic WHERE year = '2021' AND NAME = '" + condname + "' AND lga_code = '" + LGAcode + "' AND indigenous_status = '" + status + "')) AS count_difference FROM health_condition_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE h.year = '2021' AND l.year = '2021' AND h.indigenous_status = '" + status + "' AND h.NAME = '" + condname + "' GROUP BY h.lga_code, l.state, h.indigenous_status, h.NAME ORDER BY count_difference, h.lga_code <> '" + LGAcode + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String name = results.getString("name");
                int count_21 = results.getInt("count");
                int lgaTot = results.getInt("count_difference");
                int count_16 = 69;
                String gender = "None";
                

                HealthStatus2 conditionStat = new HealthStatus2(lga_code, state, indigenous_status, name, count_21, lgaTot, count_16, gender);
                
                // Add the lga object to the array
                healthStat.add(conditionStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return healthStat;
    }

    public ArrayList<AgeStatus> getAgeLGA(String status, String condname, String LGAcode) {
        ArrayList<AgeStatus> rangeStatus = new ArrayList<AgeStatus>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // returns data based on filtered category
            String query = "SELECT h.lga_code, l.state, h.indigenous_status, h.age_range, Sum(h.count) AS count, Abs(Sum(h.count) - (SELECT Sum(count) FROM age_range_statistic WHERE year = '2016' AND age_range = '" + condname + "' AND lga_code = '" + LGAcode + "' AND indigenous_status = '" + status + "')) AS count_difference FROM age_range_statistic h JOIN lga l ON h.lga_code = l.lga_code WHERE h.year = '2016' AND l.year = '2016' AND h.indigenous_status = '" + status + "' AND h.age_range = '" + condname + "' GROUP BY h.lga_code, l.state, h.indigenous_status, h.age_range ORDER BY count_difference, h.lga_code <> '" + LGAcode + "'";
            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                int lga_code = results.getInt("lga_code");
                String state = results.getString("state");
                String indigenous_status = results.getString("indigenous_status");
                String range = results.getString("age_range");
                int count_21 = results.getInt("count");
                int lgaTot = results.getInt("count_difference");
                int count_16 = 69;
                String gender = "None";

                AgeStatus rangeAgeStat = new AgeStatus(state, count_21, indigenous_status, range, lga_code, lgaTot, count_16, gender);
                
                // Add the lga object to the array
                rangeStatus.add(rangeAgeStat);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return rangeStatus;
    }

}

   