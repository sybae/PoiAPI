package com.my.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.service.POIService;

@RestController
public class POIController {

	private static final Logger logger = LoggerFactory.getLogger(POIController.class);
	
	@Autowired
	private POIService poiService;
	
	/**
	 * 직사각형 범위 POI 조회
	 * 
	 * @param req
	 *         - x1 첫번째 경도 좌표
	 *         - y1 첫번째 위도 좌표
	 *         - x2 두번째 경도 좌표
	 *         - y2 두번째 위도 좌표
	 * @return 대상 POI 정보
	 * @throws Exception
	 */
	@RequestMapping(value="/rectangle", method=RequestMethod.GET)
	public ArrayList<HashMap<String,Object>> rectangle(HttpServletRequest req) throws Exception {
		
		String x1 = req.getParameter("x1");
		String y1 = req.getParameter("y1");
		String x2 = req.getParameter("x2");
		String y2 = req.getParameter("y2");
		
		logger.info("#### rectangle | x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2);
		
		return poiService.rectangle(x1, y1, x2, y2);
	}
	
	/**
	 * 원형 범위 POI 조회
	 * 
	 * @param req
	 *         - x 경도 좌표
	 *         - y 위도 좌표
	 *         - radius 범위 (미터)
	 * @return 대상 POI 정보
	 * @throws Exception
	 */
	@RequestMapping(value="/circle", method=RequestMethod.GET)
	public ArrayList<HashMap<String,Object>> circle(HttpServletRequest req) throws Exception {
		
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		String radius = req.getParameter("radius");
		
		logger.info("#### circle | x = " + x + ", y = " + y + ", radius = " + radius);
		
		return poiService.circle(x, y, radius);
	}
}