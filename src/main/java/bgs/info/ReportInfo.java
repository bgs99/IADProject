package bgs.info;


import bgs.model.Report;

public class ReportInfo{
    public int id;
    public int mission;
    public String name;
    public String purpose;
    public String description;
    public int author;
    public int subject;
    public ReportInfo(Report report){
        this.id = report.getId();
        this.mission = report.getMission().getId();
        this.name = report.getName();
        this.purpose = report.getPurpose();
        this.description = report.getDescription();
        this.author = report.getAuthor().getId();
        this.subject = report.getSubject().getId();
    }
}
