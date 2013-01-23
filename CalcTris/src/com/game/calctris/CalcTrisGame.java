package com.game.calctris;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import android.view.KeyEvent;


/**
 * @author Matim Development
 * @version 1.0.0
 * <br><br>
 * https://sites.google.com/site/matimdevelopment/
 */
public class CalcTrisGame extends BaseGameActivity
{
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	private Camera camera;
	//private BaseScene splashScene,menuScene;
	

	@SuppressWarnings("unused")
	private ResourcesManager resourcesManager;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// TODO Auto-generated method stub
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	//Create EngineOptions
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		this. camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		    return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback arg0)
			throws Exception {
		// TODO Auto-generated method stub
		//set base path to the Images
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    resourcesManager = ResourcesManager.getInstance();
		arg0.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback arg0) throws Exception {
		// TODO Auto-generated method stub
		SceneManager.getInstance().createSplashScene(arg0);
	}
	/**/
	@Override
	public void onPopulateScene(Scene arg0, OnPopulateSceneCallback arg1)
			throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	                mEngine.unregisterUpdateHandler(pTimerHandler);
	                // load menu resources, create menu scene
	                SceneManager.getInstance().createMenuScene();
	                // set menu scene using scene manager
	                // disposeSplashScene();
	                // READ NEXT ARTICLE FOR THIS PART.
	            }
	    }));
		arg1.onPopulateSceneFinished();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}
}
