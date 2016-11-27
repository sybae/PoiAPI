package com.my.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface POIService {

	/**
	 * 직사각형 범위 POI 조회
	 * 
	 * @param x1 첫번째 경도 좌표
	 * @param y1 첫번째 위도 좌표 
	 * @param x2 두번째 경도 좌표
	 * @param y2 두번째 위도 좌표
	 * @return 대상 POI 정보
	 * @throws Exception
	 */
	public ArrayList<HashMap<String,Object>> rectangle(String x1, String y1, String x2, String y2) throws Exception;
	
	/**
	 * 원형 범위 POI 조회
	 * 
	 * @param x 경도 좌표
	 * @param y 위도 좌표
	 * @param radius 범위 (미터)
	 * @return 대상 POI 정보
	 * @throws Exception
	 */
	public ArrayList<HashMap<String,Object>> circle(String x, String y, String radius) throws Exception;
}