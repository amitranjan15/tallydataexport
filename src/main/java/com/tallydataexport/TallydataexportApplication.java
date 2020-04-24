package com.tallydataexport;

import javafx.application.Application;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class TallydataexportApplication {
	public static void main(String[] args) {
		Application.launch(JavafxMain.class,args);
	}
}