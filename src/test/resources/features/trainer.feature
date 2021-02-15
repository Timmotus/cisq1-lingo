Feature: Lingo Trainer
    As a player
    I want to guess 5, 6, 7 letter words
    In order to prepare for Lingo

    Scenario: Start a new game
        When I start a new game
        Then the word guess has "5" letters
        And I should see the first letter
        And my score is "0"

    Scenario Outline: Start a new round
        Given I am playing a game
        And the round was won
        And the last word had <previous length> letters
        When I start a new round
        Then the word to guess has <next length> letters

        Examples:
            | previous length | next length |
            | 5               | 6           |
            | 6               | 7           |
            | 7               | 5           |

        # Failure path
        Given I am playing a game
        And the round was lost
        Then I cannot start a new round

    Scenario Outline: Guessing a word
        Given I am playing a game
        And the round has not been won
        And the round has not been lost
        When I guess word <guess>
        Then I should see <feedback> on if the length of <guess> is the same as <word>
        And I should see <feedback> on what letters in <guess> are in the exact same place in <word>
        And I should see <feedback> on what letters in <guess> are present in <word> that have not yet been verified in the previous step
        And I should see <feedback> on what letters in <guess> are absent in <word> that have not yet been verified in the previous steps

        Examples:
            | word  | guess  | feedback                                             |
            | BAARD | BERGEN | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
            | BAARD | BONJE  | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT              |
            | BAARD | BARST  | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
            | BAARD | DRAAD  | ABSENT, PRESENT, CORRECT, PRESENT, CORRECT           |
            | BAARD | BAARD  | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |

