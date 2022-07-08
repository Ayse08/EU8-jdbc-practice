package jdbctests;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listOfMapExample {

    String dbUrl = "jdbc:oracle:thin:@35.174.107.83:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void test1(){

        //creating list for keeping all the rows maps
        List<Map<String, Object>> queryData = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();

        row1.put("first_name", "Steven");
        row1.put("last_name", "King");
        row1.put("salary", 24000);
        row1.put("job_id", "AD_PRES");

        System.out.println(row1.toString());

        Map<String, Object> row2 = new HashMap<>();

        row1.put("first_name", "Neena");
        row1.put("last_name", "Kochbar");
        row1.put("salary", 17000);
        row1.put("job_id", "AD_VP");

        System.out.println(row2.toString());

        //adding rows one by one to my list
        queryData.add(row1);
        queryData.add(row2);

        //get the steven last_name directly from the list
        System.out.println(queryData.get(0).get("last_name"));


    }

    @Test
    public void test2() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select FIRST_NAME, LAST_NAME, SALARY, JOB_ID\n" +
                "from EMPLOYEES\n" +
                "where ROWNUM <6");

        //in order to get the column names we need resultSetMetaData
        ResultSetMetaData rsmd = resultSet.getMetaData();

        //move to first row
        resultSet.next();

        //creating list for keeping all the rows maps
        List<Map<String, Object>> queryData = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();

        row1.put(rsmd.getColumnName(1), resultSet.getString(1));
        row1.put(rsmd.getColumnName(2), resultSet.getString(2));
        row1.put(rsmd.getColumnName(3), resultSet.getString(3));
        row1.put(rsmd.getColumnName(4), resultSet.getString(4));


        System.out.println(row1.toString());

        //move to second row
        resultSet.next();

        Map<String, Object> row2 = new HashMap<>();

        row2.put(rsmd.getColumnName(1), resultSet.getString(1));
        row2.put(rsmd.getColumnName(2), resultSet.getString(2));
        row2.put(rsmd.getColumnName(3), resultSet.getString(3));
        row2.put(rsmd.getColumnName(4), resultSet.getString(4));

        System.out.println(row2.toString());

        //adding rows one by one to my list
        queryData.add(row1);
        queryData.add(row2);



        resultSet.close();
        statement.close();
        connection.close();

    }


}
