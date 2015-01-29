import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

public class HW2
{
    Connection mainConnection = null;
    Statement mainStatement = null;
    ResultSet mainResultSet = null;
    ResultSet mainResultSet2 = null;
    static String buildingsfile;
    static String studentsfile;
    static String aSysfile;
    
    /**
     * @throws SQLException 
     * @throws IOException ***************************/
    public static void main(String args[]) throws IOException, SQLException
    {
        System.out.println();
    	HW2 e = new HW2(args);
        System.out.println();
    }
    /*****************************/
    public HW2(String args[]) throws IOException, SQLException
    {

    	ConnectToDB();	// connect to DB
    	//PublishData();			// publish some data
        	
    		//for query 1
    		String str1="window";
    		if(str1.equals(args[0]))
    		{
        	String str11="building";
        	String str12="firehydrant";
        	String str13="firebuilding";
        	String str14=args[2];
        	String str15=args[3];
        	String str16=args[4];
        	String str17=args[5];
        	if(str11.equals(args[1]))
        	{	
        		Search11(str14,str15,str16,str17);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str12.equals(args[1]))
        	{		
        		Search12(str14,str15,str16,str17);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str13.equals(args[1]))
        	{
        		Search13(str14,str15,str16,str17);
        		ShowAllTuples();		// show the result of the search
        	}
    		}
        	//for query 2
    		String str2="within";
        	if(str2.equals(args[0]))
        	{
        	String str21="building";
            String str22="firehydrant";
            String str23="firebuilding";
        	String str24=args[2];//building name
        	String str25=args[3];//distance
        	if(str21.equals(args[1]))
        	{	
        		Search21(str24,str25);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str22.equals(args[1]))
        	{		
        		Search22(str24,str25);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str23.equals(args[1]))
        	{
        		Search23(str24,str25);
        		ShowAllTuples();		// show the result of the search
        	}
        	}
        	//for query 3
    		String str3="nn";
        	if(str3.equals(args[0]))
        	{
        	String str31="building";
            String str32="firehydrant";
            String str33="firebuilding";
        	String str34=args[2];//building id
        	String str35=args[3];//neighbor number
        	if(str31.equals(args[1]))
        	{	
        		Search31(str34,str35);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str32.equals(args[1]))
        	{		
        		Search32(str34,str35);
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str33.equals(args[1]))
        	{
        		Search33(str34,str35);
        		ShowAllTuples();		// show the result of the search
        	}
        	}
            //for query 4
        	String str4="demo";
        	if(str4.equals(args[0]))
        	{
        	String str41="1";
            String str42="2";
            String str43="3";
        	String str44="4";
        	String str45="5";
        	if(str41.equals(args[1]))
        	{	
        		Search41();
        		ShowAllTuples();		// show the result of the search
        	}
        	if(str42.equals(args[1]))
        	{		
        		Search42();
        	}
        	if(str43.equals(args[1]))
        	{
        		Search43();
        	}
        	if(str44.equals(args[1]))
        	{
        		Search44();
        	}
        	if(str45.equals(args[1]))
        	{
        		Search45();
        	}
        	}
        //ShowMetaData();			// show meta data

    	
    }
    /*****************************/
    public void PublishData()
    {
        try
        {

				// delete previous data from the DB
            System.out.print( "\n ** Deleting previous tuples ..." );
            mainStatement.executeUpdate( "delete from SYSTEM.buildings" );
            mainStatement.executeUpdate( "delete from SYSTEM.hydrant" );
            mainStatement.executeUpdate( "delete from SYSTEM.firebuilding" );
            System.out.println( ", Deleted. **" );


			// publish new data
            System.out.print( " ** Inserting Data ..." );
            //insert buildings 
            
            FileReader reader = new FileReader("C:/Users/dell/Desktop/cs585-hw2 Spring 2014/building.xy");
            BufferedReader br = new BufferedReader(reader);
           
            String str = null;
            String coodinate = "" ;
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(",");
                  
                  coodinate = "";//reset
                  for(int i = 3; i < a.length; i++){
                	  if(i != a.length - 1)
                		  coodinate += a[i] + ",";
                	  else
                		  coodinate += a[i];
                  }
                  
                 System.out.println( "insert into SYSTEM.buildings values "
                		  	+ "('" + a[0] + "','" + a[1].trim() + "',SDO_GEOMETRY(2003, NULL, NULL,"
                		  	+ "SDO_ELEM_INFO_ARRAY(1,1003,1),"
                		  	+ "SDO_ORDINATE_ARRAY(" + coodinate+"," +a[3]+","+a[4]+")));");
                  
                 mainStatement.executeUpdate( "insert into SYSTEM.buildings values "
             		  	+ "('" + a[0].trim() + "','" + a[1].trim() + "',SDO_GEOMETRY(2003, NULL, NULL,"
             		  	+ "SDO_ELEM_INFO_ARRAY(1,1003,1),"
             		  	+ "SDO_ORDINATE_ARRAY(" + coodinate+"," +a[3]+","+a[4]+")))");
                 // System.out.println(str);
            }
           
            br.close();
            reader.close();
            
            //insert hydrant
            
            reader = new FileReader("C:/Users/dell/Desktop/cs585-hw2 Spring 2014/hydrant.xy");
            br = new BufferedReader(reader);
           
            str = "";
            coodinate = "" ;
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(",");
                  
                  coodinate = "";//reset
                  for(int i = 1; i < a.length; i++){
                	  if(i != a.length - 1)
                		  coodinate += a[i] + ",";
                	  else
                		  coodinate += a[i];
                  }
                  
                  System.out.println( "insert into SYSTEM.hydrant values "
                		  	+ "('" + a[0] + "',SDO_GEOMETRY(2001, NULL,"
                		  	+ "SDO_POINT_TYPE(" + coodinate + ", NULL),"
                		  	+ "NULL, NULL));");
                  
                  mainStatement.executeUpdate( 
                		  "insert into SYSTEM.hydrant values "
                      		  	+ "('" + a[0].trim() + "',SDO_GEOMETRY(2001, NULL,"
                      		  	+ "SDO_POINT_TYPE(" + coodinate + ", NULL),"
                      		  	+ "NULL, NULL))");
            }
            
            br.close();
            reader.close();
            
            //insert firebuilding 
            
            reader = new FileReader("C:/Users/dell/Desktop/cs585-hw2 Spring 2014/firebuilding.txt");
            br = new BufferedReader(reader);
           
            str = null;
            coodinate = "" ;
            System.out.println("123");
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(" ");
                  

                  
                 System.out.println( "insert into SYSTEM.firebuilding values "+"('" + a[0].trim()+"');");
                  
                 mainStatement.executeUpdate( "insert into SYSTEM.firebuilding values "+"('" + a[0].trim()+"')");
            }
           
            br.close();
            reader.close();
           
            
        }
        catch( Exception e )
        { System.out.println( " Error 2: " + e.toString() ); }
    }
    public void Search11(String str4,String str5,String str6,String str7)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "select distinct B.bid from SYSTEM.buildings B where B.bname not in (select f.fname from SYSTEM.firebuilding f) and SDO_RELATE(B.BLoc, mdsys.sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,3),mdsys.sdo_ordinate_array("+str4+","+str5+","+str6+","+str7+")),'mask=covers+inside') = 'TRUE'" );
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search12(String str4,String str5,String str6,String str7)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "select distinct hid from SYSTEM.hydrant B where SDO_RELATE(B.HLoc, mdsys.sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,3),mdsys.sdo_ordinate_array("+str4+","+str5+","+str6+","+str7+")),'mask=covers+inside') = 'TRUE'" );
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }  
    /*****************************/
    public void Search13(String str4,String str5,String str6,String str7)
    {
    	try
        {
            System.out.println( "select b.bid from SYSTEM.firebuilding f, SYSTEM.buildings b where f.fname=b.bname and sdo_filter(b.bloc, mdsys.sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,3),mdsys.sdo_ordinate_array("+str4+","+str5+","+str6+","+str7+")),'querytype=window') = 'TRUE'" );
            mainResultSet = mainStatement.executeQuery("select b.bid from SYSTEM.firebuilding f, SYSTEM.buildings b where f.fname=b.bname and sdo_filter(b.bloc, mdsys.sdo_geometry(2003,NULL,NULL, mdsys.sdo_elem_info_array(1,1003,3),mdsys.sdo_ordinate_array("+str4+","+str5+","+str6+","+str7+")),'querytype=window') = 'TRUE'");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/    
    public void Search21(String str24,String str25)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "select b2.bid from SYSTEM.buildings b1, SYSTEM.buildings b2 where b1.bid <> b2.bid AND b1.bname = '"+str24+"' and sdo_within_distance (b1.bloc,b2.bloc,'distance="+str25+"') = 'TRUE'");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search22(String str24,String str25)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "select h.hid from SYSTEM.buildings b, SYSTEM.hydrant h where b.bname = '"+str24+"' and sdo_within_distance (b.bloc,h.hloc,'distance="+str25+"') = 'TRUE'");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search23(String str24,String str25)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "select b2.bid from SYSTEM.buildings b1, SYSTEM.firebuilding f, SYSTEM.buildings b2 where b2.bname in f.fname and b1.bname = '"+str24+"' and sdo_within_distance (b1.bloc,b2.bloc,'distance="+str25+"') = 'TRUE'");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search31(String str34,String str35)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "SELECT b1.bid FROM SYSTEM.buildings b1,SYSTEM.buildings b2 WHERE b1.bid<>b2.bid and SDO_NN(b1.bloc ,b2.bloc , 'sdo_batch_size=1') = 'TRUE' AND b2.bid = '"+str34+"' AND ROWNUM <="+str35+"");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search32(String str34,String str35)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "SELECT h.hid FROM SYSTEM.hydrant h,SYSTEM.buildings b WHERE SDO_NN(h.hloc ,b.bloc , 'sdo_batch_size=1') = 'TRUE' AND b.bid = '"+str34+"' AND ROWNUM <="+str35+"");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search33(String str34,String str35)
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "SELECT b1.bid FROM SYSTEM.buildings b1,SYSTEM.buildings b2,SYSTEM.firebuilding f WHERE SDO_NN(b1.bloc ,b2.bloc , 'sdo_batch_size=1') = 'TRUE' AND b2.bid='"+str34+"' and b1.bname in f.fname AND ROWNUM <="+str35+"");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search41()
    {
    	try
        {
            mainResultSet = mainStatement.executeQuery( "(select b.bname from SYSTEM.buildings b where b.bname like 'S%') minus (select f.fname from SYSTEM.firebuilding f)");

        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search42()
    {
    	try
        {
            System.out.println( "");
            mainResultSet2 = mainStatement.executeQuery("select f.fname from SYSTEM.firebuilding f");
            int i=0;
            String [] temp = new String [100];
            while( mainResultSet2.next() )
	    	{
            	temp[i]=mainResultSet2.getString(1);
				i++;
	    	}
           for(int j=0;j<=i-1;j++)
           {
        	System.out.println(temp[j]);
        	mainResultSet = mainStatement.executeQuery( "SELECT h.hid FROM SYSTEM.hydrant h,SYSTEM.buildings b WHERE SDO_NN(h.hloc ,b.bloc , 'sdo_batch_size=1') = 'TRUE' AND b.bname = '"+temp[j]+"' AND ROWNUM <=5");
            ShowAllTuples();
            }//System.out.println(123);
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search43()
    {
    	try
        {
            mainResultSet2 = mainStatement.executeQuery("select h.hid from SYSTEM.hydrant h");
            int i=0;
            int max=0;
            String [] temp1 = new String [1500];
            String [] temp2 = new String [1500];
            while( mainResultSet2.next() )
	    	{
            	temp1[i]=mainResultSet2.getString(1);
				i++;
	    	}
            for(int j=0;j<=i-1;j++)
            {
            	mainResultSet = mainStatement.executeQuery( "select count(*)from (select distinct b.bid from SYSTEM.buildings b, SYSTEM.hydrant h where h.hid = '"+temp1[j]+"' and sdo_within_distance (h.hloc,b.bloc,'distance=120') = 'TRUE')");
            	while(mainResultSet.next())
            	{
            		temp2[j]=mainResultSet.getString(1);
            		int n = Integer.parseInt(temp2[j]);
            		if (n>max) 
            		{	
            			max=n;
            		}
            	}
            }
            for(int k=0;k<=i-1;k++)
        	{
        		if(Integer.parseInt(temp2[k])==max)
        		{
        			System.out.println(temp1[k]);
        		}
        	}
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search44()
    {
    	try
        {
            mainResultSet2 = mainStatement.executeQuery("select b.bid from SYSTEM.buildings b");
            int i=0;
            String [] temp = new String [1500];//bid
            String [] temp2 = new String [1500];//hid
            while( mainResultSet2.next() )
	    	{
            	temp[i]=mainResultSet2.getString(1);
				i++;
	    	}
            for(int j=0;j<=i-1;j++)
            {
            mainResultSet = mainStatement.executeQuery( "SELECT h.hid FROM SYSTEM.hydrant h,SYSTEM.buildings b WHERE SDO_NN(h.hloc ,b.bloc , 'sdo_batch_size=1') = 'TRUE' AND b.bid = '"+temp[j]+"' AND ROWNUM <=1");
            while(mainResultSet.next())
        	{
        		temp2[j]=mainResultSet.getString(1);
        	}
            }
            mainStatement.executeUpdate( "create table SYSTEM.demo4temp(bid varchar(5), hid varchar(5),primary key(bid))" );
            for(int j=0;j<=i-1;j++)
            {
            	mainStatement.executeQuery("insert into SYSTEM.demo4temp values('"+temp[j]+"','"+temp2[j]+"')");
            }
            mainResultSet=mainStatement.executeQuery("select hid,num from (select d.hid,count(*) as num from SYSTEM.demo4temp d group by d.hid order by count(*) desc) where rownum<=5");
            ShowAllTuples();
            mainStatement.executeUpdate("drop table SYSTEM.demo4temp");
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void Search45()
    {
    	try
        {

            mainResultSet = mainStatement.executeQuery( "select SDO_AGGR_MBR(b.bloc) from SYSTEM.buildings b where b.bname like '%HE'");
            while(mainResultSet.next())
        	{
            STRUCT st = (oracle.sql.STRUCT) mainResultSet.getObject(1);
            JGeometry j_geom = JGeometry.load(st);
            double[] a= j_geom.getMBR();
            System.out.println("("+a[0]+","+a[1]+")");
            System.out.println("("+a[2]+","+a[3]+")");
        	}
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/    
    public void ShowMetaData()
    {
    	try
		{
				// shows meta data
	    	ResultSetMetaData meta = mainResultSet.getMetaData();
	    	for( int col=1; col<=meta.getColumnCount(); col++ )
    			System.out.println( "Column: " + meta.getColumnName(col) + "\t, Type: " + meta.getColumnTypeName(col) );
		}
    	catch( Exception e )
		{ System.out.println( " Error : " + e.toString() ); }
    }
    /*****************************/
    public void ShowAllTuples()
    {
    	try
		{
				// shows result of the query
    	    ResultSetMetaData meta = mainResultSet.getMetaData();

	    //	System.out.println("\n ** Showing all Tuples ** " );
	    	int tupleCount=1;
     		while( mainResultSet.next() )
	    	{
				System.out.print( "Tuple " + tupleCount++ + " : " );
	        	for( int col=1; col<=meta.getColumnCount(); col++)
		   			System.out.print( mainResultSet.getString( col ));
        		System.out.println();
    	    }
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
    }
    /*****************************/
    public void ConnectToDB()
    {
		try
		{
	    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			String URL = "jdbc:oracle:thin:@127.0.0.1:1521:HW2";
	    	String userName = "sys as sysdba";
	    	String password = "zjl214";

	    	mainConnection = DriverManager.getConnection(URL, userName, password);

    		mainStatement = mainConnection.createStatement();

   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
    }
}

