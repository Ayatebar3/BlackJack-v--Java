import java.util.*;

// Each Player will have a unique
// Set of chips, Hand, Number of Cards, and total value of their hand
// Each New Round during the game will reset all members/properties/variables
// within the 'Player' object EXCEPT FOR stack.
public class Player {
    private int stack; // chips
    private int totalBet;
    private ArrayList<Card> hand; // Actual Hand
    private int cardCount; 
    private int handValue;

    // initialize players with $500
    public Player() { 
        this.stack = 500;
        this.totalBet = 0;
        this.hand = new ArrayList<Card>();
        this.cardCount = 0;
    }

    public int getStack(){
        return this.stack;
    }

    public int getBet(){
        return this.totalBet;
    }

    public int getValue(){
        return this.handValue;
    }

    public int getCardCount(){
        return this.cardCount;
    }

    // showHand() reveals the player's entire hand on one line
    // with it's total value on the end.
    // The function argument, if TRUE, will reveal only the top card of the dealer
    public void showHand(boolean dealer){
        if(dealer){
            this.hand.get(0).display();
            System.out.print("[--]");
            System.out.println(" == " + this.hand.get(0).sendvalue());
        }
        else{
            for(int i = 0; i < cardCount; i++){
                this.hand.get(i).display();
                System.out.print(' ');
            }
            System.out.println(" == " + Integer.toString(handValue));
        }
    }

    // add another card to player's hand
    public void hit(Card c){
        this.hand.add(c);
        this.cardCount++;
        this.handValue += c.sendvalue();
    }

    public int bust(boolean isDealer){
        
        if(this.handValue > 21){
            
            if(!isDealer){
                for( Card kard : hand){
                    if( kard.sendFace() == "A" && kard.sendvalue() == 11){
                        kard.lowerAce();
                        this.handValue -= 10;
                        return 1;
                    }
                }
            }
            return -1;
        }
        return 0;
    }

    // Wager chips 
    public void bet(int bet) {
        totalBet += bet;
        this.stack -= bet;
    }

    // Accept winnings, and reset card array, total bets, card count, and hand value.
    public void endRound(int winnings) {
        this.stack += winnings;
        this.totalBet = 0;

        for( Card kard : hand){
            if(kard.sendFace() == "A" && kard.sendvalue() == 1){
                kard.resetAce();
            }
        }

        this.hand.clear();
        this.cardCount = 0;
        this.handValue = 0;
    }

    // Check if the first two cards made a blackjack
    public boolean isBlackJack(){
        if(this.hand.get(0).sendvalue() + this.hand.get(1).sendvalue() == 21
            && this.cardCount == 2)
            return true;
        else
            return false;
    }

}