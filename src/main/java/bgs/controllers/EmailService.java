package bgs.controllers;

import bgs.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailService {
    @Autowired
    Environment env;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String subject, Agent to, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom("bgs99c@gmail.com");
        simpleMailMessage.setTo(to.getEmail());
        simpleMailMessage.setText(text);
        if(env.getProperty("PMAIL")!=null)
            javaMailSender.send(simpleMailMessage);
    }
    public void sendMail(String subject, String from, String to, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(text);
        if(env.getProperty("PMAIL")!=null)
            javaMailSender.send(simpleMailMessage);
    }
    @RequestMapping(path = "/mail", method = RequestMethod.POST)
    public String testMail(){
        sendMail("Test","bgs99c@gmail.com", "bgs99c@gmail.com","Test completed!");
        return "Email sent";
    }
}