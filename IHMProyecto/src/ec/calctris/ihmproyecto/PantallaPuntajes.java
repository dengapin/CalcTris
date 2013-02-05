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
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.graphics.Typeface;

public class PantallaPuntajes extends SimpleBaseGameActivity{
	
	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px
    
    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mBotones;//Arreglo de botones
    private ITextureRegion mBoton1;//BotonAtras
    
    private BitmapTextureAtlas mSonido;
    private ITextureRegion mSonidoRegionOn;
    private Music mMusic;	
    private Sound mClicButton;

    private Scene mScene;
	private org.andengine.opengl.font.Font Font;
	private org.andengine.opengl.font.Font Font2;
	private org.andengine.opengl.font.Font Font3;
	private org.andengine.opengl.font.Font Font4;
	private org.andengine.opengl.font.Font Font5;
	private org.andengine.opengl.font.Font Font6;
	private org.andengine.opengl.font.Font Font7;
	private org.andengine.opengl.font.Font Font8;
	private org.andengine.opengl.font.Font Font9;
	private org.andengine.opengl.font.Font Font10;
	private org.andengine.opengl.font.Font Font11;
	private org.andengine.opengl.font.Font Font12;
	private org.andengine.opengl.font.Font Font13;
	private org.andengine.opengl.font.Font Font14;
	private org.andengine.opengl.font.Font Font15;
	private org.andengine.opengl.font.Font Font16;
	
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

    // ============================================================
    // Method: onCreateResources
    // ============================================================
	@Override
	protected void onCreateResources() {
		
		//Obteniendo la carpeta donde estaran las imagenes
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        //Para el fondo
        this.mFondo = new BitmapTextureAtlas(this.getTextureManager(), 480, 800, TextureOptions.BILINEAR);//Arreglo donde almaceno la imagen
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaPuntajes.png", 0, 0);
        this.mFondo.load();//Cargo la imagen
        
        //Para el fondo con la nube en movimiento
        this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
        this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
        this.mNube.load();
        
        //Para los botones
        this.mBotones = new BitmapTextureAtlas(this.getTextureManager(),148, 45, TextureOptions.BILINEAR);//Arreglo para los botones iniciales
        this.mBoton1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonAtras.png", 0, 0);//BotonAtras
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
        
        //Para la letra
        this.Font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font.load();
		this.Font2 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font2.load();
		this.Font3 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font3.load();
		this.Font4 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font4.load();
		this.Font5 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font5.load();
		this.Font6 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font6.load();
		this.Font7 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font7.load();
		this.Font8 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font8.load();
		this.Font9 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font9.load();
		this.Font10 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font10.load();
		this.Font11 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font11.load();
		this.Font12 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font12.load();
		this.Font13 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font13.load();
		this.Font14 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font14.load();
		this.Font15 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font15.load();
		this.Font16 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.Font16.load();
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
        
        //Para la letra
 		final Text centerText = new Text(20, 300, this.Font, "SUMA", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText);
		final Text centerText2 = new Text(20, 340, this.Font, "NIVELES", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText2);
		final Text centerText3 = new Text(200, 340, this.Font, "1", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText3);
		final Text centerText4 = new Text(250, 340, this.Font, "2", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText4);
		final Text centerText5 = new Text(300, 340, this.Font, "3", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText5);
		final Text centerText6 = new Text(350, 340, this.Font, "4", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText6);
		final Text centerText7 = new Text(400, 340, this.Font, "5", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText7);
		final Text centerText8 = new Text(20, 380, this.Font, "PUNTAJES", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
		this.mScene.attachChild(centerText8);
		
        //Para los botones
        final Sprite boton1 = new Sprite(0, 50, this.mBoton1, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		PantallaPuntajes.this.mClicButton.play();
        		Intent intent = new Intent (PantallaPuntajes.this, ActivityProyecto.class);
        		startActivity(intent);
        		finish();
        		return true;
        	}
        };
        this.mScene.attachChild(boton1);
        //BotonSonido
        final Sprite On = new Sprite(400, 50, this.mSonidoRegionOn, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		if(pSceneTouchEvent.isActionDown()) {
					if(PantallaPuntajes.this.mMusic.isPlaying()) {
						PantallaPuntajes.this.mMusic.pause();
					} else {
						PantallaPuntajes.this.mMusic.play();
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
