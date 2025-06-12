package org.scoula.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:/application.properties"})
@MapperScan(basePackages = {"org.scoula.todo.mapper"})
public class RootConfig {

    @Autowired
    ApplicationContext applicationContext;

    // application.properties에서 데이터베이스 연결 정보 주입
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    /**
     * HikariCP 커넥션 풀을 사용한 DataSource 빈 생성
     * @return 설정된 DataSource 객체
     */
    @Bean
    public DataSource dataSource() {
        // HikariCP 설정 객체 생성
        HikariConfig config = new HikariConfig();

        // 데이터베이스 연결 정보 설정
        config.setDriverClassName(driver);          // JDBC 드라이버 클래스
        config.setJdbcUrl(url);                    // 데이터베이스 URL
        config.setUsername(username);              // 사용자명
        config.setPassword(password);              // 비밀번호

        // 커넥션 풀 추가 설정 (선택사항)
        config.setMaximumPoolSize(10);             // 최대 커넥션 수
        config.setMinimumIdle(5);                  // 최소 유지 커넥션 수
        config.setConnectionTimeout(30000);       // 연결 타임아웃 (30초)
        config.setIdleTimeout(600000);            // 유휴 타임아웃 (10분)

        // HikariDataSource 생성 및 반환
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        // MyBatis 설정 파일 위치 지정
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));

        // 데이터베이스 연결 설정
        sqlSessionFactory.setDataSource(dataSource);

        return sqlSessionFactory.getObject();
    }

    /**
     * 트랜잭션 매니저 설정
     * - 데이터베이스 트랜잭션을 스프링이 관리하도록 설정
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());
        return manager;
    }
}