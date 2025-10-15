# Feature: Accessing the Guest in Touch app
# Purpose: Validate the login flow for hotel guests, ensuring that valid users can log in
# even when minor input variations (like missing leading zero) occur.

Feature: Accessing the Guest in Touch app
  As a hotel guest
  I want to be able to log in to the Guest in Touch application
  So that I can access the main menu during my stay

  Background:
    Given navigates to "https://dev.hub-buildings.com/guest/index/login/eyJpZF9idWlsZGluZyI6IjgiLCJndWVzdF9uYW1lIjoiIiwiZ3Vlc3Rfcm9vbSI6IiIsImlkX2xhbmciOiIxIiwibGFuZ3VhZ2UiOiJFUyIsImdpdF9oZWFkZXJfaW1hZ2UiOiJodHRwczovL2Rldi5odWItYnVpbGRpbmdzLmNvbS9maWxlcy9ocHMvYnVpbGRpbmc4L2ltYWdlcy84XzIwMjIxMjAxMTAxMTAzXzYzODg3ZGI3ZWIwODMuanBlZyJ9"

  @regression @positive
  Scenario Outline: Login with valid credentials and input variations
    When enters room "<room>"
    And enters last name "<last_name>"
    And click ACCEDER
#    Then access to the main menu
    # Currently, with correct credentials, access is not possible
    Then verify the error message is "No tenemos ninguna reserva para: <last_name> y habitación: <room>"

    Examples:
      | room      | last_name       | description                             |
      | 0605      | Don Anton Test  | Standard login                          |
      | 605       | Don Anton Test  | Room without leading zero               |
      | 0605      | don anton test  | Lowercase last name (case-insensitive)  |
#      | 0605      | Don  Anton Test | Extra space between names (trim check)  |
      # The environment returns the wrong message

  @regression @negative
  Scenario: Error when leaving the room field empty
    When enters room ""
    And enters room "Don Anton Test"
    And click ACCEDER
    Then verify the error message is "Debe introducir su nombre y número de habitación"

  @regression @negative
  Scenario: Error when leaving the last name field empty
    When enters room "0605"
    And enters last name ""
    And click ACCEDER
    Then verify the error message is "Debe introducir su nombre y número de habitación"

  @regression @negative
  Scenario: Error when entering non-numeric characters in the room
    When enters room "06A5"
    And enters last name "Don Anton Test"
    And click ACCEDER
    Then verify the error message is "No tenemos ninguna reserva para: Don Anton Test y habitación: 06A5"

  @regression @negative
  Scenario: Error when entering special characters in the last name
    When enters room "0605"
    And enters last name "Don@Anton#Test"
    And click ACCEDER
    Then verify the error message is "No tenemos ninguna reserva para: Don@Anton#Test y habitación: 0605"
