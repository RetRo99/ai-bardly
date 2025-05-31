# Fastlane Setup Guide for Windows

This guide will help you install and set up Fastlane on your Windows PC for the ai-bardly project, which is a Kotlin Multiplatform (KMP) project supporting both Android and iOS platforms.

## Prerequisites

Before installing Fastlane, you need to set up Ruby on your Windows machine:

1. **Install Ruby**:
   - Download and install Ruby+Devkit from [RubyInstaller for Windows](https://rubyinstaller.org/downloads/)
   - Choose the recommended version (Ruby+Devkit 3.2.X (x64))
   - During installation, check the option to add Ruby to your PATH
   - At the end of the installation, run the ridk install step and select option 3 (MSYS2 and MINGW development toolchain)

2. **Verify Ruby Installation**:
   ```powershell
   ruby -v
   gem -v
   ```

3. **Install Bundler**:
   ```powershell
   gem install bundler
   ```

## Installing Fastlane

Now that Ruby is set up, you can install Fastlane and the required plugins:

### Option 1: Using Bundler (Recommended)

This method ensures you get the exact same versions of Fastlane and plugins as specified in the project:

1. **Navigate to the project directory**:
   ```powershell
   cd path\to\project
   ```

2. **Install dependencies using Bundler**:
   ```powershell
   bundle install
   ```

3. **Verify Fastlane installation**:
   ```powershell
   bundle exec fastlane --version
   ```

### Option 2: Direct Installation

If you prefer to install Fastlane globally:

1. **Install Fastlane**:
   ```powershell
   gem install fastlane -v 2.226.0
   ```

2. **Install required plugins**:
   ```powershell
   fastlane add_plugin android_versioning_kts
   fastlane add_plugin changelog
   ```

3. **Verify installation**:
   ```powershell
   fastlane --version
   ```

## Additional Setup for Android Development

Since this is an Android project, you'll need:

1. **Java Development Kit (JDK)**:
   - Make sure you have JDK 17 installed (as specified in the project)
   - Ensure JAVA_HOME environment variable is set correctly

2. **Android SDK**:
   - Make sure you have Android SDK installed (typically through Android Studio)
   - Ensure ANDROID_HOME environment variable is set correctly

## Using Fastlane

Once installed, you can run Fastlane commands from the project directory:

### Using Bundler (Recommended)

```powershell
bundle exec fastlane android test
bundle exec fastlane android beta
bundle exec fastlane android deploy
```

### Direct Usage

```powershell
fastlane android test
fastlane android beta
fastlane android deploy
```

## Available Lanes

The project has the following lanes configured:

- `test`: Runs all the tests
- `beta`: Submits a new Beta Build to Crashlytics Beta
- `deploy`: Deploys a new version to the Google Play Store
- `deploy_and_bump`: Deploys and automatically updates version information
- `release`: Creates a release using git-flow

## Troubleshooting

If you encounter issues:

1. **SSL Certificate Problems**:
   - You might need to update Ruby's certificate store:
   ```powershell
   gem update --system
   ```

2. **Path Issues**:
   - Ensure Ruby, Git, and Android SDK are in your PATH

3. **Permission Issues**:
   - Try running PowerShell as Administrator

4. **Bundler Issues**:
   - If you get errors with bundle install, try:
   ```powershell
   gem update bundler
   bundle update --bundler
   ```

For more help, refer to the [official Fastlane documentation](https://docs.fastlane.tools/).
