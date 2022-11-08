package testingSikuli;

import java.io.Console;
import java.io.IOException;
import java.awt.*;
import java.awt.datatransfer.*;
import java.util.ArrayList;
import java.util.List;

import org.sikuli.basics.*;
import org.sikuli.script.*;
import java.time.LocalDate;
import testingSikuli.FreshReporting;

public class mainClass {
	public static final String IMGPATH = "C:\\Users\\hkarn\\eclipse-workspace\\testingSikuli\\imgs\\";
	public static final String reportSaveLoc = "C:\\Users\\hkarn\\Documents\\PDF\\FreshReports\\Nov 1 - Nov 18";
	
	public static final Pattern jobsEE = new Pattern(IMGPATH+"jobsEE.png");
	public static final Pattern jobSelectEE = new Pattern(IMGPATH+"jobsSelectEE.png");
	public static final Pattern okButtonEE = new Pattern(IMGPATH+"okButtonEE.png");
	public static final Pattern browser = new Pattern(IMGPATH+"fileBrowserBar.png");
	public static final Pattern browserLeft = new Pattern(IMGPATH+"fileBrowserBarLeft.png");
	public static final Pattern viewAssociation = new Pattern(IMGPATH+"viewAssociation.png");
	public static final Pattern exeCheck = new Pattern(IMGPATH+"exeCheck.png");
	public static final Pattern editFileEE = new Pattern(IMGPATH+"editFileEE.png");
	public static final Pattern exeDoneCheck = new Pattern(IMGPATH+"exeDoneCheck.png");
	public static final Pattern exeDoneCheck2 = new Pattern(IMGPATH+"exeDoneCheck2.png");
	public static final Pattern adobePrinter = new Pattern(IMGPATH+"adobePrinter.png");
	public static final Pattern sortButtonEE = new Pattern(IMGPATH+"sortButtonEE.png");
	public static final Pattern quickSort = new Pattern(IMGPATH+"quickSort.png");
	public static final Pattern reportsPage1 = new Pattern(IMGPATH+"reportsPage1.png");
	public static final Pattern reportsName = new Pattern(IMGPATH+"reportsName.png");
	public static final Pattern printCardType = new Pattern(IMGPATH+"printCardType.png");
	public static final Pattern printPreviewCards = new Pattern(IMGPATH+"printPreviewCards.png");
	public static final Pattern printAdobeCCards = new Pattern(IMGPATH+"printAdobeCCards.png");
	public static final Pattern selectAdobePDF = new Pattern(IMGPATH+"selectAdobePDF.png");
	
	public static ArrayList<String> sortArray = new ArrayList<String>();
	
	public static Screen screenOne;
	public static Screen screenTwo;
	
	public static void main(String[] args) throws Exception {
		screenOne = new Screen(0);
		screenTwo = new Screen(1);
		Settings.AutoWaitTimeout = 60;
		Settings.MoveMouseDelay = 0;
		ArrayList<String> jobCodes = new ArrayList<String>();
		jobCodes = CopyJobCodes(screenTwo);
		FreshReport(screenOne, jobCodes);
	}
	
	static String CopyClipboard(Screen s) throws UnsupportedFlavorException, IOException {
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		String clip = "";
		s.type("c", KeyModifier.CTRL);
		return (String) c.getData(DataFlavor.stringFlavor);
	}
	static ArrayList<String> CopyJobCodes(Screen screen) throws FindFailed, Exception, IOException {
		Pattern jobCheck = new Pattern(IMGPATH+"JobsCheck.png");
		ArrayList<String> jobCodes = new ArrayList<String>();
		Location clicker = screen.find(jobCheck.similar(0.7)).getTarget();
		screen.click(clicker);
		screen.click(clicker);
		screen.click(clicker);
		String copy = CopyClipboard(screen);
		var jobsRB = copy.split("\n");
		for(String seg : jobsRB) {
			if(seg.indexOf("-") > 0) {
				var i = seg.indexOf("-");
				
				if(seg.indexOf("groups") > 0) {
					jobCodes.add(seg.substring(i+1,i+11)+"groups");
				}
				else {
					jobCodes.add(seg.substring(i+1,i+11));
				}
			}
		}
		
		return jobCodes;
	}
	static String JobLocation(Screen s) throws Exception {
		String jobLoc = "";
		Pattern fileBrowser = new Pattern(IMGPATH+"FileBrowserBar.png");
		s.wait(fileBrowser.similar(0.7), 2).click();
		jobLoc = CopyClipboard(s);
		return jobLoc;
	}
	static Location JobFinder(Screen s, String code_) throws Exception {
		Location clickLoc = new Location(0,0);
		String code = code_;
		Pattern E231 = new Pattern(IMGPATH+"E231.png");
		Pattern H231 = new Pattern(IMGPATH+"H231.png");
		Pattern E232 = new Pattern(IMGPATH+"E232.png");
		Pattern jobList = new Pattern(IMGPATH+"jobListEE.png");
		Location regLoc = s.find(jobList).getTarget();
		Region reg = new Region(regLoc.getX()-39, regLoc.getY()+21, 50, 100);
		
		if(code == "E231") {
			s.wait(E231.exact());
			clickLoc = reg.find(E231.exact()).getTarget();
		}
		else if(code == "H231") {
			s.wait(H231.exact());
			clickLoc = reg.find(E231.exact()).getTarget();
		}
		else if(code == "E232") {
			s.wait(E232.exact());
			clickLoc = reg.find(E231.exact()).getTarget();
		}
		else if(code == "H232") {
			
		}
		
		return clickLoc;
	}
	static void PrintPop(Screen s) throws Exception {
		Region reg = new Region(220,115,1480,850);
		Pattern SavePDF = new Pattern(IMGPATH+"pdfSaveAs.png");
		Pattern SaveAs = new Pattern(IMGPATH+"popUpSaveAs.png");
		Pattern BSave = new Pattern(IMGPATH+"BSave.png");
		while (true) {
			if(reg.exists(SavePDF.exact()) != null) {
				break;
			}
			else {
				if(s.exists(SaveAs.similar(0.69)) != null)
					s.click(SaveAs.similar(0.69));
				else if(s.exists(BSave.similar(0.69)) != null)
					s.click(BSave.similar(0.69));
			}
		}
	}
	static void FreshReport(Screen s, ArrayList<String> jobCodes) throws Exception {
		FreshReporting frb = new FreshReporting(jobCodes, screenOne);
		frb.Fresh();
	}
}


