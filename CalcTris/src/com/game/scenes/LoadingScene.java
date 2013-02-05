/**
 * 
 */
package com.game.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.game.calctris.BaseScene;
import com.game.manager.SceneManager.SceneType;

/**
 * @author Efrain Astudillo
 *
 */
public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(210, 400, resourcesManager.font, "Loading...", vbom));
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		//this.dispose();
	}

}
