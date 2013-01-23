package com.game.calctris;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;



public class SceneManager {
	 //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
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
        currentScene = scene;
        currentSceneType = scene.getSceneType();
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
    public void createMenuScene()
	{
	    ResourcesManager.getInstance().loadMenuResources();
	    menuScene = new MainMenuScene();
	    SceneManager.getInstance().setScene(menuScene);
	    disposeSplashScene();
	}
    public void createHelpScene(){
    	ResourcesManager.getInstance().loadMenuResources();
	    helpScene = new HelpScene();
	    //SceneManager.getInstance().getCurrentScene().disposeScene();
	    SceneManager.getInstance().setScene(helpScene);
	    //disposeSplashScene();
    }
   
}
