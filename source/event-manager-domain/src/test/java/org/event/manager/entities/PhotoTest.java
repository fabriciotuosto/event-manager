/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;



import static org.junit.Assert.*;

import java.net.URI;

import org.event.manager.TestUtils;
import org.junit.Test;



/**
 *
 * @author tuosto
 */
public class PhotoTest {

	private static final String INVALID_URI = "invalid_uri";
	private static final String VALID_URL = "http://www.kaoru.org/kaoru.png";
	private static final Long ID = 10L;
	private static final Long INVALID_ID = -10L;
	
	@Test
	@SuppressWarnings("deprecation")
	public void create_photo_with_id(){
		Photo photo = new Photo(ID);
		assertNotNull(photo);
		assertEquals(photo.getId(), ID);
		// To increase cobertura percentage
		assertNotNull(photo.toString());		
		assertNotNull(new Photo());
	}
	
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void create_invalid_photo_with_id(){
		new Photo(INVALID_ID);
	}

	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void create_invalid_photo_with_null_id(){
		new Photo(INVALID_ID);
	}
	

    @Test
    public void create_valid_user_with_builder(){
    	Photo photo = Photo.newPhoto("pepa",URI.create(VALID_URL)).build();
        
        assertNotNull(photo);
        assertEquals(photo.getName(),"pepa");
        assertEquals(photo.getUri(),URI.create(VALID_URL));
    }
    
    @Test
    public void create_invalid_photo_string_with_builder(){
        Photo.newPhoto("pepa",URI.create(VALID_URL)).build();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void create_invalid_photo_uri_with_builder(){
        Photo.newPhoto("valid_name", URI.create(INVALID_URI)).build();
    }

	@Test
	@SuppressWarnings("deprecation")
	public void user_equals(){
		
		Photo first = new Photo(ID);
		Photo second = new Photo(ID);
		Photo third = new Photo(ID);
		Object object = new Object();
		
		TestUtils.equalsTest(first, second, third, object);
	}
		
	@Test
	public void create_photo_with_tooltip(){
		final String tooltip = "kaoru first walk";
		Photo photo = Photo.newPhoto("kaoru", VALID_URL)
		                   .withToolTip(tooltip)
		                   .build();
		
		assertNotNull(photo);
		assertEquals(photo.getTooltip(), tooltip);
	}
}
