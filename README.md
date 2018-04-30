# GoonUtils - Server utilities plugin for Sponge
GoonUtils is a plugin created for server owners that want simple stuff like automatic restarts, broadcast or other tasks to be done with a single plugin and easy config.

**Currently only built for API7!**
## Features
* Automatic server restarts
* Broadcasts
* More to come

## Commands
There are multiple command in this plugin for each module its seperate alias.
All commands start with `/goon`
#### Basic
There are a few basic command next to the modules. For example `/goon reload`
* info - Info about the plugin
* reload - Reload configs (note a `/sponge plugins reload` will also work)
* where - Tells player who does the command in which dimension he/she is
#### Broadcast
All broadcast command start with `broadcast`. So for example `/goon broadcast list`
* list - Show a list of current broadcasts
* send - Will send a serverwide broadcast
* add - Will add a broadcast to the list
* remove # - Will remove broadcast with id # from the list. Id's can be seen in the list

#### Restart
All Restart commands start with `restart`. So for example `/goon restart stop`
* stop - Stop current restart timer
* start - Start a new restart timer (only works if theres no running restart timer)

## Permissions
Permissions are simple they use the same names as the commands
* goonutils.info
* goonutils.reload
* goonutils.where
* goonutils.broadcast.list
* goonutils.broadcast.send
* goonutils.broadcast.add
* goonutils.broadcast.remove
* goonutils.restart.start
* goonutils.restart.stop

## Config
The config is pretty self explenatory and is explained in the config itself under goonutils/goonutils.conf

## Suggestion
Im open to suggestions you might find handy for this plugin. Feel free to make an issue for a new feature.


## Extra
Its not an official Discord for this plugin but feel free to ask question on my [Discord](https://discord.gg/5AyyXYz) channel

If for some reason you want to support me you can do so [Here](https://www.paypal.me/daanbroere)

~~Note that GoonUtils uses bStats. You can disable this if you want in the bStats config folder.~~ (disabled for now)


