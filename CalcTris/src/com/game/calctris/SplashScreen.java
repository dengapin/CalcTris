package com.game.calctris;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;


/*This is a init main Scene*/
public class SplashScreen extends Scene{
	Sprite inicio;
	public SplashScreen(ITextureRegion tr,Engine engine) {
		// TODO Auto-generated constructor stub
		this.inicio=new Sprite(engine.getCamera().getCenterX(), engine.getCamera().getCenterY(), 
				tr, engine.getVertexBufferObjectManager()){@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					if(pSceneTouchEvent.isActionUp()){
						
					}
					return super
							.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}};
		this.attachChild(this.inicio);
	}
	
	public Sprite getInicio(){
		return inicio;
	}
}
