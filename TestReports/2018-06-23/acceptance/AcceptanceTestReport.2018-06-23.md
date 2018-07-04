# Acceptance Test Report 4

## Metadata

| Property | Value |
|:--|--:|
| Serial number | 4 |
| Tester | Ovidio Manteiga Moar |
| Method | Manual |
| Device type | Android Emulator |
| Device name | Pixel_2_API_27 |
| Android version | 8.1 |
| Date | 2018-06-23 |

## Related work items

1. [PPDA#37](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/37)
    1. [VAL#142](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/142)

## Features tested

1. [`04.PABF#25.predict-actions-based-on-frequency`](../../../AcceptanceTests): Predict actions based on usage frequency.

## Results

1. **[PASSED ✅]** Feature: Predict actions based on usage frequency.
    1. **[PASSED ✅]** Scenario: Show the list of predicted apps after unlock.
    2. **[PASSED ✅]** Scenario: Show a list of predicted apps.
    3. **[PASSED ✅]** Scenario: Perform a predicted action.
    4. **[PASSED ✅]** Scenario: Switch to the list of all apps.
    5. **[PASSED ✅]** Scenario: Switch to the list of predicted apps.
    6. **[PASSED ✅]** Scenario: Refresh the list of predicted actions.
    
## Comments

- The first scenario ("Show the list of predicted apps after unlock.") is considered passed even though more than 20 predicted actions might be shown so long as for the moment it is not considered relevant and it might be easily changed in the future if needed.
