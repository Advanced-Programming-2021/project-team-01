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
    private Player playerOne;		
    private Player playerTwo;		
    private ArrayList<Card> playerOneDrawZone;		
    private ArrayList<Card> playerTwoDrawZone;
    {
        playerOneMonsterZone = new ZoneSlot[6];
        playerOneBanishedZone = new ArrayList<>();
        playerOneSpellZone = new ZoneSlot[6];
        playerTwoMonsterZone = new ZoneSlot[6];
        playerTwoBanishedZone = new ArrayList<>();
        playerTwoSpellZone = new ZoneSlot[6];
        playerOneGraveYard = new ArrayList<>();
        playerTwoGraveYard = new ArrayList<>();
    }
}
