package ec.calctris.ihmproyecto;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.hardware.SensorManager;

public class PantallaGame extends SimpleBaseGameActivity implements IAccelerationListener{

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mEsferas;
    private ITextureRegion mFondoEsfera1;
    private ITextureRegion mFondoEsfera2;
    private ITextureRegion mFondoEsfera3;
    private ITextureRegion mFondoEsfera4;
    private ITextureRegion mFondoEsfera5;
    private ITextureRegion mFondoEsfera6;
    private ITextureRegion mFondoEsfera7;
    private ITextureRegion mFondoEsfera8;
    private ITextureRegion mFondoEsfera9;
    
    private Scene mScene;
    
    private PhysicsWorld myPhysicsWorld;
    
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

	    //Para el fondo con la nube en movimiento
	    this.mNube = new BitmapTextureAtlas(this.getTextureManager(), 227, 85, TextureOptions.BILINEAR);
	    this.mNubeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mNube, this, "Nubes_pequenas.png", 0, 0);
	    this.mNube.load();
        
        //Para las esferas
		this.mEsferas = new BitmapTextureAtlas(this.getTextureManager(), 50, 450, TextureOptions.BILINEAR);
		this.mFondoEsfera1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera1.png", 0, 0);
		this.mFondoEsfera2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera2.png", 0, 50);
		this.mFondoEsfera3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera3.png", 0, 100);
		this.mFondoEsfera4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera4.png", 0, 150);
		this.mFondoEsfera5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera5.png", 0, 200);
		this.mFondoEsfera6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera6.png", 0, 250);
		this.mFondoEsfera7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera7.png", 0, 300);
		this.mFondoEsfera8 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera8.png", 0, 350);
		this.mFondoEsfera9 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera9.png", 0, 400);
		this.mEsferas.load();
	}

	@Override
	protected Scene onCreateScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        this.myPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        //El body del mundo fisico
        final Body body1;
        final Body body2;
        final Body body3;
        final Body body4;
        final Body body5;
        final Body body6;
        final Body body7;
        final Body body8;
        final Body body9;
        
        //Para el fondo
        final AutoParallaxBackground fondo = new AutoParallaxBackground(0, 0, 0, 5);
        fondo.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0, this.mFondoRegion, vertexBufferObjectManager)));
        fondo.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, 0, this.mNubeRegion, vertexBufferObjectManager)));
        mScene.setBackground(fondo);
        
        //El mundo físico
        final Rectangle pared1 = new Rectangle(0, 0, 1, CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle pared2 = new Rectangle(0, CAMERA_HEIGHT, 317, 1, vertexBufferObjectManager);
        final Rectangle pared3 = new Rectangle(317, 0, 1, CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle pared4 = new Rectangle(0, 0, CAMERA_WIDTH, 1, vertexBufferObjectManager);
        
        //Le doy texture dentro del mundo fisico
        final FixtureDef texturepared = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared1,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared2,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared3,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared4,BodyType.StaticBody,texturepared);
        
        //Creando Sprite para cada esfera
        final Sprite esf1 = new Sprite(0, 50, this.mFondoEsfera1, vertexBufferObjectManager);
        final Sprite esf2 = new Sprite(0, 100, this.mFondoEsfera2, vertexBufferObjectManager);
        final Sprite esf3 = new Sprite(0, 150, this.mFondoEsfera3, vertexBufferObjectManager);
        final Sprite esf4 = new Sprite(0, 200, this.mFondoEsfera4, vertexBufferObjectManager);
        final Sprite esf5 = new Sprite(0, 250, this.mFondoEsfera5, vertexBufferObjectManager);
        final Sprite esf6 = new Sprite(0, 300, this.mFondoEsfera6, vertexBufferObjectManager);
        final Sprite esf7 = new Sprite(100, 50, this.mFondoEsfera7, vertexBufferObjectManager);
        final Sprite esf8 = new Sprite(150, 50, this.mFondoEsfera8, vertexBufferObjectManager);
        final Sprite esf9 = new Sprite(200, 50, this.mFondoEsfera9, vertexBufferObjectManager);
        
        //Creando las esferas en el mundo físico
        body1 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf1, BodyType.DynamicBody, texturepared);
        body2 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf2, BodyType.DynamicBody, texturepared);
        body3 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf3, BodyType.DynamicBody, texturepared);
        body4 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf4, BodyType.DynamicBody, texturepared);
        body5 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf5, BodyType.DynamicBody, texturepared);
        body6 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf6, BodyType.DynamicBody, texturepared);
        body7 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf7, BodyType.DynamicBody, texturepared);
        body8 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf8, BodyType.DynamicBody, texturepared);
        body9 = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf9, BodyType.DynamicBody, texturepared);
        
        //Para interactuar
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf1,body1,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf2,body2,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf3,body3,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf4,body4,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf5,body5,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf6,body6,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf7,body7,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf8,body8,true,true));
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf9,body9,true,true));
        
        //Le hago un merge
        esf1.setUserData(body1);
        esf2.setUserData(body2);
        esf3.setUserData(body3);
        esf4.setUserData(body4);
        esf5.setUserData(body5);
        esf6.setUserData(body6);
        esf7.setUserData(body7);
        esf8.setUserData(body8);
        esf9.setUserData(body9);
        
        //Agrego las esferas al scene
        mScene.attachChild(esf1);
        mScene.attachChild(esf2);
        mScene.attachChild(esf3);
        mScene.attachChild(esf4);
        mScene.attachChild(esf5);
        mScene.attachChild(esf6);
        mScene.attachChild(esf7);
        mScene.attachChild(esf8);
        mScene.attachChild(esf9);
        
        //Se retorna
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        return mScene;
	}
	
	public void OnResumeGame(){
		super.onResumeGame();
		this.enableAccelerationSensor(this);
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}
	
	public void OnPauseGame(){
		super.onPauseGame();
		this.disableAccelerationSensor();
	}

}
