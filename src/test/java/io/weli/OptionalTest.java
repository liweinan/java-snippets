package io.weli;

import org.junit.Test;

import java.util.Optional;

import static org.jgroups.util.Util.assertTrue;
import static org.junit.Assert.assertFalse;

// https://www.baeldung.com/java-optional
public class OptionalTest {
   @Test
   public void whenCreatesEmptyOptional_thenCorrect() {
      Optional<String> empty = Optional.empty();
      assertFalse(empty.isPresent());
   }

   @Test
   public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
      String name = "baeldung";
      Optional<String> opt = Optional.of(name);
      assertTrue(opt.isPresent());
   }

   @Test
   public void givenNonNull_whenCreatesNullable_thenCorrect() {
      String name = "baeldung";
      Optional<String> optionalName = Optional.ofNullable(name);
      assertTrue(optionalName.isPresent());
   }
}
