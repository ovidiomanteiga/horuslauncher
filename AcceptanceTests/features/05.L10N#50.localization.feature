
Feature: Localization.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/50

    Scenario: English should be the default language.
        Given there device language is not available on the app.
        When the user launches the launcher app.
        Then the launcher app language must be English.

    Scenario: Use the user's top language available.
        Given the user has set a list of preferred languages.
            And the app is localized to some of those languages.
        When the user launches the launcher app.
        Then the launcher app language must be that which
            has the higher priority on among those on the user's
            preferred list which are available on the app.

    Scenario: Change device language.
        Given the user's device language is a certain one.
            And that language is also active on the app.
        When the user changes the device language.
        Then the launcher app language changes accordingly.

    Scenario: All UI strings are localized.
        Given the launcher app is showing on screen.
        When any given string of text appears on screen.
        Then that string of text is localized if it needs to.
