package com.magneto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
* MagnetoApplication class se encarga del start de la Aplicacion
*
* @author  Mauricio Borelli
* @version 1.0
* @since   2019-06-01 
*/

@SpringBootApplication
public class MagnetoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MagnetoApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MagnetoApplication.class);
    }
	
}
