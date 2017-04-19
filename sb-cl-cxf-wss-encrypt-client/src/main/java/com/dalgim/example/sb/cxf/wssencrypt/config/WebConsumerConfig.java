package com.dalgim.example.sb.cxf.wssencrypt.config;

import com.dalgim.example.sb.cxf.wssencrypt.endpoint.FruitCatalog;
import com.google.common.collect.Maps;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

import static org.apache.wss4j.common.ConfigurationConstants.ACTION;
import static org.apache.wss4j.common.ConfigurationConstants.DEC_PROP_FILE;
import static org.apache.wss4j.common.ConfigurationConstants.ENCRYPTION_USER;
import static org.apache.wss4j.common.ConfigurationConstants.ENC_PROP_FILE;
import static org.apache.wss4j.common.ConfigurationConstants.MUST_UNDERSTAND;
import static org.apache.wss4j.common.ConfigurationConstants.PW_CALLBACK_CLASS;

/**
 * Created by dalgim on 09.04.2017.
 */
@Configuration
public class WebConsumerConfig {

    @Bean
    public FruitCatalog jaxWsProxyFactoryBean(@Value("${fruitService.address}") String address) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(FruitCatalog.class);
        jaxWsProxyFactoryBean.setAddress(address);
        FruitCatalog fruitCatalog = (FruitCatalog) jaxWsProxyFactoryBean.create();
        Client client = ClientProxy.getClient(fruitCatalog);
        Endpoint endpoint = client.getEndpoint();
        endpoint.getInInterceptors().add(loggingInInterceptor());
        endpoint.getInInterceptors().add(wss4JInInterceptor());
        endpoint.getOutInterceptors().add(loggingOutInterceptor());
        endpoint.getOutInterceptors().add(wss4JOutInterceptor());
        return fruitCatalog;
    }

    private WSS4JOutInterceptor wss4JOutInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Encrypt");
        securityProperties.put(ENC_PROP_FILE, "client_encrypt.properties");
        securityProperties.put(ENCRYPTION_USER, "servercert");
        securityProperties.put(MUST_UNDERSTAND, "true");
        securityProperties.put(PW_CALLBACK_CLASS, CertificatePasswordHandler.class.getName());
        return new WSS4JOutInterceptor(securityProperties);
    }

    private WSS4JInInterceptor wss4JInInterceptor() {
        Map<String, Object> securityProperties = Maps.newHashMap();
        securityProperties.put(ACTION, "Encrypt");
        securityProperties.put(ENCRYPTION_USER, "clientkey");
        securityProperties.put(DEC_PROP_FILE, "client_encrypt.properties");
        securityProperties.put(PW_CALLBACK_CLASS, CertificatePasswordHandler.class.getName());
        return new WSS4JInInterceptor(securityProperties);
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
