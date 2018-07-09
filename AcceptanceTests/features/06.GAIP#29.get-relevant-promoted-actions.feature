
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

    Scenario: Select a promoted action.
        Given the Horus List is shown.
            And the Horus List contains at least one promoted action.
        When the user selects the promoted action.
        Then a confirmation dialog is shown to confirm the user really wants to execute that action.
            And the dialog shows the information about the action to be performed.
            And the dialog gives the user the choices of performing the action or canceling.

    Scenario: Perform a promoted action.
        Given a promoted action confirmation dialog has been shown.
        When the user confirms they want to perform the promoted action.
        Then that promoted action is executed.
            And the promoted action service is notified about that execution.

    Scenario: Cancel a promoted action.
        Given a promoted action confirmation dialog has been shown.
        When the user cancels.
        Then that promoted action is not executed.
            And the dialog is dismissed.
            And the Horus List is shown again.
