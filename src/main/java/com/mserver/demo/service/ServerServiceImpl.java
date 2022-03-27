package com.mserver.demo.service;

import com.mserver.demo.model.Server;
import com.mserver.demo.repositories.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import static com.mserver.demo.enumeration.Status.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new Server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP {}:",ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP: SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching the server by id {}:",id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Saving new Server: {}",server.getName());
        return serverRepository.save(server);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting server by ID: {}",id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String serverImage = "server1.png";
        System.out.println("Set image url---------------------------------------------------------------------------------------------------------------------------------------");
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/server/image/"+ serverImage).toUriString();
    }
}
