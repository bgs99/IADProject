package bgs.controllers;

import bgs.info.Info;
import bgs.model.Agent;
import bgs.model.InfoRequest;
import bgs.repo.InfoRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class RequestController {
    @Autowired
    LoginManager manager;
    @Autowired
    InfoRequestRepository info;
    @Autowired
    EmailService mail;

    /**
     * Return your requests
     * @return Stream of info
     */
    @RequestMapping("/requests")
    public Stream<Info> getRequests(){
        Agent cur = manager.getCurrentAgent();
        return info.findVisible(cur).stream().map(Info::new);
    }

    /**
     * Return requests available for processiong
     * @return Stream of info
     */
    @RequestMapping("/requests/process")
    public Stream<Info> getProcessableRequests(){
        return info.findAllAvailable(manager.getLevel()).stream().map(Info::new);
    }

    /**
     * Reply to request
     * @param id Request ID
     * @param status New request status
     * @param answer Answer to request
     * @param level Level change
     * @return success
     */
    @RequestMapping(path = "/requests/process/reply", method = RequestMethod.POST)
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
        mail.sendMail("You request have been answered", req.getAgent(),
                String.format("For the request '%s' you have got an answer:\n\t %s",
                        req.getRequest(),
                        answer));

        return true;
    }

    /**
     * Write info request
     * @param request Request body
     * @param purpose Purpose of the request
     * @return
     */
    @RequestMapping(path = "/requests/send", method = RequestMethod.POST)
    public boolean getSendRequest(
            @RequestParam(value = "request") String request,
            @RequestParam(value = "purpose") String purpose){

        InfoRequest req = new InfoRequest(manager.getCurrentAgent(), request, purpose);
        info.save(req);
        return true;
    }
}