import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.pokemondatabase.exceptions.IncorrectVariableAmountException;
import org.pokemondatabase.exceptions.InvalidPokedexNumberException;
import org.pokemondatabase.exceptions.InvalidPokemonTypeException;
import org.pokemondatabase.exceptions.NoDuplicateTypesException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionClassTests {
    @Test
    public void testNoDuplicateTypesException() {
        String errorMessage = "Duplicate Pokemon Types are not allowed.";
        NoDuplicateTypesException ex = new NoDuplicateTypesException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    @Test
    public void testInvalidPokemonTypeException() {
        String errorMessage = "Invalid Pokemon Types are not allowed.";
        InvalidPokemonTypeException ex = new InvalidPokemonTypeException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    @Test
    public void testInvalidPokedexNumberException() {
        String errorMessage = "Must be a valid Number between 1 and 1164.";
        InvalidPokedexNumberException ex = new InvalidPokedexNumberException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }

    @Test
    public void testIncorrectVariableAmountException() {
        String errorMessage = "There must be 7-8 variables per line.";
        IncorrectVariableAmountException ex = new IncorrectVariableAmountException(errorMessage);
        assertEquals(errorMessage, ex.getMessage());
    }


}
