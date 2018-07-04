
Feature: Perform an action.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/33


    Scenario: Launch an app selected from all the available ones.
        Given the list of all available apps is shown.
        When the user selects one app among them.
        Then the selected app is launched.
          And the selected app comes to foreground.

    Scenario: Navigate back from a launched app.
        Given an app has been launched from the launcher.
          And the user has not interacted with it.
        When the user chooses to navigate back.
        Then the list of all available apps is shown again.
    
    Scenario: Navigate back until possible from any activity.
        Given any given activity is visible and on the foreground.
        When the user chooses to navigate back repeatedly.
        Then the last activity the user reaches is the launcher.
          And in this case it is the HorusLauncher list of all apps.
