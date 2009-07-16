/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.event.manager.entities;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import org.event.manager.test.TestGroup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author tuosto
 */
@Test(groups={TestGroup.UNIT})
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
		assertNotNull(photo.toString());
		
		assertNotNull(new Photo());
	}
	
	@Test(dataProvider="invalid_ids", expectedExceptions={IllegalArgumentException.class})
	public void create_invalid_photo_with_id(Long id){
		@SuppressWarnings({ "deprecation", "unused" })
		Photo photo = new Photo(id);
	}
	
    @Test(dataProvider="valid_photo_string_creation")
    public void create_valid_user_with_builder(String name,String uri){
        URI _uri = URI.create(uri);
    	Photo photo = Photo.createPhoto(name, uri).build();
        
        assertNotNull(photo);
        assertEquals(photo.getName(),name);
        assertEquals(photo.getUri(),_uri);
    }

    @Test(dataProvider="valid_photo_uri_creation")
    public void create_valid_user_with_builder(String name,URI uri){
    	Photo photo = Photo.createPhoto(name, uri).build();
        
        assertNotNull(photo);
        assertEquals(photo.getName(),name);
        assertEquals(photo.getUri(),uri);
    }
    
    @Test(dataProvider="invalid_photo_string_creation",expectedExceptions={IllegalArgumentException.class})
    public void create_invalid_user_string_with_builder(String name,String uri){
        Photo.createPhoto(name, uri).build();
    }
    
    @Test(dataProvider="invalid_photo_string_creation",expectedExceptions={IllegalArgumentException.class})
    public void create_invalid_user_uri_with_builder(String name,URI uri){
        Photo.createPhoto(name, uri).build();
    }
    
    @DataProvider(name="valid_photo_uri_creation")
    public Object[][] photo_creation_uri_data(){
        return new Object[][]{
            new Object[] {"pepa",URI.create(VALID_URL)}
        };
    }
    
    @DataProvider(name="valid_photo_string_creation")
    public Object[][] photo_creation_data(){
        return new Object[][]{
            new Object[] {"pepa",VALID_URL}
        };
    }

    @DataProvider(name="invalid_ids")
    public Object[][] invalid_ids(){
        return new Object[][]{
            new Object[] {null},
            new Object[] {INVALID_ID}
        };
    }
    
    @DataProvider(name="invalid_photo_string_creation")
    public Object[][] photo_creation_string_data_invalid(){
        return new Object[][]{
            new Object[] {"pipo",null},
            new Object[] {null,INVALID_URI},
            new Object[] {"pipo",INVALID_URI},
            new Object[] {null,VALID_URL}
        };
    }
    
    @DataProvider(name="invalid_photo_uri_creation")
    public Object[][] photo_creation_uri_data_invalid(){
        return new Object[][]{
            new Object[] {"pipo",null},
            new Object[] {"pipo",URI.create(INVALID_URI)},
            new Object[] {null,URI.create(VALID_URL)}
        };
    }
	@Test
	@SuppressWarnings("deprecation")
	public void user_equals(){
		Photo first = new Photo(ID);
		Photo second = new Photo(ID);
		Photo third = new Photo(ID);
		assertTrue(first.equals(second));
		assertTrue(second.equals(third));
		assertTrue(first.equals(third));
		assertTrue(first.equals(first));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void setter_getters(){
		Photo photo = new Photo(ID);
		Long id = 1L;
		String name = "kaoru";
		String tooltip = "kaoru picture";
		URI uri = URI.create(VALID_URL);
		
		photo.setId(id);
		photo.setTooltip(tooltip);
		photo.setName(name);
		photo.setUri(uri);
		
		assertSame(photo.getId(), id);
		assertSame(photo.getTooltip(), tooltip);
		assertSame(photo.getUri(), uri);
		assertSame(photo.getName(), name);
	}
	
	@Test
	public void create_photo_with_tooltip(){
		final String tooltip = "kaoru first walk";
		Photo photo = Photo.createPhoto("kaoru", VALID_URL)
		                   .withToolTip(tooltip)
		                   .build();
		
		assertNotNull(photo);
		assertEquals(photo.getTooltip(), tooltip);
	}
}
