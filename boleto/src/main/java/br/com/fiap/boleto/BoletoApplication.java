package br.com.fiap.boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoletoApplication.class, args);
	}

}