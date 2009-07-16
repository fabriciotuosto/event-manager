package org.event.manager.utils;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test(groups = { TestGroup.UNIT })
public class InternetUtilsTest {

	@Test(dataProvider = "valid_mails")
	public void valid_emails(String validEmail) {
		assertTrue(InternetUtils.isEmailAdressValid(validEmail));
	}

	@Test(dataProvider = "invalid_mails")
	public void invalid_emails(String invalidEmail) {
		assertFalse(InternetUtils.isEmailAdressValid(invalidEmail));
	}

	@Test(dataProvider = "invalid_images")
	public void invalid_images(String images_uri) {
		assertFalse(InternetUtils.isImageLinkValid(images_uri));
	}

	@Test(dataProvider = "valid_images")
	public void valid_images(String images_uri) {
		assertTrue(InternetUtils.isImageLinkValid(images_uri));
	}

	@DataProvider(name = "valid_mails")
	public Object[][] validMails() {
		return new Object[][] { new Object[] { "pepe@pepito.net" },
				new Object[] { "pepe@pepito-net.info" },
				new Object[] { "_pepe@pepi_to.net" } };
	}

	@DataProvider(name = "invalid_mails")
	public Object[][] invalidMails() {
		return new Object[][] { new Object[] { "pepepepito.net" },
				new Object[] { "pepe@pepitonet" },
				new Object[] { "@pepe_pepito.net" },
				new Object[] { "pepe@pepito_net" },
				new Object[] { "pepepepito" } };
	}

	@DataProvider(name = "invalid_images")
	public Object[][] invalidImages() {
		return new Object[][] { new Object[] { "http://www.kaoru.org" },
				new Object[] { "http://goear.com/listen/7d13372" },
				new Object[] { "_pepe@pepi_to.net" } };
	}

	@DataProvider(name = "valid_images")
	public Object[][] validImages() {
		return new Object[][] {
				new Object[] { "http://www.kaoru.org/kaoru.png" },
				new Object[] { "http://www.kaoru.org/kaoru.jpeg" },
				new Object[] { "http://www.kaoru.org/kaoru.jpg" },
				new Object[] { "http://www.kaoru.org/kaoru.gif" }
		};
	}
}
