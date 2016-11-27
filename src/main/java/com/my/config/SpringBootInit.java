package com.my.config;
import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication(scanBasePackages = {"com.my"})
public class SpringBootInit {

	/**
	 * Spring Boot 가동
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootInit.class, args);
	}
	
	/**
	 * H2 콘솔 적용 (브라우저)
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}
	
	/**
	 * H2 전국공중화장실 CSV 데이터 등록 및 인덱스 적용
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:db/sql/create-db.sql")
				.build();
		return db;
	}
}