package com.dalgim.example.sb.cxf.wssencrypt.config;

import com.dalgim.example.sb.cxf.wssencrypt.endpoint.FruitCatalog;
import com.dalgim.example.sb.cxf.wssencrypt.endpoint.FruitCatalogImpl;
import com.google.common.collect.Maps;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;
import java.util.Map;

import static org.apache.wss4j.common.ConfigurationConstants.ACTION;
import static org.apache.wss4j.common.ConfigurationConstants.DEC_PROP_FILE;
import static org.apache.wss4j.common.ConfigurationConstants.ENCRYPTION_USER;
import static org.apache.wss4j.common.ConfigurationConstants.ENC_PROP_FILE;
import static org.apache.wss4j.common.ConfigurationConstants.MUST_UNDERSTAND;
import static org.apache.wss4j.common.ConfigurationConstants.PW_CALLBACK_CLASS;

/**
 * Created by dalgim on 08.04.2017.
 */
@Configuration
public class WebServiceServerConfig {

    private static final String SERVLET_URL_PATH = "/api";
    private static final String SERVICE_URL_PATH = "/FruitCatalog";

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), SERVLET_URL_PATH + "/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public FruitCatalog fruitService() {
        return new FruitCatalogImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), fruitService());
        endpoint.publish(SERVICE_URL_PATH);
        endpoint.getProperties().put(Message.EXCEPTION_MESSAGE_CAUSE_ENABLED, "true");
        endpoint.getProperties().put(Message.FAULT_STACKTRACE_ENABLED, "true");
        endpoint.getInInterceptors().add(wss4JInInterceptor());
        endpoint.getInInterceptors().add(loggingInInterceptor());
        endpoint.getOutInterceptors().add(loggingOutInterceptor());
        endpoint.getOutInterceptors().add(wss4JOutInterceptor());
        return endpoint;
    }

    private WSS4JInInterceptor wss4JInInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Encrypt");
        securityProperties.put(ENCRYPTION_USER, "serverkey");
        securityProperties.put(DEC_PROP_FILE, "server_encrypt.properties");
        securityProperties.put(PW_CALLBACK_CLASS, CertificatePasswordHandler.class.getName());
        return new WSS4JInInterceptor(securityProperties);
    }

    private WSS4JOutInterceptor wss4JOutInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Encrypt");
        securityProperties.put(ENC_PROP_FILE, "server_encrypt.properties");
        securityProperties.put(ENCRYPTION_USER, "clientcert");
        securityProperties.put(MUST_UNDERSTAND, "true");
        securityProperties.put(PW_CALLBACK_CLASS, CertificatePasswordHandler.class.getName());
        return new WSS4JOutInterceptor(securityProperties);
    }

    private LoggingInInterceptor loggingInInterceptor() {
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        return loggingInInterceptor;
    }

    private LoggingOutInterceptor loggingOutInterceptor() {
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        return loggingOutInterceptor;
    }

}
