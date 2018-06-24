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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.javalec.InflearnSpring16_20.dto.BDto;
import com.javalec.InflearnSpring16_20.util.Constant;

public class BDao {
	
	DataSource dataSource;
	
	JdbcTemplate template = null;
	
	
	public BDao(){
//		try {
//			Context context = new InitialContext();
//			System.out.println("context 초기화");
//			Context context2 = (Context) context.lookup("java:comp/env");
//			System.out.println("context 초기화2");
//			dataSource = (DataSource) context2.lookup("jdbc/Oracle");
//			System.out.println("context 초기화3");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
		template = Constant.template;
	}
	
	
	// 1.리스트
		
		public ArrayList<BDto> list(){
			
			//ArrayList<BDto> dtos = new ArrayList<BDto>();
			
			System.out.println("template : " + template);
			
			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			// Spring - jdbc - 4.1.1 release.jar // 4.3.1(x)
			return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
					
					 		
			/*Connection connection = null;
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
			return dtos; */
				
			
		}
	
	// 2. 글쓰기
	public void write(final String bName, final String bTitle, final String bContent){
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String query ="insert into mvc_board(bId, bName, bTitle, bContent,bDate, bHit, bGroup, bStep, bIndent)"
						+ " values (mvc_board_seq.nextval, ? , ? , ?,sysdate , 0 , mvc_board_seq.currval , 0 , 0)";
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1,bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
			
				
				return pstmt;
			}
		});
		
		
		/*Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		try {
			connection = dataSource.getConnection();
			String query ="insert into mvc_board(bId, bName, bTitle, bContent,bDate, bHit, bGroup, bStep, bIndent)"
							+ " values (mvc_board_seq.nextval, ? , ? , ?,sysdate , 0 , mvc_board_seq.currval , 0 , 0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,bName);
			preparedStatement.setString(2,bTitle);
			preparedStatement.setString(3,bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			System.out.println("업데이트 수 : " + rn);
			
			
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
			
			
		}*/
		
	}
	//3. 글보기
	public BDto contentView(final String strID){
		System.out.println("글번호 " + strID);
		upHit(strID); // 조회수
		String query = "select * from mvc_board where bId = " + strID;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		
		/*//ArrayList<BDto> dtos = new ArrayList<BDto>();
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
		return dto;*/
		
	}
	
	// 3-1. 글보기 관련 조회수 메소드
	private void upHit(final String bId) { // 값이 변경되면 아래 bId가 영향받을 수 있으므로, final
		// TODO Auto-generated method stub
		String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
		
		template.update(query,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));;
				
			}
		});
		/*
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
		}*/
	}
	
	//4. 글 수정
	public void modify(final String bId, final String bName, final String bTitle, final String bContent){
		String query = "update mvc_board set bName = ?, bTitle =?, bDate = sysdate, bContent =? where bId = ?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2,bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));
			}
		});
		/*Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		try{
			connection = dataSource.getConnection();
			String query = "update mvc_board set bName = ?, bTitle =?, bDate = sysdate, bContent =? where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			int rn = preparedStatement.executeUpdate();
			
			System.out.println("4. 글 수정 업데이트 개수 : " +  rn);
			
			
			
		}catch(Exception e){
			
		}finally{
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}*/
		
	}
	
	// 글 삭제
	public void delete(final String bId){
		
		
		String query ="delete from mvc_board where bId =?";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bId);
				
			}
		});
		/*Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		try{
			connection = dataSource.getConnection();
			String query ="delete from mvc_board where bId =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			
			int rn = preparedStatement.executeUpdate();
			
			System.out.println("삭제된 로우수 : " +  rn);
			
		}catch(Exception e){
			
		}finally{
			// 항상자원해제
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}*/
		
	}
	
	public BDto reply_View(String strID){
		
		
		String query = "select * from mvc_board where bId = " + strID;
		
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		/*BDto dto = null;
		
		Connection connection= null;
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
		    
		    dto = new BDto(bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent);
			
			
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
		
				
		return dto;*/
	}
	
	public void reply(final String bId, final String bName,final String bTitle,final String bContent,final String bGroup,final String bStep,final String bIndent){
		
		System.out.println("댓글 달기");
		replyShape(bGroup, bStep);
		
		String query = "insert into mvc_board(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent) values(mvc_board_seq.nextval,?,?,?,sysdate,0,?,?,?)";
		
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bGroup));
				ps.setInt(5, Integer.parseInt(bStep)+1);
				ps.setInt(6, Integer.parseInt(bIndent)+1);
				
			}
		});
		
		
		
		
		// 본문 보다 들여쓰기
		/*replyShape(bGroup, bStep);
		
		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		
		try{
			
			connection = dataSource.getConnection();
			
			String query = "insert into mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values(mvc_board_seq.nextval,?,?,?,?,?,?)";
			
			preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bName);
            preparedStatement.setString(2, bTitle);
            preparedStatement.setString(3, bContent);
            preparedStatement.setInt(4, Integer.parseInt(bGroup));
            preparedStatement.setInt(5, Integer.parseInt(bStep)+1);
            preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);
            
            int rn = preparedStatement.executeUpdate(); //resultNumber = rn
            
            System.out.println("reply insert() 개수 : " + rn);
            
		}catch(Exception e){
			
		}finally{
			try {
				
				if(preparedStatement!=null) preparedStatement.close();
				if(connection!=null) preparedStatement.close();
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
		}*/
		
		
	}
	// 본문 보다 들여쓰기
	private void replyShape( final String strGroup, final String strStep) {
		String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
		template.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(strGroup));
				ps.setInt(2, Integer.parseInt(strStep));
				
			}
		});
	
		
		
		// TODO Auto-generated method stub
		/*Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strGroup));
			preparedStatement.setInt(2, Integer.parseInt(strStep));
			
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
		}*/
	}

}
