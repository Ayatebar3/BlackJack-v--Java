//Ali Atebar
//CISP400 C++
//Fowler
//Blackjack baby!

#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <time.h>   //to not repeat rand
#include <stdlib.h> //for rand
using namespace std;

//BONUS- Array of objects
//BONUS- Kept objects safe by using reference
//BONUS- yesno function for memory allocation
//BONUS- choice function for easier entry/memory allocation
//BONUS- rules are outputted
//BONUS- Used 'main' for bets and 'round' for cards
//BONUS- Used vector to hold undetermined amount of hits
//BONUS- Displayed cards inside '[]' to resemble a card
//BONUS- Aces can be used as 11 or 1
//BONUS- 'Double Down' is an option

class card
{
private:
    string face; //wouldn't print '10' so needed string;
    char suit;
    int value;

public:
    void assigncard(int a);
    void display();
    int sendvalue();
};
card cards[52];
int cardref[52];

void card::assigncard(int a)
{
    switch (a % 4) //for suits
    {
    case 0:
        suit = 'S';
        break;
    case 1:
        suit = 'D';
        break;
    case 2:
        suit = 'C';
        break;
    case 3:
        suit = 'H';
        break;
    }
    ////////////////////////////////////////////////

    switch (a % 13) //for face
    {
    case 0:
        face = 'A';
        break;
    case 1:
        face = '2';
        break;
    case 2:
        face = '3';
        break;
    case 3:
        face = '4';
        break;
    case 4:
        face = '5';
        break;
    case 5:
        face = '6';
        break;
    case 6:
        face = '7';
        break;
    case 7:
        face = '8';
        break;
    case 8:
        face = '9';
        break;
    case 9:
        face = "10";
        break;
    case 10:
        face = 'J';
        break;
    case 11:
        face = 'Q';
        break;
    case 12:
        face = 'K';
        break;
    }

    /////////////////////////////////////////////////

    switch (a % 13) //for value
    {
    case 0:
        value = 11;
        break;
    case 1:
        value = 2;
        break;
    case 2:
        value = 3;
        break;
    case 3:
        value = 4;
        break;
    case 4:
        value = 5;
        break;
    case 5:
        value = 6;
        break;
    case 6:
        value = 7;
        break;
    case 7:
        value = 8;
        break;
    case 8:
        value = 9;
        break;
    case 9:
        value = 10;
        break;
    case 10:
        value = 10;
        break;
    case 11:
        value = 10;
        break;
    case 12:
        value = 10;
        break;
    }
}

void card::display() //BONUS- outputted brackets for cards
{
    cout << "[" << face << suit << "] ";
}

int card::sendvalue()
{
    return value;
}

//BONUS- Choice function for numerical entries
int choice()
{
    string number;
    int flag = 1;
    cin >> number;
    while (flag)
    {
        for (int i = 0; i < number.length(); i++)
        {
            if (number[i] < 48 || number[i] > 57)
            {
                cout << "Sorry, but your entries must be numerical characters only. Try again: ";
                flag++;
                break;
            }
        }
        if (flag > 1)
            cin >> number;
        else
            flag = 0;
    }
    return stoi(number);
}

void shuffleref()
{
    int a, b, temp = 0;
    srand((unsigned)time(NULL));
    for (int i = 0; i < 150; i++)
    {
        a = rand() % 52;
        b = rand() % 52;
        cardref[temp] = cardref[a];
        cardref[a] = cardref[b];
        cardref[b] = cardref[temp];
    }
}

bool yesno() //using yesno for better memory allocation
{
    char yesno;
    cin >> yesno;
    while (yesno != 'y' && yesno != 'Y' && yesno != 'N' && yesno != 'n')
    {
        cout << "Invalid response; Please enter either 'Y' or 'N': ";
        cin >> yesno;
    }

    if (yesno == 'y' || yesno == 'Y')
        return true;
    else
        return false;
}

//BONUS- card handling
int round()
{
    int i = 0, j, aceval, doubledown = 0, // 'i' is card reference, 'j' is for loops
        playertotal, dealertotal = 0, hitcount = -1;

    //BONUS-Vectors for unknown hits
    vector<int> playercards, dealercards;

    //first two cards for player and dealer

    dealercards.push_back(cardref[i]);
    i++;
    playercards.push_back(cardref[i]);
    i++;
    dealercards.push_back(cardref[i]);
    i++;

    do
    {
        hitcount++;                        //first count is 0
        playertotal = 0;                   //resets back to zero
        playercards.push_back(cardref[i]); //
        i++;                               //
        cout << endl
             << "Your cards: ";                  //
        for (j = 0; j < playercards.size(); j++) //
        {                                        //
            cards[playercards[j]].display();     //total is recalculated
            playertotal += cards[playercards[j]].sendvalue();
        }

        if (playertotal == 21)
        {
            cout << endl
                 << "You have 21. You can't hit anymore" << endl;
            system("pause");
            break;
        }

        if (playertotal > 21)
        {
            for (j = 0; j < playercards.size(); j++)
            {
                //BONUS- dual value for aces
                aceval = cards[playercards[j]].sendvalue(); //searching for ace in players hand
                if (aceval == 11)
                {
                    cout << endl
                         << "\nYou have an ";
                    cards[playercards[j]].display();
                    cout << " that can be either an 11 or a 1. Which value do you wish to use?[1 or 11]: ";
                    aceval = choice();
                }
                if (aceval == 1)
                    playertotal -= 10;
            }

            if (playertotal > 21)
            {
                cout << endl
                     << "You busted...";
                return 1;
            }
        }

        cout << endl
             << "Your total is: " << playertotal; //recalculated total after ace
        cout << endl
             << "Dealer cards: ";
        cards[dealercards[0]].display();
        cout << "[?]" << endl;

        if ((playertotal == 11 && hitcount == 0) || (playertotal == 10 && hitcount == 0))
        {
            cout << "Do you want to double down?[Y/N]: ";
            if (yesno())
            {
                doubledown++;
                break;
            }
        }

        cout << endl
             << "Do you want to hit?[Y/N]: ";
    } while (yesno());

    //BONUS- Double Down
    if (doubledown)
    {
        playercards.push_back(cardref[i]);
        i++;
        cout << endl
             << "Your cards: ";
        for (j = 0; j < playercards.size(); j++)
        {
            cards[playercards[j]].display();
            playertotal += cards[playercards[j]].sendvalue();
        }
    }

    ///////////////////  Calculating the winner  /////////////////////////

    if (!hitcount && playertotal == 21) //player gets blackjack
    {
        cout << "\n\nBLACKJACK!!!\n\n";
        return 0;
    }

    else //not blackjack and not bust
    {
        cout << endl
             << endl;
        while (dealertotal <= 17) //hits on 16, stays on 17
        {
            dealercards.push_back(i);
            i++;
            dealertotal = 0;
            cout << endl
                 << "Here are the dealer's cards: ";

            for (j = 0; j < dealercards.size(); j++)
                dealertotal += cards[dealercards[j]].sendvalue();

            for (j = 0; j < dealercards.size(); j++)
                cards[dealercards[j]].display();

            cout << endl
                 << "Dealer total is: " << dealertotal << endl;
        }

        if ((doubledown == 1) && (playertotal > dealertotal) && (dealertotal <= 21))
        {
            cout << "The dealer beats you..." << endl;
            return 5;
        }
        else if ((doubledown == 1) && (playertotal < dealertotal) && (playertotal <= 21))
        {
            cout << "You beat the dealer!!!" << endl;
            return 6;
        }
        else if (doubledown == 1 && dealertotal > 21) //dealer busts
        {
            cout << "The dealer busts, you win!!!" << endl;
            return 6; //dealer busts
        }
        else if (playertotal > dealertotal) // player still beats dealer
        {
            cout << "You beat the dealer!!!" << endl;
            return 2;
        }
        else if (playertotal < dealertotal && dealertotal <= 21) // player loses, dealer doesn't bust
        {
            cout << "The dealer beats you..." << endl;
            return 3;
        }
        else if (dealertotal > 21) //dealer busts
        {
            cout << "The dealer busts, you win!!!" << endl;
            return 4; //dealer busts
        }
        else //no one wins!
        {
            cout << "It is a TIE...!!!" << endl;
            return 7;
        }
    }
}

int main() //controls betting and rules
{
    int i, bet, pot = 500;

    for (i = 0; i < 52; i++)
    {
        cardref[i] = i;
        cards[i].assigncard(i); //assigning deck and reference deck
    }

    ////////////////////// Menu for Game //////////////////////

    /////////////////BONUS- Rules/////////////////

    cout << "Welcome to BlackJack Beta v2.0\n\n";
    cout << "Here are the Rules:" << endl;
    cout << "-You vs the Dealer" << endl;
    cout << "-You will start with $500 and will need to make a 25 minimum bet" << endl;
    cout << "-Once you bet, both will be given two cards. Only one of the dealers card will be showing." << endl;
    cout << "\t~If you get a blackjack(21 on the first two cards), you will be rewarded double your bet." << endl;
    cout << "-You will be asked to hit or stay" << endl;
    cout << "\t~If you stay, you wait to see if you beat the dealer" << endl;
    cout << "\t~If you hit, you will be given another card" << endl;
    cout << "-Try to get close to, but not exceeding, 21" << endl;
    cout << "-Once you are done hitting, and you did not bust, the dealer will reveal his other card." << endl;
    cout << "-The dealer will keep hittin until reaching 17 or more" << endl;
    cout << "\t~If you have a greater total than the dealer, you are rewarded your bet" << endl;
    cout << "\t~If you have a lower total than the dealer, you will lose your bet" << endl;
    cout << "-Once the round is over, you will be asked to continue or quit(if you're a sore loser)" << endl;

    cout << "\n\nReady to play?[Y/N]: ";

    while (yesno() && pot)
    {
        cout << "\nHere is your stack: " << pot << endl;
        cout << "Place your bet: ";
        bet = choice();
        while (bet > pot || bet < 25)
        {
            cout << "Your bets must be a minimum of 25 and may not exceed your current pot. Try again: ";
            bet = choice();
        }
        pot -= bet;
        shuffleref();
        cout << "\n";
        switch (round())
        {
        case 0:
            cout << "You win: " << 2 * bet << endl;
            pot += (3 * bet);
            cout << "Here is your stack: " << pot << endl;
            break;
        case 1:
            cout << "You lost your bet." << endl;
            cout << "Here is your stack: " << pot << endl;
            break;
        case 2:
            cout << "You win: " << bet << endl;
            pot += (2 * bet);
            cout << "Here is your stack: " << pot << endl;
            break;
        case 3:
            cout << "You lost your bet." << endl;
            cout << "Here is your stack: " << pot << endl;
            break;
        case 4:
            cout << "You win: " << bet << endl;
            pot += (2 * bet);
            cout << "Here is your stack: " << pot << endl;
            break;
        case 5:
            cout << "You lost twice your bet: " << 2 * bet << endl;
            pot -= bet;
            cout << "Here is your stack: " << pot << endl;
            break;
        case 6:
            cout << "You win twice your bet: " << 2 * bet << endl;
            pot += (3 * bet);
            cout << "Here is your stack: " << pot << endl;
            break;
        case 7:
            cout << "You get your bet back!" << endl;
            pot += bet;
            cout << "Here is your stack: " << pot << endl;
            break;
        }

        if (!pot)
        {
            cout << "You're out of Money!!!" << endl;
            break;
        }
        else
            cout << endl
                 << "Play another round?[Y/N]: ";
    }

    cout << "\n\nThanks for playing! See ya next time\n\n";

    return 0;
}

//Peer Review Summary Form

// Complete and attach to the bottom of your source file and turn in to D2L.

//

// Reader: Ruthvik Chowdry
// Recorder: Varshini Pabba
// Other: This game is very addictive!!!

//

// Approx Time for this asignment: 8hrs 25 mins

// Use the following ranking for the following:

// 5 - Sophisticated, 4 - Highly Competent, 3 - Competent, 2 - Not Yet Competent, 1 - Unacceptable

// Solution Fit with Client Needs: 5

// User Friendliness: 5

// Comments and Documentation:5

// Overall Score: 5
