package com.hanshin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Project1 {

	public static void main(String[] args) {
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://127.0.0.1:3306/databasetest?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "1234");

			
			// 1) Java ������Ʈ���� Statement�� �̿��Ͽ� addressbook �̶�� table ����� 
			
			Statement st = con.createStatement();
			String createSql = "CREATE TABLE addressbook ("
					+ "id INT,"
					+ "name VARCHAR(45),"
					+ "tel VARCHAR(45),"
					+ "email VARCHAR(60),"
					+ "address VARCHAR(60) )";
			
			st.executeUpdate(createSql);
			System.out.printf("Create Table...\n");

			
			// 2) ������ ������ addressbook table�� PreparedStatement�� �̿��Ͽ� 5���� ���� ä��� (���� ���Ƿ� ������ ����)
			
			PreparedStatement stSql = con.prepareStatement("INSERT INTO addressbook VALUES (?, ?, ?, ?, ?)");
			
			int id[] = { 1, 2, 3, 4, 5 };
			String name[] = { "Kim", "Park", "Seo", "Lee", "Kang" };
			String tel[] = { "000-0000-0000", "000-0000-1111", "000-0000-2222", "000-0000-3333", "000-0000-4444" };
			String email[] = { "kim@hs.ac.kr", "Park@hs.ac.kr", "Seo@hs.ac.kr", "Lee@hs.ac.kr", "Kang@hs.ac.kr" };
			String address[] = { "���� ������", "��� ����", "���� ��õ", "��� �Ⱦ�", "���� ����" };
			
			for (int i=0; i<5; i++) {
				stSql.setInt(1, id[i]);
				stSql.setString(2, name[i]);
				stSql.setString(3, tel[i]);
				stSql.setString(4, email[i]);
				stSql.setString(5, address[i]);
				stSql.executeUpdate(); 
			}
						
			ResultSet rs = stSql.executeQuery("SELECT * FROM addressbook");
			
			while(rs.next()) {
				int Id = rs.getInt("id");
				String Name = rs.getString("name");
				String Tel = rs.getString("tel");
				String Email = rs.getString("email");
				String Address = rs.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s"
						+ "\n", Id, Name, Tel, Email, Address);
			} rs.close();
			
			
			// 3) 2)�� �ڵ� ���� PreparedStatement �̿��Ͽ� 5���� ���� email�� �������� @naver.com���� UPDATE ����
			
			System.out.printf("Update...\n");
			
			stSql = con.prepareStatement("UPDATE addressbook SET email=? WHERE id=?");
			
			String new_email[] = { "kim@naver.com", "Park@naver.com", "Seo@naver.com", "Lee@naver.com", "Kang@naver.com"};
			for (int i=0; i<5; i++) {
				stSql.setString(1, new_email[i]);
				stSql.setInt(2, id[i]);
				stSql.executeUpdate();
			} 
			
			ResultSet rs2 = stSql.executeQuery("SELECT * FROM addressbook");
						
			while(rs2.next()) {
				int Id = rs2.getInt("id");
				String Name = rs2.getString("name");
				String Tel = rs2.getString("tel");
				String Email = rs2.getString("email");
				String Address = rs2.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s"
						+ "\n", Id, Name, Tel, Email, Address);
			} rs2.close();
			
			
			// 4) 3)�� �ڵ� ���� Statement�� �̿��Ͽ� ���� 2���� ���� ����� �ڵ� ����

			System.out.printf("Delete...\n");

			st.executeUpdate("DELETE FROM addressbook WHERE id > 3");
			
			ResultSet rs3 = stSql.executeQuery("SELECT * FROM addressbook");
			
			while(rs3.next()) {
				int Id = rs3.getInt("id");
				String Name = rs3.getString("name");
				String Tel = rs3.getString("tel");
				String Email = rs3.getString("email");
				String Address = rs3.getString("address");
				System.out.printf("id:  %d, name: %s, tel: %s, email: %s, address: %s"
						+ "\n", Id, Name, Tel, Email, Address);
			} rs3.close();

			
			st.close();
			stSql.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}