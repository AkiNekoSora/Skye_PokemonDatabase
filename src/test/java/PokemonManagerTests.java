import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pokemondatabase.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PokemonManagerTests {

    public PokemonManager pokemonManager;
    public UserInputHelper mockUserInputHelper;

    @BeforeEach
    public void setUp() {
        this.mockUserInputHelper = mock(UserInputHelper.class);
        pokemonManager = new PokemonManager(mockUserInputHelper);
    }

    @Test
    @DisplayName("Test Adding a Pokémon using the PokemonManager Clas")
    public void testAddPokemonUsingPokemonManager() {
        List<Pokemon> pokemonStorage = new ArrayList<>();

        when(mockUserInputHelper.getValidPokemonName(anyString())).thenReturn("Charizard");
        when(mockUserInputHelper.getIntInRange(contains("Pokédex Number"), eq(1), eq(1164), anyString()))
                .thenReturn(7);
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.FIRE);
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(true);
        when(mockUserInputHelper.getValidPokemonType(contains("secondary"))).thenReturn(PokemonTypes.FLYING);
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(true);
        when(mockUserInputHelper.getIntInRange(contains("Next Evolution"), eq(1), eq(100), anyString()))
                .thenReturn(0);
        when(mockUserInputHelper.getBigDecimal(contains("weight"), anyString()))
                .thenReturn(new BigDecimal("6.9"));
        when(mockUserInputHelper.getBigDecimal(contains("height"), anyString()))
                .thenReturn(new BigDecimal("0.7"));
        when(mockUserInputHelper.getBooleanInput(contains("Caught"))).thenReturn(true);
        when(mockUserInputHelper.getBooleanInput(contains("Pokédex Entry"))).thenReturn(true);
        when(mockUserInputHelper.getString(contains("Pokédex Entry")))
                .thenReturn("If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.");

        String result = pokemonManager.addPokemon(pokemonStorage);


        assertEquals(1, pokemonStorage.size());

        Pokemon added = pokemonStorage.get(0);
        assertEquals("Charizard", added.getPokemonName());
        assertEquals(7, added.getPokedexNumber());
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.FIRE);
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(true);
        when(mockUserInputHelper.getValidPokemonType(contains("secondary"))).thenReturn(PokemonTypes.FLYING);
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(true);
        assertEquals(0, added.getNextEvolutionLevel());
        assertEquals(new BigDecimal("6.9"), added.getPokemonWeightKilograms());
        assertEquals(new BigDecimal("0.7"), added.getPokemonHeightMeters());
        assertTrue(added.getPokemonIsCaught());
        assertEquals("If Charizard becomes truly angered, the flame " +
                "at the tip of its tail burns in a light blue shade.", added.getPokedexEntry());

        assertTrue(result.contains("Pokémon Added"));
    }

    public List<Pokemon> addPokemonList(List<Pokemon> pokemonStorage) {
        String filePath = "C:\\Users\\akine\\Desktop\\Skye_PokemonDatabase\\PokemonDatabaseForTESTS.txt";

        assertTrue(new File(filePath).exists(), "File doesn't exist!");

        when(mockUserInputHelper.getString(anyString())).thenReturn(filePath);
        when(mockUserInputHelper.hasNoDigitsOrSpaces(anyString())).thenReturn(true);
        when(mockUserInputHelper.isDigitOrPeriod(anyString())).thenReturn(true);

        return pokemonStorage;
    }

    @Test
    @DisplayName("Verify a file can be opened")
    public void verifyFileCanBeOpened() {
        String filePath = "C:\\Users\\akine\\Desktop\\Skye_PokemonDatabase\\PokemonDatabaseForTESTS.txt";

        assertTrue(new File(filePath).exists(), "File doesn't exist!");
    }

    @Test
    @DisplayName("Test Adding Pokémon with a file using the PokemonManager Class")
    public void testAddingPokemonWithFileUsingPokemonManager() {
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);

        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertEquals(4, pokemonStorage.size());
        assertEquals("Charmander", pokemonStorage.get(0).getPokemonName());
        assertEquals("Charizard", pokemonStorage.get(2).getPokemonName());
    }

    @Test
    @DisplayName("Test to Remove a Pokemon from a list")
    public void testRemovePokemonFromList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        pokemonManager.addPokemonFromFile(pokemonStorage);

        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);

        String result = pokemonManager.removePokemonByPokedexID(pokemonStorage);

        assertTrue(result.contains("Pokémon Successfully Removed!"));
    }

    @Test
    @DisplayName("Test to Update a Pokemon in a list")
    public void testUpdatePokemonInList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertFalse(pokemonStorage.isEmpty());

        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);
        when(mockUserInputHelper.getIntInRange(anyString(), eq(0), eq(8))).thenReturn(1);
        when(mockUserInputHelper.getValidPokemonName(anyString())).thenReturn("TESTCharmander");
        when(mockUserInputHelper.getBooleanInput(contains("more information"))).thenReturn(false);

        String updateResult = pokemonManager.updatePokemonInformation(pokemonStorage);

        assertEquals("TESTCharmander", pokemonStorage.get(0).getPokemonName());
        assertTrue(updateResult.contains("was updated!"));
    }

    @Test
    @DisplayName("Test to Find a Pokemon in a list")
    public void testFindPokemonInList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertFalse(pokemonStorage.isEmpty());

        // Searches for Charmander
        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);

        String findResult = pokemonManager.findPokemonByPokedexID(pokemonStorage);

        assertTrue(findResult.contains("Charmander"));
    }

    @Test
    @DisplayName("Test the comparePokemonByPokedexID method")
    public void testComparePokemon(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertFalse(pokemonStorage.isEmpty());

        when(mockUserInputHelper.getInt(contains("first"), anyString())).thenReturn(4);
        when(mockUserInputHelper.getInt(contains("second"), anyString())).thenReturn(5);

        String compareResult = pokemonManager.comparePokemonByPokedexID(pokemonStorage);

        System.out.println(compareResult);

        assertTrue(compareResult.contains("Charmander"));
    }

    @Test
    @DisplayName("Test the checkPokemonNextEvolution method")
    public void testCheckPokemonNextEvolution(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertFalse(pokemonStorage.isEmpty());

        when(mockUserInputHelper.getInt(contains("Pokédex"), anyString())).thenReturn(4);
        when(mockUserInputHelper.getInt(contains("evolution"), anyString())).thenReturn(10);

        String compareResult = pokemonManager.checkPokemonNextEvolution(pokemonStorage);

        System.out.println(compareResult);

        assertTrue(compareResult.contains("There are 6"));
    }

}
