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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class PantallaTutorial extends SimpleBaseGameActivity{

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
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
	protected void onCreateResources() {

		//Obteniendo la carpeta donde estaran las imagenes
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        //Para el fondo
        this.mFondo = new BitmapTextureAtlas(this.getTextureManager(), 480, 800, TextureOptions.BILINEAR);//Arreglo donde almaceno la imagen
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaTutorial.png", 0, 0);
        this.mFondo.load();//Cargo la imagen
        
        //Para el fondo con la nube en movimiento
        this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
        this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
        this.mNube.load();
        
        //Para el boton del sonido
        this.mSonido = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
        this.mSonidoRegionOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSonido, this, "SonidoOn.png", 0, 0);
        this.mSonido.load();
        
	}

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
        //BotonSonido
        final Sprite On = new Sprite(400, 50, this.mSonidoRegionOn, vertexBufferObjectManager);
        mScene.registerTouchArea(On);
        mScene.attachChild(On);
                
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        return this.mScene;
	}

}
