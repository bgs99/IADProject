package bgs.controllers;

import bgs.info.Info;
import bgs.model.*;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class RequestController {
    @Autowired
    LoginManager manager;
    @Autowired
    InfoRequestRepository info;

    @RequestMapping("/requests")
    public Stream<Info> getRequests(){
        Agent cur = manager.getCurrentAgent();
        return info.findVisible(cur).stream().map(Info::new);
    }
    @RequestMapping("/requests/process")
    public Stream<Info> getProcessableRequests(){
        return info.findAllAvailable(manager.getLevel()).stream().map(Info::new);
    }

    @RequestMapping("/requests/process/reply")
    public boolean getReplyToRequest(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "status", defaultValue = "Принят") String status,
            @RequestParam(value = "answer") String answer,
            @RequestParam(value = "level", defaultValue = "-1") int level){

        InfoRequest req = info.findById(id);
        if(req.getLevel() > manager.getLevel())
            return false;
        req.setStatus(status);
        req.setResponse(answer);
        if(level >= 0)
            req.setLevel(level);
        info.save(req);
        return true;
    }

    @RequestMapping("/requests/send")
    public boolean getSendRequest(
            @RequestParam(value = "request") String request,
            @RequestParam(value = "purpose") String purpose){

        InfoRequest req = new InfoRequest(manager.getCurrentAgent(), request, purpose);
        info.save(req);
        return true;
    }
}