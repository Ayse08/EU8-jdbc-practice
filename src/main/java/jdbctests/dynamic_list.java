package jdbctests;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

public class dynamic_list {

    String dbUrl = "jdbc:oracle:thin:@35.174.107.83:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void test2() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select FIRST_NAME, LAST_NAME, SALARY, JOB_ID\n" +
                "from EMPLOYEES\n" +
                "where ROWNUM <6");

        //in order to get the column names we need resultSetMetaData
        ResultSetMetaData rsmd = resultSet.getMetaData();

       //list of maps to keep all information (empty list of map)
        List<Map<String, Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount = rsmd.getColumnCount();

        //loop through each row
        while(resultSet.next()){

            Map<String, Object> row = new LinkedHashMap<>(); //empty map inside the while loop

            //some code to fill the dynamically
            //inner loop giving column name and column value
            for (int i = 1; i <= colCount ; i++) {

                row.put(rsmd.getColumnName(i), resultSet.getString(i));
            }


            //add ready map row to the list
            queryData.add(row);
        }

        // to print results to the console
        for (Map<String, Object> row : queryData){
            System.out.println(row.toString());
        }


        resultSet.close();
        statement.close();
        connection.close();

    }


}
