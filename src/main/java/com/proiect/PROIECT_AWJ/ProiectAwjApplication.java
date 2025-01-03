package com.proiect.PROIECT_AWJ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/** Clasa pentru START APLICATIE
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProiectAwjApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProiectAwjApplication.class, args);
	}
}