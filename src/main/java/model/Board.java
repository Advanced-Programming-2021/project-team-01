package model;

import model.card.Card;

import java.util.ArrayList;

public class Board
{
    private FieldZoneSlot playerOneFieldZone;		
    private FieldZoneSlot playerTwoFieldZone;		
    private ArrayList<Card> playerOneGraveYard;
    private ArrayList<Card> playerTwoGraveYard;
    private ArrayList<Card> playerOneBanishedZone;		
    private ArrayList<Card> playerTwoBanishedZone;		
    private Player playerOne;		
    private Player playerTwo;		
    private ArrayList<Card> playerOneDrawZone;		
    private ArrayList<Card> playerTwoDrawZone;		
}
