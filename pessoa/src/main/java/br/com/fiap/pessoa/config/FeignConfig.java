package br.com.fiap.pessoa.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class FeignConfig {
    @Bean
    public Client feignClient() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(
                        new File("/app/certs/pessoa-keystore.p12"), // Caminho para o keystore
                        "senhasegura".toCharArray(), // Senha do keystore
                        "senhasegura".toCharArray()) // Senha da chave privada
                .loadTrustMaterial(
                        new File("/app/certs/ca-truststore.p12"), // Caminho para o truststore
                        "senhasegura".toCharArray()) // Senha do truststore
                .build();


        // Configura o HttpClient com o SSLContext
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        // Retorna o Feign Client com ApacheHttpClient
        return new ApacheHttpClient(httpClient);
    }
}