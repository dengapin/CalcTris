package ec.calctris.ihmproyecto;

import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
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
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class ActivityProyecto extends SimpleBaseGameActivity implements IAccelerationListener, IOnSceneTouchListener {

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px
    public int tamarreglo = 8;

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mEsferas;
    //private ITextureRegion[] mFondoEsferas = new ITextureRegion[tamarreglo];
    //private ITextureRegion mFondoEsferas;
    
    /* =======================================================
     * Variables que usare y me ayudarán para crear aleatoridad
       =======================================================*/
    private ITextureRegion mEsferasRegion;
    private LinkedList esferasLL;
    private LinkedList EspheresToAdd;
    
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
		this.mEsferas = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
		/*String ruta[] = {"Esfera1.png", "Esfera2.png", "Esfera3.png", "Esfera4.png", "Esfera5.png", "Esfera6.png", "Esfera7.png", "Esfera8.png", "Esfera9.png"};
		for(int i = 0; i < tamarreglo; i++){
			this.mFondoEsferas[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, ruta[i], 0, i*50);
		}*/
		//this.mFondoEsferas = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera1.png", 0, 0);
		//NEW
		this.mEsferasRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, "Esfera1.png", 0, 0);
		this.mEsferas.load();
	}

	@Override
	protected Scene onCreateScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        this.myPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        //El body del mundo fisico
        /*final Body[] body = new Body[tamarreglo];
        final Sprite[] esf = new Sprite[tamarreglo];*/
        /*final Body body;
        final Sprite sprite;*/
        //NEW
        esferasLL = new LinkedList();
        EspheresToAdd = new LinkedList();
        
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
        final FixtureDef texturepared = PhysicsFactory.createFixtureDef(0, 0.1f, 0.5f);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared1,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared2,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared3,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared4,BodyType.StaticBody,texturepared);
        
        //Creando Sprite para cada esfera, se le hace un merge y se lo integra al mundo con attachChild
        /*for (int i = 0; i<tamarreglo; i++){
        	esf[i] = new Sprite(158, 0, this.mFondoEsferas[i], vertexBufferObjectManager){
        		@Override
    			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
    				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
    				return true;
    			}
        	};
        	body[i] = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esf[i], BodyType.DynamicBody, texturepared);
        	this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esf[i],body[i],true,true));
        	esf[i].setUserData(body[i]);
        	mScene.attachChild(esf[i]);	
        }*/
        /*sprite = new Sprite(158, 0, this.mFondoEsferas, vertexBufferObjectManager){
    		public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
                if(pSceneTouchEvent.isActionDown()) {
                        //this.addFace(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                    return true;
                }
                return false;
    		}
    	};
    	body = PhysicsFactory.createBoxBody(this.myPhysicsWorld, sprite, BodyType.DynamicBody, texturepared);
    	this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite,body,true,true));
    	sprite.setUserData(body);
    	mScene.attachChild(sprite);*/
        
    	mScene.attachChild(pared1);
        mScene.attachChild(pared2);
        mScene.attachChild(pared3);
        mScene.attachChild(pared4);
        
        //Se retorna
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.mScene.registerUpdateHandler(this.myPhysicsWorld);
        createSpriteSpawnTimeHandler();
        return mScene;
	}
	
	/*
	 * MEtodo para hacer aleatoriadad 
	 */
	public void addTarget() {
	    Random rand = new Random();
	    int x = ((int)Math.random()*(317 - 20) + 20);
	    int y=0;

	    Sprite target = new Sprite(x, y, mEsferasRegion.deepCopy(), this.getVertexBufferObjectManager());
	    mScene.attachChild(target);

	    EspheresToAdd.add(target);

	}
	private void createSpriteSpawnTimeHandler() {
	    TimerHandler spriteTimerHandler;
	    float mEffectSpawnDelay = 1f;
	    spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true, new ITimerCallback() {
	        @Override
	        public void onTimePassed(TimerHandler pTimerHandler) {
	            addTarget();
	        }
	    });

	    getEngine().registerUpdateHandler(spriteTimerHandler);
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

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
}
