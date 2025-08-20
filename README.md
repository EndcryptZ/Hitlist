# Hitlist

**Hitlist** is a fully featured bounty plugin designed for [PaperMC](https://papermc.io/)/[Spigot](https://www.spigotmc.org/) Minecraft servers. This plugin allows players to place assassination-style bounties on others with a strong emphasis on economy integration, custom rules, and a sleek GUI-based experience. It's ideal for survival, factions, and PvP servers that want to encourage player-driven conflict and innovative reward systems.

---

## Features

- **Assassination-style Bounties:** Players can place bounties on other players, creating dynamic incentives and rivalries.
- **Economy Integration:** Seamlessly connects with your server's economy plugin for reward payouts.
- **Custom Rules:** Flexible configuration to suit any server's needs, including restrictions and bounty claim conditions.
- **GUI Experience:** Intuitive graphical user interfaces make placing and claiming bounties easy for everyone.
- **Suitable for PvP & Factions:** Boosts player engagement and competition in PvP-centric environments.

- ### Opt-In / Opt-Out System

Players have control over whether they can be targeted by bounties:

- **Opt-In:** Players who opt-in can be placed on the Hitlist and receive bounties.  
- **Opt-Out:** Players who opt-out cannot be targeted by bounties, keeping them safe from assassination-style gameplay.  
- **Command Example:** `/bounty opt-in` to allow bounties, `/bounty opt-out` to prevent bounties.  

---

## Installation

1. Download the latest release of Hitlist.
2. Place the `.jar` file into your server's `plugins` directory.
3. Start or restart your PaperMC/Spigot server.
4. Configure the plugin to suit your server's rules and economy via the generated configuration files.

---

## Usage

- **Placing a Bounty:** Use the `/hitlist` command or interact with the bounty GUI to place a bounty on another player.
- **Claiming a Bounty:** Fulfill the bounty's conditions (usually defeating the target) to claim the reward.
- **Admin Controls:** Server admins can adjust bounty settings, view active bounties, and manage economy integration.

---

## Configuration

After installing, a configuration file will be generated in your plugins folder. Customize the following settings:

- **Economy Plugin Integration:** Link with Vault or other supported economy plugins.
- **Bounty Limits:** Set minimum and maximum bounty amounts.
- **GUI Customization:** Adjust messages, colors, and layout of the bounty interface.
- **Permissions:** Control who can place and claim bounties.

---

## Commands

### `/bounty`
Opens the main bounty GUI for quick access to all bounty features.

### `/bounty place <player> <amount>`
Place a bounty on a player. The amount will be deducted from the placer and added to the bounty pool.

### `/bounty list [page]`
View a paginated list of active bounties. Optional `page` argument for navigation.

### `/bounty edit remove <player>`
Remove an existing bounty from a player (requires permission/admin).

### `/bounty edit lower <player> <amount>`
Lower the amount of an existing bounty on a player (requires permission/admin).

### `/bounty top claimed [page]`
View the leaderboard of players with the highest total claimed bounty rewards. Optional `page` argument for pagination.

### `/bounty top placed [page]`
View the leaderboard of players who have placed the most bounties. Optional `page` argument for pagination.

### `/bounty opt-in`
Allow yourself to be targeted by bounties (enable participation). Respect cooldowns if configured.

### `/bounty opt-out`
Prevent yourself from being targeted by bounties (disable participation). Respect cooldowns if configured.

### `/hitlistreload`
Reloads all Hitlist plugin configuration files without restarting the server.  
- **Permission:** `hitlist.cmd.reload`  
- Sends a confirmation message to both players and console.
  
---

## Permissions

### Bounty Permissions

- **`hitlist.bounty.edit.others`**  
Allows a player to edit or remove bounties placed by other players.

- **`hitlist.bounty.place.limit`**  
Allows a player to bypass or set limits on how many bounties they can place.

### Command Permissions

- **`hitlist.cmd.reload`**  
Allows a player or admin to reload all Hitlist plugin configuration files without restarting the server.

---

## Requirements

- **Minecraft Server:** PaperMC (recommended version matching your plugin release)
- **Java:** Latest recommended version for PaperMC
- **Economy Plugin:** For reward payouts (e.g., EssentialsX)

---

## Support

If you encounter issues or need help with configuration, please open an issue on the [GitHub Issues page](https://github.com/EndcryptZ/Hitlist/issues).

---

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)

---

## Credits

Developed by [EndcryptZ](https://github.com/EndcryptZ).
