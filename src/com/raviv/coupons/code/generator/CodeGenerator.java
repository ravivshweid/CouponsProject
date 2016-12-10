package com.raviv.coupons.code.generator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.raviv.coupons.dao.utils.ConnectionPoolManager;
/**
 * This class generates bean classes according to given table name
 * @author raviv
 *
 */
public class CodeGenerator 
{

	public static void main(String[] args) throws Exception {
		
		
		ConnectionPoolManager connectionPoolManager = ConnectionPoolManager.getInstance();
		
		Connection con1 = connectionPoolManager.getConnection();
		String tableName;
		
		//tableName = "COMPANYS";
		tableName = "COUPONS";
		//tableName = "CUSTOMER_COUPON";
		//tableName = "CUSTOMERS";		
		//tableName = "USERS";
		
		
		

		// Java bean
		//generateJavaBean( tableName, 25, con1);
		
		// Create method
		//generateInsertStatmentCode( tableName, 25, con1);

		// get method
		//generateSelectStatmentCode( tableName, 25, con1);

		// Update method
		generateUpdateStatmentCode( tableName, 25, con1);
		
		
		connectionPoolManager.returnConnection(con1);

		//generateJavaBean ( tableName, 25, con1);
		
		/*
		Connection con2 = ConnectionPoolManager.getConnection();
		generateJavaBean ("COMPANY", 25, con2);
		
		ConnectionPoolManager.returnConnection(con1);
		ConnectionPoolManager.returnConnection(con2);

		
		Connection con3 = ConnectionPoolManager.getConnection();		
		generateJavaBean ("CUSTOMER", 25, con3);

		Connection con4 = ConnectionPoolManager.getConnection();		
		generateJavaBean ("USER_PROFILE", 25, con4);

		generateJavaBean ("USERS", 24, con4);
		ConnectionPoolManager.returnConnection(con3);
		ConnectionPoolManager.returnConnection(con4);
		
		generateJavaBean ("COUPON", 24, con4);
		*/
		
		connectionPoolManager.closeAllConnections();
	}

	@SuppressWarnings("unused")
	private static void generateJavaBean(String tableName, int columnWidth, Connection con) throws SQLException 
	{
		if ( con == null 	) 	{return;}
		if ( con.isClosed() )	{return;}
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + tableName );
       	try
		{ 
           	
       		// Print table name header
       		
	        int underLineLength = tableName.length() + 4;
	        String underLine = "";
	        for ( int i = 1; i <= underLineLength ; i++ ) 
	        {
	        	underLine += "=";
	        }
       		
       		System.out.println();
	        System.out.println( underLine );
			System.out.println("= " + tableName + " =");
	        System.out.println( underLine );
       		System.out.println();

       		
	        ResultSetMetaData metadata = rs.getMetaData();
	        int columnCount = metadata.getColumnCount();    

	        // Print java bean class
	        System.out.println("public class "  + toClassName(tableName) + " extends InfraBean {");
	        System.out.println();

	        // Print java bean properties
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
            	String columnName = metadata.getColumnName(i);
            	columnName = toCamelCase(columnName);
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	String addRemarks = "";
            	
            	if ( isControlField(columnName) )
            		addRemarks = "//";
            		
            	System.out.println( addRemarks + "\t" + "private"+ "\t" + dataType + "\t" + columnName + ";");
            	
            	
	        }
	        System.out.println();
	        System.out.println("}");
	        System.out.println();
	        
	        // Print column header
	        String header = "";
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
            	String columnName = metadata.getColumnName(i);
            	String columnNameFormated = String.format("%1$-" + columnWidth + "s", columnName);
	        	header += columnNameFormated;
	        }	        
	        System.out.println(header);

	        // Print column header under line
	        underLineLength = columnCount * columnWidth;
	        underLine = "";
	        for ( int i = 1; i <= underLineLength ; i++ ) 
	        {
	        	underLine += "=";
	        }
	        System.out.println( underLine );
	        
	        // Print rows data
	        while ( rs.next() ) 
	        {
	            String row = "";
	            for (int i = 1; i <= columnCount; i++) 
	            {	            	
	            	String columnData = rs.getString(i);
	            	String columnDataRightPad = String.format("%1$-" + columnWidth + "s", columnData);
	            	row += columnDataRightPad;
	            }
	            System.out.println(row);
	        }
	       	
		}
       	catch (SQLException e)
       	{
       		e.printStackTrace();
       	}
		finally
		{
			st.close();
			rs.close();
		}
	}// end printUserProfileTable

	@SuppressWarnings("unused")
	private static void generateInsertStatmentCode(String tableName, int columnWidth, Connection con) throws SQLException 
	{
		if ( con == null 	) 	{return;}
		if ( con.isClosed() )	{return;}
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + tableName );
       	try
		{ 
           	
	        ResultSetMetaData metadata = rs.getMetaData();
	        int columnCount = metadata.getColumnCount();    

	        // Print java bean class
	        System.out.println("public class "  + toClassName(tableName) + " extends InfraBean {");
	        System.out.println();

	        // Print java bean properties
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	
            	String columnName = metadata.getColumnName(i);
            	columnName = toCamelCase(columnName);
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	String addRemarks = "";
            	
            	if ( isControlField(columnName) )
            		addRemarks = "//";
            		
            	System.out.println( addRemarks + "\t" + "private"+ "\t" + dataType + "\t" + columnName + ";");
            	
            	
	        }
	        System.out.println();
	        System.out.println("}");
	        System.out.println();

	        // Print insert into statement
	        String doubleQuotes = "\"";
	        System.out.println( "sql = " + doubleQuotes + "INSERT INTO "  + tableName + " (" + doubleQuotes + ";");
	        String questionMarks = "";
	        int k =0;
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	if ( metadata.isAutoIncrement(i) ) continue;
            	String columnName = metadata.getColumnName(i);
            	
            	if ( "SYS_CREATION_DATE".equals(columnName) ) continue;
            	if ( "SYS_UPDATE_DATE".equals(columnName) ) continue;
            	
            	k++;
            	
            	String columnNameFormated ;
	        	
	        	if ( k >= 2 ) 
 	        	{
	        		columnNameFormated = "sql += " + doubleQuotes + "\t," + columnName + doubleQuotes + ";";
	        		questionMarks += ", ?";
	        	}
	        	else
	        	{
	        		columnNameFormated = "sql += " + doubleQuotes + "\t " + columnName + doubleQuotes + ";";
	        		questionMarks += "?";
	        	}
	        	
	        	System.out.println(columnNameFormated);
	        }
	        System.out.println("sql += " + doubleQuotes +")"+doubleQuotes + ";");
	        System.out.println("sql += " + doubleQuotes + "VALUES (" + questionMarks + ")" + doubleQuotes + ";");

	        

	        // Print java bean properties
	        k=0;
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {

	        	if ( metadata.isAutoIncrement(i) ) continue;
            	String columnName = metadata.getColumnName(i);
            	
            	if ( "SYS_CREATION_DATE".equals(columnName) ) continue;
            	if ( "SYS_UPDATE_DATE".equals(columnName) ) continue;
            	
            	k++;
            	

            	columnName = firstToUpperCase(toCamelCase(columnName));
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	//dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	//String addRemarks = "";
            	
            	//if ( isControlField(columnName) )
            	//	addRemarks = "//";
            	String index;
            	
            	if ( k <=9 )
            	{
            		index =  k + " ";
            	}
            	else
            	{
            		index = "" + k;
            	}
            		
            	System.out.println( "preparedStatement.set" + firstToUpperCase(dataType) + "\t(" +index+",\t"+ firstToLowerCase(toClassName(tableName)) +".get"+columnName+"() );");
            	
            	
	        }
	        	       	
		}
       	catch (SQLException e)
       	{
       		e.printStackTrace();
       	}
		finally
		{
			st.close();
			rs.close();
		}
	}// end 

	@SuppressWarnings("unused")
	private static void generateUpdateStatmentCode(String tableName, int columnWidth, Connection con) throws SQLException 
	{
		if ( con == null 	) 	{return;}
		if ( con.isClosed() )	{return;}
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + tableName );
       	try
		{ 
           	
	        ResultSetMetaData metadata = rs.getMetaData();
	        int columnCount = metadata.getColumnCount();    

	        // Print java bean class
	        System.out.println("public class "  + toClassName(tableName) + " extends InfraBean {");
	        System.out.println();

	        // Print java bean properties
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	
            	String columnName = metadata.getColumnName(i);
            	columnName = toCamelCase(columnName);
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	String addRemarks = "";
            	
            	if ( isControlField(columnName) )
            		addRemarks = "//";
            		
            	System.out.println( addRemarks + "\t" + "private"+ "\t" + dataType + "\t" + columnName + ";");
            	
            	
	        }
	        System.out.println();
	        System.out.println("}");
	        System.out.println();

	        // Print insert into statement
	        String doubleQuotes = "\"";
	        System.out.println( "sql = " + doubleQuotes + "UPDATE "  + tableName + " SET " + doubleQuotes + ";" );
	        String questionMarks = "";
	        String autoIncrementColumn ="";
	        int k =0;
	        boolean isFirstColumn = true;
        	String columnNameFormated ;

	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	if ( metadata.isAutoIncrement(i) ) {
	        		autoIncrementColumn = metadata.getColumnName(i);
	        		continue;
	        	}
            	String columnName = metadata.getColumnName(i);
            	
            	if ( "SYS_CREATION_DATE".equals(columnName) ) continue;
            	
            	if ( "CREATED_BY_USER_ID".equals(columnName) ) continue;

	        	String strComa;

	        	if ( isFirstColumn ) 
 	        	{
	        		strComa = " ";
	        		isFirstColumn = false;
	        	}
	        	else
	        	{
	        		strComa = ",";
	        	}

            	
            	if ( "SYS_UPDATE_DATE".equals(columnName) ){
            		
    	        	columnName = String.format("%1$-" + 25 + "s", columnName);

            		columnNameFormated = "sql += " + doubleQuotes + "\t" + strComa + columnName + " = ?" + doubleQuotes + ";";
    	        	
    	        	
    	        	System.out.println(columnNameFormated);
            		
            		continue;
            	}

            	
            	k++;
            	
	        	
	        	columnName = String.format("%1$-" + 25 + "s", columnName);

        		columnNameFormated = "sql += " + doubleQuotes + "\t" + strComa + columnName + " = ? " + doubleQuotes + ";";
	        	
	        	
	        	System.out.println(columnNameFormated);
	        }

        	System.out.println("sql += " + doubleQuotes + "WHERE" + doubleQuotes + ";");

    		columnNameFormated = "sql += " + doubleQuotes + "      \t" + autoIncrementColumn  + " = ? " + doubleQuotes + ";";
        	System.out.println(columnNameFormated);
	        
	        
	        
	        // Print java bean properties
	        k=0;
        	String index ="";
        	
        	
        	String autoIncrementColumnDataType="";
        	
       
        	
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {

	        	if ( metadata.isAutoIncrement(i) ) {
	        		autoIncrementColumn = metadata.getColumnName(i);
	        		autoIncrementColumn = firstToUpperCase(toCamelCase(autoIncrementColumn));
	        		
	            	autoIncrementColumnDataType = metadata.getColumnTypeName(i);   	
	            	autoIncrementColumnDataType = sqToJavaPrimitive(autoIncrementColumnDataType);
	        		
	        		continue;
	        	}
            	String columnName = metadata.getColumnName(i);
            	
            	if ( "SYS_CREATION_DATE".equals(columnName) ) continue;            	
            	if ( "CREATED_BY_USER_ID".equals(columnName) ) continue;
            	
            	k++;
            	

            	columnName = firstToUpperCase(toCamelCase(columnName));
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	//dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	//String addRemarks = "";
            	
            	//if ( isControlField(columnName) )
            	//	addRemarks = "//";
            	
            	if ( k <=9 )
            	{
            		index =  k + " ";
            	}
            	else
            	{
            		index = "" + k;
            	}
            	
            	if ( "SysUpdateDate".equals(columnName) ) {
            		
                	System.out.println( "preparedStatement.setTimestamp\t(" +index+",\t new Timestamp ( System.currentTimeMillis() ) );");
            		
            		continue;
            	}
            	
            		
            	System.out.println( "preparedStatement.set" + firstToUpperCase(dataType) + "\t(" +index+",\t"+ firstToLowerCase(toClassName(tableName)) +".get"+columnName+"() );");
            	
            	
	        }
	        
	        k++;
        	if ( k <=9 )
        	{
        		index =  k + " ";
        	}
        	else
        	{
        		index = "" + k;
        	}
	        System.out.println( "preparedStatement.set" + firstToUpperCase(autoIncrementColumnDataType) + "\t(" +index+",\t"+ firstToLowerCase(toClassName(tableName)) +".get"+autoIncrementColumn+"() );");

	        
		}
       	catch (SQLException e)
       	{
       		e.printStackTrace();
       	}
		finally
		{
			st.close();
			rs.close();
		}
	}// end 

	@SuppressWarnings("unused")
	private static void generateSelectStatmentCode(String tableName, int columnWidth, Connection con) throws SQLException 
	{
		if ( con == null 	) 	{return;}
		if ( con.isClosed() )	{return;}
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + tableName );
       	try
		{ 
           	
	        ResultSetMetaData metadata = rs.getMetaData();
	        int columnCount = metadata.getColumnCount();    

	        // Print java bean class
	        System.out.println("public class "  + toClassName(tableName) + " extends InfraBean {");
	        System.out.println();

	        // Print java bean properties
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	
            	String columnName = metadata.getColumnName(i);
            	columnName = toCamelCase(columnName);
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	String addRemarks = "";
            	
            	if ( isControlField(columnName) )
            		addRemarks = "//";
            		
            	System.out.println( addRemarks + "\t" + "private"+ "\t" + dataType + "\t" + columnName + ";");
            	
            	
	        }
	        System.out.println();
	        System.out.println("}");
	        System.out.println();

	        // Print insert into statement
	        String doubleQuotes = "\"";
	        System.out.println( "sql = " + doubleQuotes + "INSERT INTO "  + tableName + " (" + doubleQuotes + ";");
	        String questionMarks = "";
	        int k =0;
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
	        	//if ( metadata.isAutoIncrement(i) ) continue;
            	String columnName = metadata.getColumnName(i);
            	
            	//if ( "SYS_CREATION_DATE".equals(columnName) ) continue;
            	//if ( "SYS_UPDATE_DATE".equals(columnName) ) continue;
            	
            	k++;
            	
            	String columnNameFormated ;
	        	
	        	if ( k >= 2 ) 
 	        	{
	        		columnNameFormated = "sql += " + doubleQuotes + "\t," + columnName + doubleQuotes + ";";
	        		questionMarks += ", ?";
	        	}
	        	else
	        	{
	        		columnNameFormated = "sql += " + doubleQuotes + "\t " + columnName + doubleQuotes + ";";
	        		questionMarks += "?";
	        	}
	        	
	        	System.out.println(columnNameFormated);
	        }
	        System.out.println("sql += " + doubleQuotes +")"+doubleQuotes + ";");
	        System.out.println("sql += " + doubleQuotes + "VALUES (" + questionMarks + ")" + doubleQuotes + ";");

	        

	        // Print java bean properties
	        k=0;
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {

	        	//if ( metadata.isAutoIncrement(i) ) continue;
            	String columnName = metadata.getColumnName(i);
            	
            	String columnNameDB = columnName;
            	
            	//if ( "SYS_CREATION_DATE".equals(columnName) ) continue;
            	//if ( "SYS_UPDATE_DATE".equals(columnName) ) continue;
            	
            	k++;
            	

            	columnName = firstToUpperCase(toCamelCase(columnName));
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	//dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	//String addRemarks = "";
            	
            	//if ( isControlField(columnName) )
            	//	addRemarks = "//";

            		
            	//System.out.println( "preparedStatement.set" + firstToUpperCase(dataType) + "\t(" +index+",\t"+ firstToLowerCase(toClassName(tableName)) +".get"+columnName+"());");

            	String setColumnName = String.format("%1$-" + 15 + "s", columnName);
            	String getDataType   = String.format("%1$-" + 10 + "s", firstToUpperCase(dataType));
            	
            	System.out.println( "returnObj.set"+setColumnName+"\t( resultSet.get"+getDataType+"( " + doubleQuotes + columnNameDB + doubleQuotes + " ) );");

            	
            	
	        }
	        	       	
		}
       	catch (SQLException e)
       	{
       		e.printStackTrace();
       	}
		finally
		{
			st.close();
			rs.close();
		}
	}// end 
	
	@SuppressWarnings("unused")
	private static void printCoupon(String tableName, int columnWidth, Connection con) throws SQLException 
	{
		if ( con == null 	) 	{return;}
		if ( con.isClosed() )	{return;}
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + tableName );
       	try
		{ 
           	
       		// Print table name header
       		
	        int underLineLength = tableName.length() + 4;
	        String underLine = "";
	        for ( int i = 1; i <= underLineLength ; i++ ) 
	        {
	        	underLine += "=";
	        }
       		
       		System.out.println();
	        System.out.println( underLine );
			System.out.println("= " + tableName + " =");
	        System.out.println( underLine );
       		System.out.println();

       		
	        ResultSetMetaData metadata = rs.getMetaData();
	        int columnCount = metadata.getColumnCount();    

	        // Print java bean class
	        System.out.println("public class "  + toClassName(tableName) + " extends TableBean {");
	        System.out.println();

	        // Print java bean properties
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
            	String columnName = metadata.getColumnName(i);
            	columnName = toCamelCase(columnName);
            	String dataType = metadata.getColumnTypeName(i);
            	
            	dataType = sqToJavaPrimitive(dataType);
            	
            	dataType = String.format("%1$-" + 25 + "s", dataType);
            	
            	String addRemarks = "";
            	
            	if ( isControlField(columnName) )
            		addRemarks = "//";
            		
            	System.out.println( addRemarks + "\t" + "private"+ "\t" + dataType + "\t" + columnName + ";");
            	
            	
	        }
	        System.out.println();
	        System.out.println("}");
	        System.out.println();
	        
	        // Print column header
	        String header = "";
	        for ( int i = 1; i <= columnCount; i++ ) 
	        {
            	String columnName = metadata.getColumnName(i);
            	String columnNameFormated = String.format("%1$-" + columnWidth + "s", columnName);
	        	header += columnNameFormated;
	        }	        
	        System.out.println(header);

	        // Print column header under line
	        underLineLength = columnCount * columnWidth;
	        underLine = "";
	        for ( int i = 1; i <= underLineLength ; i++ ) 
	        {
	        	underLine += "=";
	        }
	        System.out.println( underLine );
	        
	        // Print rows data
	        while ( rs.next() ) 
	        {
	            String row = "";
	            for (int i = 1; i <= columnCount; i++) 
	            {	            	
	            	String columnData = rs.getString(i);
	            	String columnDataRightPad = String.format("%1$-" + columnWidth + "s", columnData);
	            	row += columnDataRightPad;
	            }
	            System.out.println(row);
	        }
	       	
		}
       	catch (SQLException e)
       	{
       		e.printStackTrace();
       	}
		finally
		{
			st.close();
			rs.close();
		}
	}// end printUserProfileTable

	static String toCamelCase(String s)
	{
	   String[] parts = s.split("_");
	   String camelCaseString = "";
	   boolean isFirstWord = true;
	   for (String part : parts)
	   {
	      camelCaseString = camelCaseString + toProperCase(part,isFirstWord);
	      if (isFirstWord) isFirstWord = false;
	   }
	   return camelCaseString;
	}

	static String toProperCase(String s, boolean isFirstWord) 
	{
		if (isFirstWord)
		{
			return s.substring(0).toLowerCase();
		}
		else
		{
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();			
		}
		
	}

	static String firstToLowerCase(String s) 
	{
		return s.substring(0, 1).toLowerCase() + s.substring(1);				
	}

	static String firstToUpperCase(String s) 
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1);				
	}

	static String sqToJavaPrimitive(String s)
	{
		
		if (s == "bigint") 		return "long";

		if (s == "binary") 		return "byte[]";

		if (s == "bit") 		return "boolean";

		if (s == "char") 		return "String";

		if (s == "date") 		return "long";

		if (s == "datetime") 	return "long";

		if (s == "datetime2") 	return "long";

//		if (s == "datetimeoffset (2)") 	return "microsoft.sql.DateTimeOffset";

		if (s == "decimal") 	return "java.math.BigDecimal";

		if (s == "float")		return "double";

		if (s == "decimal") 	return "java.math.BigDecimal";

		if (s == "image") 		return "byte[]";

		if (s == "int") 		return "int";

		if (s == "money") 		return "java.math.BigDecimal";
		
		if (s == "nchar") 		return "String";
		
		if (s == "ntext") 		return "String";

		if (s == "numeric") 		return "java.math.BigDecimal";

		if (s == "nvarchar") 		return "String";

		if (s == "nvarchar(max)") 		return "String";

		if (s == "real") 		return "float";

		if (s == "smalldatetime") 		return "long";

		if (s == "smallint") 		return "short";

		if (s == "smallmoney") 		return "java.math.BigDecimal";

		if (s == "text") 		return "String";

		if (s == "text") 		return "String";

		if (s == "varchar")		return "String";

		if (s == "varchar(max)")		return "String";


		return s;
	}

	static String toClassName(String s)
	{
	   String[] parts = s.split("_");
	   String camelCaseString = "";
	   boolean isFirstWord = false;
	   for (String part : parts)
	   {
	      camelCaseString = camelCaseString + toProperCase(part,isFirstWord);
	   }
	   
	   if (camelCaseString.charAt(camelCaseString.length()-1)=='s')
	   {
		   camelCaseString = camelCaseString.substring(0,camelCaseString.length()-1);
	   }
	   
	   return camelCaseString;
	}

	static boolean isControlField(String s)
	{
		if ("sysCreationDate".equals(s)) 	return true;
		if ("sysUpdateDate".equals(s)) 		return true;
		if ("createdByUserId".equals(s)) 	return true;
		if ("updatedByUserId".equals(s)) 	return true;
		
		return false;
	}
	
}
