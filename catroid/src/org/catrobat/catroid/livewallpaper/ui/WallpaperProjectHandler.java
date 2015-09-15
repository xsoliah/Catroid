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

import android.util.Log;

import org.catrobat.catroid.content.Project;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by marco on 12.05.15.
 */
public class WallpaperProjectHandler extends Observable {

	private static WallpaperProjectHandler INSTANCE = null;
	private Project lwp_project = null;
	private ReentrantLock lock;
	public Project futureProject;

	private WallpaperProjectHandler() {
		lwp_project = null;
		lock = new ReentrantLock();
	}

	public static WallpaperProjectHandler getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new WallpaperProjectHandler();
		}

		return INSTANCE;
	}


	public void setProject(Project p) {
		lock.lock();
			setChanged();
			this.lwp_project = p;
			notifyObservers();
		lock.unlock();
	}

	public Project getProject() {
		Project p;
		lock.lock();
		p = this.lwp_project;
		lock.unlock();
		return p;
	}
}
