import java.util.*;

public class Card {
    private String face;
    private char suit;
    private int value;

    public Card(int a){

        //assign the suit of the card
        switch(a % 4) { //4 suits
            case 0:
                this.suit = 'S';
                break;
            case 1:
                this.suit = 'H';
                break;
            case 2:
                this.suit = 'C';
                break;
            case 3:
                this.suit = 'D';
                break;
        }

        //assign the face and value of the card
        switch (a % 13)
        {
        case 0:
            this.face = "A";
            this.value = 11;
            break;
        case 1:
            this.face = "2";
            this.value = 2;
            break;
        case 2:
            this.face = "3";
            this.value = 3;
            break;
        case 3:
            this.face = "4";
            this.value = 4;
            break;
        case 4:
            this.face = "5";
            this.value = 5;
            break;
        case 5:
            this.face = "6";
            this.value = 6;
            break;
        case 6:
            this.face = "7";
            this.value = 7;
            break;
        case 7:
            this.face = "8";
            this.value = 8;
            break;
        case 8:
            this.face = "9";
            this.value = 9;
            break;
        case 9:
            this.face = "10";
            this.value = 10;
            break;
        case 10:
            this.face = "J";
            this.value = 10;
            break;
        case 11:
            this.face = "Q";
            this.value = 10;
            break;
        case 12:
            this.face = "K";
            this.value = 10;
            break;
        }
    }

    public void display(){
        System.out.print('[' + this.face + this.suit + ']');
    }

    public String sendFace(){
        return this.face;
    }

    public int sendvalue(){
        return this.value;
    }

    public void lowerAce(){
        if(this.face == "A")
            this.value = 1;
    }

    public void resetAce(){
        if(this.face == "A")
            this.value = 11;
    }
}

