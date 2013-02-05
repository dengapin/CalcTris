package com.game.manager;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;

import com.game.calctris.CalcTrisGame;


public class ResourcesManager {
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE =new ResourcesManager();
    //variables for the easy access in our game
    public Engine engine;
    public CalcTrisGame mainGameActivity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public Music music;
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;
    //background of the scenes
    public ITextureRegion menu_background_region;
    public ITextureRegion help_background_region;
    public ITextureRegion game_background_region;
    public ITextureRegion options_background_region;
    public ITextureRegion como_jugar_background_region;
    
    public ITextureRegion play_region;
    public ITextureRegion options_region;
    
    //
    private BitmapTextureAtlas menuTextureAtlas;
    private BuildableBitmapTextureAtlas helpTextureAtlas;
    @SuppressWarnings("unused")
	private BitmapTextureAtlas gameTextureAtlas;
    @SuppressWarnings("unused")
    private BuildableBitmapTextureAtlas optionsTextureAtlas;
    @SuppressWarnings("unused")
    private BuildableBitmapTextureAtlas comoJugarTextureAtlas;
    
    //-----------------------------------------------------------------------------
    //  FONTS
    //--------------------------------------------------------------------------------
    public Font font;
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    /**
     *  For minimize time processing and memory space not 
     *  overload once again if already loaded
     * @return true if resources loaded,  otherwise return false
     */
    public boolean isLoadedMenuResources(){
    	if(this.menuTextureAtlas!=null )
    		return this.menuTextureAtlas.isLoadedToHardware();
    	else
    		return false;
    }
    /**
     *  For minimize time processing and memory space not 
     *  overload once again if already loaded
     * @return true if resources loaded,  otherwise return false
     */
    public boolean isLoadedHelpResources(){
    	if(this.helpTextureAtlas!=null)
    		return this.helpTextureAtlas.isLoadedToHardware();
    	else
    			return false;
    }
  
    /**
     * 		Load Menu Resources of the Scene
     */
    public void loadMenuResources()
    {
    	loadMenuGraphics();
    	loadMenuFonts();
      //  loadMenuAudio();
    }
   
	/**
     *  	Load Game Resources of the Scene
     */
    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
       // loadGameAudio();
    }
    /**
     *  	Load Help Resources of the Scene
     */
    public void loadHelpResources(){
    	loadHelpGraphics();
    }
    
    //===================================================
    //		PRIVATE FUNCTIONS
    //===================================================
    private void loadHelpGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
    	helpTextureAtlas = new BuildableBitmapTextureAtlas(mainGameActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	help_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, mainGameActivity, "fondoAP.png");
    	helpTextureAtlas.load();
    }
    //========================================
    //		********		RESOURCES FOR MENU SCREEN		*************
    //========================================
    private void loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BitmapTextureAtlas(mainGameActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, mainGameActivity, "base.png",10,10);
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, mainGameActivity, "play.png",500,10);
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, mainGameActivity, "options.png",500,120);
    	 this.menuTextureAtlas.load();
    }
    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    public void unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }
    /**
     * 		Load Fonts Menu
     */
    private void loadMenuFonts() {
    	FontFactory.setAssetBasePath("ffx/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(mainGameActivity.getTextureManager(),
        		256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(mainGameActivity.getFontManager(), 
        		mainFontTexture, mainGameActivity.getAssets(), 
        		"font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
	}
   
    //=====================================
    //		*******	RESOURCES FOR GAME SCREEN		************
    //=====================================
    /**
     * 		Load Game Graphics
     */
    private void loadGameGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	gameTextureAtlas = new BitmapTextureAtlas(mainGameActivity.getTextureManager(), 512, 1024, TextureOptions.BILINEAR);
    	game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, "base.png",10,10);
    	 this.gameTextureAtlas.load();
    }
    /**
     * 		Load Game Fonts
     */
    private void loadGameFonts()
    {
        
    }
    public void unloadGameTextures()
    {
        
    }
    //========================================
    //		********		RESOURCES FOR SPLASH SCREEN		*************
    //========================================
    /**
     *  	Load resources (images) for SplashScreen 
     */
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
    	splashTextureAtlas = new BitmapTextureAtlas(mainGameActivity.getTextureManager(), 480, 800, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, mainGameActivity, "ca.png", 0, 0);
    	splashTextureAtlas.load();
    }
    /**
     * 		Unload Splash Screen
     */
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;
    }
    //  ///////////////////////////////////////////////////////////////////////////////////////////////////		\\
   //	 \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\		//
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, CalcTrisGame activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().mainGameActivity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    /**
     * This is an implementation of the Singleton Design Pattern
     * @return an Instance of the ResourcesManager 
     */
    public static ResourcesManager getInstance()
    {
    			return INSTANCE;
    	
    }
}
