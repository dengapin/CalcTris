package com.game.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.game.calctris.BaseScene;
import com.game.manager.SceneManager;
import com.game.manager.SceneManager.SceneType;

public class GameScene extends BaseScene{
	private HUD gameHUD;
	private Text scoreText;
	private PhysicsWorld physicsWorld;
	private int score = 0;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createHUD();
	    createPhysics();
	}
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		 camera.setHUD(null);
		 camera.setCenter(240, 400);
	}
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		//SceneManager.getInstance().setScene(SceneManager.getInstance().getPreviousScene());
		SceneManager.getInstance().loadMenuScene(engine);
	}
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}
	
	//-------------------------------------------------------------------------------------------------
	private void createBackground()
	{
		
		 attachChild(new Sprite(480, 0, resourcesManager.game_background_region, vbom)
		    {
			 boolean hasloaded = false;
		        @Override
		        protected void preDraw(GLState pGLState, Camera pCamera) {
		        	// TODO Auto-generated method stub
		        	super.preDraw(pGLState, pCamera);
		        	pGLState.enableDither();
		        }
		        @Override
		        protected void onManagedUpdate(float pSecondsElapsed) {
		        	// TODO Auto-generated method stub
		        	super.onManagedUpdate(pSecondsElapsed);
		        	if(!this.hasloaded){
		        		hasloaded=true;
		        		this.registerEntityModifier(new MoveModifier(0.75f, 480, 0, 0, 0));
		        	}
		        }
		    });
		 		
	}
	private void createHUD()
	{
	    gameHUD = new HUD();
	 // CREATE SCORE TEXT
	    scoreText = new Text(10, 10, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT ), vbom);
	   
	    scoreText.setText("Score: 0");
	    gameHUD.attachChild(scoreText);
	    camera.setHUD(gameHUD);
	}
	private void createPhysics(){
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0.0f, -17.0f), false); 
	    registerUpdateHandler(physicsWorld);
	}
	@SuppressWarnings("unused")
	private void addToScore(int i)
	{
	    score += i;
	    scoreText.setText("Score: " + score);
	}
}
