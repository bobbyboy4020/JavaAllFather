package testingSikuli;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.sikuli.script.*;

public class Retaking {
	public static final String IMGPATH = mainClass.IMGPATH;

	private static final Pattern edgeIcon = new Pattern(IMGPATH+"Report\\edgeIcon.png");
	private static final Pattern edgeOpener = new Pattern(IMGPATH+"Report\\edgeOpener.png");
	private static final Pattern buttonFindSubject = new Pattern(IMGPATH+"Report\\buttonFindSubject.png");
	private static final Pattern menuFindSubject = new Pattern(IMGPATH+"Report\\menuFindSubject.png");
	private static final Pattern imageLoadCheck = new Pattern(IMGPATH+"Report\\imageLoadCheck.png");
	private static final Pattern selectAdobePDF = new Pattern(IMGPATH+"Report\\selectAdobePDF.png");
	private static final Pattern adobeDropDown = new Pattern(IMGPATH+"Report\\adobeDropDown.png");
	
	Screen s;
	ArrayList<String> jobCodes;
	
	private static List<List<String>> codes;
	private static List<String> sorts;
	
	
	public Retaking(Screen s, ArrayList<String> jobCodes) {
		this.s = s;
		this.jobCodes = jobCodes;
	}
	public void Retake(boolean frbDone) throws Exception {
		List<App> eeApp;
		for(int i = 0; i < jobCodes.size(); i++) {//gets info from table into managable split entries
			codes.add(Arrays.asList(jobCodes.get(i).toUpperCase().split("-")));
		}
		if(jobCodes.size() < 3) {
			for(int i = 0; i < 3; i++) {
				s.doubleClick(edgeIcon);
				s.wait(edgeOpener);
				s.click(edgeOpener.targetOffset(1,33));
				Thread.sleep(500);
			}
		}
		else {
			for(int i = 0; i < jobCodes.size(); i++) {
				s.doubleClick(edgeIcon);
				s.wait(edgeOpener);
				s.click(edgeOpener.targetOffset(1,33));
				Thread.sleep(500);
			}
		}
		eeApp = App.getApps("Edge Entry.exe");
		Region eeReg = new Region(390,115,1140,844);
		JFrame f = new JFrame();
		for(int i = 0; i < jobCodes.size(); i+=3) {
			int exeMake = 0;
			int exeGood = 0;
			try {
				exeMake = JOptionPane.showConfirmDialog(f,"Make exes? " + jobCodes.get(i) + ", " + jobCodes.get(i+1) + ", " + jobCodes.get(i+2));
				if (frbDone == false) {	
					String dialog = "sorts for jobs? split by comma, enter nothing if all are default\nh for homeroom, g for grade, a for alpha\n" + jobCodes.get(i).toString() + ", " + jobCodes.get(i+1) + ", " + jobCodes.get(i+2);
					String sort = JOptionPane.showInputDialog(f, dialog);
					sorts.addAll(Arrays.asList(sort.split(",")));
				}
			}
			catch (Exception e) {
				try {
					exeMake = JOptionPane.showConfirmDialog(f,"Make exes? " + jobCodes.get(i) + ", " + jobCodes.get(i+1));
					if (frbDone == false) {
						String dialog = "sorts for jobs? split by comma, enter nothing if all are default\nh for homeroom, g for grade, a for alpha\n" + jobCodes.get(i) + ", " + jobCodes.get(i+1);
						String sort = JOptionPane.showInputDialog(f, dialog);
						sorts.addAll(Arrays.asList(sort.split(",")));
					}
				} 
				catch (Exception ex) {
					try {
						exeMake = JOptionPane.showConfirmDialog(f,"Make exes? " + jobCodes.get(i));
						if (frbDone == false) {
							String dialog = "sorts for jobs? split by comma, enter nothing if all are default\nh for homeroom, g for grade, a for alpha\n" + jobCodes.get(i);
							String sort = JOptionPane.showInputDialog(f, dialog);
							sorts.addAll(Arrays.asList(sort.split(",")));
						}
					} 
					catch (Exception exc) {
					}
				}
			}
			
			if (exeMake == JOptionPane.YES_OPTION) {
				CreateJobRetake(codes.get(i), eeApp.get(0), eeReg);
				MakeExe(codes.get(i), eeApp.get(0));
				try {
					CreateJobRetake(codes.get(i+1), eeApp.get(1), eeReg);
					MakeExe(codes.get(i+1), eeApp.get(1));
				} catch (Exception e) {
					try {
						eeApp.get(1).close();
					} catch(Exception xe) {}
				}
				try {
					CreateJobRetake(codes.get(i+2), eeApp.get(2), eeReg);
					MakeExe(codes.get(i+2), eeApp.get(2));
				} catch (Exception exc) {
					try {
						eeApp.get(2).close();
					} catch (Exception ecx){}
				}
			}
				
			exeGood = JOptionPane.showConfirmDialog(f,"Exes made?");
			if(exeGood == JOptionPane.YES_OPTION) {
				if(frbDone)
					SaveCardsRetake(codes.get(i), eeApp.get(0), true, eeReg, mainClass.sortArray.get(i));
				else
					SaveCardsRetake(codes.get(i), eeApp.get(0), true, eeReg, sorts.get(0));
				try {
					if(frbDone)
						SaveCardsRetake(codes.get(i+1), eeApp.get(1), true, eeReg, mainClass.sortArray.get(i+1));
					else
						SaveCardsRetake(codes.get(i+1), eeApp.get(1), true, eeReg, sorts.get(1));
				}
				catch(Exception ex) {
					try { SaveCardsRetake(codes.get(i+1), eeApp.get(1), true, eeReg, "");}
					catch(Exception exc) {}
				}
				try {
					if(frbDone) 
						SaveCardsRetake(codes.get(i+2), eeApp.get(2), true, eeReg, mainClass.sortArray.get(i+2));
					else
						SaveCardsRetake(codes.get(i+2), eeApp.get(2), true, eeReg, sorts.get(2));
				} catch(Exception e) {
					try { SaveCardsRetake(codes.get(i+2), eeApp.get(2), true, eeReg, "");}
					catch (Exception ex) {}
				}
			}
			try {
				if(codes.get(i).get(3) == "groups") {
					eeApp.get(0).focus();
					JOptionPane.showMessageDialog(f, jobCodes.get(i) + " has groups");
				}
			} catch (Exception ex) {}
			try {
				if(codes.get(i+1).get(3) == "groups") {
					eeApp.get(1).focus();
					JOptionPane.showMessageDialog(f, jobCodes.get(i+1) + " has groups");
				}
			} catch (Exception ex) {}
			try {
				if(codes.get(i+2).get(3) == "groups") {
					eeApp.get(2).focus();
					JOptionPane.showMessageDialog(f, jobCodes.get(i+2) + " has groups");
				}
			} catch (Exception ex) {}

		}
		JOptionPane.showMessageDialog(f, "retakes all done :)");
		eeApp.get(0).close();
		try {
			eeApp.get(1).close();
			eeApp.get(2).close();
		} catch (Exception ex) {}
		
	}
	
	public void CreateJobRetake(List<String> codes, App eeApp, Region eeReg) throws Exception{
		eeApp.focus();
		Location clickLoc;
		eeReg.wait(mainClass.jobsEE).click();
		eeReg.wait(mainClass.jobSelectEE).click();
		for(int i=0;i<3;i++) {
			s.type(Key.BACKSPACE);
		}
		Thread.sleep(200);
		s.paste(codes.get(0));
		Thread.sleep(200);
		s.type(Key.ENTER);
		Thread.sleep(1000);
		eeReg.wait(mainClass.viewAssociation).click();
		Thread.sleep(250);
		clickLoc = mainClass.JobFinder(s, codes.get(1));
		s.doubleClick(clickLoc);
	}
	
	public void MakeExe(List<String> codes, App eeApp) throws Exception {
		eeApp.focus();
		String jobLoc = "Q:\\Jobs23\\"+codes.get(0).toString()+"\\"+codes.get(1).toString();
		Thread.sleep(500);
		Location regLoc = s.find(mainClass.editFileEE).getTarget();
		Region regExe = new Region(regLoc.getX()+25, regLoc.getY()+5, 150, 100);
		s.keyDown(Key.ALT);
		s.type("j");
		while (true) {
			if(regExe.exists(mainClass.exeCheck) != null){
				break;
			}
			else {
				eeApp.focus();
				s.keyDown(Key.ALT);
				s.type("j");
			}
		}
		Thread.sleep(150);
		s.type("e");
		Thread.sleep(150);
		s.type("e");
		Thread.sleep(150);
		s.type("r");
		s.keyUp();
		Thread.sleep(500);
		s.type(codes.get(2));
		s.type(Key.ENTER);
	}
	
	public void SaveCardsRetake(List<String> codes, App eeApp, boolean first, Region eeReg, String sortType) throws Exception {
		String jobLoc = "Q:\\Jobs23\\"+codes.get(0)+"\\"+codes.get(0);
		eeApp.focus();
		Thread.sleep(500);
		eeReg.wait(mainClass.exeDoneCheck.targetOffset(2,51)).click();
		eeReg.wait(mainClass.exeDoneCheck2);
		Thread.sleep(1000);
		eeReg.click(mainClass.exeDoneCheck2.targetOffset(22,68));
		Thread.sleep(500); //working on changing languages over
		eeReg.click(menuFindSubject);
		while (true)
			if(eeReg.exists(imageLoadCheck) != null)
				break;
			else
				eeReg.click(menuFindSubject);
		Thread.sleep(100);
		eeReg.click(menuFindSubject.targetOffset(24,6));
		Thread.sleep(100);
		eeReg.click(buttonFindSubject);
		Thread.sleep(500);
		eeReg.click(mainClass.sortButtonEE);
		while (true)
			if(eeReg.exists(mainClass.quickSort) != null)
				break;
			else
				eeReg.click(mainClass.sortButtonEE); Thread.sleep(200);
		Thread.sleep(200);
		if (sortType != "") {
			sortType = sortType.substring(0);
			sortType.toLowerCase();
		}
		if (sortType.contains("h")) {
			eeReg.click(mainClass.quickSort);
		}
		else if (sortType.contains("g")) {
			eeReg.click(mainClass.quickSort.targetOffset(0,-27));
		}
		else if (sortType.contains("a")) {
			eeReg.click(mainClass.quickSort.targetOffset(0,35));
		}
		else {
			if(codes.get(1) == "E231")
				eeReg.click(mainClass.quickSort);
			else if(codes.get(1) == "H231")
				eeReg.click(mainClass.quickSort.targetOffset(0,-27));
			else if(codes.get(1) == "E232")
				eeReg.click(mainClass.quickSort);
			else if(codes.get(1) == "H232")
				eeReg.click(mainClass.quickSort.targetOffset(0,-27));
		}
		Thread.sleep(500);
		eeReg.click(mainClass.reportsPage1);
		while (true) 
			if(eeReg.exists(mainClass.reportsName) != null)
				break;
			else {
				eeReg.click(mainClass.reportsPage1);
				Thread.sleep(250);
			}
		eeReg.click(mainClass.reportsName.targetOffset(154,39));
		Thread.sleep(200);
		s.paste("Q:\\Edge\\Lab\\Reports\\Camera Cards\\School\\Subjects Not Photographed - Alpha.rtm");
		Thread.sleep(200);
		eeReg.click(mainClass.printCardType.targetOffset(53,-3));
		Thread.sleep(250);
		s.type("v", KeyModifier.ALT);
		Thread.sleep(250);
		eeReg.wait(mainClass.printPreviewCards);
		Thread.sleep(1000);
		eeReg.click(mainClass.printPreviewCards.targetOffset(-37,27));
		while (true) {
			if(eeReg.exists(mainClass.okButtonEE) != null)
				break;
			else
				eeReg.click(mainClass.printPreviewCards.targetOffset(-37,27));
				Thread.sleep(200);
		}
		Thread.sleep(200);
		if(first) {
			eeReg.wait(mainClass.adobePrinter);
			eeReg.click(adobeDropDown);
			s.type("a");
			eeReg.click(selectAdobePDF);
			Thread.sleep(200);
		}
		eeReg.click(mainClass.okButtonEE);
		Thread.sleep(500);
		mainClass.PrintPop(s);
		Thread.sleep(1000);
		s.paste(jobLoc);
		Thread.sleep(200);
		s.type(Key.ENTER);
		Thread.sleep(500);
		s.type("a", KeyModifier.CTRL);
		Thread.sleep(500);
		s.paste(codes.get(0) + "-" + codes.get(1) + "-" + codes.get(2) + " Subjects Not Photographed - Alpha.pdf");
		Thread.sleep(500);
		s.type(Key.ENTER);
		Thread.sleep(1000);
		Region regon = new Region(0,0,150,150);
		try {
			regon.wait(mainClass.printAdobeCCards);		//Wait for the camera cards to start printing
			while (regon.exists(mainClass.printAdobeCCards) != null) //while printing exists, stay printing
				Thread.sleep(1000);
		}
		catch (Exception ex) {
			Thread.sleep(1000);
		}
		eeApp.focus();
		Thread.sleep(1000);
		eeReg.click(mainClass.printPreviewCards.targetOffset(459,-1));
		Thread.sleep(2000);
	}
}
