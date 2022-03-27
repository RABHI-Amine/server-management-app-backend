package com.mserver.demo.resource;

import com.mserver.demo.enumeration.Status;
import com.mserver.demo.model.Response;
import com.mserver.demo.model.Server;
import com.mserver.demo.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerService serverService;

    @GetMapping("/servers")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers",serverService.list(30)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
               );
    }

    @GetMapping("/server/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping Failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
               );
    }

    @PostMapping("/servers")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server ){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
               );
    }

    @GetMapping("/servers/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",serverService.get(id)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",serverService.delete(id)))
                        .message("Servers deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(value = "/server/image/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+ "\\Downloads\\server-images\\"+ fileName));
    }
}
