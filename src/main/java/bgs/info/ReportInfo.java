package bgs.info;


import bgs.model.Report;
import org.springframework.data.util.Pair;

public class ReportInfo{
    public int id;
    public Pair<Integer, String> mission;
    public String name;
    public String purpose;
    public String description;
    public Pair<Integer, String> author;
    public Pair<Integer, String> subject;
    public ReportInfo(Report report){
        this.id = report.getId();
        this.mission = Pair.of(report.getMission().getId(), new TargetInfo(report.getMission().getTarget()).name);
        this.name = report.getName();
        this.purpose = report.getPurpose();
        this.description = report.getDescription();
        this.author = Pair.of(report.getAuthor().getId(), report.getAuthor().getName());
        this.subject = Pair.of(report.getSubject().getId(), report.getSubject().getName());
    }
}
