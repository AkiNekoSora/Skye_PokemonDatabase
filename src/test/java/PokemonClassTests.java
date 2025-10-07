import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.pokemondatabase.Pokemon;
import org.pokemondatabase.PokemonTypes;
import org.pokemondatabase.PokemonTypesManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonClassTests {

    private Pokemon charmander;
    private String expectedPokemonName;
    private int expectedPokedexNumber;
    PokemonTypesManager expectedPokemonType;
    private int expectedNextEvolutionLevel;
    private BigDecimal expectedWeight;
    private BigDecimal expectedHeight;
    private Boolean expectedIsCaught;
    private String expectedPokedexEntry;

    @BeforeEach
    public void setUp() throws Exception {
        expectedPokemonName = "Charmander";
        expectedPokedexNumber = 4;
        expectedPokemonType = new PokemonTypesManager(PokemonTypes.FIRE);
        expectedNextEvolutionLevel = 16;
        expectedWeight = (new BigDecimal("18.7"));
        expectedHeight = (new BigDecimal("0.6"));
        expectedIsCaught = false;
        expectedPokedexEntry = "The flame on its tail shows the strength of its " +
                "life-force. If Charmander is weak, the flame also burns weakly.";

        //Act
        charmander = new Pokemon(
                expectedPokemonName,
                expectedPokedexNumber,
                expectedPokemonType,
                expectedNextEvolutionLevel,
                expectedWeight,
                expectedHeight,
                expectedIsCaught,
                expectedPokedexEntry
        );
    }

    @Test
    @DisplayName("Are Fields assigned correctly? And do the getters work?")
    void pokemonConstructorTest() {
        //Assert
        assertEquals(expectedPokemonName, charmander.getPokemonName());
        assertEquals(expectedPokedexNumber, charmander.getPokedexNumber());
        assertEquals(expectedPokemonType, charmander.getPokemonType());
        assertEquals(expectedNextEvolutionLevel, charmander.getNextEvolutionLevel());
        assertEquals(expectedWeight, charmander.getPokemonWeightKilograms());
        assertEquals(expectedHeight, charmander.getPokemonHeightMeters());
        assertEquals(expectedIsCaught, charmander.getPokemonIsCaught());
        assertEquals(expectedPokedexEntry, charmander.getPokedexEntry());
    }

    @Test
    @DisplayName("Does Pokémon Class hold a Pokémon?")
    void pokemonSettersTest() {
        String updatedExpectedPokemonName = "Charmeleon";
        int updatedexpectedPokedexNumber = 6;
        PokemonTypesManager updatedexpectedPokemonType = new PokemonTypesManager(PokemonTypes.FIRE);
        Integer updatedexpectedNextEvolutionLevel = null;
        BigDecimal updatedexpectedWeight = (new BigDecimal("199.5"));
        BigDecimal updatedexpectedHeight = (new BigDecimal("1.7"));
        Boolean updatedexpectedIsCaught = true;
        String updatedexpectedPokedexEntry = "If Charizard becomes truly angered, the flame " +
                "at the tip of its tail burns in a light blue shade.";

        assertEquals("Pokémon Name updated to: " + updatedExpectedPokemonName,
                charmander.setPokemonName(updatedExpectedPokemonName));
        assertEquals("Pokédex Number has been updated to: " + updatedexpectedPokedexNumber,
                charmander.setPokedexNumber(updatedexpectedPokedexNumber));
        assertEquals("Pokémon Type has been updated to: " + updatedexpectedPokemonType,
                charmander.setPokemonType(updatedexpectedPokemonType));
        assertEquals("Pokémon Next Evolution as been updated to: " + updatedexpectedNextEvolutionLevel,
                charmander.setNextEvolutionLevel(updatedexpectedNextEvolutionLevel));
        assertEquals("Pokémon Weight has been updated to: " + updatedexpectedWeight,
                charmander.setPokemonWeightPounds(updatedexpectedWeight));
        assertEquals("Pokémon Height has been updated to: " + updatedexpectedHeight,
                charmander.setPokemonHeightMeters(updatedexpectedHeight));
        //Had to manually enter the correct answer since it said "true" instead of "Caught".
        assertEquals("'Has Pokémon been caught?' has been updated to: Caught",
                charmander.setPokemonIsCaught(updatedexpectedIsCaught));
        assertEquals("Pokémon Name updated to: \"" + updatedexpectedPokedexEntry + "\"",
                charmander.setPokedexEntry(updatedexpectedPokedexEntry));
    }

    @Test
    @DisplayName("Does Pokémon Class toString Method Work?")
    void pokemontoStringTest() {
        String expectedtoString =
                "\n" +
                "    Pokémon Name: Charmander" + "\n" +
                "    Pokédex Number: 4" + "\n" +
                "    Pokédex Type: Fire" + "\n" +
                "    Next Evolution Level: 16" + "\n" +
                "    Pokémon Weight in Pounds: 18.7" + "\n" +
                "    Pokémon Height in Meters: 0.6" + "\n" +
                "    Have you Caught this Pokémon?: Not Caught" + "\n" +
                "    Pokédex Entry: The flame on its tail shows the strength of its life-force. " +
                        "If Charmander is weak, the flame also burns weakly.\n";

        assertEquals(expectedtoString, charmander.toString());
    }
}
