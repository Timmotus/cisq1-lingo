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
        And the last word had "<previous length>" letters
        When I start a new round
        Then the word to guess has "<next length>" letters

        Examples:
            | previous length | next length |
            | 5               | 6           |
            | 6               | 7           |
            | 7               | 5           |

        # Failure path
        Given I am playing a game
        And the round was lost
        Then I cannot start a new round