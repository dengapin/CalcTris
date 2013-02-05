package com.game.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.R.menu;

import com.game.calctris.BaseScene;
import com.game.scenes.GameScene;
import com.game.scenes.HelpScene;
import com.game.scenes.LoadingScene;
import com.game.scenes.MainMenuScene;
import com.game.scenes.SplashScene;



public class SceneManager {
	 //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    private SceneType previousSceneType=null;
    
    private BaseScene previousScene;
    private BaseScene currentScene;
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene helpScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType
    {
    	SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_HELP,
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        if(scene.getSceneType()!=SceneType.SCENE_LOADING){
        	currentScene = scene;
        	currentSceneType = scene.getSceneType();
        }
    }
    
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_HELP:
            	setScene(helpScene);
            	break;
            default:
                break;
        }
    }
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    public BaseScene getPreviousScene(){
    	return previousScene;
    }
    public SceneType getPreviousSceneType(){
    	return previousSceneType;
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    /**
     * Create the Menu Scene
     */
    public void createMenuScene()
	{
    	//load resources for the menu if don't already loaded
    	//if(!ResourcesManager.getInstance().isLoadedMenuResources())
    		ResourcesManager.getInstance().loadMenuResources();
    	//create Menu Scene
	    menuScene = new MainMenuScene();
	   //leave loading the scene for later
	    loadingScene = new LoadingScene();
	    //show the Menu Scene
	    SceneManager.getInstance().setScene(menuScene);
	    disposeSplashScene();
	}
    /**
     * Create the Help Scene
     */
    public void createHelpScene(){
    	//if not loaded resources for the Help Scene
    	if(!ResourcesManager.getInstance().isLoadedHelpResources())
    		ResourcesManager.getInstance().loadHelpResources();
    	//create the help Scene
	    helpScene = new HelpScene();
	    previousScene=SceneManager.getInstance().getCurrentScene();//i believe that this i wont need more
	    //show the Help Scene
	    SceneManager.getInstance().setScene(helpScene);
    }
    
    public void createGameScene(){
    	
    }
    public void loadGameScene(final Engine mEngine)
    {
        //setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
        	@Override
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
               menuScene. registerEntityModifier(new MoveModifier(0.75f, 0, -480, 0, 0));
                gameScene = new GameScene();
               // loadingScene.disposeScene();
                setScene(gameScene);
            }
        }));
    }
    public void loadMenuScene(final Engine mEngine)
    {	 
        setScene(loadingScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }
   
}
