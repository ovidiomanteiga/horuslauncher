
Feature: Measure system performance.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/43

    Scenario: Record user selected predicted action.
        Given the Horus List has been shown.
        When the user selects a predicted action.
        Then the fact the user has selected a predicted action is recorded.
            Then the position of the performed action in the Horus List is recorded.

    Scenario: Record user selected promoted action.
        Given the Horus List has been shown.
            And the Horus List contains some promoted actions.
        When the user selects a promoted action.
        Then the fact the user has selected a promoted action is recorded.
            Then the position of the performed promoted action in the Horus List is recorded.

    Scenario: Record user selected action from all actions.
        Given the Horus List has been shown.
            And the user has switched to the all actions list.
        When the user selects an action from all actions.
        Then the fact the user has selected an action from all actions is recorded.
