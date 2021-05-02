package model;

import model.card.Card;

import java.util.ArrayList;

public class Board
{
    private ZoneSlot[] playerOneMonsterZone;
    private ZoneSlot[] playerTwoMonsterZone;
    private ZoneSlot[] playerOneSpellZone;
    private ZoneSlot[] playerTwoSpellZone;
    private ArrayList<Card> playerOneGraveYard;
    private ArrayList<Card> playerTwoGraveYard;
    private ArrayList<Card> playerOneBanishedZone;		
    private ArrayList<Card> playerTwoBanishedZone;		
    private ArrayList<Card> playerOneDrawZone;
    private ArrayList<Card> playerTwoDrawZone;
    private ZoneSlot playerOneFieldZone;
    private ZoneSlot playerTwoFieldZone;

    {
        playerOneMonsterZone = new ZoneSlot[6];
        playerOneBanishedZone = new ArrayList<>();
        playerOneSpellZone = new ZoneSlot[6];
        playerTwoMonsterZone = new ZoneSlot[6];
        playerTwoBanishedZone = new ArrayList<>();  //TODO: 1 start index
        playerTwoSpellZone = new ZoneSlot[6];
        playerOneGraveYard = new ArrayList<>();
        playerTwoGraveYard = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            playerOneMonsterZone[i] = new ZoneSlot();
            playerOneSpellZone[i] = new ZoneSlot();
            playerTwoMonsterZone[i] = new ZoneSlot();
            playerTwoSpellZone[i] = new ZoneSlot();
        }
    }

    public String toString(){
        return null;
    }

    public Card getCard(String field, int number,int player){
        if (field.equals("monster")){
            if (player == 1){
                return playerOneMonsterZone[number].getCard();
            }else if (player == 2){
                return playerTwoMonsterZone[number].getCard();
            }
        }
        else {
            if (player == 1){
                return playerOneSpellZone[number].getCard();
            }else if (player == 2){
                return playerTwoSpellZone[number].getCard();
            }
        }
        return null;
    }

    public Card getCard(String field, int player){
        if (field.equals("field")){
            if (player == 1){
                return playerOneFieldZone.getCard();
            }else if (player == 2){
                return playerTwoFieldZone.getCard();
            }
        }
        return null;
    }



}
