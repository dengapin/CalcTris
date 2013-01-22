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

public class PantallaJugar extends SimpleBaseGameActivity{
	
	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px
    
    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mBotones;//Arreglo de botones
    private ITextureRegion mBoton1;//BotonJuego Nuevo
    private ITextureRegion mBoton2;//Boton Continuar
    private ITextureRegion mBoton3;//BotonRegresar
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;

    private Scene mScene;

    // ============================================================
    // Method: onCreateEmgineOptions
    // ============================================================
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
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
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaJugar.png", 0, 0);
        this.mFondo.load();//Cargo la imagen de fondo
        
        //Para el fondo con la nube en movimiento
        this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
        this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
        this.mNube.load();
        
        //Para los botones
        this.mBotones = new BitmapTextureAtlas(this.getTextureManager(),148, 135, TextureOptions.BILINEAR);//Arreglo para los botones iniciales
        this.mBoton1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonJuegoNuevo.png", 0, 0);//BotonJuegoNuevo
        this.mBoton2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonContinuar.png", 0, 45);//BotonContinuar
        this.mBoton3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBotones, this, "BotonAtras.png", 0, 90);//BotonAtras
        this.mBotones.load();
		
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
        fondo.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0,0, this.mNubeRegion, vertexBufferObjectManager)));
        this.mScene.setBackground(fondo);
        
        //Para los botones
        //BotonJuegoNuevo
        final Sprite boton1 = new Sprite(0, CAMERA_HEIGHT - this.mBoton1.getHeight() - 390, this.mBoton1, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (PantallaJugar.this, PantallaSeleccionar.class);
        		startActivity(intent);
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton1);//Se registra el evento
        this.mScene.attachChild(boton1);
        //BotonContinuar
        final Sprite boton2 = new Sprite(0, CAMERA_HEIGHT - this.mBoton2.getHeight() - 390 + 55, this.mBoton2, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (PantallaJugar.this, PantallaGame.class);
        		startActivity(intent);
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton2);//Se registra el evento
        this.mScene.attachChild(boton2);
        //BotonAtras
        final Sprite boton3 = new Sprite(0, CAMERA_HEIGHT - this.mBoton3.getHeight() - 390 + 110, this.mBoton3, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		Intent intent = new Intent (PantallaJugar.this, ActivityProyecto.class);
        		startActivity(intent);
        		return true;
        	}
        };
        this.mScene.registerTouchArea(boton3);//Se registra el evento
        this.mScene.attachChild(boton3);
                                
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        return this.mScene;
	}

}
