package ec.calctris.ihmproyecto;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Intent;

public class PantallaPausa extends SimpleBaseGameActivity{

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mBotones;//Arreglo de botones
    private ITextureRegion mBoton1;//BotonContinuar
    private ITextureRegion mBoton2;//BotonNuevaPartida
    private ITextureRegion mBoton3;//BotonTutorial
    private ITextureRegion mBoton4;//BotonHome

    private BitmapTextureAtlas mSonido;
    private ITextureRegion mSonidoRegionOn;
    
    private Scene mScene;
    
    public Music mMusic;
    private Sound mClicButton;
    
    // ============================================================
    // Method: onCreateEmgineOptions
    // ============================================================
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		final Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions; 
	}

	@Override
	protected void onCreateResources() {

		//Obteniendo la carpeta donde estaran las imagenes
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        //Para el fondo
        this.mFondo = new BitmapTextureAtlas(this.getTextureManager(), 480, 800, TextureOptions.BILINEAR);//Arreglo donde almaceno la imagen
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaPausa.png", 0, 0);
        this.mFondo.load();//Cargo la imagen        
        
        //Para el fondo con la nube en movimiento
        this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
        this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
        this.mNube.load();
        
        //Para los botones
        this.mBotones = new BitmapTextureAtlas(this.getTextureManager(),148, 180, TextureOptions.BILINEAR);//Arreglo para los botones iniciales
        this.mBoton1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonContinuar.png", 0, 0);//BotonContinuar
        this.mBoton2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonJuegoNuevo.png", 0, 45);//BotonNuevaPartida
        this.mBoton3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonTutorial.png", 0, 90);//BotonTutorial
        this.mBoton4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonHome.png", 0, 135);//BotonHome
        this.mBotones.load();
        
        //Para el boton del sonido
        this.mSonido = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
        this.mSonidoRegionOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSonido, this, "SonidoOn.png", 0, 0);
        this.mSonido.load();
        
        //Play the music
        MusicFactory.setAssetBasePath("mfx/");
        //Play the sound
  		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "MusicaFondo.ogg");
			this.mMusic.setLooping(true);
			this.mClicButton = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "SoundClic.ogg");
		} catch (final IOException e) {
			//Debug.e("Error", e);
		}
		mMusic.play();
	}

	// ============================================================
    // Method: onCreateScene
    // ============================================================
	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        
        //Para el fondo
        final AutoParallaxBackground fondo = new AutoParallaxBackground(0, 0, 0, 5);
        fondo.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0, this.mFondoRegion, vertexBufferObjectManager)));
        fondo.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, 0, this.mNubeRegion, vertexBufferObjectManager)));
        this.mScene.setBackground(fondo);
        
       
        //Para los botones
        //BotonContinuar
        final Sprite boton1 = new Sprite(0, CAMERA_HEIGHT - this.mBoton1.getHeight() - 390, this.mBoton1, vertexBufferObjectManager){
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
        		PantallaPausa.this.mClicButton.play();
        		Intent intent = new Intent (PantallaPausa.this, PantallaGame.class);
        		startActivity(intent);
        		PantallaPausa.this.mMusic.stop();
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton1);//Se registra el evento
        this.mScene.attachChild(boton1);
        //BotonNuevaPartida
        final Sprite boton2 = new Sprite(0, CAMERA_HEIGHT - this.mBoton2.getHeight() - 390 + 55, this.mBoton2, vertexBufferObjectManager){
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
        		PantallaPausa.this.mClicButton.play();
        		Intent intent = new Intent (PantallaPausa.this, PantallaSeleccionar.class);
        		startActivity(intent);
        		finish();
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton2);
        this.mScene.attachChild(boton2);
        //BotonTutorial
        final Sprite boton3 = new Sprite(0, CAMERA_HEIGHT - this.mBoton3.getHeight() - 390 + 110, this.mBoton3, vertexBufferObjectManager){
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
        		PantallaPausa.this.mClicButton.play();
        		Intent intent = new Intent (PantallaPausa.this, PantallaTutorial.class);
        		startActivity(intent);
        		finish();
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton3);
        this.mScene.attachChild(boton3);
        //BotonHome
        final Sprite boton4 = new Sprite(0, CAMERA_HEIGHT - this.mBoton4.getHeight() - 390 + 165, this.mBoton4, vertexBufferObjectManager){
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
        		PantallaPausa.this.mClicButton.play();
        		Intent intent = new Intent (PantallaPausa.this, ActivityProyecto.class);
        		startActivity(intent);
        		finish();
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton4);
        this.mScene.attachChild(boton4);
        //BotonSonido
        final Sprite On = new Sprite(400, 50, this.mSonidoRegionOn, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		if(pSceneTouchEvent.isActionDown()) {
					if(PantallaPausa.this.mMusic.isPlaying()) {
						PantallaPausa.this.mMusic.pause();
					} else {
						PantallaPausa.this.mMusic.play();
					}
				}
				return true;
        	}
        };
        mScene.registerTouchArea(On);
        mScene.attachChild(On);
                                        
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        return this.mScene;
	}

}
