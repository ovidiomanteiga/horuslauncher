
Feature: Record action execution information.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/26


    Scenario: Record who launched an app and when it was launched.
        Given the list of all available apps is shown.
        When an app is launched by the user.
        Then the information of that launching is stored.
            And that info includes the app identifier.
            And that info includes the timestamp.
            And that info includes a user identifier.
            And that info includes a device identifier.

    Scenario: Keep just last week's data to save space.
        Given some info about app launches has been stored.
        When once a day.
        Then the info older than a week must be deleted.
    

