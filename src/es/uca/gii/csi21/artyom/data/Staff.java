package es.uca.gii.csi21.artyom.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.StringJoiner;


/**
 * @author sergi
 *
 */
public class Staff {
	private int _iID;
	private String _sName;
	private boolean _bIsDeleted;
	
	/**
	 * @return 
	 */
	public int getiID() {
		return _iID;
	}
	/**
	 * @param iID
	 */
	public void setiID(int iID) {
		this._iID = iID;
	}
	/**
	 * @return
	 */
	public String getsName() {
		return _sName;
	}
	/**
	 * @param sName
	 */
	public void setsName(String sName) {
		this._sName = sName;
	}
	
	public boolean is_bIsDeleted() {
		return _bIsDeleted;
	}
	public void set_bIsDeleted(boolean _bIsDeleted) {
		this._bIsDeleted = _bIsDeleted;
	}
	
	/**
	 * @param iID
	 * @throws Exception
	 * TODO description
	 */
	public Staff(int iID) throws Exception{
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT ID, Name FROM staff");
			
			rs.next();
			_iID = rs.getInt("ID");
			_sName = rs.getString("Name");	
		} catch (Exception e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	/**
	 * TODO description
	 */
	@Override
	public String toString() {
		String sString = new String();
		super.toString();
		sString = _iID + ":" + _sName;
		return sString;
	}
	
	/**
	 * @param iID
	 * @param sName
	 * @return
	 * TODO description
	 * @throws Exception
	 */
	
	/* TODO cambiar lastID (ya no es autoincremental) */
	public static Staff Create(int iID, String sName) throws Exception {
		Connection con = null;
		
		try {
			con = Data.Connection();
			iID = Data.LastId(con) + 1;
			con.createStatement().executeUpdate("INSERT INTO staff (ID, Name) VALUES (" + iID + ","
			+ Data.String2Sql(sName, true, false) + ")");
			return new Staff(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally { if(con != null) con.close(); }
	}
	
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) throw new IOException(_iID + " " + _sName + "ya ha sido eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM staff WHERE id = " + _iID + ";");
			_bIsDeleted = true;
			
		} catch (SQLException ee) { throw ee; }
		finally { if(con != null) con.close(); }
	}
	
	public void Update(String sQuery) throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted) throw new IOException(_iID + " " + _sName + "ya ha sido eliminado.");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) { throw e; }
		finally { if(con != null) con.close(); }
	}
	
	public void Update() throws Exception {
		Update("UPDATE staff SET Name = " + _sName + "WHERE id = " + _iID); 
	}
	
	public static ArrayList<Staff> Select(int iID, String sName) throws Exception{
		Connection con = null;
		ResultSet rs = null;
		
		ArrayList<Staff> aStaff = new ArrayList<Staff>();
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT staff.ID, staff.Name FROM staff"
					+ Where(iID, sName));
			
			while(rs.next())
				aStaff.add(Create(rs.getInt("ID"), rs.getString("Name")));
			return aStaff;
		} catch (Exception e) { throw e; }
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	public static String Where(int iID, String sName) {
		StringJoiner stringjoiner = new StringJoiner(" AND ");
		
		if(iID != 0) stringjoiner.add("ID = " + iID);
		
		if(sName != null) stringjoiner.add("Name LIKE " + Data.String2Sql(sName.toString(), true, true));

		return sName;
	}
}
