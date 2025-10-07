import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.pokemondatabase.PokemonTypes;
import org.pokemondatabase.PokemonTypesManager;

import static org.junit.jupiter.api.Assertions.*;

class TypesManagerClassTests {
    private final PokemonTypesManager pokemonTypesManager = new PokemonTypesManager(PokemonTypes.FIRE);

    private PokemonTypes expectedPrimaryType;
    private PokemonTypes expectedSecondaryType;

    private PokemonTypesManager testingTypesWithTwoTypes;
    private PokemonTypesManager testingTypesWithOneType;

    @BeforeEach
    public void setUp() throws Exception {
        expectedPrimaryType = PokemonTypes.FIRE;
        expectedSecondaryType = PokemonTypes.FLYING;

        testingTypesWithTwoTypes = new PokemonTypesManager(expectedPrimaryType, expectedSecondaryType);
        testingTypesWithOneType = new PokemonTypesManager(expectedPrimaryType);
    }

    @Test
    @DisplayName("Are Fields assigned correctly? And do the getters work?")
    void testTypesConstructor() {
        assertEquals(expectedPrimaryType, testingTypesWithTwoTypes.getPokemonPrimaryType());
        assertEquals(expectedSecondaryType, testingTypesWithTwoTypes.getPokemonSecondaryType());
    }

    @Test
    @DisplayName("Are Fields assigned correctly - Overloaded Constructor? And do the getters work?")
    void testTypesConstructor2() {
        assertEquals(expectedPrimaryType, testingTypesWithOneType.getPokemonPrimaryType());
        assertNull(testingTypesWithOneType.getPokemonSecondaryType());
    }

    @Test
    @DisplayName("Does the toString work correctly with a single or double type Pokemon?")
    void testToString() {
        assertEquals("Fire & Flying", testingTypesWithTwoTypes.toString());
        assertEquals("Fire", testingTypesWithOneType.toString());
    }

    @Test
    @DisplayName("Does the NoDuplicateTypesException throw correctly?")
    void testNoDuplicateTypesReturnsFalse() {
        assertEquals(false, pokemonTypesManager.verifyNoDuplicatePokemonTypes(expectedSecondaryType
                , expectedSecondaryType));
    }

    @Test
    @DisplayName("Does the NoDuplicateTypesException not get thrown when type is not duplicated?")
    void testNoDuplicateTypesReturnsTrue() {
        assertEquals(true, pokemonTypesManager.verifyNoDuplicatePokemonTypes(expectedPrimaryType
                , expectedSecondaryType));
    }

    @Test
    @DisplayName("Testing getAllPokemonTypes method with One or Two Types")
    void testGetAllPokemonTypes() {
        assertEquals("Fire", testingTypesWithOneType.getAllPokemonTypes());
        assertEquals("Fire & Flying", testingTypesWithTwoTypes.getAllPokemonTypes());
    }



}
