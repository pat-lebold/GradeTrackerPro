package gradetrackerpro.transmission;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import gradetrackerpro.course.Course;
import gradetrackerpro.course.Grade;
import gradetrackerpro.course.GradeGrouping;

public class DataManager {
	
	public static ArrayList<BufferedImage> pullTutorials(){
		ArrayList<BufferedImage> tutorials = new ArrayList<BufferedImage>();
		File file = new File("img/tutorials");
		int numTutorials = file.listFiles().length;
		for(int n=1;n<=numTutorials;n++){
			try {
				BufferedImage tutorialImage = ImageIO.read(new File("img/tutorials/tutorial_"+n+".png"));
				tutorials.add(tutorialImage);
			} catch (IOException e) {
				//Do nothing
			}
		}
		return tutorials;
	}

	public static Course loadCourse(String courseName){
		File file = new File(courseName+".cf");
		try {
			Scanner in = new Scanner(file);
			String data = in.nextLine();
			in.close();
			String[] courseString = data.split("_");
			Course course = new Course(courseString[0]);
			if(courseString.length==1)
				return course;
			String[] courseGroups = courseString[1].split(":");
			for(String groupString:courseGroups){
				String[] groupStringElements = groupString.split(";");
				int percentCounted = Integer.parseInt(groupStringElements[0]);
				GradeGrouping group = new GradeGrouping(course,percentCounted);
				if(groupStringElements.length==1)
					continue;
				String[] groupGrades = groupStringElements[1].split("~");
				for(String gradeString:groupGrades){
					String[] gradeStringElements = gradeString.split("&");
					String name = gradeStringElements[0];
					int earned = Integer.parseInt(gradeStringElements[1]);
					int total = Integer.parseInt(gradeStringElements[2]);
					Grade grade = new Grade(name,earned,total);
					group.addGrade(grade);
				}
				course.addGroup(group);
			}
			return course;
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		}
	}

	public static void saveCourse(Course course){
		if(course==null)
			return;
		String data = "";
		data = data.concat(course.getName());
		data = data.concat("_");
		ArrayList<GradeGrouping> visited = new ArrayList<GradeGrouping>();
		for(int n=0;n<course.getGroups().size();n++){
			if(visited.contains(course.getGroups().get(n)))
				continue;
			else
				visited.add(course.getGroups().get(n));
			if(n>0)
				data = data.concat(":");
			GradeGrouping group = course.getGroups().get(n);
			data = data.concat(""+group.getPercentCounted()); 
			data = data.concat(";");
			for(int m=0;m<group.getGrades().size();m++){
				if(m>0)
					data = data.concat("~");
				Grade grade = group.getGrades().get(m);
				data = data.concat(grade.getName());
				data = data.concat("&" + grade.getEarned());
				data = data.concat("&" + grade.getTotal());
			}
		}
		try {
			PrintWriter writer = new PrintWriter(new File(course.getName() + ".cf"));
			writer.println(data);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file");
		}
	}

	public static ArrayList<String> getCourseNames(){
		ArrayList<String> files = new ArrayList<String>();
		File[] filesInDirectory = (new File(".")).listFiles();
		for(File file:filesInDirectory){
			if(file.getName().endsWith(".cf")){
				files.add(file.getName().substring(0,file.getName().length()-3));
			}
		}
		return files;
	}
}
