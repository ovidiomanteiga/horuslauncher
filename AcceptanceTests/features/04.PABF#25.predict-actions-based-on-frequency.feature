
Feature: Predict actions based on usage frequency.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/25

    Scenario: Show a list of predicted apps.
        Given there is information to predict a list of apps.
        When the a list of predicted actions has to be shown.
        Then a list with the top 20 more likely actions is shown.
            And their probability is based on their frequency during the last week.
            And the list is ordered from more to less probability.
            And each action shows at least a name and an optional icon.
            And it takes less than 1 second to show.

    Scenario: Show the list of predicted apps after unlock.
        Given the device is locked.
            And the app is set as the default launcher.
        When the user unlocks the device.
        Then the list of predicted actions is shown.

    Scenario: Perform a predicted action.
        Given the list of all apps has been shown.
        When the user selects a predicted action.
        Then the action is executed (the same way as on the list of all actions).

    Scenario: Show empty view over list of predicted actions if no predicted actions.
        Given the list of predicted actions is to be shown.
            And the list of predicted actions is empty.
        When the list of predicted actions is to be shown.
        Then an empty view indicating there are no predicted actions is shown.

    Scenario: Show list of all actions if no predicted actions.
        Given the list of predicted actions has been shown.
            And the user come from unlock or another activity.
            And the list of predicted actions is empty.
        When immediately after.
        Then the list of all actions is shown.

    Scenario: Switch to the list of all apps.
        Given the list of predicted apps has been shown.
        When the user selects the list of all apps.
        Then the list of all apps is shown.

    Scenario: Switch to the list of predicted apps.
        Given the list of all apps has been shown.
        When the user selects the predicted apps.
        Then the list of predicted apps is shown.

    Scenario: Refresh the list of predicted actions.
        When the list of predicted actions is to be shown.
        Then the list recalculated.
