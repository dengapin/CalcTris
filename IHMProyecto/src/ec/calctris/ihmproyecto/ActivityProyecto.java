package ec.calctris.ihmproyecto;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
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
    private ITextureRegion[] mFondoEsferas = new ITextureRegion[tamarreglo];
    private Scene mScene;
    
    private PhysicsWorld myPhysicsWorld;
    
    //Parte lógica
    public int Matriz[][] = new int[6][16];
    
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
		String ruta[] = {"Esfera1.png", "Esfera2.png", "Esfera3.png", "Esfera4.png", "Esfera5.png", "Esfera6.png", "Esfera7.png", "Esfera8.png", "Esfera9.png"};
		for(int i = 0; i < tamarreglo; i++){
			this.mFondoEsferas[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, ruta[i], 0, i*50);
		}
		this.mEsferas.load();
	}

	@Override
	protected Scene onCreateScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        this.myPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
                
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
        final FixtureDef texturepared = PhysicsFactory.createFixtureDef(0, 0.1f, 10.0f);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared1,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared2,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared3,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared4,BodyType.StaticBody,texturepared);
                    
        mScene.attachChild(pared1);
        mScene.attachChild(pared2);
        mScene.attachChild(pared3);
        mScene.attachChild(pared4);
        createSpheresbyTimeHandler();
        
        //Se retorna
        this.mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.mScene.registerUpdateHandler(this.myPhysicsWorld);
        return mScene;
	}
	
	/* ======================================================
	 * Metodo que añade las esferas por un lapso de 5 segundo
	 ========================================================*/
	public void createSpheresbyTimeHandler(){
		
		TimerHandler timeSpheres;
		float mEffectSpawnDelay = 5f;

		
		timeSpheres = new TimerHandler(mEffectSpawnDelay, true, new ITimerCallback(){
			@Override
            public void onTimePassed(TimerHandler pTimerHandler) {
				addSpheres();
            }
		});
		getEngine().registerUpdateHandler(timeSpheres);
	}
	
	/* =====================================================
	 * Metodo que me crea cada esfera y las agrega al mScene
	 =======================================================*/
	private void addSpheres() {
		
     	//El body del mundo fisico
        final Sprite[] esf = new Sprite[tamarreglo];
        final FixtureDef textureEsphere = PhysicsFactory.createFixtureDef(0f, 0.1f, 100.0f);
        Random number = new Random();
        final Sprite esferaElegida;
        final Body body;
		Random px = new Random();
		final int py = 0;
		final int num = px.nextInt(300);
        
        for (int i = 0; i<tamarreglo; i++){
        	esf[i] = new Sprite(num, py, this.mFondoEsferas[i], this.getVertexBufferObjectManager());
        }
        int aleatorio = number.nextInt(8);
        esferaElegida = new Sprite(num, py, this.mFondoEsferas[aleatorio], this.getVertexBufferObjectManager());
        esf[aleatorio] = esferaElegida;
        body = PhysicsFactory.createBoxBody(this.myPhysicsWorld, esferaElegida, BodyType.DynamicBody, textureEsphere);
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(esferaElegida, body, true, true));
        esferaElegida.setUserData(body);
        this.mScene.registerTouchArea(esferaElegida);
        this.mScene.attachChild(esferaElegida);
        InicializarMatriz();
        Matriz[num][py] = aleatorio;//Tengo q ponerle los valores actuales a la matriz.

    }
	
	/* ==================================================
	 * Metodo que me inicializa la matriz con el valor -1
	 ====================================================*/
	public void InicializarMatriz(){
		
		for(int i=0; i<Matriz.length; i++){
			for(int j=0; j<Matriz[i].length; j++){
				Matriz[i][j] = -1;
			}
		}
	}
	
	/* ====================================================================================
	 * Metodo boleano que verifica si esta en el borde y si esta disponible la celda actual
	 ======================================================================================*/
	public int esta_vacia(final int x, final int y, int [][] Matriz){
		
		//Verifica si esta dentro de la matriz
		if ((y<0) || (x<0)){
			return 1;
		}
		else{
			//pregunta si la celda esta libre o no libre
			if(Matriz[x][y] == -1){
				return 1;
			}
			else{
				return -1;
			}
		}
	}  
	
	/* ===========================================
	 * Metodo boleano para las posiciones actuales
	 =============================================*/
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY, Sprite esfera){
		if(pSceneTouchEvent.isActionDown()){
			esfera.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
        }
	   return true;
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
