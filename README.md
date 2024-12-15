<a>
  <h1 align="center">Android Auto Password Taxonomy</h1>
</a>

<p align="center">
  Create easy-to-remember passwords that help prevent data-driven attacks
</p>

<p align="center">
  <a href="#features"><strong>Features</strong></a> ·
  <a href="#running-locally"><strong>Running Locally</strong></a> ·
  <a href="#android-tech-used"><strong>Android Tech Used</strong></a> ·
  <a href="#author"><strong>Author</strong></a>
</p>
<br/>

## Features
- Easy-to-use password creator
- Password saving in the "Password Vault" (Locally saved SQLite Database)
- Ability to add newly generated passwords to the clipboard
- Ability to add previously vaulted passwords to the clipboard
- Password sorting in the vault by recent and alphabetical descending order


## Android Tech Used

- Utilizes multiple pages
- Implements a date picker fragment
- Uses an SQLite database
- SQLite database stores company names and associated passwords
- Uses Clipboard Manager service
- Uses intents to pass information between activities
- Form data is not destroyed while using the app
- Uses coroutines to manage database insertion

## Running Locally

- To run this app locally, you must have Android Studio since it is not on the Play Store yet.
- Additionally, you must have a device running API 30 or above.
- In your Gradle configuration, your target SDK must be at least version 34.


## Author
- Carter Auer

