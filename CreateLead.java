package week5.Day2.Assignmnet;


import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CreateLead extends BaseLead {
	
	@Test(dataProvider="createdata")
	public void create(String cmpny, String fname, String lname, String phn) {
		
		driver.findElement(By.linkText("Create Lead")).click();
		driver.findElement(By.id("createLeadForm_companyName")).sendKeys(cmpny);
		driver.findElement(By.id("createLeadForm_firstName")).sendKeys(fname);
		driver.findElement(By.id("createLeadForm_lastName")).sendKeys(lname);
		driver.findElement(By.id("createLeadForm_primaryPhoneNumber")).sendKeys(phn);
		driver.findElement(By.name("submitButton")).click();
}
	@DataProvider
	public String[][] createdata(){
		String[][] data=new String[2][4];
		data[0][0]="TCS";
		data[0][1]="Pujitha";
		data[0][2]="J";
		data[0][3]="123";
		
		data[1][0]="CTS";
		data[1][1]="Siri";
		data[1][2]="Ch";
		data[1][3]="456";
		return data;
		
	}
}






