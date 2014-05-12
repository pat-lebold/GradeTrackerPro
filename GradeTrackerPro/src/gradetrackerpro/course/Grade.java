package gradetrackerpro.course;
public class Grade{
	private String name;
	private int earned;
	private int total;
	public Grade(){
		this("",0,0);
	}
	public Grade(String name, int earned, int total){
		this.name=name;
		this.earned=earned;
		this.total=total;
	}
	public void setEarned(int earned){
		this.earned=earned;
	}
	public void setTotal(int total){
		this.total=total;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getEarned(){
		return this.earned;
	}
	public int getTotal(){
		return this.total;
	}
	public String getName(){
		return this.name;
	}
	public int getPercentEarned(){
		if(this.total==0)
			return 100;
		else{
			double earned = (double)this.earned;
			return (int)(100*earned/total);
		}
	}
}