package org.event.manager.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InternetUtilsTest {
	@Test
	public void valid_emails() {
		assertTrue(InternetUtils.isEmailAdressValid("pepe@pepe-net.info"));
	}

	@Test
	public void invalid_emails() {
		assertFalse(InternetUtils.isEmailAdressValid("pepepepito.net"));
	}

	@Test
	public void invalid_images() {
		assertFalse(InternetUtils.isImageLinkValid("http://goear.com/listen/7d13372"));
	}

	@Test
	public void valid_images() {
		assertTrue(InternetUtils.isImageLinkValid("http://www.kaoru.org/kaoru.png"));
	}

}
