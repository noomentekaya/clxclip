Feature: UrlShortener
 
Scenario: Calling UrlShortner
 Given my urlShortener exists
 When I call my urlShortener
 Then it should have been a not null