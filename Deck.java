import java.util.*;

// Deck class holds ALL decks in play

public class Deck {

    private int decks;
    private Card[] cards;
    private int[] reference;
    private int ref;
        
    // 'decks' refers to the number of decks in play (1-5)

    // 'cards' array is ALL of the cards in play from ALL decks

    // 'reference' array elements hold indices of 'cards' array, 
    //      easier to shuffle and is used as a Queue to deal from 

    // 'ref' is used as a pointer for "Next Card"

    public Deck(int a){ // 'a' represents number of decks to be used

        this.decks = a;

        // first allocate space for the array slots
        this.cards = new Card[52 * decks]; 

        // before allocating space for each element
        this.reference = new int[52 * decks]; 

        // set "pointer" to top of deck
        this.ref = 0;
        
        // loop through indices and allocate space 
        // and initialize each element
        for(int i = 0; i < 52 * decks; i++){
            this.cards[i] = new Card(i);
            this.reference[i] = i;
        }
    }

    // reference array initialized as [0, 1, 2,..., n-1]
    // shuffleDeck() swaps random indices 255 times (one byte)
    // reference array will result in something like:
    // [55, 31, 9, 34, 6, 4, ..., 26]
    public void shuffleDeck(){

        Random rand = new Random();

        for (int i = 0; i < 255; i++) {
            int a = rand.nextInt(52*this.decks - 1);
            int b = rand.nextInt(52*this.decks - 1);
            int temp = this.reference[a];
            this.reference[a] = this.reference[b];
            this.reference[b] = temp;
        }
    }

    // "POP" top card 
    // use 'ref' to keep track of next card
    public Card dealCard(){
        this.ref++;
        return this.cards[reference[ref-1]];
    }

    // remaining cards in deck
    public int cardsLeft(){
        return this.decks*52 - ref;
    }
    // if 'cardsLeft()' returns < 15
    public void resetDeck(){
        this.ref = 0;
        shuffleDeck();
    }
}