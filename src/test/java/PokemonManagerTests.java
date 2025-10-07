import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pokemondatabase.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
 * Autumn Skye
 * CEN-3024C 13950
 * October 7th, 2025
 *
 * Class Name: Pokémon Manager Class Tests
 * Purpose: Used to test that the PokémonManager class methods work as needed. Uses Mockito to
 *          mock the UserInputHelper class and send fake user inputs to the system. (Since the setIn()
 *          method and ByteArrayInputStream does not work since I have a different class that gets
 *          the user input)
 */
public class PokemonManagerTests {
    // Variables used in the tests
    public PokemonManager pokemonManager;
    public UserInputHelper mockUserInputHelper;

    /* Method Name: set up
     * Purpose: Used before each class to create a mock userInputHelper class that gets sent to
     *          the pokemonManager class.
     * Parameters: None
     * Return Value: Void
     */
    @BeforeEach
    public void setUp() {
        this.mockUserInputHelper = mock(UserInputHelper.class);
        pokemonManager = new PokemonManager(mockUserInputHelper);
    }

    /* Method Name: Add Pokémon using PokemonManager class test
     * Purpose: Uses mockUserInputHelper to create fake user inputs to test adding a new Pokémon
     *          with the PokemonManager class.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test Adding a Pokémon using the PokemonManager Clas")
    public void testAddPokemonUsingPokemonManager() {
        List<Pokemon> pokemonStorage = new ArrayList<>();

        // Using mockUserInputHelper to return fake user inputs to the method when called.
        // Enter Pokémon Name:
        when(mockUserInputHelper.getValidPokemonName(anyString())).thenReturn("Charizard");
        // Enter Pokédex Number:
        when(mockUserInputHelper.getIntInRange(contains("Pokédex Number"), eq(1), eq(1164), anyString())).thenReturn(7);
        // Please enter the Primary Pokémon Type:
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.FIRE);
        // Would you like to add a Secondary Pokémon Type?
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(true);
        // Enter secondary Pokémon type:
        when(mockUserInputHelper.getValidPokemonType(contains("secondary"))).thenReturn(PokemonTypes.FLYING);
        // Does this Pokémon have an evolution?
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(false);
        // Enter Pokémon's weight using kilograms:
        when(mockUserInputHelper.getBigDecimal(contains("weight"), anyString())).thenReturn(new BigDecimal("90.5"));
        // Enter Pokémon's height using meters:
        when(mockUserInputHelper.getBigDecimal(contains("height"), anyString())).thenReturn(new BigDecimal("1.7"));
        // Has the Pokémon been Caught?
        when(mockUserInputHelper.getBooleanInput(contains("Caught"))).thenReturn(true);
        // Would you like to add a Pokédex Entry?
        when(mockUserInputHelper.getBooleanInput(contains("Pokédex Entry"))).thenReturn(true);
        // Enter Pokémon's Pokédex Entry:
        when(mockUserInputHelper.getString(contains("Pokédex Entry")))
                .thenReturn("If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.");

        String result = pokemonManager.addPokemon(pokemonStorage);

        // Calls the first item in the list and checks to make sure all items match
        Pokemon testPokemon = pokemonStorage.get(0);
        assertEquals("Charizard", testPokemon.getPokemonName());
        assertEquals(7, testPokemon.getPokedexNumber());
        assertEquals("Fire & Flying", testPokemon.getPokemonType().toString());
        assertEquals(0, testPokemon.getNextEvolutionLevel());
        assertEquals(new BigDecimal("90.5"), testPokemon.getPokemonWeightKilograms());
        assertEquals(new BigDecimal("1.7"), testPokemon.getPokemonHeightMeters());
        assertTrue(testPokemon.getPokemonIsCaught());
        assertEquals("If Charizard becomes truly angered, the flame " +
                "at the tip of its tail burns in a light blue shade.", testPokemon.getPokedexEntry());

        assertTrue(result.contains("Pokémon Added"));
    }

    /* Method Name: Add Pokémon list using a file method
     * Purpose: Used to start the call mockUserInputHelper to add the test list.
     * Parameters: None
     * Return Value: The list of Pokémon in the file
     */
    public List<Pokemon> addPokemonList(List<Pokemon> pokemonStorage) {
        String filePath = "C:\\Users\\akine\\Desktop\\Skye_PokemonDatabase\\PokemonDatabaseForTESTS.txt";

        // Verifies the file exists so no exceptions are thrown.
        assertTrue(new File(filePath).exists(), "File doesn't exist!");

        // Sends the file path and verifies that it is a valid string.
        when(mockUserInputHelper.getString(anyString())).thenReturn(filePath);
        when(mockUserInputHelper.hasNoDigitsOrSpaces(anyString())).thenReturn(true);
        when(mockUserInputHelper.isDigitOrPeriod(anyString())).thenReturn(true);

        return pokemonStorage;
    }

    /* Method Name: Verify a file can be opened test
     * Purpose: Testing to make sure the system can open a valid file
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Verify a file can be opened")
    public void verifyFileCanBeOpened() {
        String filePath = "C:\\Users\\akine\\Desktop\\Skye_PokemonDatabase\\PokemonDatabaseForTESTS.txt";

        assertTrue(new File(filePath).exists(), "File doesn't exist!");
    }

    /* Method Name: Add Pokémon with file using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test Adding Pokémon with a file using the PokemonManager Class")
    public void testAddingPokemonWithFileUsingPokemonManager() {
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);

        // Calls the addPokemonFromFile method to add Pokémon list from a file and adds results
        // to a list in this test.
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        assertTrue(result.contains("All Pokémon added successfully!"));
        assertEquals(4, pokemonStorage.size());
        assertEquals("Charmander", pokemonStorage.get(0).getPokemonName());
        assertEquals("Charizard", pokemonStorage.get(2).getPokemonName());
    }

    /* Method Name: Remove Pokémon from a list using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully. Then calls the removePokemonByPokedexID from the PokemonManager
     *          class to verify that a Pokémon can be removed from a list.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test to Remove a Pokemon from a list")
    public void testRemovePokemonFromList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);

        // Calls the addPokemonFromFile method to add Pokémon list from a file and adds results
        // to a list in this test.
        pokemonManager.addPokemonFromFile(pokemonStorage);

        // Enter the Pokédex ID number you would like to remove:
        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);

        // Calls the removePokemon method from the pokemonManager class and saves the result string
        String result = pokemonManager.removePokemonByPokedexID(pokemonStorage);

        assertTrue(result.contains("Pokémon Successfully Removed!"));
    }

    /* Method Name: Update Pokémon from a list using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully. Then calls the updatePokemonInformation from the
     *          PokemonManager class. Testing to verify that all variables can be updated.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test Update a Pokemon in a list")
    public void testUpdatePokemonInList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        // Calls the addPokemonFromFile method to add Pokémon list from a file
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        // Enter the Pokédex ID number you would like to update:
        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);
        // Please enter a number between 0 and 8 (0 to cancel update): (ALL ANSWERS)
        when(mockUserInputHelper.getIntInRange(anyString(), eq(0), eq(8))).thenReturn(1,2, 3, 4, 5, 6, 7, 8);
        // Would you like to update any more information for this Pokémon? (ALL ANSWERS)
        when(mockUserInputHelper.getBooleanInput(contains("more information"))).thenReturn(true,
         true, true, true, true, true, true, false);

        // Enter Pokémon Name:
        when(mockUserInputHelper.getValidPokemonName(anyString())).thenReturn("TESTCharmander");
        // Enter Pokédex Number:
        when(mockUserInputHelper.getIntInRange(contains("Pokédex Number"), eq(1), eq(1164), anyString())).thenReturn(7);
        //Please enter the Primary Pokémon Type:
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.WATER);
        // Would you like to add a Secondary Pokémon Type?
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(false);
        // Does this Pokémon have an evolution?
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(false);
        // Enter Pokémon's weight using kilograms:
        when(mockUserInputHelper.getBigDecimal(contains("weight"), anyString())).thenReturn(new BigDecimal("90.5"));
        // Enter Pokémon's height using meters:
        when(mockUserInputHelper.getBigDecimal(contains("height"), anyString())).thenReturn(new BigDecimal("1.7"));
        // Has the Pokémon been Caught?
        when(mockUserInputHelper.getBooleanInput(contains("Caught"))).thenReturn(false);
        // Would you like to add a Pokédex Entry?
        when(mockUserInputHelper.getBooleanInput(contains("Pokédex Entry"))).thenReturn(true);
        // Enter Pokémon's Pokédex Entry:
        when(mockUserInputHelper.getString(contains("Pokédex Entry")))
                .thenReturn("If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.");

        String updateResult = pokemonManager.updatePokemonInformation(pokemonStorage);

        assertTrue(updateResult.contains("was updated!"));
        assertEquals("TESTCharmander", pokemonStorage.get(0).getPokemonName());
        assertEquals(7, pokemonStorage.get(0).getPokedexNumber());
        assertEquals("Water", pokemonStorage.get(0).getPokemonType().toString());
        assertEquals(0, pokemonStorage.get(0).getNextEvolutionLevel());
        assertEquals(new BigDecimal("90.5"), pokemonStorage.get(0).getPokemonWeightKilograms());
        assertEquals(new BigDecimal("1.7"), pokemonStorage.get(0).getPokemonHeightMeters());
        assertEquals(false, pokemonStorage.get(0).getPokemonIsCaught());
        assertEquals("If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
                pokemonStorage.get(0).getPokedexEntry());
    }

    /* Method Name: Find Pokémon from a list using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully. Then calls the findPokemonByPokedexId method in the
     *          PokemonManager class and verifies the correct Pokémon has been returned.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test to Find a Pokemon in a list")
    public void testFindPokemonInList(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        // Searches for Charmander
        when(mockUserInputHelper.getInt(contains("Enter"), anyString())).thenReturn(4);

        String findResult = pokemonManager.findPokemonByPokedexID(pokemonStorage);

        assertTrue(findResult.contains("Charmander"));
    }

    /* Method Name: Compare Pokémon from a list using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully. Then calls the comparePokemonByPokedexID method in the
     *          PokemonManager class and verifies the correct answer has been printed.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test the comparePokemonByPokedexID method")
    public void testComparePokemon(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        // Enters the first and second Pokédex Numbers
        when(mockUserInputHelper.getInt(contains("first"), anyString())).thenReturn(4);
        when(mockUserInputHelper.getInt(contains("second"), anyString())).thenReturn(5);

        String compareResult = pokemonManager.comparePokemonByPokedexID(pokemonStorage);

        System.out.println(compareResult);

        assertTrue(compareResult.contains("23.2"));
        assertTrue(compareResult.contains(".5"));
    }

    /* Method Name: Check Pokémon Next Evolution from a list using PokemonManager class test
     * Purpose: Uses the addPokemonList method above to call a valid file and verifies they are
     *          entered successfully. Then calls the checkPokemonNextEvolution method in the
     *          PokemonManager class and verifies the correct answer has been printed.
     * Parameters: None
     * Return Value: Void
     */
    @Test
    @DisplayName("Test the checkPokemonNextEvolution method")
    public void testCheckPokemonNextEvolution(){
        List<Pokemon> pokemonStorage = new ArrayList<>();
        pokemonStorage = addPokemonList(pokemonStorage);
        String result = pokemonManager.addPokemonFromFile(pokemonStorage);

        // Enters the Pokédex Numbers and the current evolution level
        when(mockUserInputHelper.getInt(contains("Pokédex"), anyString())).thenReturn(4);
        when(mockUserInputHelper.getInt(contains("evolution"), anyString())).thenReturn(10);

        String compareResult = pokemonManager.checkPokemonNextEvolution(pokemonStorage);

        assertTrue(compareResult.contains("There are 6"));
    }

}
