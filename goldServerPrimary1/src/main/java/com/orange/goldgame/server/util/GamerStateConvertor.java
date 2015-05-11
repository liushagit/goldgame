package com.orange.goldgame.server.util;

import com.orange.goldgame.server.emun.GamerState;


public class GamerStateConvertor {
	public static Byte converterToByte(GamerState state) {
		byte gamePlayerState = 0;
		if(state==GamerState.GAME_NOREADY){
			gamePlayerState = 0;
		}else if(state==GamerState.GAME_READY){
			gamePlayerState = 1;
		}else if(state==GamerState.GAME_GIVEUP){
			gamePlayerState = 2;
		}else if(state==GamerState.GAME_LOOK){
			gamePlayerState = 3;
		}else if(state==GamerState.GAME_LOSE){
			gamePlayerState = 4;
		}else if(state==GamerState.GAME_WIN){
			gamePlayerState = 5;
		}
		return gamePlayerState;
	}
	public static GamerState converterToEmun(byte state) {
		GamerState gamePlayerState = GamerState.GAME_NOREADY ;
		if(state==0){
			gamePlayerState = GamerState.GAME_NOREADY;
		}else if(state==1){
			gamePlayerState = GamerState.GAME_READY;
		}else if(state==2){
			gamePlayerState = GamerState.GAME_GIVEUP;
		}else if(state==3){
			gamePlayerState = GamerState.GAME_LOOK;
		}else if(state==4){
			gamePlayerState = GamerState.GAME_LOSE;
		}else if(state==5){
			gamePlayerState = GamerState.GAME_WIN;
		}
		return gamePlayerState;
	}
}
