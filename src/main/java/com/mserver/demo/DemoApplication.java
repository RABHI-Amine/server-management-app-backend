package com.mserver.demo;

import com.mserver.demo.enumeration.Status;
import com.mserver.demo.model.Server;
import com.mserver.demo.repositories.ServerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class DemoApplication implements CommandLineRunner{
	ServerRepository serverRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serverRepository.save(new Server(null, "192.168.1.160","Ubuntu Linux","16 GB","Personnal PC","http://localhost:8080/api/server/image/server1.png", Status.SERVER_UP));
		serverRepository.save(new Server(null, "192.168.1.58","FREDA Linux","16 GB","Dell Tower","http://localhost:8080/api/server/image/server1.png", Status.SERVER_DOWN));
		serverRepository.save(new Server(null, "192.168.1.21","MS 2008","32 GB","Web Server","http://localhost:8080/server/api/image/server1.png", Status.SERVER_UP));
	}
}
