package week5.Day2.Assignmnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SnowPOI extends BaseSnow {
	@Test(dataProvider = "createdata",priority=1)
	public void create(String frame1, String frame2, String dscrptn) throws InterruptedException {
		driver.findElement(By.xpath("(//div[text()='Create New'])[1]")).click();
		driver.switchTo().frame(frame1);
		driver.findElement(By.xpath("(//span[@class='icon icon-search'])[1]")).click();
		Set<String> WH1 = driver.getWindowHandles();
		List<String> wh1 = new ArrayList(WH1);
		driver.switchTo().window(wh1.get(1));
		driver.findElement(By.xpath("(//a[@class='glide_ref_item_link'])[1]")).click();
		driver.switchTo().window(wh1.get(0));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frame2);
		driver.findElement(By.id("incident.short_description")).sendKeys(dscrptn);
		WebElement id = driver.findElement(By.id("incident.number"));
		String Val = id.getAttribute("value");
		driver.findElement(By.id("sysverb_insert")).click();
		WebElement ival = driver.findElement(By.xpath("(//input[@class='form-control'])[1]"));
		ival.sendKeys(Val);
		ival.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		WebElement ver = driver.findElement(By.xpath("(//a[@class='linked formlink'])[1]"));
		String result = ver.getText();
		if (Val.equals(result)) {
			System.out.println("Incident created successfully");
		} else {
			System.out.println("Incident not created");
		}
	}
	@DataProvider
	public String[][] createdata() throws IOException {
		XSSFWorkbook WB= new XSSFWorkbook("./data/Snow.xlsx");
		XSSFSheet WS= WB.getSheet("create");
		int rc=WS.getLastRowNum();
		int cc=WS.getRow(0).getLastCellNum();
		String[][] data = new String[rc][cc];
		for (int i = 1; i <= rc; i++) {
			for (int j = 0; j < cc; j++) {
				String v = WS.getRow(i).getCell(j).getStringCellValue();
				data[i-1][j]=v;
			}
		}
		WB.close();
		return data;
	}

	@Test(dataProvider = "updatedata",priority=2, enabled=true)
	public void update(String frm, String fltrwith) {
		driver.findElement(By.xpath("(//div[text()='All'])[2]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frm);
		WebElement fltr = driver.findElement(By.xpath("//select[@class='form-control default-focus-outline']"));
		Select filter = new Select(fltr);
		filter.selectByValue("state");
		WebElement up = driver.findElement(By.xpath("(//input[@class='form-control'])[1]"));
		up.sendKeys(fltrwith);
		up.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//a[@class='linked formlink']")).click();
		WebElement state = driver.findElement(By.id("incident.state"));
		Select State = new Select(state);
		State.selectByValue("2");
		boolean check1 = State.equals(State);

		WebElement urgency = driver.findElement(By.id("incident.urgency"));
		Select Urgency = new Select(urgency);
		Urgency.selectByVisibleText("1 - High");
		boolean check2 = Urgency.equals(Urgency);
		if (check1 && check2 == true) {
			System.out.println("Incident Updated Successfully");
		} else {
			System.out.println("Incident not Updated");

		}

	}

	@DataProvider
	public String[][] updatedata() throws IOException {
		XSSFWorkbook wb=new XSSFWorkbook("./data/Snow.xlsx");
		XSSFSheet ws= wb.getSheet("update");
		int rc= ws.getLastRowNum();
		int cc = ws.getRow(0).getLastCellNum();
		String data[][] = new String[rc][cc];
for (int i = 1; i <= rc; i++) {
	for (int j = 0; j <cc; j++) {
		String val=ws.getRow(i).getCell(j).getStringCellValue();
		data[i-1][j]=val;
	}
}
		wb.close();
		return data;

	}

	@Test(dataProvider = "assigndata",priority=3,enabled=true)
	public void assign(String frm, String fltrwith, String wrkNote) {
		driver.findElement(By.xpath("(//div[text()='All'])[2]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frm);
		WebElement fltr = driver.findElement(By.xpath("//select[@class='form-control default-focus-outline']"));
		Select filter = new Select(fltr);
		filter.selectByValue("state");
		WebElement up = driver.findElement(By.xpath("(//input[@class='form-control'])[1]"));
		up.sendKeys(fltrwith);
		up.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//a[@class='linked formlink']")).click();
		WebElement id = driver.findElement(By.id("incident.number"));
		String ID = id.getAttribute("value");
		driver.findElement(By.id("lookup.incident.assignment_group")).click();
		Set<String> wh = driver.getWindowHandles();
		List<String> WH = new ArrayList<String>(wh);
		driver.switchTo().window(WH.get(1));
		driver.findElement(By.xpath("//a[text()='Software']")).click();
		driver.switchTo().window(WH.get(0));
		driver.switchTo().defaultContent();
		driver.switchTo().frame("gsft_main");
		driver.findElement(By.xpath("//textarea[@id='activity-stream-textarea']")).sendKeys(wrkNote);
		driver.findElement(By.id("sysverb_update")).click();
		WebElement fltrs = driver.findElement(By.xpath("//select[@class='form-control default-focus-outline']"));
		Select filters = new Select(fltrs);
		filters.selectByValue("number");
		WebElement srch = driver.findElement(By.xpath("(//input[@class='form-control'])[1]"));
		srch.sendKeys(ID);
		srch.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//a[@class='linked formlink']")).click();
		WebElement val = driver.findElement(By.xpath("//input[@id='sys_display.incident.assignment_group']"));
		String Value = val.getAttribute("value");
		if (Value.contains("Software")) {
			System.out.println("Assigned to Software group");
		} else {
			System.out.println("Not Assigned to Software group");
		}
	}

	@DataProvider
	public String[][] assigndata() throws IOException {
		XSSFWorkbook wb=new XSSFWorkbook("./data/Snow.xlsx");
		XSSFSheet ws=wb.getSheet("assign");
		int rc=ws.getLastRowNum();
		int cc=ws.getRow(0).getLastCellNum();
		String[][] data = new String[rc][cc];
		for (int i = 1; i <=rc; i++) {
			for (int j = 0; j < cc; j++) {
				String val=ws.getRow(i).getCell(j).getStringCellValue();
				data[i-1][j]=val;
			}
		}
		wb.close();
		return data;
	}

	String ID = "";

	@Test(priority=4,enabled=true)
	public void Delete() {
		driver.findElement(By.xpath("(//div[text()='All'])[2]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("gsft_main");
		WebElement incident = driver.findElement(By.xpath("//a[@class='linked formlink']"));
		incident.click();
		driver.findElement(By.xpath("//button[text()='Delete']")).click();
		WebElement button = driver.findElement(By.id("ok_button"));
		String text = button.getText();
		button.click();
		if (text.contains("Delete")) {
			System.out.println("Incident Deleted");
		} else {
			System.out.println("Incident not Deleted");

		}
	}

}
