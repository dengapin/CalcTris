package ec.andrea.prueba;

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

public class PantallaGame extends SimpleBaseGameActivity{

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
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
        this.mFondoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mFondo, this, "PantallaGame.png", 0, 0);
        this.mFondo.load();//Cargo la imagen
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        
        //Para el fondo
        final AutoParallaxBackground fondo = new AutoParallaxBackground(0, 0, 0, 5);
        fondo.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0, this.mFondoRegion, vertexBufferObjectManager)));
        mScene.setBackground(fondo);
        return mScene;
	}

}
