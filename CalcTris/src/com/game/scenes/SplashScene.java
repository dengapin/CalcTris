package com.game.scenes;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.game.calctris.BaseScene;
import com.game.manager.SceneManager.SceneType;

/**
 * @author Mateusz Mysliwiec
 * @author www.matim-dev.com
 * @version 1.0
 */
public class SplashScene extends BaseScene{
	private Sprite splash;
	
		@Override
		public void createScene() {
			// TODO Auto-generated method stub
			splash = new Sprite(0, 0, resourcesManager.splash_region, vbom)
			{
			    @Override
			    protected void preDraw(GLState pGLState, Camera pCamera) {
			    	// TODO Auto-generated method stub
			    	super.preDraw(pGLState, pCamera);
			    	pGLState.enableDither();
			    }
			};
			splash.setScale(1.0f);
			splash.setPosition(0, 0);
			attachChild(splash);
		}
		
		@Override
		public void onBackKeyPressed() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SceneType getSceneType() {
			// TODO Auto-generated method stub
			return SceneType.SCENE_SPLASH;
		}
		
		@Override
		public void disposeScene() {
			// TODO Auto-generated method stub
			splash.detachSelf();
		    splash.dispose();
		    this.detachSelf();
		    this.dispose();
		}
	  
}
