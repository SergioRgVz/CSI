package es.uca.gii.csi21.artyom.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import es.uca.gii.csi21.artyom.data.Data;
import es.uca.gii.csi21.artyom.data.Staff;

public class StaffTest {

	@BeforeAll
	/* TODO description*/
	public static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	/**
	 * @throws Exception
	 * TODO description
	 */
	@Disabled
	@Test
	public void testConstructor() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Staff staff = new Staff(10);
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT ID, Name FROM staff");
			
			rs.next();
			
			assertEquals(rs.getInt("ID"), staff.getiID());
			assertEquals(rs.getString("Name"), staff.getsName());
		}
		catch(SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) rs.close();
		}
	}
	
	/**
	 * @throws Exception
	 * TODO description
	 */
	@Disabled
	//@Test
	public void testCreate() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Staff staff = Staff.Create(2, "Sergio");
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT ID, Name FROM staff");
			
			rs.next();
			
			assertEquals(rs.getInt("ID"), staff.getiID());
			assertEquals(rs.getString("Name"), staff.getsName());
			
			staff.Delete();
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testSelect() throws Exception {
		Staff.Create(3, "Claudia");
		
		ArrayList<Staff> aStaff = Staff.Select(3, null);
		Staff staff = aStaff.get(0);
		assertEquals(3, staff.getiID());
		assertEquals("Claudia", staff.getsName());

		aStaff = Staff.Select(0, "Claudia");
		staff = aStaff.get(0);
		assertEquals(3, staff.getiID());
		assertEquals("Claudia", staff.getsName());
		
		aStaff = Staff.Select(3, "Claudia");
		staff = aStaff.get(0);
		assertEquals(3, staff.getiID());
		assertEquals("Claudia", staff.getsName());
	
		staff.Delete();
	}
}
