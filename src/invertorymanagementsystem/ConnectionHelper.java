/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertorymanagementsystem;

/**
 *
 * @author ibnay
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Grace
 */
public class ConnectionHelper {
    
    private ResultSet result;
    private String tableName;
    private final Connection connection;
    
    public ConnectionHelper (String tableName) throws SQLException{
            this.tableName = tableName;
            connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-F3HVSOT:1433;databaseName=POS;User={MuhammadAbdullah};password={12975}");
            PreparedStatement statement = connection.prepareStatement("Select * from "+tableName);
            result = statement.executeQuery();
            /*while(result.next()){
                System.out.println(result.getInt("Product_ID")+"");
            }*/
    }
    
    public void closeDataBase(){
        
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ResultSet getResult(){
        return result;
    }
    
    public Connection getConnection(){
        return connection;
    } 
    
    public static void main(String args[]){
        try {
            ConnectionHelper conhelp = new ConnectionHelper("products");
            //conhelp.
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}  
