package com.game.calctris;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.game.calctris.SceneManager.SceneType;



public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);//para salir totalmente de la aplicacion
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		 this.detachSelf();
		  this.dispose();
	}
	
	/*Create a menu background*/
	private void createBackground(){
	    attachChild(new Sprite(0, 50, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) {
	        	// TODO Auto-generated method stub
	        	super.preDraw(pGLState, pCamera);
	        	pGLState.enableDither();
	        }
	    });
	}

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() );
	    optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY() );
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		// TODO Auto-generated method stub
		switch(arg1.getID())
        {
	        case MENU_PLAY:
	            return true;
	        case MENU_OPTIONS:
	            return true;
	        default:
	            return false;
        }
	}

}
