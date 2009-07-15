package org.event.manager.utils;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test(groups={TestGroup.UNIT})
public class EmailUtilsTest {
	
	@Test(dataProvider="valid_mails")
	public void valid_emails(String validEmail){
		assertTrue(EmailUtils.isEmailAdressValid(validEmail));
	}

	@Test(dataProvider="invalid_mails")
	public void invalid_emails(String invalidEmail){
		assertFalse(EmailUtils.isEmailAdressValid(invalidEmail));
	}
	
	@DataProvider(name="valid_mails")
	public Object[][] validMails(){
		return new Object[][]{
				new Object[]{"pepe@pepito.net"},
				new Object[]{"pepe@pepito-net.info"},
				new Object[]{"_pepe@pepi_to.net"}
		};
	}
	
	@DataProvider(name="invalid_mails")
	public Object[][] invalidMails(){
		return new Object[][]{
				new Object[]{"pepepepito.net"},
				new Object[]{"pepe@pepitonet"},
				new Object[]{"@pepe_pepito.net"},
				new Object[]{"pepe@pepito_net"},
				new Object[]{"pepepepito"}
		};
	}	
}
