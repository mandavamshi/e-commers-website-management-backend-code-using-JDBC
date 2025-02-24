package SellerCustomerDao;

//import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SellerCustomerDao {
	
//	SELLER
	public void sellerlogin() {

		Connection con1 = null;
		PreparedStatement ps1 = null;
		Scanner s1= new Scanner(System.in);
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps1=con1.prepareStatement("select * from seller where Semail=? AND Spassword=?");
			System.out.println("Enter Email:");
			String em=s1.next();
			ps1.setString(1, em);
			System.out.println("Enter Password:");
			String ep=s1.next();
			ps1.setString(2, ep);
			ResultSet set=ps1.executeQuery();
			while(set.next()) {
				if((set.getString(3).equals(em)) && (set.getString(5).equals(ep))) {
					int sid=set.getInt(1);
					System.out.println("Choose the required Operation\n 1.Add Product details\n 2.Display Product details\n 3.Update Product details\n 4.Remove a Product");
					int a1=s1.nextInt();
					switch (a1) {
					case 1: {
						dao.addProducts(sid);
						break;
					}
					case 2: {
						dao.displaySellerProducts(sid);
						break;
					}
					case 3: {
						dao.updateProducts(sid);
						break;
					}
					case 4: {
						dao.removeProducts(sid);
						break;
					}
					
					default:
						throw new IllegalArgumentException("Unexpected value: ");
					}
				}
				else {
					System.out.println("entered EMail or Password is incorrect");
				}
			}
			ps1.close();
			con1.close();
		}
			catch (ClassNotFoundException e) {
			e.printStackTrace();
			}
			catch (SQLException e) {
			e.printStackTrace();
			}
	}
	public void displaySellerProducts(int sid) {
		Connection con = null;
		PreparedStatement ps = null;
		Scanner s= new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			
			System.out.println("Choose the option to display Products\n 1.Show My Products");
			int a=s.nextInt();
			switch (a) {
			case 1: {
				ps=con.prepareStatement("select * from products where Sid=?");
				ps.setInt(1, sid); //************should keep 2nd arg as sid***********
				ResultSet set=ps.executeQuery();
				while(set.next()) {
					System.out.println("Pid: "+set.getInt(1)+", Sid: "+set.getInt(2)+", ProductName: "+set.getString(3)+", piecesleft: "+set.getInt(4)+", ProductPrice: "+set.getDouble(5));
				}
				ps.executeQuery();
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
			ps.close();
			con.close();
			
		}
		catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			}
		catch (SQLException e) {
			e.printStackTrace();
			}
	}
	public void updateProducts(int sid) {

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		Scanner s=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps2=con.prepareStatement("select * from products where Sid=?");
			ps2.setInt(1, sid); //************should keep 2nd arg as sid***********
			ResultSet set=ps2.executeQuery();
			while(set.next()) {
				System.out.println("Pid: "+set.getInt(1)+", Sid: "+set.getInt(2)+", ProductName: "+set.getString(3)+", piecesleft: "+set.getInt(4)+", ProductPrice: "+set.getDouble(5));
			}
			ps2.executeQuery();
			System.out.println("Enter Productid to perform change:");
			int id=s.nextInt();
			System.out.println("What you want to change ? \n 1.ProductName\n 2.Piecesleft\n 3.ProductPrice");
			int a=s.nextInt();
			switch (a) {
			case 1: {
				ps = con.prepareStatement("update products set Pname=? where Pid=?");
				System.out.println("Enter new Productname:");
				ps.setString(1, s.next());
				ps.setInt(2, id);
				ps.executeUpdate();
//				ps.close();
//				con.close();
				break;
				
			}
				
			case 2: {
				ps = con.prepareStatement("update products set Ppiecesleft=? where Pid=?");
				System.out.println("Enter How many Pieces left:");
				ps.setInt(1, s.nextInt());
				ps.setInt(2, id);
				ps.executeUpdate();
//				ps.close();
//				con.close();
				break;
				
			}
			case 3: {
				ps = con.prepareStatement("update products set Pprice=? where Pid=?");
				System.out.println("Enter new Price:");
				ps.setDouble(1, s.nextDouble());
				ps.setInt(2, id);
				ps.executeUpdate();
//				ps.close();
//				con.close();
				break;
				
			}
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
//			ps.executeUpdate();
			System.out.println("Updated successfully...!");
			ps.close();
			con.close();
		}
		catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
				}
		catch (SQLException e) {
				e.printStackTrace();
				}

	}
	public void addProducts(int sid) {

		Connection con = null;
		PreparedStatement ps = null;
		Scanner s=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps = con.prepareStatement("insert into products values(?,?,?,?,?)");
//			!  PRODUCT ID WILL BE "AUTOMATICALLY INCREMENTED"
			System.out.println("Enter Productid:");
			ps.setInt(1, s.nextInt());
			System.out.println("Sellerid is: "+sid);
			ps.setInt(2, sid);
			System.out.println("Enter Productname:");
			ps.setString(3, s.next());
			System.out.println("Enter How many pieces you are having:");
			ps.setInt(4, s.nextInt());
			System.out.println("Enter Productprice");
			ps.setDouble(5, s.nextDouble());

//			Statement statement = con.createStatement();
//			statement.execute("CREATE TABLE products(Pid int primary key, Sid int, Pname varchar(50), Ppicesleft int, Pprice double), FOREIGN KEY (Sid) REFERENCES seller(Sid)");
			ps.executeUpdate();
			System.out.println("inserted");
			con.close();
			ps.close();
//			statement.close();
		}catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		catch (SQLException e) {
		e.printStackTrace();
		}

	}
	public void	removeProducts(int sid) {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		Scanner s= new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			
				ps=con.prepareStatement("select * from products where Sid=?");
				ps.setInt(1, sid); //************should keep 2nd arg as sid***********
				ResultSet set=ps.executeQuery();
				while(set.next()) {
					System.out.println("Pid: "+set.getInt(1)+", Sid: "+set.getInt(2)+", ProductName: "+set.getString(3)+", piecesleft: "+set.getInt(4)+", ProductPrice: "+set.getDouble(5));
					
				}
				ps.executeQuery();
				ps2=con.prepareStatement("delete from products where Pid=?");
				System.out.println("Enter ProductId to remove:");
				ps2.setInt(1, s.nextInt());
				ps2.executeUpdate();
				ps.close();
				ps2.close();
				con.close();
			}
			catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			}
			catch (SQLException e) {
			e.printStackTrace();
			}
	}
	public void sellerSignup(){
		Connection con = null;
		PreparedStatement ps = null;
		Scanner s=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps = con.prepareStatement("insert into seller values(?,?,?,?,?,?)");
			System.out.println("Enter Sellerid:");
			ps.setInt(1, s.nextInt());
			System.out.println("Enter Sellerstorename:");
			ps.setString(2, s.next());
			System.out.println("Enter Selleremail:");
			ps.setString(3, s.next());
			System.out.println("Enter Sellerphone:");
			ps.setLong(4, s.nextLong());
			System.out.println("Enter Sellerpassword:");
			ps.setString(5, s.next());
			System.out.println("SellerCurrentBalance is = 0$");
			ps.setDouble(6, 0);
//			Statement statement = con.createStatement();
//			statement.execute("CREATE TABLE seller(Sid int primary key, Sstorename varchar(50), Semail varchar(50), Sphone char(10), Spassword varchar(20), Sbalance double)");
			ps.executeUpdate();
			System.out.println("inserted");
			con.close();
			ps.close();
//			statement.close();
		}catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
//	CUSTOMER	
	public void customerSignup() {

		Connection con = null;
		PreparedStatement ps = null;
		Scanner s=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps = con.prepareStatement("insert into customer values(?,?,?,?,?,?,?)");
			System.out.println("Enter Customerid:");
			ps.setInt(1, s.nextInt());
			System.out.println("Enter Customername:");
			ps.setString(2, s.next());
			System.out.println("Enter Customeremail:");
			ps.setString(3, s.next());
			System.out.println("Enter Customerphone:");
			ps.setLong(4, s.nextLong());
			System.out.println("Enter Customeraddress");
			ps.setString(5, s.next());
			System.out.println("Enter Customerbalance");
			ps.setDouble(6, s.nextDouble());
			System.out.println("Enter a Customerpassword:");
			ps.setString(7, s.next());

//			Statement statement = con.createStatement();
//			statement.execute("CREATE TABLE customer(Cid int primary key, Cname varchar(50), Cemail varchar(50), Cphone char(10), Caddress varchar(60),Cbalance double, Cpassword varchar(20))");
			ps.executeUpdate();
			System.out.println("inserted");
			con.close();
			ps.close();
//			statement.close();
		}catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
		return;
	}
	
	public void displayCustomerProducts() {

		Connection con = null;
		PreparedStatement ps = null;
		Scanner s= new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			
			System.out.println("Choose the option to display Products\n 1.Show All Products");
			int a=s.nextInt();
			switch (a) {
			case 1: {
				ps=con.prepareStatement("select * from products where Ppiecesleft>=0");
				ResultSet set=ps.executeQuery();
				while(set.next()) {
					System.out.println("Pid: "+set.getInt(1)+", Sid: "+set.getInt(2)+", ProductName: "+set.getString(3)+", piecesleft: "+set.getInt(4)+", ProductPrice: "+set.getDouble(5));
				}
				ps.executeQuery();
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
			
			ps.close();
			con.close();
			
		}
		catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			}
		catch (SQLException e) {
			e.printStackTrace();
			}		

	}
	public void customerLogin() {

		Connection con1 = null;
		PreparedStatement ps1 = null;
		Scanner s1= new Scanner(System.in);
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps1=con1.prepareStatement("select * from customer where Cemail=? AND Cpassword=?");
			System.out.println("Enter Email:");
			String em=s1.next();
			ps1.setString(1, em);
			System.out.println("Enter Password:");
			String ep=s1.next();
			ps1.setString(2, ep);
			ResultSet set=ps1.executeQuery();
			while(set.next()) {
				if((set.getString(3).equals(em)) && (set.getString(7).equals(ep))) {
					int cid=set.getInt(1);
					
					System.out.println("Choose the required Operation\n 1.Buy a Product\n 2.Check your orders");
					int a1=s1.nextInt();
					switch (a1) {
					case 1: {
						dao.buyProducts(cid);
						break;
					}
					case 2: {
						dao.customerOrders(cid);
						break;
					}
					
					default:
						throw new IllegalArgumentException("Unexpected value: ");
					}
				}
				else {
					System.out.println("entered EMail or Password is incorrect");
				}
			}
			ps1.close();
			con1.close();
		}
		
			catch (ClassNotFoundException e) {
			e.printStackTrace();
			}
			catch (SQLException e) {
			e.printStackTrace();
			}
	}
	public void customerOrders(int cid) {
		Connection con = null;
		PreparedStatement ps = null;
		Scanner s=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps=con.prepareStatement("select * from orders where Cid=?");
			ps.setInt(1, cid);  //*** we have to keep cid in 2nd argument
			ResultSet set=ps.executeQuery();
			while(set.next()) {
				System.out.println("Orderid = "+set.getInt(1)+", Customerid = "+set.getInt(2)+", Product = "+set.getString(3)+", Quantity = "+set.getInt(4)+", Sellerid = "+set.getInt(5)+", Order Value = "+set.getDouble(6));
			}
			ps.close();
		}
		catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
	public void buyProducts(int cid) {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		PreparedStatement ps8 = null;
		Scanner s= new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore?user=root&password=ROOT");
			ps=con.prepareStatement("select * from products where Ppiecesleft>=0");
			ResultSet set=ps.executeQuery();
			while(set.next()) {
				System.out.println("Pid: "+set.getInt(1)+", Sid: "+set.getInt(2)+", ProductName: "+set.getString(3)+", piecesleft: "+set.getInt(4)+", ProductPrice: "+set.getDouble(5));
			}
			
			ps.close();
			System.out.println();
			System.out.println("Enter product's id to buy: ");
			int pid=s.nextInt();
			
			System.out.println("Enter quantity:");
			int quantity=s.nextInt();
			ps2=con.prepareStatement("select * from customer where Cid=?");
			ps2.setInt(1, cid);// **** we should keep 2nd arg as cid
			ResultSet cset=ps2.executeQuery();
			if(cset.next()) {
			double cbalance=cset.getDouble(6);// customer balance
			}
			
	
			ps3=con.prepareStatement("select * from products where Pid=?");
			ps3.setInt(1, pid);
			ResultSet pset=ps3.executeQuery();
			if(pset.next()) {
			String pname=pset.getString(3);// product Name
			int psid=pset.getInt(2);// product sid
			double pprice=pset.getDouble(5);// product price
			int pleft=pset.getInt(4); // product left
			}
			
			double cbalance=cset.getDouble(6);
			double pprice=pset.getDouble(5);
			int pleft=pset.getInt(4);
			String pname=pset.getString(3);
			int psid=pset.getInt(2);
			ps8=con.prepareStatement("select * from seller where Sid=?");
			ps8.setInt(1, psid);
			ResultSet sset=ps8.executeQuery();
			if(sset.next()) {
				double sbalance=set.getDouble(6);
			}
			double sbalance=set.getDouble(6);
			
			
			ps2.close();
			ps3.close();
			ps8.close();
			if((pleft >= quantity)) {
			if(cbalance >= (quantity*pprice)) {
				double bill = (quantity)*(pprice);
				sbalance+=bill;
				System.out.println("Total bill: "+bill);
				System.out.println("You can buy this product...\n Enter 1. to conform order\n 2. to Cancle");
				int o=s.nextInt();
				switch (o) {
				case 1: {
					double bill1 = (quantity)*(pprice);
					pleft-=quantity;
					cbalance-=bill;
					
					ps5=con.prepareStatement("update products set Ppiecesleft=? where pid=?");
					ps5.setInt(1, pleft);
					ps5.setInt(2, pid);
					ps5.executeUpdate();
					ps5.close();
					ps6=con.prepareStatement("insert into orders values(?,?,?,?,?,?)");
					ps6.setInt(1, 1);
					ps6.setInt(2, cid);// **** we should keep 2nd arg as cid
					ps6.setString(3, pname);
					ps6.setInt(4, quantity);
					ps6.setInt(5, psid);
					ps6.setDouble(6, bill1);
					ps6.executeUpdate();
					ps6.close();
					ps4=con.prepareStatement("update customer set Cbalance=? where Cid=?");
					ps4.setDouble(1, cbalance);
					ps4.setInt(2, cid);// **** we should keep 2nd arg as cid
					ps4.executeUpdate();
					ps4.close();
					ps7=con.prepareStatement("update seller set Sbalance=? where Sid=?");
					ps7.setDouble(1, sbalance);
					ps7.setInt(2, psid);
					ps7.executeUpdate();
					ps7.close();
					System.out.println("ORDER PLACED SUCCESSFULLY....");
					break;
				}
				case 2: {
					System.out.println("Order Canceled");
					break;
				}
				default:
					break;
				}
			}
			else {
				System.out.println("Unable to place order\n ****INSUFFICIENT BALANCE****");
				return;
			}
			}
			else {
				System.out.println("PRODUCT QUANTITY IS SHOULD BE LOW");
			}
			
		}	
		catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
 	
}
