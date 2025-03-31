public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind mans Bluff \n");
        int losses = 0, wins = 0;

        for (int round = 0; round < 5; round++) {
            System.out.println("\nRound " + (round + 1) + ":");

            Card playerCard = player1.remove_from_head();
            Card compCard = computer.remove_from_head();

            System.out.print("Computer's card: ");
            compCard.print_card();
            System.out.println();

            System.out.print("Is your card higher or lower? (h/l): ");
            java.util.Scanner input = new java.util.Scanner(System.in);
            String guess = input.nextLine().trim().toLowerCase();

            System.out.print("Your card: ");
            playerCard.print_card();
            System.out.println();

            boolean playerWins = false;
            if (playerCard.getRank().ordinal() > compCard.getRank().ordinal()) playerWins = true;
            else if (playerCard.getRank().ordinal() == compCard.getRank().ordinal() &&
                    playerCard.getSuit().ordinal() > compCard.getSuit().ordinal()) playerWins = true;

            if ((guess.equals("h") && playerWins) || (guess.equals("l") && !playerWins)) {
                System.out.println("You guessed right!");
                wins++;
                losses = 0;
            } else {
                System.out.println("You guessed wrong :(");
                losses++;
            }

            //Add cards back to deck
            deck.add_at_tail(playerCard);
            deck.add_at_tail(compCard);

            if (losses == 3) {
                rage_quit(player1, computer, deck);
                round = -1; // restart game
                losses = 0;
                wins = 0;
            }
        }

        System.out.println("\nGame Over. You won " + wins + " rounds.");
    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nRage Quit! Shuffling and redealing cards...\n");

        // Return all cards to deck
        while (player1.size > 0) deck.add_at_tail(player1.remove_from_head());
        while (computer.size > 0) deck.add_at_tail(computer.remove_from_head());

        deck.shuffle(512);

        for (int i = 0; i < 5; i++) {
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}
