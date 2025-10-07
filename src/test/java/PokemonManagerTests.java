import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pokemondatabase.*;

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
        pokemonManager = new PokemonManager();
        mockUserInputHelper = mock(UserInputHelper.class);
        pokemonManager.userInputHelper = mockUserInputHelper;
    }

    @Test
    public void testAddPokemon_SuccessfulInput() {
        List<Pokemon> pokemonStorage = new ArrayList<>();

        when(mockUserInputHelper.getValidPokemonName(anyString())).thenReturn("Bulbasaur");
        when(mockUserInputHelper.getIntInRange(contains("Pokédex Number"), eq(1), eq(1164), anyString()))
                .thenReturn(1);
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.GRASS);
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(true);
        when(mockUserInputHelper.getValidPokemonType(contains("secondary"))).thenReturn(PokemonTypes.POISON);
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(true);
        when(mockUserInputHelper.getIntInRange(contains("Next Evolution"), eq(1), eq(100), anyString()))
                .thenReturn(16);
        when(mockUserInputHelper.getBigDecimal(contains("weight"), anyString()))
                .thenReturn(new BigDecimal("6.9"));
        when(mockUserInputHelper.getBigDecimal(contains("height"), anyString()))
                .thenReturn(new BigDecimal("0.7"));
        when(mockUserInputHelper.getBooleanInput(contains("Caught"))).thenReturn(true);
        when(mockUserInputHelper.getBooleanInput(contains("Pokédex Entry"))).thenReturn(true);
        when(mockUserInputHelper.getString(contains("Pokédex Entry")))
                .thenReturn("A strange seed was planted on its back at birth.");


        String result = pokemonManager.addPokemon(pokemonStorage);


        assertEquals(1, pokemonStorage.size());

        Pokemon added = pokemonStorage.get(0);
        assertEquals("Bulbasaur", added.getPokemonName());
        assertEquals(1, added.getPokedexNumber());
        when(mockUserInputHelper.getValidPokemonType(contains("Primary"))).thenReturn(PokemonTypes.GRASS);
        when(mockUserInputHelper.getBooleanInput(contains("Secondary"))).thenReturn(true);
        when(mockUserInputHelper.getValidPokemonType(contains("secondary"))).thenReturn(PokemonTypes.POISON);
        when(mockUserInputHelper.getBooleanInput(contains("evolution"))).thenReturn(true);
        assertEquals(16, added.getNextEvolutionLevel());
        assertEquals(new BigDecimal("6.9"), added.getPokemonWeightKilograms());
        assertEquals(new BigDecimal("0.7"), added.getPokemonWeightKilograms());
        assertTrue(added.getPokemonIsCaught());
        assertEquals("A strange seed was planted on its back at birth.", added.getPokedexEntry());

        assertTrue(result.contains("Pokémon Added"));
    }
}
