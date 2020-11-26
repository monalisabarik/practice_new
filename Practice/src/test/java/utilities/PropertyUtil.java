package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	
	private static Properties prop;

	public static void intializePropertyFile() throws IOException {
		File file = new File("C:\\Users\\91986\\git\\practice_orangeHRM_001\\src\\test\\resources\\TestData\\ConfigData.Properties");

		FileInputStream fis = new FileInputStream(file);

		prop = new Properties();

		prop.load(fis);
	}

	public static  String readProperty(String keyName) {

		return prop.getProperty(keyName);
	}

}
