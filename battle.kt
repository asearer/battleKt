import kotlin.random.Random

// Define a basic character class
class Character(
    val name: String,
    var health: Int,
    var attackPower: Int,
    var magicPower: Int,
    var experience: Int = 0,
    var level: Int = 1
) {
    // Function to attack another character
    fun attack(target: Character) {
        val damage = Random.nextInt(1, attackPower + 1)
        println("$name attacks ${target.name} for $damage damage!")
        target.takeDamage(damage)
    }

    // Function to cast a magic spell
    fun castSpell(target: Character) {
        if (magicPower >= 20) { // Check if there's enough magic power
            val spellDamage = Random.nextInt(10, 31) // Random spell damage
            println("$name casts a spell on ${target.name} for $spellDamage magic damage!")
            target.takeMagicDamage(spellDamage)
            magicPower -= 20 // Reduce magic power cost
        } else {
            println("$name doesn't have enough magic power to cast a spell!")
        }
    }

    // Function to take damage
    fun takeDamage(damage: Int) {
        health -= damage
        if (health <= 0) {
            println("$name has been defeated!")
        } else {
            println("$name has $health health remaining.")
        }
    }

    // Function to take magic damage
    fun takeMagicDamage(damage: Int) {
        health -= damage
        if (health <= 0) {
            println("$name has been defeated by magic!")
        } else {
            println("$name has $health health remaining after the magic attack.")
        }
    }

    // Function to gain experience points
    fun gainExperience(xp: Int) {
        experience += xp
        println("$name gains $xp experience points! Total XP: $experience")
    }
}

class LevelingSystem {
    private val xpToLevelUp = mapOf(
        1 to 100,
        2 to 200,
        3 to 300,
        4 to 400
        // Add more levels and XP requirements as needed
    )

    fun levelUp(character: Character) {
        val currentLevel = character.level
        val currentXP = character.experience

        val xpRequired = xpToLevelUp[currentLevel]

        if (xpRequired != null && currentXP >= xpRequired) {
            character.level++
            character.magicPower += 10 // Increase magic power on level up
            character.attackPower += 10 // Increase attack power on level up
            println("${character.name} leveled up to level ${character.level}!")
        }
    }
}

fun main() {
    val levelingSystem = LevelingSystem() // Create an instance of the leveling system

    // Create two characters
    val player = Character("Player", 100, 20, 50)
    val enemy = Character("Enemy", 80, 15, 40)

    println("A wild ${enemy.name} appears! It's time for battle!")

    // Main game loop
    var isPlayerTurn = true
    while (player.health > 0 && enemy.health > 0) {
        if (isPlayerTurn) {
            // Player's turn
            println("Your turn, ${player.name}!")
            println("Choose your action: (1) Attack, (2) Cast Spell")
            val choice = readLine()?.toIntOrNull()

            when (choice) {
                1 -> {
                    player.attack(enemy)
                    if (enemy.health <= 0) {
                        val xpGain = Random.nextInt(10, 21)
                        player.gainExperience(xpGain)
                        levelingSystem.levelUp(player) // Check for level up
                    }
                }
                2 -> {
                    player.castSpell(enemy)
                    if (enemy.health <= 0) {
                        val xpGain = Random.nextInt(10, 21)
                        player.gainExperience(xpGain)
                        levelingSystem.levelUp(player) // Check for level up
                    }
                }
                else -> println("Invalid choice. Player loses a turn.")
            }
        } else {
            // Enemy's turn
            println("It's ${enemy.name}'s turn!")
            enemy.attack(player)
        }

        // Toggle turns
        isPlayerTurn = !isPlayerTurn
    }

    // Determine the winner
    if (player.health <= 0) {
        println("Game over! ${enemy.name} wins!")
    } else {
        println("Victory! ${player.name} wins!")
    }
}
