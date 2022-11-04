package testingSikuli;

import java.util.ArrayList;
import org.sikuli.script.*;

public class FreshReporting {
	public static final String IMGPATH = mainClass.IMGPATH;

	private static final Pattern browser = mainClass.BROWSER;
	private static final Pattern browserLeft = mainClass.BROWSERLEFT;
	private static final Pattern fileBrowserCheck = new Pattern(IMGPATH+"Report\\fileBrowserCheck.png");
	private static final Pattern frbAdobePrinter = new Pattern(IMGPATH+"Report\\frbAdobePrinter.png");
	private static final Pattern frbMagniGlass = new Pattern(IMGPATH+"Report\\frbMagniGlass.png");
	private static final Pattern frbOk = new Pattern(IMGPATH+"Report\\frbOk.png");
	private static final Pattern frbPreview = new Pattern(IMGPATH+"Report\\frbPreview.png");
	private static final Pattern frbSavePDF = new Pattern(IMGPATH+"Report\\frbSavePDF.png");
	private static final Pattern launchButton = new Pattern(IMGPATH+"Report\\launchButton.png");
	private static final Pattern magGlass1 = new Pattern(IMGPATH+"Report\\magGlass1.png");
	private static final Pattern magGlass2 = new Pattern(IMGPATH+"Report\\magGlass2.png");
	private static final Pattern printAllPages = new Pattern(IMGPATH+"Report\\printAllPages.png");
	private static final Pattern printer1 = new Pattern(IMGPATH+"Report\\printer1.png");
	private static final Pattern printer2 = new Pattern(IMGPATH+"Report\\printer2.png");
	private static final Pattern printerIcon1 = new Pattern(IMGPATH+"Report\\printerIcon1.png");
	private static final Pattern printerIcon2 = new Pattern(IMGPATH+"Report\\printerIcon2.png");
	private static final Pattern properties = new Pattern(IMGPATH+"Report\\properties.png");
	private static final Pattern reportBar = new Pattern(IMGPATH+"Report\\reportBar.png");
	private static final Pattern reportDropDown = new Pattern(IMGPATH+"Report\\reportDropDown.png");
	private static final Pattern reportLogo = new Pattern(IMGPATH+"Report\\reportLogo.png");
	private static final Pattern reportMain = new Pattern(IMGPATH+"Report\\reportMainCheck.png");
	private static final Pattern reportOpen1 = new Pattern(IMGPATH+"Report\\reportOpen1.png");
	private static final Pattern reportOpen2 = new Pattern(IMGPATH+"Report\\reportOpen2.png");
	private static final Pattern reportSort = new Pattern(IMGPATH+"Report\\reportSort.png");
	
	public FreshReporting(ArrayList<String> jobCodes) throws Exception{
		ArrayList<App> frbApp = new ArrayList<App>();
		boolean first = true;
		Region frbReg = new Region(220,115,1480,850);
		Location locMag;
		Location locPrint;
		
		if(jobCodes.size()>2) 
			for(int i = 0; i < 3; i++) {
				FRBLaunch(mainClass.screenOne);
			}
		else
			for(int i = 0; i < jobCodes.size(); i++) {
				FRBLaunch(mainClass.screenOne);
			}
	}
	static Location PrintReport(Region magReg) throws Exception {
		Location locPrint = new Location(0,0);
		if(magReg.exists(printerIcon1) != null) locPrint = magReg.find(printerIcon1).getTarget();
		else if(magReg.exists(printerIcon2) != null) locPrint = magReg.find(printerIcon2).getTarget();
		return locPrint;
	}
	static Location MagReport(Region magReg) throws Exception {
		Location locPrint = new Location(0,0);
		if(magReg.exists(magGlass1) != null) locPrint = magReg.find(magGlass1).getTarget();
		else if(magReg.exists(magGlass2) != null) locPrint = magReg.find(magGlass2).getTarget();
		return locPrint;
	}
	static Region GetMagReg(Screen s) throws Exception {
		Region magreg;
		Location mag = s.find(reportBar).getTarget();
		magreg = new Region(mag.getX()-88,mag.getY()+17,98,18);
		return magreg;
	}
	static void FRBLaunch(Screen s) throws Exception {
		s.wait(reportLogo).doubleClick();
		s.wait(reportOpen1).click();
		s.click(launchButton);
		s.wait(reportOpen2).doubleClick();
		s.wait(frbPreview).click();
	}
	
	static void FRBFirstHalf(Screen s, String job, App frbApp, Location locMag, Region frbReg) throws Exception {
		frbApp.focus();
		s.setAutoWaitTimeout(50);
		Thread.sleep(2000);
		s.click(locMag);
		while (true){
			if(frbReg.exists(frbMagniGlass) != null) {
				break;
			}
			else {
				s.click(locMag);
				Thread.sleep(200);
			}
		}
		Thread.sleep(1000);
		s.paste(job.substring(0,10));
		Thread.sleep(3000);
		while (true){
			if(frbReg.exists(reportMain) != null) {
				break;
			}
			else {
				Thread.sleep(200);
			}
		}
		Thread.sleep(3000);
		s.type(Key.ENTER);
		while (true) {
			if(frbReg.exists(frbMagniGlass) != null) {
				s.type(Key.ENTER);
			}
			else {
				break;
			}
		}
	}
	static void FRBSecondHalf(Screen s, String job, App frbApp, boolean first, Location locPrint, Region frbReg) throws Exception {
		frbApp.focus();
		s.wait(reportMain);
		
		Thread.sleep(4000);
		s.click(locPrint);
		while (true) 
			if(frbReg.exists(frbAdobePrinter) != null) break;
			else s.click(locPrint);
		Thread.sleep(1000);
		s.type("s");
		Thread.sleep(200);
		s.type("a");
		frbReg.click(frbOk);
		mainClass.PrintPop(s);
		Thread.sleep(1000);
		s.wait(fileBrowserCheck);
		if(first) {
			frbReg.click(mainClass.BROWSERLEFT.targetOffset(526,0));
			s.type((String) mainClass.reportSaveLoc);
			s.type(Key.ENTER);
			Thread.sleep(200);
			frbReg.click(fileBrowserCheck);
		}
		Thread.sleep(1000);
		s.paste(job.substring(0,10)+" Field Ticket.pdf");
		frbReg.click(frbSavePDF);
		Thread.sleep(2500);
		s.click(locPrint);
		Thread.sleep(500);
		while (true) 
			if(frbReg.exists(frbAdobePrinter) != null) break;
			else s.click(locPrint);
		Thread.sleep(500);
		frbReg.click(properties.similar(0.7).targetOffset(-51, -4));
		
		frbReg.click(printer1);
		frbReg.click(printAllPages);
		Thread.sleep(2000);
		s.doubleClick(reportSort.similar(0.65).targetOffset(0, -15));
		String sort = mainClass.CopyClipboard(s);
		mainClass.sortArray.add(sort);
	}
}
