package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="Data")
	public String[][] getAllData() throws IOException{
		String path = System.getProperty("user.dir") +"//testData//Userdata.xlsx";
		XLUtility xl = new XLUtility(path);
		
		int rowNum = xl.getRowCount("sheet1");
		int colcount = xl.getCellCount("sheet1", 1);
		
		String apidata[][] = new String[rowNum][colcount];
		
		for(int i=1; i<=rowNum;i++) {
			for(int j=0; j<colcount; j++) {
				apidata[i-1][j]= xl.getCellData("sheet1", i, j);
			}
		}
		
		return apidata;
	}
	
	@DataProvider(name="UserNames")
	public String[] getUserNames() throws IOException {
		String path = System.getProperty("user.dir")+"//testData//Userdata.xlsx";
		XLUtility xl = new XLUtility(path);
		
		int rowNum = xl.getRowCount("sheet1");
		String apidata[]  = new String[rowNum];
		
		for(int i=1;i<=rowNum; i++) {
			apidata[i-1] = xl.getCellData("sheet1", i, 1);
		}
		
		return apidata;
		
	}
	
	
}
