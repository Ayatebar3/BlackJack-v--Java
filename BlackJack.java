import java.util.*;

public class BlackJack {


    //Globals:
    //  - One player
    //  - One Dealer
    //  - 1-5 Deck(s)
    //  - STDIN Input
    public static Player player;
    public static Player dealer;
    public static Deck deck;
    public static Scanner reader;

    public static void pause(){
        System.out.println("\nPress 'ENTER' to continue...");
            try {
                System.in.read();
            } 
            catch(Exception e) {
                System.out.println("");
            }
    }

    // Handles user input for [Y/N]
    public static boolean yesNo(){
        String yesno = reader.next();
        
        // Error Checking
        while (!yesno.equals("y") && !yesno.equals("Y") && !yesno.equals("n") && !yesno.equals("N")) {
            System.out.print("Invalid response; Please enter either 'Y' or 'N': ");
            yesno = reader.next();
        }


        if (yesno.equals("y") || yesno.equals("Y"))
            return true;
        else
            return false;

    }

    // Handles numeric inputs from user
    public static int numberEntry() {
        String num = reader.next();
        boolean flag = true;
        while (flag)
        {
            for (int i = 0; i < num.length(); i++)
            {
                if (num.charAt(i) < 48 || num.charAt(i) > 57)
                {
                    System.out.print("Sorry, but your entries must be numerical characters ONLY. Try again: ");
                    flag = true;
                    break;
                }

                flag = false;
            }
            if (flag)
                num = reader.next();
        }       
        return Integer.parseInt(num);
    }

    public final static void clearConsole()
    {
        for (int i = 0; i < 20; ++i) 
            System.out.println();
    }



    public static void showTable(boolean hideDealer){

        clearConsole();
        System.out.println("Current Bet: " + Integer.toString(player.getBet()));
        System.out.println("\n\n");

        System.out.println("--------------{ YOUR   CARDS }--------------\n");
        player.showHand(false);
        System.out.println("\n\n");

        System.out.println("--------------{ DEALER CARDS }--------------\n");
        dealer.showHand(hideDealer);
        System.out.println("\n\n");
    }

    // Sequence for each round 
    // STARTING with betting
    // ENDING with winnings

    public static int round(){
        boolean doubleDown = false; // did the player doubleDown


        System.out.println("\nHere is your stack: " + Integer.toString(player.getStack()));
        System.out.print("Place your bet: ");
        int bet = numberEntry();

        // Error Check Bets
        while (bet > player.getStack() || (bet < 25 && player.getStack() >= 25)) {

            System.out.print("Your bets must be a minimum of 25 or at most your current stack. Try again: ");
            bet = numberEntry();
        }

        // player makes bet
        player.bet(bet);

        // deal initial cards
        dealer.hit(deck.dealCard());
        player.hit(deck.dealCard());
        dealer.hit(deck.dealCard());

        // Keep dealing until player stays 
        do {

            player.hit(deck.dealCard());

            showTable(true);

            if(player.isBlackJack()){
                return 0;
            }

            if(player.getCardCount() == 2 &&
               player.getValue() <= 11 && player.getValue() >= 9 &&
               player.getBet() <= player.getStack()){
                System.out.print("You have: " + Integer.toString(player.getValue()));
                System.out.print(" Double-Down? [Y/N]: ");
                if(yesNo()){
                    player.bet(player.getBet());
                    player.hit(deck.dealCard());
                    doubleDown = true;
                }
            }

            if(doubleDown)
                break;

            switch(player.bust(false)) {
                case -1: 
                    return -1;
                case 1:
                    System.out.println("You've Exceeded 21, so your ACE will revalue to 1");
                    pause();
                    showTable(true);
                    break;
            }
            
            if(player.getValue() == 21){
                System.out.println("\n\t\t*******************");
                System.out.println("\t\t*** YOU HAVE 21 ***");
                System.out.println("\t\t*******************\n\n");
                System.out.println("\tNo More Hitting...\n\n ");
                pause();
                break;
            }

            System.out.print("Would you like to hit for another card? [Y/N]: ");
        }while(yesNo());

        if(dealer.getValue() < 17){ // dealer hits on 16, stays on 17
            do { 
                showTable(false);
                System.out.print("Dealing Cards to the Dealer... ");
                pause();
                dealer.hit(deck.dealCard());

            }while(dealer.getValue() < 17);
        }

        showTable(false);
        pause();

        if(dealer.bust(true) == -1){
            System.out.println("\n\t\t********************");
            System.out.println("\t\t*** DEALER BUSTS ***");
            System.out.println("\t\t********************\n\n");
            pause();
        }
        
        /********************
         *      RESULTS     *
         ********************/

        // If Player Wins
        if(player.getValue() > dealer.getValue() || dealer.bust(true) == -1){

            if(doubleDown)
                return 2;

            return 1;
        }

        //If Player Draws with Dealer
        else if(player.getValue() == dealer.getValue()){
            return 3;
        }
        
        //Player loses
        else {
            return 4;
        }
    }
    public static void main (String[] args){
        player = new Player();
        dealer = new Player();
        reader = new Scanner(System.in);

        clearConsole();

        System.out.println("\n\n----------------------------------{ Welcome to BlackJack Beta v2.0 Java }----------------------------------\n\n");
        System.out.println("Here are the Rules:\n");
        System.out.println("-You vs the Dealer");
        System.out.println("-You will start with $500 and will need to make a 25 minimum bet");
        System.out.println("-Once you bet, both will be given two cards. Only one of the dealers card will be showing.");
        System.out.println("\t~If you get a blackjack(21 on the first two cards), you will be rewarded double your bet.\n");
        System.out.println("-You will be asked to hit or stay");
        System.out.println("\t~If you stay, you wait to see if you beat the dealer");
        System.out.println("\t~If you hit, you will be given another card\n");
        System.out.println("-Try to get close to, but not exceeding, 21");
        System.out.println("-Once you are done hitting, and you did not bust, the dealer will reveal his other card.");
        System.out.println("-The dealer will keep hittin until reaching 17 or more");
        System.out.println("\t~If you have a greater total than the dealer, you are rewarded your bet");
        System.out.println("\t~If you have a lower total than the dealer, you will lose your bet\n");
        System.out.println("-Once the round is over, you will be asked to continue or quit(if you're a sore loser)");

        System.out.print("\n\n\n*** Ready to play? *** [Y/N]: ");
        
        // Player will input the number of decks to use in the game
        // ONLY ONCE at the beginning of the game
        if(yesNo()){
            int decks = 0;
            boolean flag = true;

            System.out.println("\n\nYou can play with up to 5 decks.");
            System.out.print("How many decks would you like to use?: ");

            do {
                switch(numberEntry()){
                    case 1:
                        decks = 1;
                        flag = false;
                        break;
                    case 2:
                        decks = 2;
                        flag = false;
                        break;
                    case 3:
                        decks = 3;
                        flag = false;
                        break;
                    case 4:
                        decks = 4;
                        flag = false;
                        break;
                    case 5:
                        decks = 5;
                        flag = false;
                        break;
                    default:                
                        System.out.print("Sorry, you may only have 1-5 decks. Try again: ");
                }                    
            } while(flag);

            System.out.println("\n\nGreat! We will use " + Integer.toString(decks) + " decks.");
            deck = new Deck(decks);
            deck.shuffleDeck();

            pause();

            do { // This loop cycles each round()
                 // and will use the return value to decide winnings
            
                if(deck.cardsLeft() < 15){
                    System.out.println("*** Deck is Running Low, Reshuffling...");
                    pause();
                    deck.resetDeck();
                }

                switch(round()) {
                    case -1:
                        System.out.println("\n\t\t**************");
                        System.out.println("\t\t*** BUSTED ***");
                        System.out.println("\t\t**************\n\n");
                        System.out.println("\tYou Lose your Bet...\n\n");
                        player.endRound(0);
                        dealer.endRound(0);
                        pause();
                        break;
                    case 0:
                        System.out.println("\n\t\t********************");
                        System.out.println("\t\t*** BLACK JACK!! ***");
                        System.out.println("\t\t********************\n\n");
                        System.out.println("\tYou Win TWICE Your Bet: " + Integer.toString(player.getBet()*3) + "\n\n");
                        player.endRound(player.getBet()*3);
                        dealer.endRound(0);
                        pause();
                        break;    
                    case 1:
                        System.out.println("\n\t\t****************");
                        System.out.println("\t\t*** YOU WIN! ***");
                        System.out.println("\t\t****************\n\n");
                        System.out.println("\tYour Winnings: " + Integer.toString(player.getBet()*2) + "\n\n");
                        player.endRound(player.getBet()*2);
                        dealer.endRound(0);
                        pause();
                        break; 
                    case 2:
                        System.out.println("\n\t\t****************");
                        System.out.println("\t\t*** YOU WIN! ***");
                        System.out.println("\t\t****************\n\n");
                        System.out.println("\tYour Winnings with Double Down: " + Integer.toString(player.getBet()*2) + "\n\n");
                        player.endRound(player.getBet()*2);
                        dealer.endRound(0);
                        pause();
                        break; 
                    case 3:
                        System.out.println("\n\t\t*****************");
                        System.out.println("\t\t*** YOU TIED! ***");
                        System.out.println("\t\t*****************\n\n");
                        System.out.println("\tYou get your bet back: " + Integer.toString(player.getBet()) + "\n\n");
                        player.endRound(player.getBet());
                        dealer.endRound(0);
                        pause();
                        break; 
                    case 4:
                        System.out.println("\n\t\t*******************");
                        System.out.println("\t\t*** You lose... ***");
                        System.out.println("\t\t*******************\n\n");
                        System.out.println("\tYou Lose your Bet...\n\n");
                        player.endRound(0);
                        dealer.endRound(0);
                        pause();
                        break; 
                }
                
                System.out.println("We went through one round");
                player.endRound(0);
                dealer.endRound(0);
                
                if(player.getStack() == 0){
                    System.out.println("\n\t\t******************************");
                    System.out.println("\t\t*** You're Out Of Money!!! ***");
                    System.out.println("\t\t******************************\n\n");
                    pause();
                    break;
                }
    
                else
                    System.out.print("Play another round?[Y/N]: ");

            } while(yesNo());

        }
        reader.close();
        
        System.out.println("\n\t\t***************************");
        System.out.println("\t\t*** Thanks for playing! ***");
        System.out.println("\t\t*** See ya next time =) ***");
        System.out.println("\t\t***************************\n\n");
        pause();
    }
}