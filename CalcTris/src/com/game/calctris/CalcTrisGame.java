package com.game.calctris;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
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
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera camera;
	private Scene splashScene,mainScene;
	
	private BitmapTextureAtlas splashTextureAtlas,buttonTexture;
	private ITextureRegion splashTextureRegion,buttonRegion;
	private Sprite splash;
	private enum SceneType
	{
		SPLASH,
		MAIN,
		OPTIONS,
		WORLD_SELECTION,
		LEVEL_SELECTION,
		CONTROLLER
	}
	private SceneType currentScene = SceneType.SPLASH;
	//Create EngineOptions
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		this.camera=new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true,ScreenOrientation.LANDSCAPE_SENSOR,
				new FillResolutionPolicy(),camera);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback arg0)
			throws Exception {
		// TODO Auto-generated method stub
		//set base path to the Images
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
        splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, this, "splash.png", 0, 0);
        splashTextureAtlas.load();
        
        buttonTexture=new BitmapTextureAtlas(this.getTextureManager(), 200, 50);
        buttonRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonTexture, this, "menu_quit.png",0,0);
        buttonTexture.load();
        
		arg0.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback arg0) throws Exception {
		// TODO Auto-generated method stub
		initSplashScene();
		arg0.onCreateSceneFinished(this.splashScene);
	}
	/**/
	@Override
	public void onPopulateScene(Scene arg0, OnPopulateSceneCallback arg1)
			throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(5f, new ITimerCallback(){
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                loadResources();
                loadScenes();
                splash.detachSelf();
                mEngine.setScene(mainScene);
                currentScene = SceneType.MAIN;
            }
		}));
		arg1.onPopulateSceneFinished();
	}
	/**/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK   && event.getAction() == KeyEvent.ACTION_DOWN)
	    {	    	
	    	switch (currentScene)
	    	{
	    		case SPLASH:
	    			break;
	    		case MAIN:
	    			System.exit(0);
	    			break;
	    	}
	    }
	    return false; 
	}
	/*Load Resources*/
	private void loadResources(){
		
	}

	private void loadScenes()
	{
		// load your game here, you scenes
		mainScene = new SplashScreen(buttonRegion,this.mEngine);
		mainScene.setBackground(new Background(0, 241, 231));
		//mainScene.attachChild(pEntity)
	}
	private void initSplashScene()
	{
    	splashScene = new Scene();
    	splash = new Sprite(0, 0, splashTextureRegion, mEngine.getVertexBufferObjectManager())
    	{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
    	};
    	
    	splash.setScale(1.5f);
    	splash.setPosition((CAMERA_WIDTH - splash.getWidth()) * 0.5f, (CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
    	splashScene.attachChild(splash);
	}
}
