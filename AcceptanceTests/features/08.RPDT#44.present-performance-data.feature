
Feature: Present performance data.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/44

    Scenario: Show stats view from Horus Launcher view.
        Given the Horus Launcher has been launched.
        When the user opts to open the stats view.
        Then the stats view is shown.

    Scenario: Show performance data on the stats view.
        Given the stats view is to be shown.
        When the stats view is actually shown.
        Then the stats view shows the percentage of times an action from the Horus List is launched.
            And it shows the average time it takes to get the Horus List.
            And it shows the averaget time it takes the user to perform any action.

    Scenario: Navigate back to the Horus Launcher view from the stats view.
        Given the stats view is shown.
        When the user opts to go back.
        Then the Horus Launcher view is presented again.

    Scenario: Send launcher stats to server every day at night.
        Given a day has passed since the launcher was used for the first time.
        When a day has passed since the last time the stats were sent.
        Then the Horus Luncher stats are sent to the server.
