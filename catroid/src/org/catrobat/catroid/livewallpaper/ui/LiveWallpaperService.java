/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2015 The Catrobat Team
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

package org.catrobat.catroid.livewallpaper.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.exceptions.CompatibilityProjectException;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.exceptions.OutdatedVersionProjectException;
import org.catrobat.catroid.stage.StageListener;


/**
 * Created by marco on 17.03.15.
 */
public class LiveWallpaperService extends AndroidLiveWallpaperService {

	public static Context context;

	Project currentProject = null;

	public static Context getContext() {
		return context;
	}

	private static LiveWallpaperService INSTANCE;

	@Override
	public void onCreateApplication() {
		Log.d("LWPService", "onCreateApplication()!");
		INSTANCE = this;

		this.context = this.getApplicationContext();
		if(currentProject == null)
			ProjectManager.getInstance().initializeDefaultProject(this.getBaseContext());

		//WallpaperProjectHandler.getInstance().addObserver(this);
		//WallpaperProjectHandler.getInstance().notifyObservers();
		//this.initialize(new SelectProjectNotificationListener());
		ApplicationListener listener = new LivewallpaperListener();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(listener, config);
		Log.d("LWP|LWPService", "LWP onCreateApplication() called");
	}
	
	public static LiveWallpaperService getInstance() {
		return INSTANCE;
	}



	public void loadProject(String projectName) {
		try {
			Log.d("LWP|Service", "loadProject " + projectName);
			ProjectManager.getInstance().loadProject(projectName, context);
		}
		catch(OutdatedVersionProjectException e) {
			e.printStackTrace();
		}
		catch(LoadingProjectException e) {
			e.printStackTrace();
		}
		catch(CompatibilityProjectException e) {
			e.printStackTrace();
		}
	}
}
