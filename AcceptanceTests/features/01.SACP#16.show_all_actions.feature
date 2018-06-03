
Feature: Show all actions that can be performed.
  
    https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16


    Scenario: HorusLauncher is not the default launcher.
        Given the device is locked.
          And HorusLauncher is not set as the default launcher.
        When the user unlocks the device.
        Then default launcher of the device is presented.
          And the default launcher allows HorusLauncher to be launched.

    Scenario: Choose among available launchers.
        Given the default launcher is presented.
          And no default launcher has been set since the installation of HorusLauncher.
        When the user taps on the home button.
        Then the user can choose which launcher app to use.
          And the user can choose whether to use it once or set it as default launcher.
          
    Scenario: Show all available apps to be launched.
        Given the device is locked.
          And HorusLauncher is set as the default launcher.
        When the user unlocks the device.
        Then all the available apps to be launched are shown.
          And those apps are paged if they do not fit in the screen.
          And those apps are alphabetically order.
