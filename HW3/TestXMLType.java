import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class TestXMLType {

    /**
     * @param args
     */
	
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Connection conn = null;
        try
        {
        	Connection mainConnection = null;
            Statement mainStatement = null;
        	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			String URL = "jdbc:oracle:thin:@127.0.0.1:1521:HW3";
	    	String userName = "sys as sysdba";
	    	String password = "zjl214";

	    	mainConnection = DriverManager.getConnection(URL, userName, password);

    		mainStatement = mainConnection.createStatement();
            
           
            InputStream ins = null;
            StringBuffer buffer = new StringBuffer();
            
            ins = new FileInputStream(new File("C:/reviews.xml"));
            //ins = new FileInputStream(new File("C:/books.xml"));
            byte[] tmp = new byte[1];
            int length = 0;
            while((length = ins.read(tmp)) != -1)
            {
            buffer.append(new String(tmp,0,length));
            }
            ins.close();
            insertXML(buffer.toString(),mainConnection);
                        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(conn != null)
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }            
        }
    }
    
    private static void insertXML(String xmlData, Connection conn) {
          CLOB clob = null;
          String query;
            // Initialize statement Object
          PreparedStatement pstmt = null;
          try{
            //query = "INSERT INTO System.books VALUES ('1',XMLType(?)) ";
            query = "INSERT INTO System.reviews VALUES ('1',XMLType(?)) ";
            pstmt = conn.prepareStatement(query);
            clob = getCLOB(xmlData, conn);
            pstmt.setObject(1, clob);
            if (pstmt.executeUpdate () == 1) {
            System.out.println ("Successfully inserted XML data");
            }
          } catch(SQLException sqlexp){
            sqlexp.printStackTrace();
          } catch(Exception exp){
            exp.printStackTrace();
          }
    }
    
    private static CLOB getCLOB(String xmlData, Connection conn) throws SQLException{
          CLOB tempClob = null;
          try{
            // If the temporary CLOB has not yet been created, create one
            tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION); 
         
            // Open the temporary CLOB in readwrite mode, to enable writing
            tempClob.open(CLOB.MODE_READWRITE); 
            // Get the output stream to write
            Writer tempClobWriter = tempClob.getCharacterOutputStream(); 
            // Write the data into the temporary CLOB
            tempClobWriter.write(xmlData); 
         
            // Flush and close the stream
            tempClobWriter.flush();
            tempClobWriter.close(); 
         
            // Close the temporary CLOB 
            tempClob.close();    
          } catch(SQLException sqlexp){
            tempClob.freeTemporary(); 
            sqlexp.printStackTrace();
          } catch(Exception exp){
            tempClob.freeTemporary(); 
            exp.printStackTrace();
          }
          return tempClob; 
        }



}