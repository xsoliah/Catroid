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
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


/**
 * Created by marco on 17.03.15.
 */
public class LiveWallpaperService extends AndroidLiveWallpaperService {

	@Override
	public void onCreate() {
		Log.d("LWP|LiveWallpaperServ", "LiveWallpaperService.onCreate()!");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d("LWP|LiveWallpaperServ", "LiveWallpaperService.onDestroy()!");
		super.onDestroy();
	}

	@Override
	public void offsetChange (ApplicationListener listener, float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
	}

	@Override
	public AndroidApplicationConfiguration createConfig () {
		return new AndroidApplicationConfiguration();
	}

	@Override
	public ApplicationListener createListener(boolean bool) {
		return new ApplicationListener() {
			@Override
			public void create() {

			}

			@Override
			public void resize(int i, int i2) {

			}

			@Override
			public void render() {

			}

			@Override
			public void pause() {

			}

			@Override
			public void resume() {

			}

			@Override
			public void dispose() {

			}
		};

	}

	/*@Override
	public void previewStateChange (boolean isPreview) {
	}*/

	@Override
	public Engine onCreateEngine() {
		return new LiveWallpaperEngine();
	}


	public class LiveWallpaperEngine extends AndroidWallpaperEngine {

		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}

		};
		private List<MyPoint> circles;
		private Paint paint = new Paint();
		private int width;
		int height;
		private boolean visible = true;
		private int maxNumber;
		private boolean touchEnabled;

		public LiveWallpaperEngine() {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(LiveWallpaperService.this);
			/*maxNumber = Integer
					.valueOf(prefs.getString("numberOfCircles", "4"));
			touchEnabled = prefs.getBoolean("touch", false);*/
			maxNumber = 10;
			touchEnabled = false;
			circles = new ArrayList<MyPoint>();
			paint.setAntiAlias(true);
			paint.setColor(Color.WHITE);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(10f);
			handler.post(drawRunner);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			this.width = width;
			this.height = height;
			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			if (touchEnabled) {

				float x = event.getX();
				float y = event.getY();
				SurfaceHolder holder = getSurfaceHolder();
				Canvas canvas = null;
				try {
					//canvas = holder.lockCanvas();
					if (canvas != null) {
						canvas.drawColor(Color.BLACK);
						circles.clear();
						circles.add(new MyPoint(String.valueOf(circles.size() + 1), (int)x, (int)y));
						drawCircles(canvas, circles);

					}
				} finally {
					if (canvas != null) {}
						//holder.unlockCanvasAndPost(canvas);
				}
				super.onTouchEvent(event);
			}
		}

		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					if (circles.size() >= maxNumber) {
						circles.clear();
					}
					int x = (int) (width * Math.random());
					int y = (int) (height * Math.random());
					circles.add(new MyPoint(String.valueOf(circles.size() + 1),
							x, y));
					drawCircles(canvas, circles);
				}
			} finally {
				if (canvas != null) {}
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 5000);
			}
		}

		private void drawCircles(Canvas canvas, List<MyPoint> circles) {
			canvas.drawColor(Color.BLACK);
			for (MyPoint point : circles) {
				canvas.drawCircle(point.x, point.y, 20.0f, paint);
			}
		}


	}
}
