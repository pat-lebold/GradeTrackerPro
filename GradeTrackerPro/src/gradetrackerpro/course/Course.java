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
	public void removeDuplicates(){
		ArrayList<GradeGrouping> visited = new ArrayList<GradeGrouping>();
		for(int n=this.groups.size()-1;n>=0;n--){
			if(visited.contains(this.groups.get(n)))
				this.groups.remove(n);
			else
				visited.add(this.groups.get(n));
		}
	}
	public ArrayList<GradeGrouping> getGroups(){
		return this.groups;
	}
	public void addGroup(){
		this.groups.add(new GradeGrouping(this));
		this.updateTotalPercentAccountedFor();
	}
	public void addGroup(GradeGrouping group){
		this.groups.add(group);
		this.updateTotalPercentAccountedFor();
	}
	public void addGroup(ArrayList<Grade> grades, int percentCounted){
		this.groups.add(new GradeGrouping(this, grades, percentCounted));
		this.updateTotalPercentAccountedFor();
	}
	public void removeGroup(GradeGrouping group){
		this.groups.remove(group);
		this.updateTotalPercentAccountedFor();
	}
	public String getName(){
		return this.courseName;
	}
	private void updateTotalPercentAccountedFor(){
		this.totalPercentAccountedFor = 0;
		for(GradeGrouping group:this.groups){
			this.totalPercentAccountedFor += group.getPercentCounted();
		}
	}
	public int getTotalPercentAccountedFor(){
		return this.totalPercentAccountedFor;
	}
	public int getPercent(){
		double total = 0;
		for(GradeGrouping group:this.groups)
			total += group.getValue();
		if(this.totalPercentAccountedFor==0)
			return 0;
		else
			total *= 100 * 100 / this.totalPercentAccountedFor;
		if(total-((int)total)>=0.98) //.98 handles floating point error causing round-down
			return (int)total + 1;
		else
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