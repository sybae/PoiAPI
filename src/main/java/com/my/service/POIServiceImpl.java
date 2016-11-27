package com.my.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class POIServiceImpl implements POIService {

	private static final Logger logger = LoggerFactory.getLogger(POIServiceImpl.class);
	
	public final static double KILO_METER = 1000;	// 1km (meter 기준)
	public final static double EARTH_RADIUS = 6371;	// 지구 반지름 (kilometer 기준)
	
	/**
	 * 직사각형 범위 내 POI 조회
	 */
	public ArrayList<HashMap<String,Object>> rectangle(String x1, String y1, String x2, String y2) throws Exception {
		
		/**
		 * 시간이 촉박하여 직접적으로 DB 접근하고, 쿼리 수행하는 방식으로 개발하였습니다.
		 * 이 점 양해를 구합니다.
		 */
		
		// H2 연결
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
		Statement statement = connection.createStatement();
		
		/* 다음과 같은 쿼리 구조임을 명시함.
		 * 
			SELECT  *
			  FROM  TB_TOILET
			 WHERE  LONG >= LEAST(128, 130)
			   AND  LONG <= GREATEST(128, 130)
			   AND  LAT >= LEAST(34, 34.7)
			   AND  LAT <= GREATEST(34, 34.7)
		 */
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM TB_TOILET ")
			.append(" WHERE LONG >= LEAST(").append(x1).append(",").append(x2).append(")")
			.append(" AND LONG <= GREATEST(").append(x1).append(",").append(x2).append(")")
			.append(" AND LAT >= LEAST(").append(y1).append(",").append(y2).append(")")
			.append(" AND LAT <= GREATEST(").append(y1).append(",").append(y2).append(")");
		
		logger.info("#### rectangle | query = " + query);
		
		// 쿼리 수행 및 결과 타입 변환
		ResultSet rs = statement.executeQuery(query.toString());
		ArrayList<HashMap<String,Object>> rtnRslt = this.convertResultSetToArrayList(rs);
		
		rs.close();
		statement.close();
		connection.close();

		logger.info("#### rectangle | result's size = " + rtnRslt.size());
		
		return rtnRslt;
	}
	
	/**
	 * 원형 범위 내 POI 조회
	 */
	public ArrayList<HashMap<String, Object>> circle(String x, String y, String radius) throws Exception {
		
		/**
		 * 시간이 촉박하여 직접적으로 DB 접근하고, 쿼리 수행하는 방식으로 개발하였습니다.
		 * 이 점 양해를 구합니다.
		 */
		
		// H2 연결
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
		Statement statement = connection.createStatement();
		
		// 인자값 세팅
		double lon = Double.parseDouble(x);	// 경도
		double lat = Double.parseDouble(y);	// 위도
		double dRadius = Double.parseDouble(radius);	// Radius
		
		// Radian 계산
		double lonRadian = lon * (Math.PI / 180);	// 경도 Radian
		double latRadian = lat * (Math.PI / 180);	// 위도 Radian
		double r = dRadius / KILO_METER / EARTH_RADIUS;
		
		// Raduis 대비, 최소/최대 Radian 계산 (위도)
		double latMin = latRadian - r;	// ex) 1.2393 rad
		double latMax = latRadian + r;	// ex) 1.5532 rad
		
		// Raduis 대비, 최소/최대 Radian 계산 (경도)
		double lonDelta = Math.asin(Math.sin(r) / Math.cos(latRadian));
		double lonMin = lonRadian - lonDelta;	// ex) -1.8184 rad
		double lonMax = lonRadian + lonDelta;	// ex) 0.4221 rad
		
		/* 다음과 같은 쿼리 구조임을 명시함.
		 * 
			SELECT * FROM (
			    SELECT * FROM TB_TOILET WHERE
			        ((LAT * 3.141592653589793 / 180) >= #{latMin} AND (LAT * 3.141592653589793 / 180) <= #{latMax})
			          AND ((LONG * 3.141592653589793 / 180) >= #{lonMin} AND (LONG * 3.141592653589793 / 180) <= #{lonMax})
			          AND  LAT IS NOT NULL AND LONG IS NOT NULL)
			WHERE
			    acos(sin(#{latRadian}) * sin((LAT * 3.141592653589793 / 180)) + cos(#{latRadian}) * cos((LAT * 3.141592653589793 / 180)) * cos((LONG * 3.141592653589793 / 180) - (#{lonRadian}))) <= #{r};
		*/
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ( ")
				.append("SELECT * FROM TB_TOILET WHERE ")
				.append("((LAT * 3.141592653589793 / 180) >= ").append(latMin).append(" AND (LAT * 3.141592653589793 / 180) <= ").append(latMax).append(") ")
				.append(" AND ((LONG * 3.141592653589793 / 180) >= ").append(lonMin).append(" AND (LONG * 3.141592653589793 / 180) <= ").append(lonMax).append(") ")
			.append(" AND LAT IS NOT NULL AND LONG IS NOT NULL) ")
				.append("WHERE acos(sin(").append(latRadian).append(") * sin((LAT * 3.141592653589793 / 180)) + cos(").append(latRadian)
				.append(") * cos((LAT * 3.141592653589793 / 180)) * cos((LONG * 3.141592653589793 / 180) - (").append(lonRadian).append("))) <= ").append(r);
		
		
		logger.info("#### circle | query = " + query);
		
		// 쿼리 수행 및 결과 타입 변환
		ResultSet rs = statement.executeQuery(query.toString());
		ArrayList<HashMap<String,Object>> rtnRslt = this.convertResultSetToArrayList(rs);
		
		rs.close();
		statement.close();
		connection.close();
		
		logger.info("#### circle | result's size = " + rtnRslt.size());
		
		return rtnRslt;
	}
	
	/**
	 * ResultSet > ArrayList 변환
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ArrayList<HashMap<String,Object>> convertResultSetToArrayList(ResultSet rs) throws Exception {
		
		// 컬럼명/값 기준으로 Map 구성
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int idx = 1; idx <= columns; ++idx) {
				row.put(md.getColumnName(idx), rs.getObject(idx));
			}
			list.add(row);
		}
		
		return list;
	}
}