package com.game.calctris;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.game.calctris.SceneManager.SceneType;

public class HelpScene extends BaseScene {

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_HELP;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		this.detachSelf();
		this.dispose();
	}
	private void createBackground(){
	    attachChild(new Sprite(0, 0, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) {
	        	// TODO Auto-generated method stub
	        	super.preDraw(pGLState, pCamera);
	        	pGLState.enableDither();
	        }
	    });
	}

}
