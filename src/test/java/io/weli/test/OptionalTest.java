package io.weli.test;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jgroups.util.Util.assertTrue;
import static org.junit.Assert.assertEquals;
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

   @Test
   public void givenNull_whenCreatesNullable_thenCorrect() {
      String name = null;
      Optional<String> opt = Optional.ofNullable(name);
      assertFalse(opt.isPresent());
   }

   @Test
   public void givenOptional_whenIsPresentWorks_thenCorrect() {
      Optional<String> opt = Optional.of("Baeldung");
      assertTrue(opt.isPresent());

      opt = Optional.ofNullable(null);
      assertFalse(opt.isPresent());
   }

   @Test
   public void givenOptional_whenIfPresentWorks_thenCorrect() {
      Optional<String> opt = Optional.of("baeldung");
      opt.ifPresent(name -> System.out.println(name.length()));
   }

   @Test
   public void whenOrElseWorks_thenCorrect() {
      String nullName = null;
      String name = Optional.ofNullable(nullName).orElse("john");
      assertEquals("john", name);
   }


   @Test
   public void whenOrElseGetWorks_thenCorrect() {
      String nullName = null;
      String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
      assertEquals("john", name);
   }

   @Test(expected = IllegalArgumentException.class)
   public void whenOrElseThrowWorks_thenCorrect() {
      String nullName = null;
      Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
   }

   @Test
   public void givenOptional_whenGetsValue_thenCorrect() {
      Optional<String> opt = Optional.of("baeldung");
      String name = opt.get();
      assertEquals("baeldung", name);
   }

   @Test(expected = NoSuchElementException.class)
   public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
      Optional<String> opt = Optional.ofNullable(null);
      opt.get();
   }

   @Test
   public void whenOptionalFilterWorks_thenCorrect() {
      Integer year = 2016;
      Optional<Integer> yearOptional = Optional.of(year);
      boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
      assertTrue(is2016);
      boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
      assertFalse(is2017);
   }

   public boolean priceIsInRange1(Modem modem) {
      boolean isInRange = false;

      if (modem != null && modem.getPrice() != null
              && (modem.getPrice() >= 10
              && modem.getPrice() <= 15)) {

         isInRange = true;
      }
      return isInRange;
   }

   @Test
   public void whenFiltersWithoutOptional_thenCorrect() {
      assertTrue(priceIsInRange1(new Modem(10.0)));
      assertFalse(priceIsInRange1(new Modem(9.9)));
      assertFalse(priceIsInRange1(new Modem(null)));
      assertFalse(priceIsInRange1(new Modem(15.5)));
      assertFalse(priceIsInRange1(null));
   }

   public boolean priceIsInRange2(Modem modem2) {
      return Optional.ofNullable(modem2)
              .map(Modem::getPrice)
              .filter(p -> p >= 10)
              .filter(p -> p <= 15)
              .isPresent();
   }

   @Test
   public void whenFiltersWithOptional_thenCorrect() {
      assertTrue(priceIsInRange2(new Modem(10.0)));
      assertFalse(priceIsInRange2(new Modem(9.9)));
      assertFalse(priceIsInRange2(new Modem(null)));
      assertFalse(priceIsInRange2(new Modem(15.5)));
      assertFalse(priceIsInRange2(null));
   }

   public String getMyDefault() {
      System.out.println("Getting Default Value");
      return "Default Value";
   }

   @Test
   public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
      String text = "Text present";

      System.out.println("Using orElseGet:");
      String defaultText
              = Optional.ofNullable(text).orElseGet(this::getMyDefault);
      assertEquals("Text present", defaultText);

      System.out.println("Using orElse:");
      defaultText = Optional.ofNullable(text).orElse(getMyDefault());
      assertEquals("Text present", defaultText);
   }

   public class Modem {
      private Double price;

      public Modem(Double price) {
         this.price = price;
      }

      public Double getPrice() {
         return price;
      }

      public void setPrice(Double price) {
         this.price = price;
      }
   }

   private Optional<String> getEmpty() {
      return Optional.empty();
   }

   private Optional<String> getHello() {
      return Optional.of("hello");
   }

   private Optional<String> getBye() {
      return Optional.of("bye");
   }

   private Optional<String> createOptional(String input) {
      if (input == null || "".equals(input) || "empty".equals(input)) {
         return Optional.empty();
      }
      return Optional.of(input);
   }

   @Test
   public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
      Optional<String> found = Stream.of(getEmpty(), getHello(), getBye())
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst();

      assertEquals(getHello(), found);
   }

   @Test
   public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
      Optional<String> found =
              Stream.<Supplier<Optional<String>>>of(this::getEmpty, this::getHello, this::getBye)
                      .map(Supplier::get)
                      .filter(Optional::isPresent)
                      .map(Optional::get)
                      .findFirst();

      assertEquals(getHello(), found);
   }

   @Test
   public void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
      Optional<String> found = Stream.<Supplier<Optional<String>>>of(
              () -> createOptional("empty"),
              () -> createOptional("hello")
      )
              .map(Supplier::get)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst();

      assertEquals(createOptional("hello"), found);
   }


   @Test
   public void givenOptional_whenPresent_thenShouldExecuteProperCallback() {
      // given
      Optional<String> value = Optional.of("properValue");
      AtomicInteger successCounter = new AtomicInteger(0);
      AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

      // when
      value.ifPresentOrElse(
              v -> successCounter.incrementAndGet(),
              onEmptyOptionalCounter::incrementAndGet);

      // then
      assertThat(successCounter.get()).isEqualTo(1);
      assertThat(onEmptyOptionalCounter.get()).isEqualTo(0);
   }
}
