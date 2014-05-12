package gradetrackerpro.course;
import gradetrackerpro.transmission.IReceiver;
import gradetrackerpro.transmission.ITrigger;

import java.util.ArrayList;
public class Course implements ITrigger{
  private ArrayList<IReceiver> receivers;
  private ArrayList<GradeGrouping> groups;
  private String courseName;
  private int totalPercentAccountedFor;
  public Course(String courseName){
    this(new ArrayList<GradeGrouping>(),courseName,0);
  }
  public Course(ArrayList<GradeGrouping> groups, String courseName, int totalPercentAccountedFor){
    this.groups=groups;
    this.courseName=courseName;
    this.totalPercentAccountedFor=totalPercentAccountedFor;
    this.receivers = new ArrayList<IReceiver>();
  }
  public void addGroup(){
    groups.add(new GradeGrouping(this));
  }
  public void addGroup(GradeGrouping group){
    groups.add(group);
  }
  public void addGroup(ArrayList<Grade> grades, int percentCounted){
    groups.add(new GradeGrouping(this, grades, percentCounted));
  }
  public void removeGroup(GradeGrouping group){
    this.groups.remove(group);
  }
  public String getName(){
    return this.courseName;
  }
  public void setTotalPercentAccountedFor(int totalPercentAccountedFor){
    this.totalPercentAccountedFor = totalPercentAccountedFor;
  }
  public int getTotalPercentAccountedFor(){
    return this.totalPercentAccountedFor;
  }
  public int getPercent(){
    double total = 0;
    for(GradeGrouping group:this.groups)
      total += group.getValue();
    /*if(this.totalPercentAccountedFor<100){
    	System.out.println("run");
      total = total / this.totalPercentAccountedFor * 100;
    }*/
    total *= 100;
    return (int)total;
  }
  public void pushData(String title, String[] data){
    for(IReceiver receiver:this.receivers)
      receiver.ping(title, data);
  }
  public void addReceiver(IReceiver receiver){
    this.receivers.add(receiver);
  }
  public void removeReceiver(IReceiver receiver){
    this.receivers.remove(receiver);
  }
}