package com.game.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

public class SFXManager {
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static  SFXManager INSTANCE ;
	
	private static Music mMusic;
	
	private static Sound mClick;
	private static Sound mVoila;
	
	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static SFXManager getInstance(){
		if(INSTANCE==null){
			return new SFXManager();
		}else{
			return INSTANCE;
		}
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public boolean mSoundsMuted;
	public boolean mMusicMuted;
	
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public SFXManager() {
		MusicFactory.setAssetBasePath("mfx/");
		try {
			mMusic = MusicFactory.createMusicFromAsset(ResourcesManager.getInstance().mainGameActivity.getMusicManager(), ResourcesManager.getInstance().mainGameActivity, "FullOn.mp3");
			mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		SoundFactory.setAssetBasePath("sfx/");
		try {
			mClick = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().mainGameActivity.getSoundManager(), ResourcesManager.getInstance().mainGameActivity, "click.mp3");
		} catch (final IOException e) { Debug.e(e); }
		try {
			mVoila = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().mainGameActivity.getSoundManager(),ResourcesManager.getInstance().mainGameActivity, "voila.mp3");
		} catch (final IOException e) { Debug.e(e); }

	}
	
	// ====================================================
	// METHODS
	// ====================================================
	private static void setVolumeForAllSounds(final float pVolume) {
		mClick.setVolume(pVolume);
		mVoila.setVolume(pVolume);
	}
	
	public static boolean isSoundMuted() {
		return getInstance().mSoundsMuted;
	}
	
	public static void setSoundMuted(final boolean pMuted) {
		getInstance().mSoundsMuted = pMuted;
		setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
	}
	
	public static boolean toggleSoundMuted() {
		getInstance().mSoundsMuted = !getInstance().mSoundsMuted;
		setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
		return getInstance().mSoundsMuted;
	}
	
	public static boolean isMusicMuted() {
		return getInstance().mMusicMuted;
	}
	
	public static void setMusicMuted(final boolean pMuted) {
		getInstance().mMusicMuted = pMuted;
		if(getInstance().mMusicMuted) mMusic.pause(); else mMusic.play();
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
	}
	
	public static boolean toggleMusicMuted() {
		getInstance().mMusicMuted = !getInstance().mMusicMuted;
		if(getInstance().mMusicMuted) mMusic.pause(); else mMusic.play();
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
		return getInstance().mMusicMuted;
	}
	
	
	public static void playMusic() {
		if(!isMusicMuted())
			mMusic.play();
	}
	
	public static void pauseMusic() {
		mMusic.pause();
	}
	
	public static void resumeMusic() {
		if(!isMusicMuted())
			mMusic.resume();
	}
	
	public static float getMusicVolume() {
		return mMusic.getVolume();
	}
	
	public static void setMusicVolume(final float pVolume) {
		mMusic.setVolume(pVolume);
	}
	
	public static void playClick(final float pRate, final float pVolume) {
		playSound(mClick,pRate,pVolume);
	}
	
	public static void playVoila(final float pRate, final float pVolume) {
		playSound(mVoila,pRate,pVolume);
	}
	
	private static void playSound(final Sound pSound, final float pRate, final float pVolume) {
		if(SFXManager.isSoundMuted()) return;
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.play();
	}
}
