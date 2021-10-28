package es.uca.gii.csi21.artyom.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi21.artyom.data.Data;
@Disabled
class DataTest {

	/**
	 * @throws Exception
	 * TODO description
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	/**
	 * @throws Exception
	 * TODO description
	 */
	@Disabled
	@Test
	void testTableAccess() throws Exception {
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT * FROM staff");
			
			int i = 0;
			while (rs.next()) {
				System.out.println(rs.getInt("ID") + " " + rs.getString("Name"));
				i++;
			}
			assertEquals(2, i);
			
			int j = rs.getMetaData().getColumnCount();
			
			assertEquals(2, j);
		}catch (Exception e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	/**
	 * @throws Exception
	 * TODO description
	 */
	@Disabled
	@Test
	void TestString2Sql() throws Exception {
		try {
			String sTest = new String();
			
			sTest = Data.String2Sql("hola", false, false);
			assertEquals("hola", sTest);
			
			sTest = Data.String2Sql("hola", true, false);
			assertEquals("'hola'",sTest);
			
			sTest = Data.String2Sql("hola", false, true);
			assertEquals("%hola%", sTest);
			
			sTest = Data.String2Sql("hola", true, true);
			assertEquals("'%hola%'", sTest);
			
			sTest = Data.String2Sql("O'Connell", false, false);
			assertEquals("O''Connell", sTest);
			
			sTest = Data.String2Sql("O'Connell", true, false);
			assertEquals("'O''Connell'", sTest);
			
			sTest = Data.String2Sql("'Smith '", false, true);
			assertEquals("%''Smith ''%", sTest);
			
			sTest = Data.String2Sql("'Smith '", true, false);
			assertEquals("'''Smith '''", sTest);
			
			sTest = Data.String2Sql("'Smith '", true, true);
			assertEquals("'%''Smith ''%'", sTest);
		}catch (Exception e) { throw e; }
	}
	
	/**
	 * @throws Exception
	 * TODO description
	 */
	@Disabled
	@Test
	void TestBoolean2Sql() throws Exception {
		try {
			assertEquals(0,Data.Boolean2Sql(false));
			assertEquals(1,Data.Boolean2Sql(true));
		}catch (Exception e) { throw e; }
	}
	
	
}
