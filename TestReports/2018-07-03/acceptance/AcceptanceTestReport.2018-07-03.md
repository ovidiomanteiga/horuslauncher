# Acceptance Test Report 6

## Metadata

| Property | Value |
|:--|--:|
| Serial number | 6 |
| Tester | Ovidio Manteiga Moar |
| Method | Manual |
| Device type | Android Emulator |
| Device name | Pixel_2_API_27 |
| Android version | 8.1 |
| Date | 2018-07-03 |

## Related work items

1. [IT01#114](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/114)
    1. [BUG#151](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/151)
        1. [VAL#159](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/159)

## Features tested

1. [`04.PABF#25.predict-actions-based-on-frequency`](../../../AcceptanceTests): Predict actions based on usage frequency.

## Results

## Results

1. **[PASSED ✅]** Feature: Predict actions based on usage frequency.
    1. **[PASSED ✅]** Scenario: Show the list of predicted apps after unlock.
    2. **[PASSED ✅]** Scenario: Show a list of predicted apps.
    3. **[PASSED ✅]** Scenario: Perform a predicted action.
    4. **[PASSED ✅]** Scenario: Show empty view over list of predicted actions if no predicted actions.
    5. **[PASSED ✅]** Scenario: Show list of all actions if no predicted actions.
    6. **[PASSED ✅]** Scenario: Switch to the list of all apps.
    7. **[PASSED ✅]** Scenario: Switch to the list of predicted apps.
    8. **[PASSED ✅]** Scenario: Refresh the list of predicted actions.
    
## Comments

- The first scenario ("Show the list of predicted apps after unlock.") is considered passed even though more than 20 predicted actions might be shown so long as for the moment it is not considered relevant and it might be easily changed in the future if needed.
