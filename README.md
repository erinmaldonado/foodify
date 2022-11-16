# foodify

# To open project for the first time
#### File > New > Project from Version Control > https://github.com/erinjmaldonado/foodify.git

## To configure your OAuth Client
#### Go to https://developers.google.com/identity/sign-in/android/start-integrating
#### Select "Configure a project"
#### "+Create a new project"
#### "Enter new project name" -> Foodify > Product name -> Foodify
#### "Where are you calling from?" Select -> "Android"
#### Package name -> foodify.com.example

## TO GET SHA-1 signing certificate
#### Select Gradle in top right corner > Gradle settings > Experimental
#### Uncheck the box labeled "Do not build Gradle task list during Gradle sync
#### Select "Sync Project with Gradle Files"
#### Navigate to Tasks > android > signingReport
#### Select "signingReport"
#### You should then be able to see the SHA-1 signing certificate

#### Paste SHA-1 signing certificate to configure your OAuth client
#### Create
#### Now you should be able to log in with google on your emulator
