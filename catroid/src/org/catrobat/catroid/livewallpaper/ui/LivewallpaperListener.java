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
import com.badlogic.gdx.Gdx;

import org.catrobat.catroid.content.Project;

/**
 * Created by Rumpfi on 15.09.2015.
 */
public class LivewallpaperListener implements ApplicationListener {

	ApplicationListener activeListener;

	public LivewallpaperListener() {
		activeListener = new SelectProjectNotificationListener(this);
	}

	@Override
	public void create() {
		if(activeListener != null) {
			activeListener.create();
		}
	}

	@Override
	public void render() {
		if (activeListener != null) {
			activeListener.render();

		}
	}

	@Override
	public void dispose() {
		if(activeListener != null)
			activeListener.dispose();
	}

	@Override
	public void resume() {
		if(activeListener != null)
			activeListener.resume();
	}

	@Override
	public void resize(int width, int height) {
		if(activeListener != null)
			activeListener.resize(width, height);
	}

	@Override
	public void pause() {
		if(activeListener != null)
			activeListener.pause();
	}

	public void setActiveListener(ApplicationListener listener) {
		activeListener = listener;
		activeListener.create();
	}
}
