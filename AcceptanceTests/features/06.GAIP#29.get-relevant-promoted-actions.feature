
Feature: Get relevant promoted actions to be inserted amongst the predicted ones.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/29

    Scenario: Show a promoted action in Horus List.
        Given there are some predicted actions in the Horus List.
            And there are some promoted actions available to be shown.
        When the Horus List is shown.
        Then one promoted action appears in the middle of the Horus List.
            And the promoted action can be tell apart from predicted actions.
            And the promoted action is about the center of the list.

    Scenario: Do not show promoted actions in empty Horus List.
        Given there are no predicted actions in the Horus List.
            And there are some promoted actions available to be shown.
        When the Horus List is shown.
        Then the Horus List empty view is shown.
            And no promoted actions are shown.

    Scenario: No promoted actions available.
        Given there are no promoted actions available to be shown.
        When the Horus List is shown.
        Then the Horus List is shown normally without any promoted actions.
