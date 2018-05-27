package com.javalec.InflearnSpring16_20.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javalec.InflearnSpring16_20.dto.BDto;

public class BDao {
	
	DataSource dataSource;
	
	public BDao(){
		try {
			Context context = new InitialContext();
			System.out.println("context �ʱ�ȭ");
			Context context2 = (Context) context.lookup("java:comp/env");
			System.out.println("context �ʱ�ȭ2");
			dataSource = (DataSource) context2.lookup("jdbc/Oracle");
			System.out.println("context �ʱ�ȭ3");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 1.����Ʈ
		public ArrayList<BDto> list(){
			
			ArrayList<BDto> dtos = new ArrayList<BDto>();
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			
				
			
			try{
				connection = dataSource.getConnection();
				
				String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
				
				preparedStatement = connection.prepareStatement(query);
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					int bId = resultSet.getInt("bId");
					String bName = resultSet.getString("bName");
					String bTitle = resultSet.getString("bTitle");
					String bContent = resultSet.getString("bContent");
					Timestamp bDate = resultSet.getTimestamp("bDate");
					int bHit = resultSet.getInt("bHit");
					int bGroup = resultSet.getInt("bGroup");
					int bStep = resultSet.getInt("bStep");
					int bIndent = resultSet.getInt("bIndent");
					
					BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
					dtos.add(dto);

				}
				
				
				
			}catch(Exception e){
				
				
			}finally{
				
					
					try {
						if(resultSet!=null) resultSet.close();
						if(preparedStatement!=null) preparedStatement.close();
						if(connection!=null) preparedStatement.close();
						
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
						
			}
			return dtos;
				
			
		}
	
	// 2. �۾���
	public void write(String bName, String bTitle, String bContent){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		try {
			connection = dataSource.getConnection();
			String query ="insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
							+ " values (mvc_board_seq.nextval, ? , ? , ? , 0 , mvc_board_seq.currval , 0 , 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,bName);
			preparedStatement.setString(2,bTitle);
			preparedStatement.setString(3,bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			System.out.println("������Ʈ �� : " + rn);
			
			
		} catch(Exception e) {
			// TODO: handle finally clause
		} finally{
			

			try {
				if(preparedStatement!=null) preparedStatement.close();
				if(connection!=null) preparedStatement.close();
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
		}
		
	}
	//3. �ۺ���
	public BDto contentView(String strID){
		
		upHit(strID); // ��ȸ��
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
		
		try{
		connection = dataSource.getConnection();
		
		String query = "select * from mvc_board where bId = ?";
		
		preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setInt(1, Integer.parseInt(strID));
		
		resultSet = preparedStatement.executeQuery();
		
		
		if(resultSet.next()){
			int bId = resultSet.getInt("bId");
			String bName = resultSet.getString("bName");
			String bTitle = resultSet.getString("bTitle");
			String bContent = resultSet.getString("bContent");
			Timestamp bDate = resultSet.getTimestamp("bDate");
		int bHit = resultSet.getInt("bHit");
		int bGroup = resultSet.getInt("bGroup");
		int bStep = resultSet.getInt("bStep");
		int bIndent = resultSet.getInt("bIndent");
		
		dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
		
		dtos.add(dto);			
			
		}
		
		}
		catch(Exception e){
			
		}finally{
			
			try {
				if(resultSet!=null) resultSet.close();
				if(preparedStatement!=null) preparedStatement.close();
				if(connection!=null) preparedStatement.close();
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		return null;
		
	}
	
	// 3. �ۺ��� ���� ��ȸ�� �޼ҵ�
	private void upHit( String bId) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	

}
