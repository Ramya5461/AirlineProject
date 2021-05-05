import java.sql.*;
import java.util.*;

public class EmployeeTest {
	public static void main(String[] args) {
		/*
		Employee empObj1 = new Employee();
		
		empObj1.setEmployeeNumber(8678);
		empObj1.setEmployeeName("Krithi");
		empObj1.setEmployeeJob("TEAM LEAD");
		empObj1.setEmployeeManager(7839);
		empObj1.setEmployeeHiredate("18-MAR-2021");
		empObj1.setEmployeeSalary(18000);
		empObj1.setEmployeeComm(0);
		empObj1.setEmployeeDeptno(40);
		
		EmployeeDAOImpl ddiObj = new EmployeeDAOImpl(); // driver loaded, connected too
		
		ddiObj.addEmployee(empObj1); // insert query is hidden inside it
		*/
		System.out.println("--------------------------");
		
		/*
		Employee empObj1 = new Employee();
		
		empObj1.setEmployeeNumber(8676);
		empObj1.setEmployeeName("RAMYA");
		empObj1.setEmployeeJob("MANAGER");
		empObj1.setEmployeeManager(7839);
		empObj1.setEmployeeHiredate("18-MAR-2021");
		empObj1.setEmployeeSalary(1800);
		empObj1.setEmployeeComm(0);
		empObj1.setEmployeeDeptno(10);
		
		
		EmployeeDAOImpl ddiObj = new EmployeeDAOImpl(); // driver loaded, connected too
		
		ddiObj.modifyEmployee(empObj1); // udpate query is hidden inside it
		*/
		
		/*
		Employee empObj1 = new Employee();
	
		empObj1.setEmployeeNumber(8678); //just fill the primary key
		
		EmployeeDAOImpl ddiObj = new EmployeeDAOImpl();
		ddiObj.removeEmployee(empObj1); // delete is hidden inside it
		*/
		
		/*
		Employee empObj = null;
		EmployeeDAOImpl ddiObj = new EmployeeDAOImpl();
		empObj = ddiObj.findEmployee(8676); // select query hidden in it
		System.out.println("emp number   : "+empObj.getEmployeeNumber());
		System.out.println("emp name     : "+empObj.getEmployeeName());
		System.out.println("emp job      : "+empObj.getEmployeeJob());
		System.out.println("emp manager  : "+empObj.getEmployeeManager());
		System.out.println("emp hiredate : "+empObj.getEmployeeHiredate());
		System.out.println("emp salary   : "+empObj.getEmployeeSalary());
		System.out.println("emp comm     : "+empObj.getEmployeeComm());
		System.out.println("emp deptno   : "+empObj.getEmployeeDeptno());
		*/
		
	
		List<Employee> empList = null;
		
		EmployeeDAOImpl ddiObj = new EmployeeDAOImpl();
		
		empList = ddiObj.findEmployees(); // select * from dept hidden in it

		Iterator<Employee> iter = empList.iterator();
		
		while(iter.hasNext()) {
			Employee empObj = iter.next();
			System.out.println("emp number   : "+empObj.getEmployeeNumber());
			System.out.println("emp name     : "+empObj.getEmployeeName());
			System.out.println("emp Job      : "+empObj.getEmployeeJob());
			System.out.println("emp manager  : "+empObj.getEmployeeManager());
			System.out.println("emp manager  : "+empObj.getEmployeeHiredate());
			System.out.println("emp salary   : "+empObj.getEmployeeSalary());
			System.out.println("emp comm     : "+empObj.getEmployeeComm());
			System.out.println("emp deptno   : "+empObj.getEmployeeDeptno());
			System.out.println("--------------------");
		}
	
	}
}

//1. POJO
class Employee  // pojo is same as dept table structure
{
	private int employeeNumber; //same as deptno column
	private String employeeName; // same as dname column
	private String employeeJob; //same as loc column
	private int employeeManager;
	private String employeeHiredate;
	private int employeeSalary;
	private int employeeComm;
	private int employeeDeptno;
	
	//setter and getter to set and fetch information 
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getEmployeeJob() {
		return employeeJob;
	}
	public void setEmployeeJob(String employeeJob) {
		this.employeeJob = employeeJob;
	}
	public int getEmployeeManager() {
		return employeeManager;
	}
	public void setEmployeeManager(int employeeManager) {
		this.employeeManager = employeeManager;
	}
	public String getEmployeeHiredate() {
		return employeeHiredate;
	}
	public void setEmployeeHiredate(String employeeHiredate) {
		this.employeeHiredate = employeeHiredate;
	}
	public int getEmployeeSalary() {
		return employeeSalary;
	}
	public void setEmployeeSalary(int employeeSalary) {
		this.employeeSalary = employeeSalary;
	}
	public int getEmployeeComm() {
		return employeeComm;
	}
	public void setEmployeeComm(int employeeComm) {
		this.employeeComm = employeeComm;
	}
	public int getEmployeeDeptno() {
		return employeeDeptno;
	}
	public void setEmployeeDeptno(int employeeDeptno) {
		this.employeeDeptno = employeeDeptno;
	}
}

interface EmployeeDAO // POJO crud interface
{
	void addEmployee(Employee dRef);		//	C - add - insert
	Employee findEmployee(int empno);		//  R - find - select
	List<Employee> findEmployees();			//  R - find - select all
	void modifyEmployee(Employee dRef);		//  U - modify - update
	void removeEmployee(Employee dRef);     //  D - remove - delete
}

class EmployeeDAOImpl implements EmployeeDAO
{
	Connection conn;
	ResultSet rs;
	Statement st;
	PreparedStatement pst;
	
	EmployeeDAOImpl() {
		try
		{
			//1st step : load the driver
			System.out.println("Trying to load the driver.......");
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("Driver loaded....");
		
			//2nd step : connect to the database 
			System.out.println("Trying to connect to the database");
			//jdbc:oracle:thin:@localhost:1521:yourInstanceName XE/ORCL/OSE whaterver name found in tnsnames.ora file
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ORCL","system","manager");
			System.out.println("Connected to the database");
		}
		catch(Exception e) {
			System.out.println("Some Problem : "+e);
		}
	}
	
	public void addEmployee(Employee dRef) { //insert query
		try {
			PreparedStatement pst = conn.prepareStatement("insert into emp values (?,?,?,?,?,?,?,?)"); // this is for DML
			pst.setInt(1, dRef.getEmployeeNumber());	
			pst.setString(2, dRef.getEmployeeName()); 
			pst.setString(3, dRef.getEmployeeJob());
			pst.setInt(4, dRef.getEmployeeManager());
			pst.setString(5, dRef.getEmployeeHiredate());
			pst.setInt(6, dRef.getEmployeeSalary());
			pst.setInt(7, dRef.getEmployeeComm());
			pst.setInt(8, dRef.getEmployeeDeptno());
			System.out.println("PrepareStatement made....for DML");
			
			System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
			int rows = pst.executeUpdate(); //fire the pst associated insert query
			System.out.println("Record inserted..."+rows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Employee findEmployee(int empno) {
		Employee empObj = null;
		
		try {
			st = conn.createStatement();
			System.out.println("Statment made .... ");
			
			rs = st.executeQuery("select * from emp where empno="+empno); // type the query here
			System.out.println("Query fired...and got the result....");
			System.out.println("Now processing the result....."); //5th step : process the result
			if(rs.next()) { // process the result set like a cursor program
				int x = rs.getInt(1); 	
				String y = rs.getString(2); // dname
				String z = rs.getString(3); 
				int a=rs.getInt(4);
				String b=rs.getString(5);
				int c=rs.getInt(6);
				int d=rs.getInt(7);
				int e=rs.getInt(8);
				
				empObj = new Employee(); //empty single object
				empObj.setEmployeeNumber(x);
				empObj.setEmployeeName(y);
				empObj.setEmployeeJob(z);
				empObj.setEmployeeManager(a);
				empObj.setEmployeeHiredate(b);
				empObj.setEmployeeSalary(c);
				empObj.setEmployeeComm(d);
				empObj.setEmployeeDeptno(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empObj;
	}
	
	public 	List<Employee> findEmployees() {
		List<Employee> empList = new ArrayList<Employee>(); //empty
		
		try {
			Statement st = conn.createStatement();
			System.out.println("Statement made...");
			rs = st.executeQuery("select * from emp"); // type the query here
			System.out.println("Query fired...and got the result....");
			System.out.println("Now processing the result....."); //5th step : process the result
			while(rs.next()) { // process the result set like a cursor program
				int x = rs.getInt(1); 	
				String y = rs.getString(2); // dname
				String z = rs.getString(3); 
				int a=rs.getInt(4);
				String b=rs.getString(5);
				int c=rs.getInt(6);
				int d=rs.getInt(7);
				int e=rs.getInt(8);
				
				Employee empObj = new Employee(); //empty single object
				empObj.setEmployeeNumber(x);
				empObj.setEmployeeName(y);
				empObj.setEmployeeJob(z);
				empObj.setEmployeeManager(a);
				empObj.setEmployeeHiredate(b);
				empObj.setEmployeeSalary(c);
				empObj.setEmployeeComm(d);
				empObj.setEmployeeDeptno(e);
				
				empList.add(empObj);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empList;
	}
	
	public 	void modifyEmployee(Employee dRef) {
		try {
			System.out.println("Trying to make a PreparedStatement for DML(update)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
			PreparedStatement pst = conn.prepareStatement("update emp set ename=?, job=?, mgr=?, hiredate=?, sal=?, comm=?, deptno=?  where empno=?"); // this is for DML	
			pst.setString(1, dRef.getEmployeeName()); 
			pst.setString(2, dRef.getEmployeeJob());
			pst.setInt(3, dRef.getEmployeeManager());
			pst.setString(4, dRef.getEmployeeHiredate());
			pst.setInt(5, dRef.getEmployeeSalary());
			pst.setInt(6, dRef.getEmployeeComm());
			pst.setInt(7, dRef.getEmployeeDeptno());
			pst.setInt(8, dRef.getEmployeeNumber());
			
			System.out.println("PrepareStatement made....for DML update");
			System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
			int rows = pst.executeUpdate();
			System.out.println("Record updated : "+rows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public 	void removeEmployee(Employee dRef) {
		try {
			System.out.println("Trying to make a PreparedStatement for DML(delete)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
			PreparedStatement pst = conn.prepareStatement("delete from emp where empno=?"); // this is for DML
			pst.setInt(1, dRef.getEmployeeNumber()); //set the question mark with dno
			
			System.out.println("PrepareStatement made....for DML delete");
			System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
			int rows = pst.executeUpdate(); //fire the pst associated insert query
			System.out.println("Record deleted..."+rows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}