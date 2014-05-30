package gradetrackerpro.course;
import java.util.ArrayList;
public class GradeGrouping{
	@SuppressWarnings("unused")
	private Course master;
	private ArrayList<Grade> grades;
	private int percentCounted;
	public GradeGrouping(Course master){
		this(master,new ArrayList<Grade>(),100);
	}
	public GradeGrouping(Course master, int percentCounted){
		this(master,new ArrayList<Grade>(),percentCounted);
	}
	public GradeGrouping(Course master, ArrayList<Grade> grades, int percentCounted){
		this.master=master;
		this.grades=grades;
		this.percentCounted=percentCounted;
	}
	public ArrayList<Grade> getGrades(){
		return this.grades;
	}
	public void addGrade(Grade grade){
		this.grades.add(grade);
	}
	public void removeGrade(Grade grade){
		this.grades.remove(grade);
	}
	public int getTotalPoints(){
		int total=0;
		for(Grade grade:this.grades)
			total += grade.getTotal();
		return total;
	}
	public int getTotalEarned(){
		int total=0;
		for(Grade grade:this.grades)
			total += grade.getEarned();
		return total;
	}
	public int getPercentCounted(){
		return this.percentCounted;
	}
	public int getPercent(){
		return this.getTotalPoints()==0?0:(int)((double)this.getTotalEarned()/this.getTotalPoints());
	}
	public double getValue(){
		double modifier = this.percentCounted/100.0;
		double percentDecimal = (double)this.getTotalEarned()/this.getTotalPoints();
		return percentDecimal * modifier;
	}
	public boolean equals(Object o){
		if(!(o instanceof GradeGrouping))
			return false;
		GradeGrouping other = (GradeGrouping)o;
		if(other.grades.size()!=this.grades.size())
			return false;
		for(int n=0;n<this.grades.size();n++){
			if(!this.grades.get(n).getName().equals(other.grades.get(n).getName()))
				return false;
			if(this.grades.get(n).getEarned()!=other.grades.get(n).getEarned())
				return false;
			if(this.grades.get(n).getTotal()!=other.grades.get(n).getTotal())
				return false;
		}
		return true;
	}
}