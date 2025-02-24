package SellerCustomerController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Scanner;

import SellerCustomerDao.SellerCustomerDao;

public class SellerCustomerController {
	public static void main(String[] args) {
		
		SellerCustomerDao dao = new SellerCustomerDao();
		Scanner sc=new Scanner(System.in);
		System.out.println("ENTER YOUR CHOICE:\n 1.Seller\n 2.Customer");
		switch (sc.nextInt()) {
		case 1: {
			System.out.println("Enter your choice:\n 1.Seller Login\n 2.Seller SignUp\n 3.Stop");
			switch (sc.nextInt()){
			case 1: {
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
								while(true) {
								System.out.println("Choose the required Operation\n 1.Add Product details\n 2.Display Product details\n 3.Update Product details\n 4.Remove a Product\n 5.Stop");
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
								case 5: {
									System.out.println("ThankYou..");
									return;
								}
								default:
									System.out.println("Invalid Input !");
									break;
								}
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
				break;
				
			case 2: {
				dao.sellerSignup();
				break;
			}
			case 3: {
				System.out.println("ThankYou...");
				return;
			}

			default:
				System.out.println("Invalid Input");
				break;
			}
			break;
		}
		case 2: {
			while(true) {
			System.out.println("Enter your choice:\n 1.Customer Login\n 2.Customer SignUp\n 3.Stop");
			switch (sc.nextInt()) {
			case 1: {
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
							while(true) {
							System.out.println("Choose the required Operation\n 1.Buy a Product\n 2.Check your orders\n 3.Stop");
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
							case 3: {
								System.out.println("ThankYou..");
								return;
							}
							
							default:
								break;
							}
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
				break;
			}
			case 2: {
				dao.customerSignup();
				break;
			}
			case 3: {
				System.out.println("ThankYou..");
				return;
			}
			default:
				System.out.println("Invalid Input !");
				break;
			}
			break;
			}
		}
		case 3: {
			
			return;
		}
		default:
			System.out.println("INVALID INPUT !");
			break;
		}
	}
}
