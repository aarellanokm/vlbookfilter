package com.knowmadmood.vlbookfilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowmadmood.vlbookfilter.model.Book;
import com.knowmadmood.vlbookfilter.model.BookWithPublicationDate;
import com.knowmadmood.vlbookfilter.service.VLBookFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


@SpringBootApplication
public class VlbookfilterApplication implements CommandLineRunner{

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {

		SpringApplication.run(VlbookfilterApplication.class, args);


	}

	@Override
	public void run (String... args) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		List<Book> books = Collections.emptyList();
		VLBookFilterService bookservice = new VLBookFilterService();
		Scanner scanner = new Scanner(System.in);

		try{
			Resource resource = resourceLoader.getResource("classpath:books.json");
			InputStream inputStream = resource.getInputStream();
			books = objectMapper.readValue(inputStream, new TypeReference<List<Book>>() {});

		}catch (IOException e) {
			e.printStackTrace();
		}

		while (true){
			System.out.println("Introduce el texto a filtrar o -exit para finalizar: ");
			String filter = scanner.nextLine();

			if("-exit".equalsIgnoreCase(filter)){
				break;
			}

			Optional<BookWithPublicationDate> result = bookservice.filter(filter, books);

			if(result.isPresent()) {
				BookWithPublicationDate bookDate = result.get();
				System.out.println("Localizado:");
				System.out.println("título: " + bookDate.getTitle());
				System.out.println("resumen: " + bookDate.getSummary());
				System.out.println("fecha: " + bookDate.getPublicationDateStr());
				System.out.println("bio: " + bookDate.getAuthor().getBio());
			}else{
				System.out.println("Sin resultados para el texto ("+filter+")");
			}
		}
		scanner.close();
	}

}
