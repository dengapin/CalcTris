package ec.calctris.ihmproyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
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
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ActivityProyecto extends SimpleBaseGameActivity implements IAccelerationListener, IOnSceneTouchListener, ContactListener{

	//Constantes
	private static final int CAMERA_WIDTH = 480; //Ancho 480px
    private static final int CAMERA_HEIGHT = 800; //Alto 800px
    public int tamarreglo = 8;

    //Variables
    private BitmapTextureAtlas mFondo;//Arreglo de fondo
    private ITextureRegion mFondoRegion;//Texture del fondo
    
    private BitmapTextureAtlas mNube;
    private ITextureRegion mNubeRegion;
    
    private BitmapTextureAtlas mBoton;//Arreglo de botones
    private ITextureRegion mPausa;//BotonJugar
    
    private BitmapTextureAtlas mEsferas;
    private ITextureRegion[] mFondoEsferas = new ITextureRegion[tamarreglo];
    private List<Sprite>mSpheres;
    
    private BitmapTextureAtlas mSonido;
    private ITextureRegion mSonidoRegionOn;
    
    private Scene mScene;
    private PhysicsWorld myPhysicsWorld;
    public Music mMusic;
    private Sound mClicButton;
    private Sound mCollision;
    
    //Fonts
    private org.andengine.opengl.font.Font mfont1;
    private org.andengine.opengl.font.Font mfont2;
    
    //Parte lógica
    public int [][] Matriz = new int[6][16];
    public int [] valores = new int [tamarreglo];
    
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
    public void onCreateResources() {
    	
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
        
        //Para los botones
        this.mBoton = new BitmapTextureAtlas(this.getTextureManager(),50, 50, TextureOptions.BILINEAR);//Arreglo para los botones iniciales
        this.mPausa = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBoton, this, "BotonPausa.png", 0, 0);
        this.mBoton.load();
        
        //Para las esferas
		this.mEsferas = new BitmapTextureAtlas(this.getTextureManager(), 50, 450, TextureOptions.BILINEAR);//450
		String ruta[] = {"Esfera1.png", "Esfera2.png", "Esfera3.png", "Esfera4.png", "Esfera5.png", "Esfera6.png", "Esfera7.png", "Esfera8.png", "Esfera9.png"};
		for(int i = 0; i < tamarreglo; i++){
			this.mFondoEsferas[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEsferas, this, ruta[i], 0, i*50);
			this.valores[i] = i;
		}
		this.mEsferas.load();
		
		//Para el boton del sonido
        this.mSonido = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
        this.mSonidoRegionOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSonido, this, "SonidoOn.png", 0, 0);
        this.mSonido.load();
        
        //Play the music
        MusicFactory.setAssetBasePath("mfx/");
        //Play the sound
  		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "SoundGame1.ogg");
			this.mMusic.setLooping(true);
			this.mClicButton = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "SoundClic.ogg");
			this.mCollision = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "SoundCollision");
		} catch (final IOException e) {
			//Debug.e("Error", e);
		}
		mMusic.play();
		
		//Font
		//Para el texto
        this.mfont1 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 25);
		this.mfont1.load();
		//Para el texto
        this.mfont2 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 25);
		this.mfont2.load();
	}

    // ============================================================
    // Method: onCreateScene
    // ============================================================
    @Override
    public Scene onCreateScene() {
    	
    	this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mScene = new Scene();
        
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        this.myPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        this.mScene.setOnSceneTouchListener(this);
             
        //Para el fondo
        final AutoParallaxBackground fondo = new AutoParallaxBackground(0, 0, 0, 5);
        fondo.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0, this.mFondoRegion, vertexBufferObjectManager)));
        fondo.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, 0, this.mNubeRegion, vertexBufferObjectManager)));
        mScene.setBackground(fondo);
        
        //Para los botones
        //BotonPausa
        final Sprite botonPause = new Sprite(370, 400, this.mPausa, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		ActivityProyecto.this.mClicButton.play();
        		Intent intent = new Intent (ActivityProyecto.this, PantallaPausa.class);
        		startActivity(intent);
        		finish();
        		return true;
        	}
        };
        mScene.registerTouchArea(botonPause);//Se registra el evento
        mScene.attachChild(botonPause);//Se lo agrega a la escena
        //BotonSonido
        final Sprite On = new Sprite(400, 50, this.mSonidoRegionOn, vertexBufferObjectManager){
        	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
        		if(pSceneTouchEvent.isActionDown()) {
					if(ActivityProyecto.this.mMusic.isPlaying()) {
						ActivityProyecto.this.mMusic.pause();
					} else {
						ActivityProyecto.this.mMusic.play();
					}
				}
				return true;
        	}
        };
        mScene.registerTouchArea(On);
        mScene.attachChild(On);
        //Para el texto de Nivel
        final Text centerText = new Text(350, 150, this.mfont1, "NIVEL 1 ", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        mScene.attachChild(centerText);
        //Para el texto de Puntajes
        final Text centerText2 = new Text(350, 200, this.mfont2, "PUNTAJE", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        mScene.attachChild(centerText2);
        
        //El mundo físico
        final Rectangle pared1 = new Rectangle(0, 0, 1, CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle pared2 = new Rectangle(0, CAMERA_HEIGHT, 317, 1, vertexBufferObjectManager);
        final Rectangle pared3 = new Rectangle(317, 0, 1, CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle pared4 = new Rectangle(0, 0, CAMERA_WIDTH, 1, vertexBufferObjectManager);
        
        //Le doy texture dentro del mundo fisico
        final FixtureDef texturepared = PhysicsFactory.createFixtureDef(0, 0f, 10.0f);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared1,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared2,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared3,BodyType.StaticBody,texturepared);
        PhysicsFactory.createBoxBody(this.myPhysicsWorld,pared4,BodyType.StaticBody,texturepared);
                    
        this.mScene.attachChild(pared1);
        this.mScene.attachChild(pared2);
        this.mScene.attachChild(pared3);
        this.mScene.attachChild(pared4);
        
        this.mScene.registerUpdateHandler(this.myPhysicsWorld);
        
        //Agregando esferas al escenario con tiempo de 5 segundos LOL
        createSpheresbyTimeHandler();
        
        //Parte logica del juego
        
        return this.mScene; 
           
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
		
		mSpheres = new ArrayList<Sprite>();//Array of Spheres
		Random number = new Random();
        final int aleatorio = number.nextInt(8);
        Random px = new Random();
        final int py = 0;
		final int num = px.nextInt(300);
		final FixtureDef textureSphere = PhysicsFactory.createFixtureDef(0, 0f, 10.0f);
		
		InicializarMatriz();
		final Sprite OneSphere = new Sprite (num, py, this.mFondoEsferas[aleatorio], this.getVertexBufferObjectManager()){
        	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		if(pSceneTouchEvent.isActionMove()){
        			this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
        		}
        		return true;
        	}
        };
        Body body = PhysicsFactory.createBoxBody(this.myPhysicsWorld, OneSphere, BodyType.DynamicBody, textureSphere);
        this.myPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(OneSphere, body,true,false));
        //OneSphere.setUserData(body);
        mSpheres.add(OneSphere);
        this.mScene.attachChild(OneSphere);
        this.mScene.registerTouchArea(OneSphere);
        this.mScene.setTouchAreaBindingOnActionMoveEnabled(true);
        /* The actual collision-checking. 
        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void reset() { }

            @Override
            public void onUpdate(final float pSecondsElapsed) {
                if(OneSphere.collidesWith(OneSphere)) {
                	ActivityProyecto.this.mCollision.play();
                }
            }
        });*/
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
	public int esta_vacia(final int x, final int y){
		
		//Verifica si esta dentro de la matriz
		if ((y<0) || (x<0)){
			return 1;
		}
		else{
			//pregunta si la celda esta libre o no libre
			if(Matriz[x][y] == -1){
				return 1;//Esta disponible
			}
			else{
				return -1;//No esta disponible
			}
		}
	}  
	
	public void OnResumeGame(){
		super.onResumeGame();
		this.enableAccelerationSensor(this);
	}
	
	public void OnPauseGame(){
		super.onPauseGame();
		this.disableAccelerationSensor();
	}

	/* =====================================
	 * Metodo para el touch en cada Sprite
	 =======================================*/
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if(this.myPhysicsWorld != null){
			if(pSceneTouchEvent.isActionMove()){
				for(int i =0; i<mSpheres.size(); i++){
					float box2d_x = (pSceneTouchEvent.getX()) / 32;
					final PhysicsConnector paddlePhysicsConnector = this.myPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(mSpheres.get(i));
					Body paddleBody = paddlePhysicsConnector.getBody();
					paddleBody.setTransform(new Vector2(box2d_x, paddleBody.getPosition().y), 0);
				}
				return true;
			}
		}
		return false;
	}
	
	/*Me Metodo que me devuelve el numero de la esfera correspondiente*/
	public int numeroEsfera(int numero){
		if(numero == 0){
			return 1;
		}
		else if (numero == 1){
			return 2;
		}
		else if(numero == 2){
			return 3;
		}
		else if (numero == 3){
			return 4;
		}
		else if (numero == 4){
			return 5;
		}
		else if (numero == 5){
			return 6;
		}
		else if (numero == 6){
			return 7;
		}
		else if (numero == 7){
			return 8;
		}
		else if (numero == 8){
			return 9;
		}
		else
			return 10;
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
