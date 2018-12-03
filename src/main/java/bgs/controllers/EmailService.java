package bgs.controllers;

<<<<<<< HEAD
=======
import bgs.model.Agent;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
<<<<<<< HEAD
=======
    public void sendMail(String subject, Agent to, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom("bgs99c@gmail.com");
        simpleMailMessage.setTo(to.getEmail());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    public void sendMail(String subject, String from, String to, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
    @RequestMapping("/mail")
<<<<<<< HEAD
    public String displayPlace(){
=======
    public String testMail(){
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
        sendMail("Test","bgs99c@gmail.com", "bgs99c@gmail.com","Test completed!");
        return "Email sent";
    }
}