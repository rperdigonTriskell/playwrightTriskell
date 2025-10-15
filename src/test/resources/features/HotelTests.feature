# Feature: Accessing the Guest in Touch app
# Purpose: Validate the login flow for hotel guests, covering both valid and invalid inputs.

Feature: Accessing the Guest in Touch app
  As a hotel guest
  I want to be able to access the main menu
  So that I can easily manage my stay

  # The Background section defines the common starting state for all scenarios.
  Background:
    Given navigates to "https://dev.hub-buildings.com/guest/index/login/eyJpZF9idWlsZGluZyI6IjgiLCJndWVzdF9uYW1lIjoiIiwiZ3Vlc3Rfcm9vbSI6IiIsImlkX2xhbmciOiIxIiwibGFuZ3VhZ2UiOiJFUyIsImdpdF9oZWFkZXJfaW1hZ2UiOiJodHRwczovL2Rldi5odWItYnVpbGRpbmdzLmNvbS9maWxlcy9ocHMvYnVpbGRpbmc4L2ltYWdlcy84XzIwMjIxMjAxMTAxMTAzXzYzODg3ZGI3ZWIwODMuanBlZyJ9"


  # ===========================================================
  # ✅ SUCCESSFUL LOGIN SCENARIOS
  # These scenarios share the same flow and only differ by data.
  # To avoid repetition, they are unified using a Scenario Outline
  # with an Examples table that provides input variations.
  # ===========================================================
  Scenario Outline: Successful login with valid room and last name
    When the guest enters room "<room>"
    And the guest enters the last name "<last_name>"
    And presses the "LOG IN" button
    Then the guest should see the main menu

    # Examples include variations of valid inputs.
    Examples:
      | room       | last_name     | description                           |
      | 0605       | Fake          | Standard login                        |
      | 605        | Fake          | Room without leading zero             |
      | 000000605  | Fake          | Very long room number (edge case)     |
      | 0605       | Fake a test   | Last name with multiple spaces        |
      | 0605       | anton         | Lowercase last name                   |


  # ===========================================================
  # ❌ VALIDATION AND ERROR SCENARIOS
  # Each of the following scenarios tests a specific invalid input case.
  # These are not unified into a Scenario Outline to keep them explicit
  # and easier to debug when one fails.
  # ===========================================================

  # Test: Missing room field
  Scenario: Error when leaving the room field empty
    When the guest leaves the room field empty
    And enters the last name "Fake"
    And presses the "LOG IN" button
    Then the guest should see a "Required field" error message

  # Test: Missing last name field
  Scenario: Error when leaving the last name field empty
    When the guest enters room "0605"
    And leaves the last name field empty
    And presses the "LOG IN" button
    Then the guest should see a "Required field" error message

  # Test: Room field contains letters instead of digits
  Scenario: Error when entering non-numeric characters in the room
    When the guest enters room "06A5"
    And enters the last name "Fake"
    And presses the "LOG IN" button
    Then the guest should see an "Invalid format" error message

  # Test: Last name contains special characters
  Scenario: Error when entering special characters in the last name
    When the guest enters room "0605"
    And enters the last name "F@ke"
    And presses the "LOG IN" button
    Then the guest should see an "Invalid format" error message
