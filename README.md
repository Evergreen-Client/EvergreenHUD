# EvergreenHUD

EvergreenHUD is a Minecraft mod developed for every version and modding platform people love. 
From a PvPer to a casual singleplayer kinda guy, this mod will have everything you desire. Also,
it is designed *for* power-users *by* a power-user so everyone's configuration will be unique!

#### Announcement
If you would like to help me on my endeavour in creating a multi-version abstraction system:

*Contact me on discord @ isXander#0162*

#### Links
- [Latest Stable Release](https://github.com/Evergreen-Client/EvergreenHUD/releases/latest)
- [Hypixel Thread](https://hypixel.net/threads/v2-beta-out-now-evergreenhud-1-3-1.3787277/)

## Summary

- [Getting Started](#getting-started)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [Authors](#authors)
- [License](#license)

## Getting Started

These instructions will help you get started to help develop
or test the mod.

### Installing

This is how you can get a working development environment.

Decompile Minecraft

    gradlew setupDecompWorkspace genSources

If you use [Intellij](https://www.jetbrains.com/idea/), then run this command

    gradlew genIntellijRuns

If you use [Eclipse](https://www.eclipse.org/) (not recommended), use this

    gradlew eclipse

Now you will be able to open the folder as a project in your chosen IDE.

## Testing

To test the mod, you will need to add a few run configurations.

Create the following run configurations in your favourite IDE:

    Type: Application
    Name: Run Forge
    Classpath: EvergreenHUD.forge-1.8.9.main
    Main Class: GradleStart

    Type: Application
    Name: Run Fabric
    Classpath: EvergreenHUD.fabric-1.16.5.main
    Main Class: net.fabricmc.devlaunchinjector.Main

## Deployment

When deploying, you will need to use gradle to build the project.

You can find a list of the build configurations you will need to build every version of the mod.

    Type: Gradle
    Name: Build Forge
    Classpath: EvergreenHUD.forge-1.8.9.main
    Tasks: clean build

    Type: Gradle
    Name: Build Fabric
    Classpath: EvergreenHUD.fabric-1.8.9.main
    Tasks: clean build

Once built, add the jar file to the `mods` folder in your minecraft directory commonly found in `%appdata%/.minecraft`

## Contributing

Please make sure to make a useful contribution that will benefit either the user or fellow developers in a noticeable way.

In addition, contributions must be tested in all minecraft versions supported and provide pictures if applicable.

## Authors

- **isXander** - *Founder of the project* -
  [isXander](https://github.com/isXander)

## License

This project is licensed under the [GPL 3.0](LICENSE)
GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for
details
