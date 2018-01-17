package cn.x.codegen;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * @author xslong
 * @time 2017/11/6 11:35
 */
@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;


    @Bean
    public Connection connection() throws Exception {
        Driver driver = (Driver) Class.forName(driverClassName).newInstance();
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }


    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
