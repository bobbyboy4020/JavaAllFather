package testingSikuli;

import java.util.ArrayList;
import java.util.List;

import org.sikuli.script.*;

public class Retaking {
	public static final String IMGPATH = mainClass.IMGPATH;

	private static final Pattern edgeIcon = new Pattern(IMGPATH+"Report\\edgeIcon.png");
	private static final Pattern edgeOpener = new Pattern(IMGPATH+"Report\\edgeOpener.png");
	private static final Pattern buttonFindSubject = new Pattern(IMGPATH+"Report\\buttonFindSubject.png");
	private static final Pattern menuFindSubject = new Pattern(IMGPATH+"Report\\menuFindSubject.png");
	private static final Pattern imageLoadCheck = new Pattern(IMGPATH+"Report\\imageLoadCheck.png");
	private static final Pattern selectAdobePDF = new Pattern(IMGPATH+"selectAdobePDF.png");
	private static final Pattern adobeDropDown = new Pattern(IMGPATH+"adobeDropDown.png");
	
	Screen s;
	ArrayList<String> jobCodes;
	
	public Retaking(Screen s, ArrayList<String> jobCodes) {
		this.s = s;
		this.jobCodes = jobCodes;
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
	
	public void MakeEXE(List<String> codes, App eeApp) throws Exception {
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
			regon.wait("ADOBEprintCCards.png");		//Wait for the camera cards to start printing
			while (regon.exists("ADOBEprintCCards.png") != null) //while printing exists, stay printing
				Thread.sleep(1000);
		}
		catch (Exception ex) {
			Thread.sleep(1000);
		}
		eeApp.focus();
		Thread.sleep(1000);
		eeReg.click(Pattern("PrintPreviewCards.png").targetOffset(459,-1));
		Thread.sleep(2000);
	}
}
