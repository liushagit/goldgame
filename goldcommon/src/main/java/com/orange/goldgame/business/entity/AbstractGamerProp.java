package com.orange.goldgame.business.entity;

public class AbstractGamerProp {

    public AbstractGamerProp(){
        reset();
    }
    
	private boolean useBanCard;
	private int useNcard;
	public boolean isUseBanCard() {
		return useBanCard;
	}
	public void setUseBanCard(boolean useBanCard) {
		this.useBanCard = useBanCard;
	}
	public int getUseNcard() {
		return useNcard;
	}
	public void setUseNcard(int useNcard) {
		this.useNcard = useNcard;
	}
	
	public void reset(){
	    useBanCard = false;
	    useNcard = 1;
	}
}
