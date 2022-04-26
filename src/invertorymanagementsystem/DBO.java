/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertorymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ibnay
 */
public class DBO {
    private ResultSet result;
    private String tableName;
    private final Connection connection;
    
    public DBO (String tableName) throws SQLException{
            this.tableName = tableName;
            connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-F3HVSOT:1433;databaseName=POS;User={MuhammadAbdullah};password={12975}");
            PreparedStatement statement = connection.prepareStatement("Select * from "+ tableName);
            result = statement.executeQuery();
    }
    
    public void closeDataBase(){
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }
    
    public ResultSet getResult(){
        return result;
    }
    
    public Connection getConnection(){
        return connection;
    } 
}    

