package com.stardust.easyassess.track.conf;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.stardust.easyassess.track.dao.router.MultiTenantMongoDbFactory;
import com.stardust.easyassess.track.dao.router.TenantContext;
import com.stardust.easyassess.core.context.ContextSession;
import com.stardust.easyassess.core.context.ShardedSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Configuration
public class TrackAppConf {
    @Value("${authentication.server}")
    private String authenticationServer;

    @Value("${track.db.default}")
    private String defaultDB;

    @Value("${track.db.server}")
    private String dbServer;

    @Value("${track.db.user}")
    private String dbUser;

    @Value("${track.db.password}")
    private String dbPassword;

    @Bean
    public MongoTemplate mongoTemplate(final Mongo mongo) throws Exception {
        return new MongoTemplate(mongoDbFactory(mongo));
    }

    @Bean
    public MultiTenantMongoDbFactory mongoDbFactory(final Mongo mongo) throws Exception {
        return new MultiTenantMongoDbFactory(mongo, defaultDB);
    }

    @Bean
    public Mongo mongo() throws Exception {
        if (dbUser == null || dbUser.isEmpty()) {
            return new MongoClient(dbServer);
        }
        List<ServerAddress> hosts = new ArrayList();
        List<MongoCredential> credentials = new ArrayList();
        hosts.add(new ServerAddress(dbServer, 3717));
        credentials.add(MongoCredential.createCredential(dbUser, defaultDB, dbPassword.toCharArray()));
        Mongo mongo = new MongoClient(hosts, credentials);
        return mongo;
    }

    @Autowired
    @Scope("request")
    @Lazy
    @Bean
    public ContextSession getContextSession(HttpSession session, HttpServletRequest request) {
        Map pathVariables
                = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String domain = (String)pathVariables.get("domain");
        if (domain == null || domain.isEmpty()) {
            domain = defaultDB;
        }

        TenantContext.setCurrentTenant(domain);

        return new ShardedSession(session, domain);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("GET","PUT","POST","DELETE","HEAD","OPTIONS");
            }
        };
    }
}