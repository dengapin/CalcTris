package com.game.scenes;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import android.widget.Toast;

import com.game.calctris.BaseScene;
import com.game.input.GrowButton;
import com.game.manager.ResourcesManager;
import com.game.manager.SFXManager;
import com.game.manager.SceneManager;
import com.game.manager.SceneManager.SceneType;


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
		System.exit(0);//for totally exit of the application
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
		 // this.setPosition(pX, pY)
	}
	
	/*Create a menu background*/
	private void createBackground(){
	    attachChild(new Sprite(0, 0, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) {
	        	// TODO Auto-generated method stub
	        	super.preDraw(pGLState, pCamera);
	        	pGLState.enableDither();
	        }
	        @Override
	        protected void postDraw(GLState pGLState, Camera pCamera) {
	        	// TODO Auto-generated method stub
	        	super.postDraw(pGLState, pCamera);
	        	pGLState.disableDither();
	        }
	    });
	}

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	/*    GrowButton button=new GrowButton(0, 0, resourcesManager.play_region) {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				ResourcesManager.getInstance().mainGameActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
							Toast.makeText(ResourcesManager.getInstance().mainGameActivity, "Este es un boton", Toast.LENGTH_LONG).show();
					}
				});
			} 
		};*/
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	 //   final IMenuItem optionsMenuItem2 = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	  //  menuChildScene.attachChild(button);
	    
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
	        	//SceneManager.getInstance().createHelpScene();
	        	SFXManager.playClick(1f, 0.5f);
	        	SceneManager.getInstance().loadGameScene(engine);
	        	
	            return true;
	        case MENU_OPTIONS:
	            return true;
	        default:
	            return false;
        }
	}

}
