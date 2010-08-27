package com.rapidftr.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Vector;

import javax.xml.ws.ServiceMode;

import junit.framework.Assert;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.rapidftr.controllers.ChildCreateUpdateController;
import com.rapidftr.controllers.Dispatcher;
import com.rapidftr.datastore.ChildrenRecordStore;
import com.rapidftr.datastore.FormStore;
import com.rapidftr.screens.ChildCreateUpdateScreen;
import com.rapidftr.screens.UiStack;

public class ChildTest {

	private Vector forms;

	@Before
	public void setUp() {
		forms = new Vector();
		Vector fieldList = new Vector();
    	FormField nameTextField = mock(FormField.class);
		when(nameTextField.getValue()).thenReturn("someName");
		when(nameTextField.getName()).thenReturn("name");
		fieldList.add(nameTextField);
		Form form = new Form("Basic_details", "basic_details", fieldList);
		forms.add(form);
	}

	@Test
	public void shouldCreateUniqueIdOnEveryInvocationOfCreateUniqueIdMethod() {
		String userName = "rapidFTR";
		Child alice = new Child();
		alice.setField("name", "Alice");
		alice.setField("last_known_location", "Leh");
		alice.createUniqueId(userName);
		String firstId = alice.getField("unique_identifier").toString();
		alice.createUniqueId(userName);
		assertNotSame(firstId, alice.getField("unique_identifier").toString());
	}

	@Test
	public void twoChildrenShouldBeSameIfHaveSameUniqueIdentifier() {
		Child alice = new Child();
		alice.setField("name", "Alice");
		alice.setField("unique_identifier", "unique_identifier");

		Child joy = new Child();
		joy.setField("name", "joy");
		joy.setField("unique_identifier", "unique_identifier");
		assertTrue(joy.equals(alice));
	}

	@Test
	public void twoChildrenShouldBeSameIfHaveSameCouchId() {
		Child alice = new Child();
		String couchId = "someRandomCouchId";
		alice.setField("name", "Alice");
		alice.setField("_id", couchId);

		Child joy = new Child();
		joy.setField("name", "joy");
		joy.setField("_id", couchId);
		assertTrue(joy.equals(alice));
	}

	@Test
	public void shouldCreateNewChildWithSupliedFormData() {
		Child alice = Child.create(forms);
		assertNotNull(alice);		
	}
	
	@Test
	public void shouldUpdateChildWithSupliedFormData() {
		Child alice = new Child();
		String couchId = "someRandomCouchId";
		alice.setField("name", "Alice");
		alice.setField("_id", couchId);		
		alice.update("rapidftr", forms);		
		assertEquals("someName", alice.getField("name"));
	}
}
