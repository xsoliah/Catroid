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
import org.catrobat.catroid.stage.StageListener;


/**
 * Created by marco on 17.03.15.
 */
public class LiveWallpaperService extends AndroidLiveWallpaperService {

	private ApplicationListener stageListener = null;

	@Override
	public void onCreateApplication() {
		//stageListener = new StageListener();
		stageListener = new SelectProjectNotificationListener();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//ProjectManager.getInstance().initializeDefaultProject(this.getBaseContext());
		this.initialize(stageListener, config);
		Log.d("LWP|LWPService", "LWP onCreateApplication() called");
	}

	public void stopWallpaper() {

	}

	public void startWallpaper() {

	}

}
