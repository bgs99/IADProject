package bgs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String subject, String from, String to, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
    @RequestMapping("/mail")
    public String displayPlace(){
        sendMail("Test","bgs99c@gmail.com", "bgs99c@gmail.com","Test completed!");
        return "Email sent";
    }
}