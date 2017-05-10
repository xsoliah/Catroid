/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.content.brick;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.widget.ListAdapter;

import org.catrobat.catroid.R;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.DeleteItemOfUserListBrick;
import org.catrobat.catroid.content.bricks.InsertItemIntoUserListBrick;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.uiespresso.util.BaseActivityInstrumentationRule;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.catrobat.catroid.uiespresso.content.brick.BrickTestUtils.onScriptList;

/**
 * Created by Manuel on 10.05.17.
 */

public class InsertIntoUserListTest {
	private int insertBrickPosition;
	private int deleteBrickPosition;

	@Rule
	public BaseActivityInstrumentationRule<ScriptActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ScriptActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		Script start = BrickTestUtils.createProjectAndGetStartScript("insertItemIntoUserListTest");
		start.addBrick(new InsertItemIntoUserListBrick(BrickValues.INSERT_ITEM_INTO_USERLIST_VALUE, BrickValues.INSERT_ITEM_INTO_USERLIST_INDEX));
		start.addBrick(new DeleteItemOfUserListBrick(BrickValues.DELETE_ITEM_OF_USERLIST));
		baseActivityTestRule.launchActivity(null);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext());
		insertBrickPosition = 1;
		deleteBrickPosition = 2;
	}

	@Test
	public void testCreateNewUserListAndDeletion() {
		String myvar = "myVariable";
		BrickTestUtils.checkIfBrickAtPositionShowsString(insertBrickPosition, R.string.brick_insert_item_into_userlist_at_position);
		BrickTestUtils.checkIfSpinnerOnBrickAtPositionShowsString(R.id.insert_item_into_userlist_spinner,
				insertBrickPosition, R.string.brick_variable_spinner_create_new_variable);
		BrickTestUtils.createNewVariableOnSpinnerInitial(R.id.insert_item_into_userlist_spinner, insertBrickPosition,
				myvar);
		BrickTestUtils.checkIfSpinnerOnBrickAtPositionShowsString(R.id.insert_item_into_userlist_spinner,
				deleteBrickPosition, myvar);

		BrickTestUtils.checkIfBrickAtPositionShowsString(deleteBrickPosition, myvar);

		onView(withId(R.id.brick_insert_item_into_userlist_value_edit_text)).perform(click());
		onView(withText("Data")).perform(click());

		/*List<String> list = new ArrayList();
		list.add(UiTestUtils.getResourcesString(R.string.brick_variable_spinner_create_new_variable));
		list.add(myvar);
		BrickTestUtils.mycheckIfValuesAvailableInSpinnerOnBrick(list, R.id.insert_item_into_userlist_spinner,
				insertBrickPosition);*/
	}

	@Test
	public void testCreateUserListInFormulaEditor() {

	}
}
