package ec.calctris.ihmproyecto;

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

public class TestActivity extends SimpleBaseGameActivity {

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mBotones;//Arreglo de botones
    private ITextureRegion mBoton1;//BotonJugar
    private ITextureRegion mBoton2;//BotonPuntajes
    private ITextureRegion mBoton3;//BotonAyuda
    
    private BitmapTextureAtlas mSonido;
    private ITextureRegion mSonidoRegionOn;

    private Scene mScene;

    
    // ============================================================
    // Method: onCreateEmgineOptions
    // ============================================================
    @Override
    public EngineOptions onCreateEngineOptions() {
        
    	final Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
        return engineOptions;        
    }

    // ============================================================
    // Method: onCreateResources
    // ============================================================
    @Override
    public void onCreateResources() {
    	
    	//Obteniendo la carpeta donde estaran las imagenes
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        //Para el fondo
        this.mFondo = new BitmapTextureAtlas(this.getTextureManager(), 480, 800, TextureOptions.BILINEAR);//Arreglo donde almaceno la imagen
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaInicial.png", 0, 0);
        this.mFondo.load();//Cargo la imagen
        
        //Para el fondo con la nube en movimiento
        this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
        this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
        this.mNube.load();
        
        //Para los botones
        this.mBotones = new BitmapTextureAtlas(this.getTextureManager(),148, 135, TextureOptions.BILINEAR);//Arreglo para los botones iniciales
        this.mBoton1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonJugar.png", 0, 0);
        this.mBoton2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonPuntajes.png", 0, 45);
        this.mBoton3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonAyuda.png", 0, 90);
        this.mBotones.load();
        
        //Para el boton del sonido
        this.mSonido = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
        this.mSonidoRegionOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSonido, this, "SonidoOn.png", 0, 0);
        this.mSonido.load();
        
    }

    // ============================================================
    // Method: onCreateScene
    // ============================================================
    @Override
    public Scene onCreateScene() {
        
    	this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        
        //Para el fondo
        final AutoParallaxBackground fondo = new AutoParallaxBackground(0, 0, 0, 5);
        fondo.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0, this.mFondoRegion, vertexBufferObjectManager)));
        fondo.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, 0, this.mNubeRegion, vertexBufferObjectManager)));
        mScene.setBackground(fondo);
        
        //Para los botones
        //BotonJugar
        final Sprite boton1 = new Sprite(0, CAMERA_HEIGHT - this.mBoton1.getHeight() - 390, this.mBoton1, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (TestActivity.this, PantallaJugar.class);
        		startActivity(intent);
        		return true;
        	}
        };
        mScene.registerTouchArea(boton1);//Se registra el evento
        mScene.attachChild(boton1);
        //JugarPuntajes
        final Sprite boton2 = new Sprite(0, CAMERA_HEIGHT - this.mBoton2.getHeight() - 390 + 55, this.mBoton2, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (TestActivity.this, PantallaPuntajes.class);
        		startActivity(intent);
        		return true;
        	}
        };
        mScene.registerTouchArea(boton2);
        mScene.attachChild(boton2);
        //BotonAyuda
        final Sprite boton3 = new Sprite(0, CAMERA_HEIGHT - this.mBoton3.getHeight() - 390 + 110, this.mBoton3, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (TestActivity.this, PantallaAyuda.class);
        		startActivity(intent);
        		return true;
        	}
        };
        mScene.registerTouchArea(boton3);
        mScene.attachChild(boton3);
        //BotonSonido
        final Sprite On = new Sprite(400, 50, this.mSonidoRegionOn, vertexBufferObjectManager);
        mScene.registerTouchArea(On);
        mScene.attachChild(On);
                                                
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        return mScene;
    }

}
